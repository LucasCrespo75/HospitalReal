package com.hospital.real.controller;

import com.hospital.real.model.Diagnostico;
import com.hospital.real.model.Doutor;
import com.hospital.real.model.Paciente;
import com.hospital.real.service.DoutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoutorController {
    private final DoutorService doutorService;

    @Autowired
    public DoutorController(DoutorService doutorService) {
        this.doutorService = doutorService;
    }

    // Endpoint para obter todos os médicos
    @GetMapping
    public ResponseEntity<List<Doutor>> getAllDoctors() {
        List<Doutor> doctors = doutorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    // Endpoint para obter um médico por ID
    @GetMapping("/{id}")
    public ResponseEntity<Doutor> getDoctorById(@PathVariable Long id) {
        Doutor doutor = doutorService.getDoctorById(id);
        if (doutor != null) {
            return ResponseEntity.ok(doutor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para registro de médico
    @PostMapping ("/register")
    public ResponseEntity<Doutor> registerDoctor(@RequestParam String email, @RequestParam String crm, @RequestParam String especializacao) {
        try {
            Doutor doutor = doutorService.cadastrarDoutor(email, crm, especializacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(doutor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Endpoint para login de médico
    @PostMapping("/login")
    public ResponseEntity<Doutor> loginDoctor(@RequestParam String email, @RequestParam String crm) {
        Doutor doutor = doutorService.login(email, crm);

        if (doutor != null) {
            return ResponseEntity.ok(doutor);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


    // Endpoint para obter pacientes por especialização
    @GetMapping("/patients/{especializacao}")
    public ResponseEntity<List<Paciente>> getPatientsBySpecialization(@PathVariable String especializacao) {
        List<Paciente> patients = doutorService.getPatientsBySpecialization(especializacao);

        if (!patients.isEmpty()) {
            return ResponseEntity.ok(patients);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/{doutorId}/diagnosticar")
    public ResponseEntity<Diagnostico> fazerDiagnostico(
            @PathVariable Long doutorId,
            @RequestParam Long pacienteId,
            @RequestParam String descricao) {
        Diagnostico diagnostico = doutorService.fazerDiagnostico(doutorId, pacienteId, descricao);

        if (diagnostico != null) {
            return ResponseEntity.ok(diagnostico);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    // ... (outros métodos existentes)
}
