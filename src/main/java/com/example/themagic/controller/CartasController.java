package com.example.themagic.controller;


import com.example.themagic.dto.CartasDTO;
import com.example.themagic.exception.ErroAutenticacaoException;
import com.example.themagic.exception.ErroDeProcessamentoException;
import com.example.themagic.model.Cartas;
import com.example.themagic.service.CartasService;
import com.example.themagic.service.impl.CartasServiceImpl;
import com.example.themagicg.utils.UniqueId;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cartas")
@RequiredArgsConstructor
public class CartasController {

    static final Logger logger = LoggerFactory.getLogger(CartasController.class);

    //Strings de uso comum
    private static final String ERROPROCESSAMENTO = "Não foi possível processar a requisição!";
    private static final String INICIOREQUISICAO = "Requisição iniciada...";
    private static final String FALHAREQUISICAO = "Requisição falhou";

    private final CartasService cartasService;
    private final CartasServiceImpl cartasServiceimpl;

    @GetMapping
    public List<Cartas> obterCartas(Cartas cartas) {
        logger.info(INICIOREQUISICAO);
        List<Cartas> ct;
        ct = cartasService.buscarCartas(cartas);
        return ct;
    }

    @GetMapping("/{id}")
    public Optional<Cartas> obterCartasPorId(@PathVariable Long id){
        logger.info(INICIOREQUISICAO, UniqueId.getInstance().getUuid()); //Exemplo de uso do UniqueId
        Optional<Cartas> ct = cartasService.obterCartasPorId(id);
        if (ct.isEmpty()){
            logger.error(FALHAREQUISICAO);
            throw new ErroAutenticacaoException("Nenhuma Carta encontrada com o ID " + id + "!");
        }
        return ct;
    }

    @PostMapping
    public ResponseEntity<Cartas> salvarCarta(@RequestBody CartasDTO dto) {
        logger.info(INICIOREQUISICAO);
        try {
            Cartas cartaSalva = cartasServiceimpl.constroiCartas(dto);
            cartaSalva = cartasService.salvarCarta(cartaSalva);
            return new ResponseEntity<>(cartaSalva, HttpStatus.CREATED);
        } catch (ErroDeProcessamentoException e) {
            logger.error(FALHAREQUISICAO);
            throw new ErroDeProcessamentoException(ERROPROCESSAMENTO);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cartas> atualizarCarta(@PathVariable Long id,@RequestBody CartasDTO dto){
        return cartasService.obterCartasPorId(id).map(entity -> {
            try {
                Cartas ct = cartasServiceimpl.constroiCartas(dto);
                ct.setId(entity.getId());
                cartasService.atualizarCarta(ct);
                return new ResponseEntity<>(ct, HttpStatus.OK);
            }catch (ErroDeProcessamentoException e){
                throw new ErroDeProcessamentoException(ERROPROCESSAMENTO);
            }

        }).orElseThrow(() ->new ErroDeProcessamentoException("Carta não encontrada na base de dados!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id){
        return cartasService.obterCartasPorId(id).map(entity ->{
            try {
                cartasService.deletarCarta(entity);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }catch (ErroDeProcessamentoException e){
                throw new ErroDeProcessamentoException(ERROPROCESSAMENTO);
            }
        }).orElseThrow(() -> new ErroDeProcessamentoException("Carta não encontrada na base de dados!"));
    }
}