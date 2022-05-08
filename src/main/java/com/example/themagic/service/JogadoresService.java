package com.example.themagic.service;


import com.example.themagic.model.Jogadores;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface JogadoresService {

    Jogadores autenticarJogador(String email, String senha);

    Jogadores salvarJogadorNovo(Jogadores jogador);

    Optional<Jogadores> obterPorId(Long id);

    List<Jogadores> buscarJogadores(Jogadores jogadores);

    void validarEmail(String s);
}
