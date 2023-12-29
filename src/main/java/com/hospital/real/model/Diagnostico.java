package com.hospital.real.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Diagnostico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doutor_id")
    private Doutor doutor;


    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private String descricao;

}
