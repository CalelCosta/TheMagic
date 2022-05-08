package com.example.themagic.model;


import com.example.themagic.model.enums.CaracteristicasCartas;
import com.example.themagic.model.enums.IdiomasCartas;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "cartas", schema = "cardgame")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cartas {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "edicao")
    private String edicao;

    @Column(name = "idioma")
    @Enumerated(value = EnumType.STRING)
    private IdiomasCartas idioma;

    @Column(name = "laminada")
    private boolean laminada;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "caracteristica")
    @Enumerated(value = EnumType.STRING)
    private CaracteristicasCartas caracteristica;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_jogadores")
    private Jogadores jogadores;
}
