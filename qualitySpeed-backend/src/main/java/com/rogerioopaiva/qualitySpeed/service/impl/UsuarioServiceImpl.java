package com.rogerioopaiva.qualitySpeed.service.impl;

import com.rogerioopaiva.qualitySpeed.exception.ErroAutenticacao;
import com.rogerioopaiva.qualitySpeed.exception.RegraNegocioException;
import com.rogerioopaiva.qualitySpeed.model.entity.Usuario;
import com.rogerioopaiva.qualitySpeed.model.repository.UsuarioRepository;
import com.rogerioopaiva.qualitySpeed.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = repository.findByEmail(email);

        if(!usuario.isPresent()) {
            throw new ErroAutenticacao("Usuário não encontrado para o email informado.");
        }

        if(!usuario.get().getSenha().equals(senha)) {
            throw new ErroAutenticacao("Senha inválida.");
        }

        return usuario.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
    boolean existe = repository.existsByEmail(email);
    if(existe) {
        throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
        }
    }
}
