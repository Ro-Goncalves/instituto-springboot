package com.rgbrain.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rgbrain.api.endereco.Endereco;
import com.rgbrain.api.medico.DadosAtualizacaoMedico;
import com.rgbrain.api.medico.DadosCadastroMedico;
import com.rgbrain.api.medico.DadosListagemMedico;
import com.rgbrain.api.medico.Medico;
import com.rgbrain.api.medico.MedicoRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping("")
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {        
        repository.save(
            new Medico(
                null, 
                Boolean.TRUE,
                dados.nome(), 
                dados.email(), 
                dados.crm(),
                dados.telefone(), 
                dados.especialidade(), 
                new Endereco(
                    dados.endereco().logradouro(), 
                    dados.endereco().bairro(), 
                    dados.endereco().cep(),
                    dados.endereco().cidade(), 
                    dados.endereco().uf(),
                    dados.endereco().complemento(),
                    dados.endereco().numero()
                )
            )
        );
    }

    @GetMapping("")
    public Page<DadosListagemMedico> buscarTodos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    }

    @PutMapping("")
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }
}
