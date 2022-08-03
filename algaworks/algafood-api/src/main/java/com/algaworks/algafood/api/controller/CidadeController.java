package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<Cidade>> listar() {
        return ResponseEntity.ok(cidadeService.listar());
    }

    @GetMapping("/{id}")
    public Cidade buscar(@PathVariable Long id) {
        return cidadeService.buscarOuFalhar(id);
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
        try {
            return ResponseEntity.ok(cidadeService.salvar(cidade));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TODO: Verificar porque não consigo retornar o nome do estado depois de atualizado.
    @PutMapping("/{id}")
    public Cidade atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
        Cidade cidadeAtual = cidadeService.buscarOuFalhar(id);
        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        return cidadeService.salvar(cidadeAtual);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        cidadeService.remover(id);
    }

}
