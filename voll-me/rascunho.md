## Sobre o Spring Boot

O Spring Boot simplifica a criação e configuração de aplicações, usando convenções em vez de configurações manuais, o que reduz o tempo de setup e aumenta a eficiência do desenvolvimento. Com ele, podemos facilmente criar APIs RESTful, configurar servidores embutidos, gerenciar dependências e realizar validação de dados.

### Funcionalidades principais do Spring Boot

- **Configuração Automática**: O Spring Boot cuida da maior parte das configurações iniciais de bibliotecas e componentes, como servidores e bancos de dados.
- **Criação de Endpoints REST com @RestController**: Facilita a criação de APIs REST sem necessidade de conversões manuais de dados.
- **Manejo Simplificado de Dependências**: Dependências necessárias para serviços REST são facilmente gerenciadas através de *starters*.
- **Validação e Tratamento de Erros**: Suporte integrado para validação de dados e tratamento de exceções.

## Estrutura do Projeto  

O projeto é composto por algumas classes principais, como `MedicoController`, `MedicoRepository`, `Medico` (entidade JPA), e `DadosCadastroMedico` (DTO para cadastro). Vamos explorar as principais:

### Controller `MedicoController`

Começamos definindo a classe MedicoController, que será responsável por lidar com as requisições relacionadas aos médicos. Veja como isso é estruturado:

```java
@RestController
@RequestMapping("/medicos")
public class MedicoController {
    
    @Autowired
    private MedicoRepository repository;
    ...implementação...
}
```

Aqui, utilizamos três elementos importantes:  

- **`@RestController`**: Indica que a classe é um controlador que expõe endpoints para uma API REST, permitindo que as respostas sejam retornadas diretamente no formato adequado (como JSON).  
- **`@RequestMapping`**: Define o caminho base `/medicos` para todas as rotas dessa classe, tornando o código mais organizado e intuitivo.  
- **Injeção de Dependência**: A anotação `@Autowired` é usada para que o Spring injete automaticamente uma instância de `MedicoRepository`, o repositório responsável por interagir com o banco de dados.

Com essa estrutura, podemos focar no desenvolvimento das funcionalidades, enquanto o framework cuida da configuração e injeção necessárias.

No método abaixo, utilizamos as anotações `@PostMapping` e `@Transactional`. A anotação `@PostMapping` define que essa rota irá lidar com requisições do tipo **POST**, enquanto `@Transactional` garante que a operação seja executada dentro de uma transação, preservando a integridade dos dados em caso de falhas. Também tratamos o retorno: retornamos status code 201, os dados do médico cadastrato e a URI do recurso criado.

```java
@PostMapping("")
@Transactional
public ResponseEntity<DadosDetalhamentoMedico> cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
    ...implementação...

    var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
    return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
}
```

Nos parâmetros do método, destacam-se outras duas anotações:  

- **`@RequestBody`**: Indica que o corpo da requisição será mapeado para o objeto `DadosCadastroMedico`.  
- **`@Valid`**: Assegura que os dados fornecidos sejam validados conforme as restrições definidas no modelo.

A próxima rota implementa a funcionalidade de listagem de usuários. Por padrão, ela retorna 10 resultados paginados e ordenados pelo campo `nome`.

```java
@GetMapping("")
public ResponseEntity<Page<DadosListagemMedico>>  buscarTodos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
    ...implementação...
    return ResponseEntity.ok(page);
}
```

Duas anotações novas são utilizadas:  

- **`@GetMapping`**: Define que essa rota responde a requisições do tipo **GET**.  
- **`@PageableDefault`**: Configura parâmetros padrão para paginação, como o número de resultados por página (`size = 10`) e o campo utilizado para ordenar os resultados (`sort = {"nome"}`).

Com a tecnologia se mostrando tão poderosa, mesmo em algo aparentemente simples como uma API REST, o próximo passo foi implementar a rota de atualização de um médico.

