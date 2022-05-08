package com.example.themagic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JogadoresDTO {

    private Long id;
    private String nome;
    private String email;
    private String senha;
}
