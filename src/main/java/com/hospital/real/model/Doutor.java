package com.hospital.real.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Doutor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String especializacao;
    private String email;
    private String crm;

    @OneToMany(mappedBy = "doutor", cascade = CascadeType.ALL)
    private List<Paciente> pacientes;

    // getters e setters
}