```java
@PutMapping("")
@Transactional
public ResponseEntity<DadosDetalhamentoMedico> atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
    ...implementação...

    return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
}
```

Aqui, introduzimos a anotação **`@PutMapping`**, que especifica que a rota responderá a requisições do tipo **PUT**. Mais uma vez, a anotação `@Transactional` é utilizada para garantir a consistência dos dados.

Implementamos a rota para exclusão de um médico:

```java
@DeleteMapping("/{id}")
@Transactional
public void excluir(@PathVariable Long id) {
    ...implementação...
    return ResponseEntity.noContent().build();
}
```

A anotação **`@DeleteMapping`** é apresentada, indicando que a rota aceitará requisições do tipo **DELETE**. O uso de `@Transactional` permanece essencial para evitar inconsistências durante a operação. Além disso, vemos a anotação **`@PathVariable`**, que vincula o valor do parâmetro `id` ao trecho correspondente no endpoint (`/{id}`). Essa rota retorna 204 quando um médico é excluído com sucesso.

Para terminar, criamos a rota para detalhar os médicos, não existe nada de novo nela.

```java
@GetMapping("/{id}")
public ResponseEntity<DadosDetalhamentoMedico> detalhar(@PathVariable Long id) {
    ...implementação...

    return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
}
```

Cada uma dessas anotações torna o código mais expressivo e facilita a construção de APIs RESTful, demonstrando a elegância e simplicidade do Spring Framework.

### Repository `MedicoRepository`

Uma classe *Repository* é responsável por representar a camada de acesso aos dados da aplicação. Ela abstrai a lógica de interação com o banco de dados, permitindo que operações como salvar, buscar, atualizar e excluir dados sejam realizadas de forma simples e padronizada. Essa abordagem reduz a complexidade e facilita a manutenção do código.

```java
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);
}
```

Como estamos utilizando o **Spring Data JPA**, ao estender a interface `JpaRepository`, herdamos automaticamente diversos métodos prontos para realizar operações básicas. Essa funcionalidade simplifica a manipulação de dados e elimina a necessidade de implementar manualmente essas operações.

Além disso, o Spring Data JPA segue uma convenção poderosa que permite criar métodos personalizados com base em padrões de nomenclatura. Por exemplo, o método `findAllByAtivoTrue` foi definido seguindo essa convenção e é automaticamente interpretado pelo framework.

### Dados de Entrada `DadosCadastroMedico`

Aqui temos uma classe `record` que define os dados necessários para o cadastro de um médico. O destaque desta classe são as anotações da **Bean Validation**, que garantem a integridade e a validação dos dados recebidos. Vamos detalhá-las:

```java
public record DadosCadastroMedico(
    @NotBlank(message = "{nome.obrigatorio}")
    String nome,

    @NotBlank(message = "{email.obrigatorio}")
    @Email(message = "{email.invalido}")
    String email,

    @NotBlank(message = "{telefone.obrigatorio}")
    String telefone,

    @NotBlank(message = "{crm.obrigatorio}")
    @Pattern(regexp = "\\d{4,6}", message = "{crm.invalido}")
    String crm,

    @NotNull(message = "{especialidade.obrigatorio}")
    Especialidade especialidade,

    @NotNull(message = "{endereco.obrigatorio}")
    @Valid
    DadosEndereco endereco
    
) {}
```

- **`@NotBlank`**: Garante que o campo não será nulo nem vazio. É usada nos campos `nome`, `email`, `telefone` e `crm`, assegurando que essas informações essenciais sejam fornecidas.  
- **`@Email`**: Aplica-se ao campo `email`, validando que o valor segue o formato padrão de um e-mail válido.  
- **`@Pattern`**: Valida o formato específico do campo `crm`. O padrão `\\d{4,6}` exige que ele contenha entre 4 e 6 dígitos.  
- **`@NotNull`**: Indica que os campos `especialidade` e `endereco` não podem ser nulos, garantindo que essas informações sejam obrigatoriamente fornecidas.  
- **`@Valid`**: Aplica-se ao campo `endereco` (que é outro objeto). Essa anotação indica que os atributos internos de `DadosEndereco` também devem ser validados de acordo com as regras definidas em sua própria classe.

