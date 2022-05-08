package com.example.themagic.dto;


import com.example.themagic.model.Jogadores;
import com.example.themagic.model.enums.CaracteristicasCartas;
import com.example.themagic.model.enums.IdiomasCartas;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartasDTO {

    private Long id;
    private String nome;
    private String edicao;
    private IdiomasCartas idioma;
    private Boolean laminada;
    private Double valor;
    private CaracteristicasCartas caracteristica;
    private Jogadores jogadores;
}
