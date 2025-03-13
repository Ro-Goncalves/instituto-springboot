# Um Pouco Sobre a Resiliência - Retry e Recover

Serviços de terceiros podem falhar devido a picos de demanda. Como consumidores, devemos antecipar essas falhas e implementar mecanismos de resiliência. O Spring Boot simplifica esse processo.

>⚠️ Importante: Nem todos os erros justificam retentativas. Avalie cada caso para evitar loops desnecessários.

## ⚙️ Configurando Retry e Recover

### ⚙️ `@EnableRetry` em Ação

Para habilitar o mecanismo de Retry no Spring, precisamos adicionar a anotação `@EnableRetry` na classe principal da aplicação.

📌 `@SpringBootApplication`  
Marca essa classe como o ponto de entrada principal da aplicação Spring Boot.  
Combina três anotações principais do Spring Boot:
- **`@Configuration`**: Define que essa classe pode conter configurações de beans.
- **`@EnableAutoConfiguration`**: Permite que o Spring Boot configure automaticamente os componentes da aplicação com base nas dependências disponíveis no classpath.
- **`@ComponentScan`**: Habilita a busca automática de componentes (`@Component`, `@Service`, `@Repository`, `@Controller`) dentro do mesmo pacote e seus subpacotes.

Isso significa que essa classe inicializa todo o contexto da aplicação, carregando configurações e beans automaticamente.

🔄 `@EnableRetry`  
Habilita o suporte ao mecanismo de retry no Spring.
- Permite que métodos anotados com `@Retryable` tenham suas execuções repetidas automaticamente em caso de falha ➡️.
- Funciona em conjunto com `@Recover`, que define um método de fallback caso todas as tentativas falhem 🆘.
- Como essa anotação está na classe principal da aplicação, o suporte a retry fica disponível globalmente para todos os beans gerenciados pelo Spring.

```java	
@SpringBootApplication
@EnableRetry
public class BrianbotApplication {

	public static void main(String[] args) {
        //--> SpringApplication.run(...) inicializa o contexto do Spring, carregando todas as configurações e componentes.
		SpringApplication.run(BrianbotApplication.class, args);
	}
}
```

### 🔁 `@Retryable` em Ação	

Uma vez que o **Mago** está no jogo, realizar a mágica fica relativamente simples: uma anotação, três parâmetros e... BOMM!! 🎇 Toda vez que a classe lançar a exception esperada, o método tentará novamente.

🔄`@Retryable(maxAttempts = 3, backoff = @Backoff(delay = 5000), value = { AdvisorClientException.class })`  
Define que esse método pode ser reexecutado automaticamente em caso de falha.
- Se o método lançar uma exceção do tipo `AdvisorClientException`, o Spring tentará executá-lo novamente até atingir o número máximo de tentativas.
- Se todas as tentativas falharem, a exceção será propagada normalmente.

🛠️ Quebrando os parâmetros  

- `maxAttempts = 3`
    - Define o número máximo de tentativas antes de desistir.
    - Neste caso, o método será executado **até 3 vezes** antes de lançar a exceção definitiva.
- `backoff = @Backoff(delay = 5000)`
    - Controla o tempo de espera entre as tentativas.
    - O parâmetro `delay = 5000` significa que o Spring esperará **5 segundos** antes de tentar novamente.
    - Isso evita que o sistema sobrecarregue o serviço externo com chamadas consecutivas.
- `value = { AdvisorClientException.class }`
    - Especifica quais exceções devem acionar a reexecução do método.
    - Aqui, somente exceções do tipo `AdvisorClientException` causarão o retry.
    - Exceções diferentes não acionarão novas tentativas e serão lançadas imediatamente.

```java
@Retryable(maxAttempts = 3, backoff = @Backoff(delay = 5000), value = {
            AdvisorClientException.class })
    public ResponsePrevisaoUmidade obterPrevisao() {
        try {
            var resultado = advisorRequests.executar(urlPrevisaoUmidade, ResponsePrevisaoUmidade.class);
            return resultado;
        } catch (Exception e) {
            throw e;
        }
    }
```
### 🩹 `@Recover` em Ação

Quando todas as tentativas de retry falham, **é esperado que algo seja feito** - e é aqui que entra o @Recover.

A boa notícia? É simples! 🎩✨ Assim como o @Retryable, o método de recuperação deve ter o mesmo tipo de retorno do que falhou, e seu primeiro parâmetro deve ser a exceção lançada por ele.

🛠️ `@Recover`  
Define um método de fallback que será executado quando todas as tentativas do `@Retryable` falharem.

- Esse método só será chamado se o número máximo de tentativas definido em `@Retryable` for atingido sem sucesso.
- Ele recebe como parâmetro a mesma exceção que acionou o retry (`AdvisorClientException`), permitindo que a aplicação lide com a falha de maneira controlada.