Veja que, em cada validação, utilizamos o formato `message = "{crm.obrigatorio}"`. Isso é possível porque criamos o arquivo de propriedades **ValidationMessages** e definimos essas mensagens personalizadas nele.

### Entidade JPA `Medico`

Esta classe representa a entidade `Medico`, que é mapeada diretamente para uma tabela no banco de dados usando **JPA**. Além de encapsular os atributos e comportamentos de um médico, a classe utiliza diversas anotações para definir seu comportamento e mapeamento. Vamos entender cada uma delas:

```java
@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
    ...
}
```

- **`@Table`**: Define o nome da tabela no banco de dados como `medicos`. Isso permite personalizar o mapeamento entre a classe e a tabela.  
- **`@Entity`**: Indica que esta classe é uma entidade JPA, ou seja, será mapeada para uma tabela no banco de dados. O nome `Medico` é usado para consultas JPQL.  
- **`@Getter`**: Gera automaticamente métodos *getter* para todos os atributos da classe, facilitando o acesso aos dados.  
- **`@NoArgsConstructor`**: Cria um construtor sem argumentos, necessário para que o JPA possa instanciar a classe.  
- **`@AllArgsConstructor`**: Gera um construtor que aceita todos os atributos como parâmetros, útil para inicializações completas.  
- **`@EqualsAndHashCode`**: Gera automaticamente os métodos `equals` e `hashCode`. A propriedade `of = "id"` indica que essas operações se basearão no atributo `id`, garantindo comparações corretas.

#### Atributos da Classe

Dentro da classe, cada atributo é mapeado para uma coluna ou estrutura correspondente no banco de dados. Vamos analisar suas anotações:

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

- **`@Id`**: Define o atributo `id` como chave primária da tabela.
  
- **`@GeneratedValue`**: Configura a estratégia de geração automática da chave primária. O tipo `GenerationType.IDENTITY` delega essa geração ao banco de dados, comum para campos *auto_increment*.

```java
@Enumerated(EnumType.STRING)
private Especialidade especialidade;
```

- **`@Enumerated`**: Define como os valores do tipo `Especialidade` (um `enum`) serão armazenados no banco de dados. A estratégia `EnumType.STRING` salva os valores como texto, tornando-os mais legíveis e flexíveis.

```java
@Embedded
private Endereco endereco;
```

- **`@Embedded`**: Indica que o atributo `endereco` é uma entidade embutida, ou seja, seus campos serão mapeados diretamente como colunas na tabela `medicos`. Isso é útil para reutilizar classes menores como componentes.

### Configurações `TratadorErros`

O Spring Framework oferece ferramentas poderosas para lidar com exceções de forma centralizada e consistente, facilitando o desenvolvimento de APIs robustas e bem estruturadas. Neste exemplo, implementamos um tratamento de erros personalizado utilizando as anotações `@RestControllerAdvice` e `@ExceptionHandler`.

- `@RestControllerAdvice`: Centraliza a lógica de tratamento de erros, fazendo com que a classe anotada com ela intercepte as exceções lançadas pela controller REST.
- `@ExceptionHandler`: Especifica o método que irá lidar com exceções específicas.

```java
@RestControllerAdvice
public class TratadorErros {
    
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Void> tratarEntityNotFoundException() {
      return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<DadosErroValidacao>> tratarMethodArgumentNoValidExcpetion(MethodArgumentNotValidException exception) {
      var erros = exception.getFieldErrors();
      
      return ResponseEntity.badRequest().body(
          erros.stream()
              .map(DadosErroValidacao::new)
              .toList()
      );
  }

  private record DadosErroValidacao(
      String campo, 
      String mensagem
  ) {
      public DadosErroValidacao(FieldError error) {
          this(
              error.getField(),
              error.getDefaultMessage()
          );
      }
  }
}
```

