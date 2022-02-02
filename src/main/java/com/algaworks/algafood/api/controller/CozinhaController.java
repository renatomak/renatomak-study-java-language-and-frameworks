package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.CozinhasXmlWrapper;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @GetMapping
    public CozinhasXmlWrapper listar() {
        return new CozinhasXmlWrapper(cozinhaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cozinha> getById(@PathVariable Long id) {
        Cozinha cozinha = cozinhaRepository.findById(id).get();

        if (cozinha != null) {
            return ResponseEntity.ok(cozinha);
        }

        ResponseEntity<Cozinha> build = ResponseEntity.notFound().build();
        return build;
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXmlWrapper listarXml() {
        return new CozinhasXmlWrapper(cozinhaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Cozinha> salvar(@RequestBody Cozinha cozinha) {
       return ResponseEntity.ok(cadastroCozinhaService.salvar(cozinha));
    }

    @PutMapping("/{id}")
    public Optional<ResponseEntity<Cozinha>> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
        return cozinhaRepository.findById(id).map(record -> {
            BeanUtils.copyProperties(cozinha, record, "id");
            Cozinha update = cozinhaRepository.save(record);
            return ResponseEntity.ok().body(update);
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cozinha> remover(@PathVariable Long id) {
        try {
            Cozinha cozinha = cozinhaRepository.findById(id).get();

            if (cozinha != null) {
                cozinhaRepository.delete(cozinha);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
