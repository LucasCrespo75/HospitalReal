package com.hospital.real.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hospital.real.model.Paciente;
import com.hospital.real.repository.PacienteRepository;

import java.util.List;

@Service
public class PacienteService {
    private final PacienteRepository pacienteRepository;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }


    public Paciente getPacienteById(Long id) {
        return pacienteRepository.findById(id).orElse(null);
    }

    public Paciente cadastrarPaciente(Paciente novoPaciente) {
        if (pacienteRepository.existsByEmail(novoPaciente.getEmail())) {
            throw new RuntimeException("Já existe um paciente com o mesmo e-mail");
        }

        return pacienteRepository.save(novoPaciente);
    }


    public List<Paciente> getPatientsBySpecializationAndDoctor(String especializacao) {
        return pacienteRepository.findPatientsByEspecializacaoDesejada(especializacao);
    }

    public Paciente updatePaciente(Long id, Paciente updatedPatient) {
        Paciente existingPatient = pacienteRepository.findById(id).orElse(null);

        if (existingPatient != null) {
            // Atualiza apenas os campos necessários, evitando nulos
            if (updatedPatient.getNome() != null) {
                existingPatient.setNome(updatedPatient.getNome());
            }
            if (updatedPatient.getEmail() != null) {
                existingPatient.setEmail(updatedPatient.getEmail());
            }
            if (updatedPatient.getTelefone() != null) {
                existingPatient.setTelefone(updatedPatient.getTelefone());
            }
            if (updatedPatient.getEspecializacaoDesejada() != null) {
                existingPatient.setEspecializacaoDesejada(updatedPatient.getEspecializacaoDesejada());
            }

            return pacienteRepository.save(existingPatient);
        }

        return null;
    }
}
