# Spring Boot - API VOLL MED  <!-- omit in toc -->

Este projeto faz parte dos meus estudos em Spring Boot, uma introdução à formação Java e Spring Boot da Alura. A API foi criada com base no curso *Spring Boot 3: desenvolva uma API Rest em Java*.

[Curso Alura](https://cursos.alura.com.br/course/spring-boot-3-desenvolva-api-rest-java)

- [Objetivo](#objetivo)
  - [Dependências principais do projeto](#dependências-principais-do-projeto)
- [Tecnologias e Ferramentas Utilizadas](#tecnologias-e-ferramentas-utilizadas)
- [Sobre o Spring Boot](#sobre-o-spring-boot)
  - [Funcionalidades principais do Spring Boot](#funcionalidades-principais-do-spring-boot)
- [Estrutura do Projeto](#estrutura-do-projeto)
  - [Controller `MedicoController`](#controller-medicocontroller)
  - [Repository `MedicoRepository`](#repository-medicorepository)
  - [Dados de Entrada `DadosCadastroMedico`](#dados-de-entrada-dadoscadastromedico)
  - [Entidade JPA `Medico`](#entidade-jpa-medico)
    - [Atributos da Classe](#atributos-da-classe)
- [Migração do Banco de Dados](#migração-do-banco-de-dados)
  - [Estrutura de Migrations](#estrutura-de-migrations)
  - [Como Funcionam as Migrations](#como-funcionam-as-migrations)
  - [Benefícios](#benefícios)
- [Executando o Projeto](#executando-o-projeto)
  - [Extensões Instaladas](#extensões-instaladas)
  - [Como Rodar o Projeto](#como-rodar-o-projeto)
  - [Exemplos de Requisições](#exemplos-de-requisições)
- [Execute Você Mesmo](#execute-você-mesmo)
  - [Pré-requisitos](#pré-requisitos)
  - [Passo 1: Clone o Repositório](#passo-1-clone-o-repositório)
  - [Passo 2: Configure o Ambiente](#passo-2-configure-o-ambiente)
  - [Passo 3: Inicie o Servidor](#passo-3-inicie-o-servidor)
  - [Passo 4: Teste a Aplicação](#passo-4-teste-a-aplicação)
- [Resultado](#resultado)
  - [Adicionando um Novo Médico](#adicionando-um-novo-médico)
  - [Consultado todos os Médicos](#consultado-todos-os-médicos)
  - [Atualizando um Médico](#atualizando-um-médico)
  - [Excluindo um Médico](#excluindo-um-médico)
- [Próximos Passos](#próximos-passos)

## Objetivo  

O projeto tem como objetivo apresentar uma introdução ao Spring Boot, criando um CRUD (Create, Read, Update, Delete) de médicos. Ele visa ensinar os conceitos fundamentais sobre a criação de APIs REST, uso de anotações do Spring Boot, integração com banco de dados utilizando JpaRepository, além de padrões de projeto como Bean Validation e injeção de dependência.

### Dependências principais do projeto

- **SQLite JDBC Driver**  
  Conecta e interage com bancos de dados SQLite.
  
- **Hibernate Community Dialects**  
  Dialetos adicionais do Hibernate para suportar o SQLite.

- **Spring Boot Starter Web**  
  Facilita o desenvolvimento de aplicações web com Spring Boot e configura automaticamente um servidor embutido (Tomcat/Jetty).

- **Spring Boot Starter Data JPA**  
  Integração com JPA (Java Persistence API) para interações com banco de dados de forma simplificada.

- **Spring Boot Starter Validation**  
  Suporte para validação de dados utilizando Bean Validation.

- **Flyway Core**  
  Ferramenta de migração de banco de dados para gerenciar alterações no schema.

- **Lombok**  
  Elimina a escrita de código boilerplate, como getters, setters e construtores.

- **Spring Boot DevTools**  
  Melhora a produtividade com reinicialização automática do servidor durante o desenvolvimento.

## Tecnologias e Ferramentas Utilizadas

- **Java 21**
- **Spring Boot 3**
- **SQLite**
- **Lombok**
- **Flyway**
- **Hibernate**
- **Spring Data JPA**

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

No método abaixo, utilizamos as anotações `@PostMapping` e `@Transactional`. A anotação `@PostMapping` define que essa rota irá lidar com requisições do tipo **POST**, enquanto `@Transactional` garante que a operação seja executada dentro de uma transação, preservando a integridade dos dados em caso de falhas.

```java
@PostMapping("")
@Transactional
public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
    ...implementação...
}
```

Nos parâmetros do método, destacam-se outras duas anotações:  

- **`@RequestBody`**: Indica que o corpo da requisição será mapeado para o objeto `DadosCadastroMedico`.  
- **`@Valid`**: Assegura que os dados fornecidos sejam validados conforme as restrições definidas no modelo.

Agora utilizamos duas anotações a `@PostMapping` e `@Transactional` para indicar que essa rota irá receber uma requisição POST e que ela deve ser executada dentro de uma transação para garantir integridade dos dados. Note que no paramtros do método existem outras duas anotatações `@RequestBody` e `@Valid` que indicam que a requisição deve ser lida como um corpo de requisição e que os dados devem ser validados com a anotação `@Valid`.

A próxima rota implementa a funcionalidade de listagem de usuários. Por padrão, ela retorna 10 resultados paginados e ordenados pelo campo `nome`.

```java
@GetMapping("")
public Page<DadosListagemMedico> buscarTodos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
    ...implementação...
}
```

Duas anotações novas são utilizadas:  

- **`@GetMapping`**: Define que essa rota responde a requisições do tipo **GET**.  
- **`@PageableDefault`**: Configura parâmetros padrão para paginação, como o número de resultados por página (`size = 10`) e o campo utilizado para ordenar os resultados (`sort = {"nome"}`).

Com a tecnologia se mostrando tão poderosa, mesmo em algo aparentemente simples como uma API REST, o próximo passo foi implementar a rota de atualização de um médico.

```java
@PutMapping("")
@Transactional
public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
    ...implementação...
}
```

Aqui, introduzimos a anotação **`@PutMapping`**, que especifica que a rota responderá a requisições do tipo **PUT**. Mais uma vez, a anotação `@Transactional` é utilizada para garantir a consistência dos dados.

Finalmente, implementamos a rota para exclusão de um médico:

```java
@DeleteMapping("/{id}")
@Transactional
public void excluir(@PathVariable Long id) {
    ...implementação...
}
```

A anotação **`@DeleteMapping`** é apresentada, indicando que a rota aceitará requisições do tipo **DELETE**. O uso de `@Transactional` permanece essencial para evitar inconsistências durante a operação. Além disso, vemos a anotação **`@PathVariable`**, que vincula o valor do parâmetro `id` ao trecho correspondente no endpoint (`/{id}`).

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
    @NotBlank(message = "O nome do médico é obrigatório")
    String nome,

    @NotBlank(message = "O email do médico é obrigatório")
    @Email(message = "O email do médico é inválido")
    String email,

    @NotBlank(message = "O telefone do médico é obrigatório")
    String telefone,

    @NotBlank(message = "O crm do médico é obrigatório")
    @Pattern(regexp = "\\d{4,6}", message = "O crm do médico deve conter 6 dígitos")
    String crm,

    @NotNull
    Especialidade especialidade,

    @NotNull
    @Valid
    DadosEndereco endereco
    
) {}
```

- **`@NotBlank`**: Garante que o campo não será nulo nem vazio. É usada nos campos `nome`, `email`, `telefone` e `crm`, assegurando que essas informações essenciais sejam fornecidas.  
- **`@Email`**: Aplica-se ao campo `email`, validando que o valor segue o formato padrão de um e-mail válido.  
- **`@Pattern`**: Valida o formato específico do campo `crm`. O padrão `\\d{4,6}` exige que ele contenha entre 4 e 6 dígitos.  
- **`@NotNull`**: Indica que os campos `especialidade` e `endereco` não podem ser nulos, garantindo que essas informações sejam obrigatoriamente fornecidas.  
- **`@Valid`**: Aplica-se ao campo `endereco` (que é outro objeto). Essa anotação indica que os atributos internos de `DadosEndereco` também devem ser validados de acordo com as regras definidas em sua própria classe.

Essas anotações facilitam a validação automática dos dados no momento em que são recebidos pela aplicação, centralizando a lógica de validação e reduzindo erros no processamento.

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

### Como Funcionam as Migrations

As *migrations* são executadas automaticamente sempre que a aplicação é iniciada. O Flyway verifica o histórico de migrações aplicadas, armazenado em uma tabela especial (`flyway_schema_history`), e executa apenas os arquivos novos, garantindo que o banco de dados evolua sem reprocessar alterações já aplicadas.

### Benefícios

- **Controle de versão**: Cada alteração no banco é rastreável, permitindo revisões e reversões se necessário.  
- **Automatização**: A aplicação sempre inicializa com a estrutura de banco correta, evitando erros manuais.  
- **Colaboração**: Em equipes, todos os desenvolvedores compartilham a mesma estrutura de banco ao sincronizar o código.

## Executando o Projeto

O processo de execução do projeto é direto, mas alguns passos importantes e ferramentas tornam tudo mais simples e eficiente. Estou usando o **Visual Studio Code (VS Code)** para desenvolver e rodar o projeto, complementado com algumas extensões que facilitam bastante o trabalho. Vamos dar uma olhada:

### Extensões Instaladas

1. **[Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)**:  
   Permite desenvolver projetos em Java no VS Code, fornecendo suporte a edição, depuração e construção.  

2. **[Spring Boot Extension](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-spring-boot-dashboard)**:  
   Adiciona o *Spring Boot Dashboard*, que permite gerenciar e executar aplicações Spring diretamente pela interface do editor.  

3. **[REST Client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client)**:  
   Uma extensão poderosa para fazer chamadas HTTP diretamente do VS Code, útil para testar endpoints da API sem precisar de ferramentas externas como Postman ou cURL.

### Como Rodar o Projeto

1. **Inicialização do Servidor**:  
   - Abra o painel do *Spring Boot Dashboard* na aba lateral do VS Code.  
   - Localize sua aplicação (neste caso, `api`).  
   - Clique no botão **Run** ao lado do nome do projeto para iniciar o servidor.  

2. **Testando os Endpoints**:  
   - No arquivo `voll_med_api.http`, você encontra exemplos de chamadas HTTP prontas para testar.  
   - Cada bloco representa uma requisição (POST, GET, PUT, DELETE). Clique em **Send Request** na linha correspondente para enviar a requisição ao servidor.

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

**Nota técnica**: Executei este projeto em um **Windows 11** utilizando o **WSL** com o **Ubuntu** como sistema operacional.

## Execute Você Mesmo  

Siga os passos abaixo para instalar, configurar e executar o projeto:  

### Pré-requisitos  

Antes de começar, certifique-se de ter os seguintes itens instalados e configurados no seu ambiente:  

- **Java 21**: Para rodar o projeto com suporte a recursos modernos da linguagem.  
- **Maven**: Para gerenciar as dependências e construir o projeto.  
- **WSL 2**: Caso esteja utilizando Windows, o **Windows Subsystem for Linux (WSL)** garante um ambiente Linux dentro do sistema operacional.

### Passo 1: Clone o Repositório  

O primeiro passo é clonar o repositório do projeto e navegar até o diretório principal.

```bash  
git clone https://github.com/Ro-Goncalves/api.git
cd api
```  

### Passo 2: Configure o Ambiente  

Use o Maven para baixar e configurar todas as dependências necessárias.  

```bash  
mvn clean install
```

### Passo 3: Inicie o Servidor

Com tudo configurado, você pode iniciar o servidor usando o Maven:  

```bash  
mvn spring-boot:run  
```  

Se tudo estiver correto, o servidor será iniciado na porta padrão **8080**.

### Passo 4: Teste a Aplicação  

1. **Acesse a API**:  
   Abra o navegador e navegue até [http://localhost:8080](http://localhost:8080).  

2. **Faça Requisições HTTP**:  
   Utilize a extensão **REST Client** no VS Code ou qualquer ferramenta de sua preferência (como Postman) para interagir com os endpoints. Se estiver usando a exstensão, acesse o arquivo `src/main/resources/voll_med_api.http`.

## Resultado  

### Adicionando um Novo Médico

```bash
#Request
POST http://localhost:8080/medicos
Content-Type: application/json

{
    "nome": "Rodrigo da Silva Sauro",
    "email": "rodrigo.sauro@voll.med",
    "crm": "987654",
    "especialidade": "GINECOLOGIA",
    "telefone": "2512349876",
    "endereco": {
        "logradouro": "Rua Ruada",
        "bairro": "Bairro Bairrado",
        "cep": "87654321",
        "cidade": "Cidade Cidadada",
        "uf": "SC",
        "numero": "69",
        "complemento": "Complemento Complementado"
        }
}

#Response
HTTP/1.1 200 
Content-Length: 0
Date: Sat, 30 Nov 2024 21:44:20 GMT
Connection: close
```

### Consultado todos os Médicos

```bash
#Request
GET http://localhost:8080/medicos

#Response
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sat, 30 Nov 2024 21:45:24 GMT
Connection: close

{
  "content": [
    {
      "id": 1,
      "nome": "Rodrigo Gonçalves",
      "email": "rodrigo.ferreira@voll.med",
      "crm": "123456",
      "especialidade": "ORTOPEDIA"
    },
    {
      "id": 2,
      "nome": "Rodrigo da Silva Sauro",
      "email": "rodrigo.sauro@voll.med",
      "crm": "987654",
      "especialidade": "GINECOLOGIA"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 2,
  "last": true,
  "first": true,
  "size": 10,
  "number": 0,
  "sort": {
    "sorted": true,
    "unsorted": false,
    "empty": false
  },
  "numberOfElements": 2,
  "empty": false
}
```

### Atualizando um Médico

```bash
#Request
PUT http://localhost:8080/medicos
Content-Type: application/json

{
    "id": 1,
    "nome": "Rodrigo Machado de Assis",
    "telefone": "9685213258"
}

#Response
HTTP/1.1 200 
Content-Length: 0
Date: Sat, 30 Nov 2024 21:47:27 GMT
Connection: close
```

### Excluindo um Médico

```bash
#Request
DELETE http://localhost:8080/medicos/1

#Response
HTTP/1.1 200 
Content-Length: 0
Date: Sat, 30 Nov 2024 21:48:11 GMT
Connection: close
```

## Próximos Passos  

A jornada com o Spring Boot está só começando! Meu próximo objetivo é refinar ainda mais o projeto, incluindo uma ferramenta de *linter* para manter o código limpo e padronizado. Estou avaliando o uso do **SonarQube**, que permitirá uma análise mais profunda da qualidade do código e ajudará a identificar possíveis pontos de melhoria. Além disso, planejo implementar uma suíte de testes robusta para aumentar a cobertura de testes e garantir que cada funcionalidade esteja devidamente validada e funcionando conforme o esperado.

Também estou animado para explorar outras ferramentas e práticas que possam melhorar o ciclo de desenvolvimento, como integração contínua (*CI*) e entrega contínua (*CD*), garantindo que o projeto evolua de forma escalável e profissional.

Se você também está se aventurando nesse universo ou apenas se interessa por tecnologia e boas práticas de desenvolvimento, será um prazer trocar ideias! Conecte-se comigo no [LinkedIn](https://www.linkedin.com/in/ro-goncalves/) e vamos construir juntos. 🚀  
