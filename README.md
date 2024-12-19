# Spring Boot - API VOLL MED  <!-- omit in toc -->

Este projeto faz parte dos meus estudos em Spring Boot, uma introdução à formação Java e Spring Boot da Alura. A API foi criada com base na formação *Java e Spring Boot: Aprenda a criar aplicações com o framework mais amado do mundo Java*.

[Formação Alura](https://cursos.alura.com.br/formacao-spring-boot-3)

- [Objetivo](#objetivo)
  - [Dependências principais do projeto](#dependências-principais-do-projeto)
- [Tecnologias e Ferramentas Utilizadas](#tecnologias-e-ferramentas-utilizadas)
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

- **Java JWT**  
  Biblioteca da Auth0 para criar e validar JSON Web Tokens (JWT), comumente usados para autenticação e autorização.

- **Spring Boot Starter Security**  
  Fornece suporte integrado para segurança em aplicações Spring, incluindo autenticação, autorização e proteção contra ataques comuns, como CSRF e XSS.

- **Native Maven Plugin**  
Plugin para criar executáveis nativos com o GraalVM, otimizando o desempenho e reduzindo o tempo de inicialização das aplicações.

- **Springdoc OpenAPI Starter WebMVC UI**  
Integração com o OpenAPI para gerar automaticamente documentação interativa (Swagger UI) de APIs RESTful desenvolvidas com Spring MVC.

## Tecnologias e Ferramentas Utilizadas

- **☕ Java 21**  
- **🌱 Spring Boot 3**  
- **📦 SQLite**  
- **🪄 Lombok**  
- **🚀 Flyway**  
- **🌀 Hibernate**  
- **📊 Spring Data JPA**  
- **🔒 Spring Security**  
- **🔑 Java JWT**  
- **⚡ GraalVM**  
- **📘 OpenAPI**  

## Executando o Projeto

O processo de execução do projeto é direto, mas alguns passos importantes e ferramentas tornam tudo mais simples e eficiente. Estou usando o **Visual Studio Code (VS Code)** para desenvolver e rodar o projeto, complementado com algumas extensões que facilitam bastante o trabalho. Vamos dar uma olhada:

### Extensões Instaladas

1. **[Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)**:  
   Permite desenvolver projetos em Java no VS Code, fornecendo suporte a edição, depuração e construção.  

2. **[Spring Boot Extension](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-spring-boot-dashboard)**:  
   Adiciona o *Spring Boot Dashboard*, que permite gerenciar e executar aplicações Spring diretamente pela interface do editor.  

### Como Rodar o Projeto

1. **Inicialização do Servidor**:  
   - Abra o painel do *Spring Boot Dashboard* na aba lateral do VS Code.  
   - Localize sua aplicação (neste caso, `api`).  
   - Clique no botão **Run** ao lado do nome do projeto para iniciar o servidor.  

2. **Testando os Endpoints**:  
   - Acesse o [Swagger UI](http://localhost:8080/swagger-ui/index.html#/).
   - Na rota de login, utilize.

      ```json
      {
          "login": "rodrigo.jesus@voll.med",
          "senha": "rodrigo@852"
      }
      ```

   - Copei o Token gerado.
   - Clique em **Authorize**, cole o Token gerado e clique em **Authorize**.

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
