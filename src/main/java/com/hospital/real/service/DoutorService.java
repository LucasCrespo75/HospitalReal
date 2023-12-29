package com.hospital.real.service;

import com.hospital.real.model.Diagnostico;
import com.hospital.real.model.Doutor;
import com.hospital.real.model.Paciente;
import com.hospital.real.repository.DoutorRepository;
import com.hospital.real.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoutorService {
    private final DoutorRepository doutorRepository;
    private final PacienteService pacienteService;
    private final PacienteRepository pacienteRepository;

    @Autowired
    public DoutorService(DoutorRepository doutorRepository, PacienteService pacienteService, PacienteRepository pacienteRepository) {
        this.doutorRepository = doutorRepository;
        this.pacienteService = pacienteService;
        this.pacienteRepository = pacienteRepository;
    }

    public List<Doutor> getAllDoctors() {
        return doutorRepository.findAll();
    }

    public Doutor getDoctorById(Long id) {
        return doutorRepository.findById(id).orElse(null);
    }

    public Doutor cadastrarDoutor(String email, String crm, String especializacao) {
        Doutor doutorExistente = doutorRepository.findByCrm(crm);

        if (doutorExistente != null) {
            throw new IllegalArgumentException("Credenciais já registradas. Faça o login.");
        }

        Doutor novoDoutor = new Doutor();
        novoDoutor.setEmail(email);
        novoDoutor.setCrm(crm);
        novoDoutor.setEspecializacao(especializacao);

        // Lógica para salvar o médico
        Doutor doutorSalvo = doutorRepository.save(novoDoutor);

        // Atualizar a quantidade de pacientes
        atualizarQuantidadePacientes(doutorSalvo);

        return doutorSalvo;
    }


    public Doutor login(String email, String crm) {
        // Buscar o médico pelo e-mail e CRM
        Doutor doutor = doutorRepository.findByEmailAndCrm(email, crm);

        if (doutor == null) {
            throw new IllegalArgumentException("Médico não encontrado. Verifique suas credenciais.");
        }

        // Atualizar a quantidade de pacientes
        atualizarQuantidadePacientes(doutor);

        return doutor;
    }

    private void atualizarQuantidadePacientes(Doutor doutor) {
        // Recuperar a lista de pacientes com a especialização correspondente
        List<Paciente> pacientes = pacienteRepository.findPatientsByEspecializacaoDesejada(doutor.getEspecializacao());

        // Contar o número de pacientes dinamicamente
        int quantidadePacientes = pacientes.size();

        // Atualizar a quantidade de pacientes diretamente no médico
        doutor.setQuantidadePacientes(quantidadePacientes);

        // Salvar o médico novamente para atualizar a quantidade no banco de dados
        doutorRepository.save(doutor);
    }


    public Paciente assignPatient(Long doutorId, Long pacienteId) {
        Doutor doctor = getDoctorById(doutorId);
        Paciente patient = pacienteService.getPacienteById(pacienteId);

        if (doctor != null && patient != null) {
            patient.setDoutor(doctor);
            return pacienteService.updatePaciente(patient.getId(), patient);
        }

        return null;
    }

    public List<Paciente> getPatientsBySpecialization(String especializacao) {
        return pacienteService.getPatientsBySpecializationAndDoctor(especializacao);
    }

    public Diagnostico fazerDiagnostico(Long doutorId, Long pacienteId, String descricao) {
        Doutor doutor = getDoctorById(doutorId);
        Paciente paciente = pacienteService.getPacienteById(pacienteId);

        if (doutor != null && paciente != null) {
            Diagnostico diagnostico = new Diagnostico();
            diagnostico.setPaciente(paciente);
            diagnostico.setDescricao(descricao);

            // Adicionar o diagnóstico diretamente à lista de diagnósticos do médico
            doutor.getDiagnosticos().add(diagnostico);

            // Atualizar o médico para persistir as mudanças
            doutorRepository.save(doutor);

            return diagnostico;
        }
        return null;
    }
}
