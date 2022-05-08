package com.example.themagic.service.impl;


import com.example.themagic.dto.CartasDTO;
import com.example.themagic.exception.ErroDeProcessamentoException;
import com.example.themagic.model.Cartas;
import com.example.themagic.model.Jogadores;
import com.example.themagic.repository.CartasRepository;
import com.example.themagic.service.CartasService;
import com.example.themagic.service.JogadoresService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartasServiceImpl implements CartasService {

    private final CartasRepository repository;
    private final JogadoresService jogadoresService;

    public CartasServiceImpl(CartasRepository repository, JogadoresService jogadoresService) {
        this.repository = repository;
        this.jogadoresService = jogadoresService;
    }

    @Override
    @Transactional
    public Cartas salvarCarta(Cartas carta) {
        Objects.requireNonNull(carta.getId());
        return repository.save(carta);
    }

    @Override
    @Transactional
    public Cartas atualizarCarta(Cartas carta) {
        return repository.save(carta);
    }

    @Override
    @Transactional
    public void deletarCarta(Cartas carta) {
        Objects.requireNonNull(carta.getId());
        repository.delete(carta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cartas> buscarCartas(Cartas cartaPorFiltro) {
        List<Cartas> ct = repository.findAll();
        return ct;
    }

    @Override
    public Optional<Cartas> obterCartasPorId(Long id) {
        return repository.findById(id);
    }

//    Esse método é para verificar a quantidade de cartas de acordo com o Tipo. Porém devido ao tempo não foi possível extraílo.

//    public String ObterQuantidadeCartasIguais(Long id) {
//        String criatura = "CRIATURA: " + repository.obterQuantidadePorCaracteristica(id, CaracteristicasCartas.CRIATURA) +"\n";
//        String artefato = "ARTEFATO: " + repository.obterQuantidadePorCaracteristica(id, CaracteristicasCartas.ARTEFATO) +"\n";
//        String encantamento = "ENCANTAMENTO: " + repository.obterQuantidadePorCaracteristica(id, CaracteristicasCartas.ENCANTAMENTO) +"\n";
//        String feitico = "FEITIÇO: " + repository.obterQuantidadePorCaracteristica(id, CaracteristicasCartas.FEITICO) +"\n";
//        String terreno = "TERRENO: " + repository.obterQuantidadePorCaracteristica(id, CaracteristicasCartas.TERRENO) +"\n";
//        String tribal = "TRIBAL: " + repository.obterQuantidadePorCaracteristica(id, CaracteristicasCartas.TRIBAL);
//
//        return criatura + artefato + encantamento + feitico + terreno + tribal;
//    }

    @Override
    public List<Cartas> obterListaCartasPorIdJogador(Cartas cartasPorJogador) {
        return null;
    }

    public Cartas constroiCartas(CartasDTO dto){
        Cartas cartas = new Cartas();

        cartas.setId(dto.getId());
        cartas.setNome(dto.getNome());
        cartas.setLaminada(dto.getLaminada());
        cartas.setCaracteristica(dto.getCaracteristica());
        cartas.setEdicao(dto.getEdicao());
        cartas.setIdioma(dto.getIdioma());
        cartas.setValor(dto.getValor());
        if (dto.getJogadores() != null) {
            Jogadores jogador = jogadoresService
                    .obterPorId(dto.getJogadores().getId())
                    .orElseThrow(() -> new ErroDeProcessamentoException("Jogador não encontrado para o ID informado."));

            cartas.setJogadores(jogador);
        }
        cartas.setJogadores(dto.getJogadores());

        return cartas;
    }
}
