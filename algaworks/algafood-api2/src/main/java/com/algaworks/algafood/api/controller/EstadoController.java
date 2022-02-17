package com.algaworks.algafood.api.controller;

import java.util.List;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoService estadoService;
	
	@GetMapping
	public ResponseEntity<List<Estado>> listar() {
		return ResponseEntity.ok(estadoService.listar());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(estadoService.buscar(id));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<Estado> salva(@RequestBody Estado estado) {
		return ResponseEntity.ok(estadoService.salva(estado));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Estado estado) {
		try {
			return ResponseEntity.ok(estadoService.atualizar(id, estado));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> remover(@PathVariable Long id) {
		try {
			estadoService.remover(id);
			return ResponseEntity.ok(String.format("Estado com id %d foi removido com sucesso!", id));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
