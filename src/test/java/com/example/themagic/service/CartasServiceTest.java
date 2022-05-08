package com.example.themagic.service;

import com.example.themagic.model.Cartas;
import com.example.themagic.repository.CartasRepository;
import com.example.themagic.repository.CartasRepositoryTest;
import com.example.themagic.service.impl.CartasServiceImpl;
import com.example.themagic.service.impl.JogadoresServiceImpl;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartasServiceTest {

    @SpyBean
    CartasServiceImpl cartasService;

    @SpyBean
    JogadoresServiceImpl jogadoresService;

    @MockBean
    CartasRepository cartasRepository;

    @Test
    public void deveSalvarUmaCarta(){
        Cartas cartasSalva = CartasRepositoryTest.criaCarta();
        Mockito.when(cartasRepository.save(cartasSalva)).thenReturn(cartasSalva);

        Cartas cartas = cartasService.salvarCarta(cartasSalva);

        Assertions.assertThat(cartas.getId()).isEqualTo(cartasSalva.getId());
    }

    @Test
    public void deveAtualizarUmaCarta(){
        Cartas cartaSalva = CartasRepositoryTest.criaCarta();

        Mockito.when(cartasRepository.save(cartaSalva)).thenReturn(cartaSalva);

        cartasService.atualizarCarta(cartaSalva);

        Mockito.verify(cartasRepository, Mockito.times(1)).save(cartaSalva);
    }

    @Test
    public void deveDeletarCarta(){
        Cartas cartaDeletar = CartasRepositoryTest.criaCarta();

        cartasService.deletarCarta(cartaDeletar);

        Mockito.verify(cartasRepository).delete(cartaDeletar);
    }

    @Test
    public void deveObterCartaPorId(){
        Cartas carta = CartasRepositoryTest.criaCarta();

        Mockito.when(cartasRepository.findById(carta.getId())).thenReturn(Optional.of(carta));

        Optional<Cartas> resultado = cartasService.obterCartasPorId(carta.getId());

        Assertions.assertThat(resultado.isPresent()).isTrue();
    }
}
