package com.hospital.real.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class Paciente {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String especializacaoDesejada;
    private String problema;

    @ManyToOne
    @JoinColumn(name = "doutor_id")
    private Doutor doutor;

}