```java	
@Recover
private ResponsePrevisaoUmidade recuperarFalhaObterPrevisao(AdvisorClientException e) {
    log.error("Todas as tentativas de obter previsão da umidade falharam após 3 tentativas", e);
    return new ResponsePrevisaoUmidade();
}
```

## 🛠️ Testando Retry e Recover

### ⚙️ Configuração

Antes de mais nada, precisamos criar uma **classe de configuração** e ativar o mecanismo de retry nela.

 - `@Configuration`: Indica que a classe é uma configuração do Spring e pode fornecer beans ao contexto da aplicação.
    - Permite que o Spring reconheça e processe a classe como parte da configuração da aplicação.
    - Todos os métodos anotados com `@Bean` dentro dessa classe serão gerenciados pelo Spring como componentes da aplicação.
 - `@EnableRetry`: Já falamos sobre isso. Você já pegou a ideia. 😎
 - `@Bean`: Define um bean gerenciado pelo Spring, ou seja, esse objeto será criado e injetado automaticamente em outras partes da aplicação quando necessário.
    - O método anotado será chamado pelo Spring para instanciar e fornecer um objeto sempre que necessário.
    - Permite personalizar a criação de instâncias de classes que não podem ser anotadas diretamente com `@Component`, como classes de terceiros.

O método dessa classe cria uma instância de AdvisorPrevisaoUmidade - é ela que queremos testar! 🔍✨

```java
@Configuration
@EnableRetry
class AdvisorRetryTestConfig {

    @Bean
    public AdvisorPrevisaoUmidade AdvisorPrevisaoUmidade(AdvisorClient advisorClient) {
        return new AdvisorPrevisaoUmidade("http://teste.url", advisorClient);
    }
}
```

### 🧪 Criando Classe para Testes

Com a **classe de configuração** em mãos, agora precisamos dizer ao **JUnit** que ele deve usá-la e que este é um **teste do Spring**.

- `@SpringBootTest`: Indica que esta é uma classe de teste de integração do Spring Boot.
    - Carrega todo o contexto da aplicação, permitindo testar beans gerenciados pelo Spring, como se fosse um ambiente real.
- `@ContextConfiguration`: Define explicitamente que o contexto de teste usará a configuração `AdvisorRetryTestConfig`.
    - O Spring usará essa configuração para instanciar os beans necessários para o teste.
    - Isso garante que o bean `AdvisorPrevisaoUmidade` será criado corretamente, usando a lógica definida na classe de configuração.
- `@MockitoBean`: Cria um mock da classe `AdvisorClient`, que será usado nos testes.
    - Em vez de usar um AdvisorClient real, o Spring injetará um mock gerenciado pelo Mockito.
    - Isso permite controlar o comportamento do AdvisorClient nos testes, simulando respostas e evitando chamadas externas.
- `@Autowired`: Injeta automaticamente o bean `AdvisorPrevisaoUmidade` criado na configuração `AdvisorRetryTestConfig`.
    - Como `AdvisorRetryTestConfig` define esse bean, o Spring cria e injeta a instância configurada.
    - A instância injetada usará o mock de `AdvisorClient`, permitindo testar seu comportamento sem acessar serviços externos.

```java	
@SpringBootTest
@ContextConfiguration(classes = { AdvisorRetryTestConfig.class })
public class AdvisorPrevisaoUmidadeTest {

    @MockitoBean
    private AdvisorClient advisorClient;

    @Autowired
    private AdvisorPrevisaoUmidade advisorPrevisaoUmidade;

    ---
}
```

### 🧪 Criando Teste do Retry

```java	
@Test
@DisplayName("Quando AdvisorClient lançar AdvisorClientException, deve tentar 3 vezes")
public void deveExecutarRetry() throws Exception {
    // Given
    var advisorClientException = new AdvisorClientException(
            "Erro na comunicação com o Advisor",
            new Exception("Erro"));

    when(advisorClient.executar(Mockito.anyString(), Mockito.any()))
            .thenThrow(advisorClientException);

    // When
    advisorPrevisaoUmidade.obterPrevisao();

    // Then
    verify(advisorClient, times(3)).executar(Mockito.anyString(), Mockito.any());
}
```

### 🛠️ Criando Teste do Recover

```java
@Test
@DisplayName("Quando falhar o retry, deve executar o recover")
public void deveExecutarRecover() throws Exception {
    // Given
    var advisorClientException = new AdvisorClientException(
            "Erro na comunicação com o Advisor",
            new Exception("Erro"));

    when(advisorClient.executar(Mockito.anyString(), Mockito.any()))
            .thenThrow(advisorClientException);

    // When
    var resposta = advisorPrevisaoUmidade.obterPrevisao();

    // Then
    assertThat(resposta).isNotNull();
}
```