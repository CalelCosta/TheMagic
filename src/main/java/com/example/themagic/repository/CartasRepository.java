package com.example.themagic.repository;


import com.example.themagic.model.Cartas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartasRepository extends JpaRepository<Cartas, Long> {
}
