package com.rgbrain.api.domain.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rgbrain.api.domain.consulta.validacoes.ValidadorAgendamentoConsultas;
import com.rgbrain.api.domain.medico.Medico;
import com.rgbrain.api.domain.medico.MedicoRepository;
import com.rgbrain.api.domain.paciente.PacienteRepository;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoConsultas> validadores;
    
    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {

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

        if (medico == null) {
            throw new ValidacaoException("Não exite médico disponível nessa data");
        }

        validadores.forEach(E -> E.validar(dados));
        
        var consulta = new Consulta(null, medico, paciente, dados.data());
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
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
