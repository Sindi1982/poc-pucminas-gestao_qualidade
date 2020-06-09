package com.rogerioopaiva.qualitySpeed.service.impl;

import com.rogerioopaiva.qualitySpeed.exception.RegraNegocioException;
import com.rogerioopaiva.qualitySpeed.model.entity.PlanoAcao;
import com.rogerioopaiva.qualitySpeed.model.enums.StatusPlanoAcao;
import com.rogerioopaiva.qualitySpeed.model.repository.PlanoAcaoRepository;
import com.rogerioopaiva.qualitySpeed.service.PlanoAcaoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class PlanoAcaoServiceImpl implements PlanoAcaoService {

    private PlanoAcaoRepository repository;

    public PlanoAcaoServiceImpl(PlanoAcaoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public PlanoAcao salvar(PlanoAcao planoAcao) {
        validar(planoAcao);
        planoAcao.setStatus(StatusPlanoAcao.PENDENTE);
        return repository.save(planoAcao);
    }

    @Override
    public PlanoAcao atualizar(PlanoAcao planoAcao) {
        Objects.requireNonNull(planoAcao.getId());
        validar(planoAcao);
        return repository.save(planoAcao);
    }

    @Override
    public void deletar(PlanoAcao planoAcao) {
        Objects.requireNonNull(planoAcao.getId());
        repository.delete(planoAcao);
    }

    @Override
    public List<PlanoAcao> buscar(PlanoAcao planoAcaoFiltro) {
        Example example = Example.of( planoAcaoFiltro,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) );
        return repository.findAll(example);
    }

    @Override
    public void validar(PlanoAcao planoAcao) {
        if(planoAcao.getDataocorrencia() == null || planoAcao.getDataocorrencia().toString().equals("")) {
            throw new RegraNegocioException("Informe a data da ocorrência.");
        }

        if (planoAcao.getOque() == null || planoAcao.getOque().trim().equals("")) {
            throw new RegraNegocioException("Informe o que aconteceu.");
        }

        if (planoAcao.getPorque() == null || planoAcao.getPorque().trim().equals("")) {
            throw new RegraNegocioException("Informe o por que.");
        }

        if (planoAcao.getOnde() == null || planoAcao.getOnde().trim().equals("")) {
            throw new RegraNegocioException("Informe Onde.");
        }

        if (planoAcao.getQuem() == null || planoAcao.getQuem().trim().equals("")) {
            throw new RegraNegocioException("Informe Quem.");
        }

        if (planoAcao.getQuando() == null || planoAcao.getQuando().toString().equals("")) {
            throw new RegraNegocioException("Informe Quando.");
        }

        if (planoAcao.getComo() == null || planoAcao.getComo().trim().equals("")) {
            throw new RegraNegocioException("Informe Como.");
        }

        if (planoAcao.getQuantocusta() == null || planoAcao.getQuantocusta().toString().equals("")) {
            throw new RegraNegocioException("Informe quanto custa.");
        }

        if (planoAcao.getInicio() == null || planoAcao.getInicio().toString().equals("")) {
            throw new RegraNegocioException("Informe a data de ínicio.");
        }

        if (planoAcao.getTermino() == null || planoAcao.getTermino().toString().equals("")) {
            throw new RegraNegocioException("Informe a data de término.");
        }

        if (planoAcao.getNovoprazo() == null || planoAcao.getNovoprazo().toString().equals("")) {
            throw new RegraNegocioException("Informe a data do novo prazo de término.");
        }
    }


    @Override
    public Optional<PlanoAcao> obterPorId(Long id) {
        return repository.findById(id);
    }
}
