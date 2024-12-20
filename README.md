# 🚑🩺 Spring Boot - API VOLL MED  <!-- omit in toc -->

Este projeto faz parte dos meus estudos em Spring Boot, uma introdução à formação Java e Spring Boot da Alura. A API foi criada com base na formação *Java e Spring Boot: Aprenda a criar aplicações com o framework mais amado do mundo Java*. [Formação Alura](https://cursos.alura.com.br/formacao-spring-boot-3)

- [Objetivo](#objetivo)
  - [Dependências principais do projeto](#dependências-principais-do-projeto)
- [Tecnologias e Ferramentas Utilizadas](#tecnologias-e-ferramentas-utilizadas)
- [Executando o Projeto](#executando-o-projeto)
  - [Extensões Instaladas](#extensões-instaladas)
  - [Como Rodar o Projeto](#como-rodar-o-projeto)
- [Execute Você Mesmo](#execute-você-mesmo)
  - [Pré-requisitos](#pré-requisitos)
  - [Passo 1: Clone o Repositório](#passo-1-clone-o-repositório)
  - [Passo 2: Configure o Ambiente](#passo-2-configure-o-ambiente)
  - [Passo 3: Inicie o Servidor](#passo-3-inicie-o-servidor)
  - [Passo 4: Teste a Aplicação](#passo-4-teste-a-aplicação)
- [Resultado](#resultado)
- [Próximos Passos](#próximos-passos)

## Objetivo  

O projeto tem como objetivo apresentar uma introdução ao Spring Boot. Ele visa ensinar os conceitos fundamentais sobre a criação de APIs REST, uso de anotações do Spring Boot, integração com banco de dados utilizando JpaRepository, além de padrões de projeto como Bean Validation e injeção de dependência.

Além disso, o projeto busca demonstrar boas práticas de desenvolvimento, como a utilização de migrações de banco de dados com Flyway, validação de dados com Bean Validation, e a configuração automática de um servidor web embutido.

### Dependências principais do projeto

- **📦 SQLite JDBC Driver**  
Conecta e interage com bancos de dados SQLite.
  
- **🔧 Hibernate Community Dialects**  
Dialetos adicionais do Hibernate para suportar o SQLite.

- **🌐 Spring Boot Starter Web**  
Facilita o desenvolvimento de aplicações web com Spring Boot e configura automaticamente um servidor embutido (Tomcat/Jetty).

- **🗄️ Spring Boot Starter Data JPA**  
Integração com JPA (Java Persistence API) para interações com banco de dados de forma simplificada.

- **✅ Spring Boot Starter Validation**  
Suporte para validação de dados utilizando Bean Validation.

- **🚀 Flyway Core**  
Ferramenta de migração de banco de dados para gerenciar alterações no schema.

- **🔍 Lombok**  
Elimina a escrita de código boilerplate, como getters, setters e construtores.

- **⚙️ Spring Boot DevTools**  
Melhora a produtividade com reinicialização automática do servidor durante o desenvolvimento.

- **🔐 Java JWT**  
Biblioteca da Auth0 para criar e validar JSON Web Tokens (JWT), comumente usados para autenticação e autorização.

- **🛡️ Spring Boot Starter Security**  
Fornece suporte integrado para segurança em aplicações Spring, incluindo autenticação, autorização e proteção contra ataques comuns, como CSRF e XSS.

- **🛠️ Native Maven Plugin**  
Plugin para criar executáveis nativos com o GraalVM, otimizando o desempenho e reduzindo o tempo de inicialização das aplicações.

- **📘 Springdoc OpenAPI Starter WebMVC UI**  
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
   Acesse o [Swagger UI](http://localhost:8080/swagger-ui/index.html#/) e seja feliz.

## Resultado  

Veja alguns exemplos de uso da API:  

**LOGIN**  
<div style="display: flex; flex-wrap: wrap; justify-content: space-between">
     <div style="width: 50%;">
        <p>REQUEST</p>
        <img src="imagens/login-request.png" style="width: 100%;"/>
    </div>
    <div style="width: 50%;">
        <p>RESPONSE</p>
        <img src="imagens/login-response.png" style="width: 100%;"/>
    </div>
</div>
<br><br>

**CADASTRAR PACIENTE**  
<div style="display: flex; flex-wrap: wrap; justify-content: space-between">
     <div style="width: 50%;">
        <p>REQUEST</p>
        <img src="imagens/pacientes-request.png" style="width: 100%;"/>
    </div>
    <div style="width: 50%;">
        <p>RESPONSE</p>
        <img src="imagens/pacientes-response.png" style="width: 100%;"/>
    </div>
</div>

## Próximos Passos  

- **🔍 Refinar o Projeto**  
  Incluir uma ferramenta de linter para manter o código limpo e padronizado.

- **📊 Análise de Código**  
  Avaliar o uso do SonarQube para uma análise mais profunda da qualidade do código e identificar possíveis pontos de melhoria.

- **🧪 Suíte de Testes**  
  Implementar uma suíte de testes robusta para aumentar a cobertura de testes e garantir que cada funcionalidade esteja devidamente validada e funcionando conforme o esperado.

- **🔄 Integração Contínua (CI)**  
  Explorar ferramentas e práticas de integração contínua para melhorar o ciclo de desenvolvimento.

- **🚀 Entrega Contínua (CD)**  
  Implementar práticas de entrega contínua para garantir que o projeto evolua de forma escalável e profissional.

- **🤝 Conexões e Colaborações**  
  Conectar-se com outros desenvolvedores interessados em tecnologia e boas práticas de desenvolvimento para trocar ideias e construir juntos.

>Se você também está se aventurando nesse universo ou apenas se interessa por tecnologia e boas práticas de desenvolvimento, será um prazer trocar ideias! Conecte-se comigo no [LinkedIn](https://www.linkedin.com/in/ro-goncalves/) e vamos construir juntos. 🚀  
