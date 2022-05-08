package com.example.themagic.repository;


import com.example.themagic.model.Jogadores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JogadoresRepository extends JpaRepository<Jogadores, Long> {

    Optional<Jogadores> findByEmail(String email);

    boolean existsByEmail(String email);
}
