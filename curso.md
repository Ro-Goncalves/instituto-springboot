# Spring Boot

## Agendamento de Consultas

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agendaDeConsultas;
    
    @PostMapping("")
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        agendaDeConsultas.agendar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(null, null, null, null));
    }
    
}

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;
    
    public void agendar(DadosAgendamentoConsulta dados) {

        var idPaciente = dados.idPaciente();
        if (!pacienteRepository.existsById(idPaciente)) {
            throw new ValidacaoException("Id do paciente informado não existe");
        }
        var paciente = pacienteRepository.getReferenceById(idPaciente);

        var idMedico = dados.idMedico();
        if (idMedico != null && !medicoRepository.existsById(idMedico)) {
            throw new ValidacaoException("Id do médico informado não existe");
        }
        var medico = escolherMedico(dados);

        var consulta = new Consulta(null, medico, paciente, dados.data());
        consultaRepository.save(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatório quando medico nao for escolhido");
        }

        return medicoRepository.escolherMedicoAleatorio(dados.especialidade(), dados.data());
    }
}

@Query(value = """
        SELECT m 
        FROM Medico m 
        WHERE 
            m.ativo = TRUE
            AND m.especialidade = :especialidade
            AND NOT EXISTS (
                SELECT 1
                FROM Consulta c
                WHERE 
                    c.medico.id = m.id
                    AND c.data = :data
            )
        ORDER BY RAND()
        LIMIT 1
    """)
    Medico escolherMedicoAleatorio(Especialidade especialidade, LocalDateTime data);

Implementar uma nova funcionalidade no projeto;
Avaliar quando é necessário criar uma classe Service na aplicação;
Criar uma classe Service, com o objetivo de isolar códigos de regras de negócio, utilizando para isso a anotação @Service;
Implementar um algoritmo para a funcionalidade de agendamento de consultas;
Realizar validações de integridade das informações que chegam na API;
Implementar uma consulta JPQL (Java Persistence Query Language) complexa em uma interface repository, utilizando para isso a anotação @Query.

## Regras de Negócio

Criado os validadores

Em agendar

validadores.forEach(E -> E.validar(dados));

e  @Autowired
    private List<ValidadorAgendamentoConsultas> validadores;

Infeta todas as classes com validadores, principios SOLID. SOL

Isolar os códigos de validações de regras de negócio em classes separadas, utilizando nelas a anotação @Component do Spring;
Finalizar a implementação do algoritmo de agendamento de consultas;
Utilizar os princípios SOLID para deixar o código da funcionalidade de agendamento de consultas mais fácil de entender, evoluir e testar.

## Documentação da API

<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.7.0</version>
</dependency>

@Configuration
public class SpringDocConfigurations {
    
    @Bean
     public OpenAPI customOpenAPI() {
        return new OpenAPI()
           .components(new Components()
                .addSecuritySchemes("bearer-key",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
                .info(new Info()
                    .title("Voll.med API")
                    .description("API Rest da aplicação Voll.med, contendo as funcionalidades de CRUD de médicos e de pacientes, além de agendamento e cancelamento de consultas")
                    .contact(new Contact()
                        .name("Time Backend")
                        .email("backend@voll.med"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("http://voll.med/api/licenca")));
    }
}

@SecurityRequirement(name = "bearer-key")

Adicionar a biblioteca SpringDoc no projeto para que ela faça a geração automatizada da documentação da API;
Analisar a documentação do SpringDoc para entender como realizar a sua configuração em um projeto;
Acessar os endereços que disponibilizam a documentação da API nos formatos yaml e html;
Utilizar o Swagger UI para visualizar e testar uma API Rest;
Configurar o JWT na documentação gerada pelo SpringDoc.

## Testes com Spring Boot

Escrever testes automatizados em uma aplicação com Spring Boot;
Escrever testes automatizados de uma interface Repository, seguindo a estratégia de usar o mesmo banco de dados que a aplicação utiliza;
Sobrescrever propriedades do arquivo application.properties, criando outro arquivo chamado application-test.properties que seja carregado apenas ao executar os testes, utilizando para isso a anotação @ActiveProfiles;
Escrever testes automatizados de uma classe Controller, utilizando a classe MockMvc para simular requisições na API;
Testar cenários de erro 400 e código 200 no teste de uma classe controller.

## Build com Maven

Compilar nativamente

./mvnw -Pnative native:compile

Funciona o build de uma aplicação com Spring Boot;
Utilizar arquivos de propriedades específicos para cada profile, alterando em cada arquivo as propriedades que precisam ser modificadas;
Configurar informações sensíveis da aplicação, como dados de acesso ao banco de dados, via variáveis de ambiente;
Realizar o build do projeto via Maven;
Executar a aplicação via terminal, com o comando java -jar, passando as variáveis de ambiente como parâmetro.
