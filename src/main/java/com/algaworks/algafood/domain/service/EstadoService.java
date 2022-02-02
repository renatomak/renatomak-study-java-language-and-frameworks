package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    public Estado buscar(Long id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> mensagemErro(id));
    }

    public Estado salva(Estado estado) {
        return estadoRepository.save(estado);
    }

    public Estado atualizar(Long id, Estado estado) {
        Optional<Estado> estadoAtual = estadoRepository.findById(id);

        if (estadoAtual.isEmpty()) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Estado com id %d não exite", id)
            );
        }
        BeanUtils.copyProperties(estado, estadoAtual.get(), "id");
        return estadoRepository.save(estadoAtual.get());
    }

    public void remover(Long id) {
        Optional<Estado> estado = estadoRepository.findById(id);

        if (estado.isEmpty()) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Estado com id %d não existe e não pode ser exluido", id)
            );
        }
        estadoRepository.delete(estado.get());
    }

    private EntidadeNaoEncontradaException mensagemErro(Long id) {
        return new EntidadeNaoEncontradaException(
                String.format("Estado com id %d não encontrado", id));
    }
}
