package com.example.themagic.controller;


import com.example.themagic.dto.JogadoresDTO;
import com.example.themagic.exception.ErroAutenticacaoException;
import com.example.themagic.exception.ErroDeProcessamentoException;
import com.example.themagic.model.Jogadores;
import com.example.themagic.service.JogadoresService;
import com.example.themagic.service.impl.JogadoresServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jogadores")
@RequiredArgsConstructor
public class JogadoresController {

    static final Logger logger = LoggerFactory.getLogger(JogadoresController.class);

    private static final String ERROPROCESSAMENTO = "Não foi possível processar a requisição!";
    private static final String INICIOREQUISICAO = "Requisição iniciada...";
    private static final String FALHAREQUISICAO = "Requisição falhou";

    private final JogadoresService jogadoresService;
    private final JogadoresServiceImpl jogadoresServiceImpl;

    @GetMapping
    public List<Jogadores> find(Jogadores jg) {
        logger.info(INICIOREQUISICAO);
        return jogadoresService.buscarJogadores(jg);
    }

    @GetMapping("/{id}")
    public Optional<Jogadores> obterJogadorPorId(@PathVariable Long id){
        logger.info(INICIOREQUISICAO);
        Optional<Jogadores> jg = jogadoresService.obterPorId(id);
        if (jg.isEmpty()){
            logger.error(FALHAREQUISICAO);
            throw new ErroAutenticacaoException("Nenhum Usuário encontrado com o ID " + id + "!");
        }
        return jg;
    }

    @PostMapping
    public ResponseEntity<Jogadores> salvarJogador(@RequestBody JogadoresDTO dto) {
        logger.info(INICIOREQUISICAO);
        Jogadores jg = jogadoresServiceImpl.constroiJogadores(dto);
        try {
            Jogadores jogadorSalvo = jogadoresService.salvarJogadorNovo(jg);
            return new ResponseEntity<>(jogadorSalvo, HttpStatus.CREATED);
        } catch (ErroDeProcessamentoException e) {
            logger.error(FALHAREQUISICAO);
            throw new ErroDeProcessamentoException(ERROPROCESSAMENTO);
        }
    }

    @PostMapping("/autenticar")
    public ResponseEntity<Jogadores> autenticarUsuario(@RequestBody JogadoresDTO dto) {
        logger.info(INICIOREQUISICAO);
        try {
            Jogadores usuarioAuth = jogadoresService.autenticarJogador(dto.getEmail(), dto.getSenha());
            return new ResponseEntity<>(usuarioAuth, HttpStatus.OK);
        } catch (ErroAutenticacaoException err) {
            logger.error(FALHAREQUISICAO);
            throw new ErroAutenticacaoException("Usuário não encontrado!");
        }
    }
}
