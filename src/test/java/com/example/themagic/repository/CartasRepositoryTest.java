package com.example.themagic.repository;

import com.example.themagic.model.Cartas;
import com.example.themagic.model.Jogadores;
import com.example.themagic.model.enums.CaracteristicasCartas;
import com.example.themagic.model.enums.IdiomasCartas;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartasRepositoryTest {

    @Autowired
    CartasRepository cartasRepository;

    @Test
    public void salvarLancamento(){
        Cartas lancamento = criaCarta();
        lancamento =  cartasRepository.save(lancamento);

        assertThat(lancamento.getId()).isNotNull();
    }

    @Test
    public void deveAtualizarCarta(){
        Cartas cartas = criarCartaEPersistir();

        cartas.setCaracteristica(CaracteristicasCartas.ARTEFATO);
        cartas.setIdioma(IdiomasCartas.JAPONES);
        cartas.setEdicao("Lendária");

        cartasRepository.save(cartas);

        Cartas cartaAtualizada = cartasRepository.getById(cartas.getId());

        assertThat(cartaAtualizada.getCaracteristica()).isEqualTo(CaracteristicasCartas.ARTEFATO);
        assertThat(cartaAtualizada.getIdioma()).isEqualTo(IdiomasCartas.JAPONES);
        assertThat(cartaAtualizada.getEdicao()).isEqualTo("Lendária");
    }

    @Test
    public void deveBuscarCartaPorId(){
        Cartas carta = criarCartaEPersistir();

        Cartas cartaEncontrada = cartasRepository.getById(carta.getId());

        assertThat(cartaEncontrada).isNotNull();
    }

    private Cartas criarCartaEPersistir(){
        Cartas carta = criaCarta();
        cartasRepository.save(carta);
        return carta;
    }

    public static Cartas criaCarta(){
        return Cartas.builder()
                .id(2L)
                .caracteristica(CaracteristicasCartas.CRIATURA)
                .idioma(IdiomasCartas.PORTUGUES)
                .edicao("Rara")
                .laminada(false)
                .valor(200.50)
                .jogadores(criarJogador())
                .nome("Naga do Sindicato das Brumas")
                .build();
    }

    public static Jogadores criarJogador(){
        return Jogadores
                .builder()
                .id(2L)
                .nome("Clark Kent")
                .email("kent@teste.com.br")
                .senha("kent")
                .build()
                ;
    }

}
