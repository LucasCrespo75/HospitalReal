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

    @Column(name = "quantidade_pacientes")
    private int quantidadePacientes;


    @OneToMany(mappedBy = "doutor", cascade = CascadeType.ALL)
    private List<Diagnostico> diagnosticos;

    // getters e setters
}