### Configurações `SecurityConfigurations`

Para adicionar segurança à API com o Spring, precisamos configurar as classes de segurança apropriadas. A principal anotação utilizada para habilitar a segurança é `@EnableWebSecurity`. Esta anotação ativa o suporte a segurança baseada em web, permitindo a personalização da configuração de segurança.

**`SecurityFilterChain securityFilterChain(HttpSecurity http)`**  
Este método define a configuração básica do fluxo de segurança da aplicação. O HttpSecurity permite configurar diversas opções de segurança, como políticas de autenticação, autorização, segurança de formulários e proteção contra CSRF (Cross-Site Request Forgery).

**`AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)`**  
Este método cria o `AuthenticationManager`, que é responsável por gerenciar o processo de autenticação de usuários. Ele utiliza o `AuthenticationConfiguration` para configurar o gerenciador.

O `AuthenticationManager` pode ser personalizado para incluir mecanismos de autenticação como autenticação por usuário e senha ou autenticação baseada em token.

**`PasswordEncoder passwordEncoder()`**  
O `PasswordEncoder` é responsável por criptografar ou decifrar senhas antes de serem armazenadas ou verificadas. Ele protege informações sensíveis e assegura a integridade da segurança de login.

```java
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

  @Autowired
  private SecurityFilter securityFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
      return http.csrf(csrf -> csrf.disable())
          .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(auth -> {
              auth.requestMatchers("/login").permitAll();
              auth.anyRequest().authenticated();
          })
          .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
          .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
      return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
  }
}
```

Além da configuração de segurança utilizando `@EnableWebSecurity`, nossa entidade JPA que representa o usuário precisa implementar a interface `UserDetails`. Este é um passo essencial para garantir que o Spring Security saiba como autenticar e autorizar os usuários adequadamente. O método mais relevante nesta implementação é o `public Collection<? extends GrantedAuthority> getAuthorities()`, que define o nível de acesso que o usuário terá.

```java
public class Usuario implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Override
    public String getPassword() {
        return senha;
    }
    @Override
    public String getUsername() {
        return login;
    }
}
```

### Filtros `SecurityFilter`

No Spring Boot, um filtro é um componente que intercepta requisições HTTP antes que elas alcancem o controlador (controller). Ele pode ser usado para tarefas como autenticação, autorização, logging, ou outras validações específicas. A interface `OncePerRequestFilter` é particularmente útil, pois garante que o filtro seja executado apenas uma vez por requisição, independentemente de quantas vezes a requisição passe por diferentes componentes.

A classe SecurityFilter implementa o método `doFilterInternal`, que é chamado para processar cada requisição.

**`FilterChain`**  
A `FilterChain` é responsável por passar a requisição para o próximo filtro na cadeia de filtros.

Em nosso caso, `filterChain.doFilter(request, response)` garante que, após o processamento do filtro de autenticação, a requisição prossiga para outros filtros ou chegue ao controlador.

**`UsernamePasswordAuthenticationToken`**  
É uma implementação de `Authentication` usada para representar as credenciais de autenticação (usuário e senha).

- O usuário autenticado é definido no token.
- As credenciais são null porque não é necessário fornecer a senha após a autenticação.
- As permissões (roles) do usuário são adicionadas através do método usuario.getAuthorities().

**`SecurityContextHolder`**
É o componente central do Spring Security responsável por armazenar informações de segurança do contexto atual.

Quando `SecurityContextHolder.getContext().setAuthentication(authentication)` é chamado, ele registra o usuário autenticado, tornando-o acessível durante toda a requisição.

```java
@Component
public class SecurityFilter extends OncePerRequestFilter{

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
          throws ServletException, IOException {
      
    var tokenJwt = obterToken(request);

    if (tokenJwt != null) {
      var subject = tokenService.obterSubject(tokenJwt);
      var usuario = usuarioRepository.findByLogin(subject);

      var authentication = new UsernamePasswordAuthenticationToken(
        usuario, 
        null, 
        usuario.getAuthorities()
      );
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
    filterChain.doFilter(request, response);
  }

  private String obterToken(HttpServletRequest request) {
    var authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null) {
      return authorizationHeader.replace("Bearer ", "");
    }
    return null;
  }
}
```

