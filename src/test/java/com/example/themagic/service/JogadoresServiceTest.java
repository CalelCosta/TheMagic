package com.example.themagic.service;

import com.example.themagic.exception.ErroAutenticacaoException;
import com.example.themagic.model.Jogadores;
import com.example.themagic.repository.JogadoresRepository;
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
public class JogadoresServiceTest {

    @SpyBean
    JogadoresServiceImpl jogadoresService;

    @MockBean
    JogadoresRepository jogadoresRepository;

    @Test
    public void validaEmail(){
        //Cenário
        Mockito.when(jogadoresRepository.existsByEmail(Mockito.anyString())).thenReturn(false);

        //Ação
        jogadoresService.validarEmail("kent@teste.com.br");
    }

    @Test
    public void autenticarComSucesso(){

        Jogadores j = Jogadores
                .builder()
                .id(2L)
                .nome("Clark Kent")
                .email("kent@teste.com.br")
                .senha("kent")
                .build()
                ;
        Mockito.when(jogadoresRepository.findByEmail("kent@teste.com.br")).thenReturn(Optional.of(j));

        Jogadores result = jogadoresService.autenticarJogador("kent@teste.com.br", "kent");

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void erroAoNaoEncontrarEmailJogador(){
        //Cenario
        Mockito.when(jogadoresRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        //Ação
        Throwable exception = Assertions.catchThrowable( () -> jogadoresService.autenticarJogador("kent@teste.com.br", "kent"));

        //Verificação
        Assertions.assertThat(exception).isInstanceOf(ErroAutenticacaoException.class).hasMessage("Jogador não encontrado para o email informado!");
    }

    @Test
    public void salvarUsuarioTest(){
        Mockito.doNothing().when(jogadoresService).validarEmail(Mockito.anyString());
        Jogadores j = Jogadores
                .builder()
                .id(2L)
                .nome("Clark Kent")
                .email("kent@teste.com.br")
                .senha("kent")
                .build()
                ;
        Mockito.when(jogadoresRepository.save(Mockito.any(Jogadores.class))).thenReturn(j);

        Jogadores jogadorSalvo = jogadoresService.salvarJogadorNovo(new Jogadores());

        Assertions.assertThat(jogadorSalvo).isNotNull();
        Assertions.assertThat(jogadorSalvo.getId()).isEqualTo(2L);
        Assertions.assertThat(jogadorSalvo.getNome()).isEqualTo("Clark Kent");
        Assertions.assertThat(jogadorSalvo.getEmail()).isEqualTo("kent@teste.com.br");
        Assertions.assertThat(jogadorSalvo.getSenha()).isEqualTo("kent");
    }

}
