package com.example.themagic.repository;

import com.example.themagic.model.Jogadores;
import org.assertj.core.api.Assertions;

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
public class JogadoresRepositoryTest {

    @Autowired
    JogadoresRepository jogadoresRepository;

    @Test
    public void verificaUmEmail(){
        //Cenário
        Jogadores j = criarJogador();
        jogadoresRepository.save(j);

        //Ação
        boolean result = jogadoresRepository.existsByEmail("kent@teste.com.br");

        //Verificação
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void verificarJogadorSemEmail(){
        //Ação
        boolean result = jogadoresRepository.existsByEmail("kent@teste.com.br");
        //Verificação
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void persisteJogadorNoDB(){
        //Cenário
        Jogadores j = criarJogador();
        //Ação
        Jogadores jSalvo = jogadoresRepository.save(j);
        //Verificação
        Assertions.assertThat(jSalvo.getId()).isNotNull();
    }

    @Test
    public void buscaJogadorPorEmail(){
        Jogadores j = criarJogador();
        jogadoresRepository.save(j);

        Optional<Jogadores> result = jogadoresRepository.findByEmail("kent@teste.com.br");

        Assertions.assertThat(result.isPresent()).isTrue();
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

    @Test
    public void retornaVazioSemEmail(){
        Jogadores j = criarJogador();
        jogadoresRepository.save(j);

        Optional<Jogadores> result = jogadoresRepository.findByEmail("");

        Assertions.assertThat(result.isPresent()).isFalse();
    }
}
