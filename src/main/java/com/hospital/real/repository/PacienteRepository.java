package com.hospital.real.repository;

import com.hospital.real.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    List<Paciente> findPatientsByEspecializacaoDesejada( String especializacao);

    boolean existsByEmail(String email);
}