**Benefícios de Usar Filtros**  

- **Centralização:** Permite realizar a autenticação e autorização antes que a lógica do controlador seja executada.
- **Segurança:** Impede que requisições não autenticadas ou mal-intencionadas alcancem os endpoints protegidos.
- **Flexibilidade:** Filtros podem ser configurados para aplicar validações específicas de acordo com a necessidade.

## Migração do Banco de Dados

As *migrations* são um conceito fascinante que permite gerenciar e versionar alterações no banco de dados de forma automatizada e confiável. Utilizando o **Flyway**, podemos criar arquivos que descrevem alterações incrementais no esquema do banco, garantindo que a estrutura do banco esteja sempre alinhada com o código da aplicação.

### Estrutura de Migrations

No diretório `resources/db/migrations`, criamos arquivos nomeados seguindo um padrão como `V1__description`, onde o número define a ordem de execução, e a descrição ajuda a identificar a alteração.

1. **`V1__create-table-medicos.sql`**:  
   Cria a tabela principal `medicos`, definindo todas as colunas básicas necessárias, como `nome`, `email`, `crm`, entre outros.

   ```sql
   CREATE TABLE medicos (
       id            INTEGER       PRIMARY KEY AUTOINCREMENT,
       nome          VARCHAR(100)  NOT NULL,
       email         VARCHAR(100)  NOT NULL UNIQUE,
       crm           VARCHAR(6)    NOT NULL UNIQUE,
       especialidade VARCHAR(100)  NOT NULL,
       logradouro    VARCHAR(100)  NOT NULL,
       bairro        VARCHAR(100)  NOT NULL,
       cep           VARCHAR(9)    NOT NULL,
       complemento   VARCHAR(100)  DEFAULT NULL,
       numero        VARCHAR(20)   DEFAULT NULL,
       uf            CHAR(2)       NOT NULL,
       cidade        VARCHAR(100)  NOT NULL
   );
   ```

   Aqui, estabelecemos não apenas os campos, mas também as restrições, como chaves primárias e unicidade.

2. **`V2__alter-table-medicos-add-column-telefone.sql`**:  
   Adiciona a coluna `telefone` à tabela `medicos`, permitindo armazenar os números de contato.

   ```sql
   ALTER TABLE medicos ADD COLUMN telefone VARCHAR(20) NOT NULL;
   ```

   Essa alteração expande a funcionalidade sem quebrar o banco de dados existente.

3. **`V3__alter-table-medicos-add-column-ativo.sql`**:  
   Introduz a coluna `ativo` para controlar se um médico está ativo ou inativo, utilizando exclusão lógica.

   ```sql
   ALTER TABLE medicos ADD COLUMN ativo TINYINT DEFAULT 0;
   UPDATE medicos SET ativo = 1;
   ```

   A coluna é adicionada com um valor padrão de `0` (inativo), e o script também inicializa todos os registros existentes como ativos (`ativo = 1`).

4. **`V4__create-table-usuarios.sql`**:
  Cria a tabela de usuários.

  ```sql
  CREATE TABLE usuarios(
      id    INTEGER      PRIMARY KEY AUTOINCREMENT,
      login VARCHAR(100) NOT NULL UNIQUE,
      senha VARCHAR(255) NOT NULL
  );

  INSERT INTO usuarios(login, senha) VALUES('rodrigo.jesus@voll.med', '$2a$10$/mbdRFp53evVENH32rxXEewML5J1aUTVvrBuaJ6/oFRfX/3bLHZWW')
  ```

### Como Funcionam as Migrations

