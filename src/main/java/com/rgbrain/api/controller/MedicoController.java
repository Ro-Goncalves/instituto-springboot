package com.rgbrain.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.rgbrain.api.domain.endereco.Endereco;
import com.rgbrain.api.domain.medico.DadosAtualizacaoMedico;
import com.rgbrain.api.domain.medico.DadosCadastroMedico;
import com.rgbrain.api.domain.medico.DadosDetalhamentoMedico;
import com.rgbrain.api.domain.medico.DadosListagemMedico;
import com.rgbrain.api.domain.medico.Medico;
import com.rgbrain.api.domain.medico.MedicoRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {        
        var medico =  new Medico(
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
            );
        
        repository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping("")
    public ResponseEntity<Page<DadosListagemMedico>> buscarTodos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);       

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}
