package com.example.themagic.service;


import com.example.themagic.model.Cartas;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CartasService {

    Cartas salvarCarta(Cartas carta);

    Cartas atualizarCarta(Cartas carta);

    void deletarCarta(Cartas carta);

    List<Cartas> buscarCartas(Cartas cartaPorFiltro);

    Optional<Cartas> obterCartasPorId(Long id);

    List<Cartas> obterListaCartasPorIdJogador(Cartas cartasPorJogador);
}
