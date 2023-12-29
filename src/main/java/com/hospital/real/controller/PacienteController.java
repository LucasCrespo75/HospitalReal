package com.hospital.real.controller;

import com.hospital.real.model.Paciente;
import com.hospital.real.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }



    @PostMapping("/cadastrarPaciente")
    public ResponseEntity<Paciente> cadastrarPaciente(@RequestBody Paciente novoPaciente) {
        try {
            Paciente paciente = pacienteService.cadastrarPaciente(novoPaciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(paciente);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @GetMapping("/especializacao/{especializacao}")
    public ResponseEntity<List<Paciente>> getPatientsBySpecializationAndDoctor(
            @PathVariable String especializacao) {
        List<Paciente> patients = pacienteService.getPatientsBySpecializationAndDoctor(especializacao);
        return ResponseEntity.ok(patients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> updatePaciente(
            @PathVariable Long id,
            @RequestBody Paciente updatedPatient) {
        Paciente paciente = pacienteService.updatePaciente(id, updatedPatient);
        if (paciente != null) {
            return ResponseEntity.ok(paciente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
