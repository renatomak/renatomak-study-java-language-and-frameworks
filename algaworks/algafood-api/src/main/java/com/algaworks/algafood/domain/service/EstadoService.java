package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoService {

    public static final String MSG_ESTADO_NAO_ENCONTRADA = "Não existe um cadastro de estado com código %d";
    public static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removida, pois está em uso";

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

    public void remover(Long estadoId) {
        try {
            estadoRepository.deleteById(estadoId);

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format(MSG_ESTADO_NAO_ENCONTRADA, estadoId));

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO, estadoId));
        }
    }

    public Estado buscarOuFalhar(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_ESTADO_NAO_ENCONTRADA, estadoId)));
    }

    private EntidadeNaoEncontradaException mensagemErro(Long id) {
        return new EntidadeNaoEncontradaException(
                String.format("Estado com id %d não encontrado", id));
    }
}
