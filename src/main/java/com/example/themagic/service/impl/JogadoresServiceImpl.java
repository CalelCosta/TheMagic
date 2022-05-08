package com.example.themagic.service.impl;


import com.example.themagic.dto.JogadoresDTO;
import com.example.themagic.exception.ErroAutenticacaoException;
import com.example.themagic.exception.ErroDeProcessamentoException;
import com.example.themagic.model.Jogadores;
import com.example.themagic.repository.JogadoresRepository;
import com.example.themagic.service.JogadoresService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JogadoresServiceImpl implements JogadoresService {

    private static final String EXISTEUSUARIOEMAIL = "Já existe um jogador com esse email cadastrado!";
    private static final String ERROPROCESSAMENTO = "Não foi possível processar a requisição!";
    private static final String SENHAINVALIDA = "Senha Inválida!";
    private static final String NAOEXISTEEMAILUSUARIO = "Jogador não encontrado para o email informado!";

    //Injeção de Dependências
    private final JogadoresRepository repository;

    //Construtor
    public JogadoresServiceImpl(JogadoresRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public Jogadores autenticarJogador(String email, String senha) {
        try {
            Optional<Jogadores> jogador = repository.findByEmail(email);

            if (jogador.isEmpty()) {
                throw new ErroAutenticacaoException(NAOEXISTEEMAILUSUARIO);
            }

            if (!jogador.get().getSenha().equals(senha)) {
                throw new ErroAutenticacaoException(SENHAINVALIDA);
            }

            return jogador.get();

        } catch (ErroDeProcessamentoException e) {
            throw new ErroDeProcessamentoException(ERROPROCESSAMENTO);
        }
    }

    @Override
    @Transactional
    public Jogadores salvarJogadorNovo(Jogadores jogador) {
        return repository.save(jogador);
    }

    @Override
    public Optional<Jogadores> obterPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Jogadores> buscarJogadores(Jogadores jogadores) {
        Example<Jogadores> example = Example.of(jogadores, ExampleMatcher.matching()
                .withIgnoreCase() //ignora caixa alta ou baixa
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)); //filtra se contém a letra digitada.
        return repository.findAll(example);
    }

    @Override
    public void validarEmail(String s) {
        boolean existe = repository.existsByEmail(s);
        if (existe){
            throw new ErroDeProcessamentoException("Já existe um usuário com esse email cadastrado!");
        }
    }

    public Jogadores constroiJogadores(JogadoresDTO dto){
        Jogadores jg = new Jogadores();

        jg.setId(dto.getId());
        jg.setNome(dto.getNome());
        jg.setSenha(dto.getSenha());
        jg.setEmail(dto.getEmail());

        return jg;
    }
}
