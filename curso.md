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