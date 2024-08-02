package com.ufs.projeto.test.projeto_teste_software.models;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pessoa {

    private String nome;
    private String cpf;
    private String profissao;
    private Integer idade;
    private String cidade;
    private String rua;
    private Integer numero;

}