As *migrations* são executadas automaticamente sempre que a aplicação é iniciada. O Flyway verifica o histórico de migrações aplicadas, armazenado em uma tabela especial (`flyway_schema_history`), e executa apenas os arquivos novos, garantindo que o banco de dados evolua sem reprocessar alterações já aplicadas.

### Benefícios

- **Controle de versão**: Cada alteração no banco é rastreável, permitindo revisões e reversões se necessário.  
- **Automatização**: A aplicação sempre inicializa com a estrutura de banco correta, evitando erros manuais.  
- **Colaboração**: Em equipes, todos os desenvolvedores compartilham a mesma estrutura de banco ao sincronizar o código.

## Autenticação com JWT

A geração de tokens JWT (JSON Web Token) é uma prática comum para autenticação e autorização em APIs modernas. Aqui, iremos detalhar como a classe `TokenService` cria tokens usando o algoritmo **HMAC256** com o uso seguro da chave secreta, bem como a integração com os atributos necessários para gerar um token robusto e seguro.

A autenticação usando JWT envolve a criação de um token com informações como o **issuer**, **subject**, **claims** e o **expiresAt**. O algoritmo utilizado para assinatura do token é **HMAC256**, que oferece segurança adequada ao envolver uma chave secreta para garantir a integridade das informações.

**@Value("${api.security.token.secret}"):**  
A anotação @Value é utilizada para recuperar a chave secreta armazenada no arquivo de configurações. A chave secreta é essencial para a assinatura do token e precisa ser protegida para evitar vazamentos de informações sensíveis.

**Algoritmo HMAC256:**  
O HMAC (Hash-based Message Authentication Code) é um algoritmo usado para garantir a integridade e autenticidade das informações no token. O HMAC256 é uma versão robusta que utiliza um hash SHA-256 para garantir a segurança do token.

**JWT.create():**  
A partir do JWT Builder, são definidos diversos atributos essenciais. O **issuer** é o emissor do token, enquanto o **subject** é o usuário associado ao token. No exemplo, há também o uso do **withClaim** para incluir informações adicionais relacionadas ao usuário.

**dataExpiracao():**  
A função `dataExpiracao` define o tempo de expiração do token, que é configurado para 2 horas após a criação.

**Tratamento de Erros:**  
Um `JWTCreationException` é lançado caso ocorra algum erro na geração do token, garantindo que o serviço tratado evita exceções não controladas.

```java
@Service
public class TokenService {

  @Value("{api.security.token.secret}")
  private String secret;

  public String gerarToken(Usuario usuario) {
    try {
      var algoritmo = Algorithm.HMAC256(secret);
      return JWT.create()
        .withIssuer("API Voll.med")
        .withSubject(usuario.getLogin())
        .withClaim("nomeUsuario", usuario.getUsername())
        .withExpiresAt(dataExpiracao())
        .sign(algoritmo);
    } catch (JWTCreationException e) {
      throw new RuntimeException("Erro ao gerar token jwt", e);
    }
  }

  private Instant dataExpiracao() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}
```

### Exemplos de Requisições

- **Criar um médico (POST)**:  

   ```http
   POST http://localhost:8080/medicos
   Content-Type: application/json

   {
       "nome": "Rodrigo",
       "email": "rodrigo@voll.med",
       "crm": "123456",
       "especialidade": "ORTOPEDIA",
       "telefone": "11999999999",
       "endereco": {
           "logradouro": "rua 1",
           "bairro": "bairro",
           "cep": "12345678",
           "cidade": "Brasilia",
           "uf": "DF",
           "numero": "1",
           "complemento": "complemento"
       }
   }
   ```

- **Consultar médicos (GET)**:  

   ```http
   GET http://localhost:8080/medicos
   ```

- **Atualizar um médico (PUT)**:  

   ```http
   PUT http://localhost:8080/medicos
   Content-Type: application/json

   {
       "id": 1,
       "nome": "Rodrigo Gonçalves",
       "telefone": "11888888888"
   }
   ```

- **Deletar um médico (DELETE)**:  

   ```http
   DELETE http://localhost:8080/medicos/1
   ```