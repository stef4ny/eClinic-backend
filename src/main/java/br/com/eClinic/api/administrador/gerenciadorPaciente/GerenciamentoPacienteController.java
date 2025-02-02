package br.com.eClinic.api.administrador.gerenciadorPaciente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import br.com.eClinic.modelo.administrador.GerenciamentoPaciente.GerenciadorPaciente;
import br.com.eClinic.modelo.administrador.GerenciamentoPaciente.GerenciamentoPacienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/gerenciadorPaciente")
@CrossOrigin

public class GerenciamentoPacienteController {
   @Autowired
    private GerenciamentoPacienteService gerenciamentoPacienteService;


     @PostMapping
    public ResponseEntity<GerenciadorPaciente> save(
            @RequestBody @Valid GerenciamentoPacienteRequest gerenciamentoPacienteRequest) {
        GerenciadorPaciente gerenciadorPaciente = gerenciamentoPacienteService.save(gerenciamentoPacienteRequest.build());
        return new ResponseEntity<GerenciadorPaciente>(gerenciadorPaciente, HttpStatus.CREATED);
    }

    @GetMapping("/filtro") // filtro no gerenciamento de Paciente
    public List<GerenciadorPaciente> filtroPaciente(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nome) {
        return gerenciamentoPacienteService.filtroPaciente(id, nome);
    }

    @GetMapping
    public List<GerenciadorPaciente> listarTodos() {
        return gerenciamentoPacienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public GerenciadorPaciente obterPorID(@PathVariable Long id) {
        return gerenciamentoPacienteService.obterPorID(id);
    }


     @PutMapping("/{id}")
    public ResponseEntity<GerenciadorPaciente> update(@PathVariable("id") Long id,
            @RequestBody GerenciamentoPacienteRequest request) {

        gerenciamentoPacienteService.update(id, request.build());
        return ResponseEntity.ok().build();
    }


     @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        gerenciamentoPacienteService.delete(id);
        return ResponseEntity.ok().build();
    }
}
