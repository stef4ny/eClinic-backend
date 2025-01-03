package br.com.eClinic.api.especialidade;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eClinic.modelo.especialidades.Especialidade;
import br.com.eClinic.modelo.especialidades.EspecialidadeService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/especialidade")
public class EspecialidadeController {

    @Autowired
    private EspecialidadeService especialidadeService;

    @PostMapping
    public ResponseEntity<Especialidade> save(@RequestBody @Valid EspecialidadeRequest especialidadeRequest) {
        Especialidade especialidade = especialidadeService.save(especialidadeRequest.build());
        return new ResponseEntity<Especialidade>(especialidade, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Especialidade> listarTodos() {
        return especialidadeService.listarTodos();
    }

    @GetMapping("/{id}")
    public Especialidade obterPorID(@PathVariable Long id) {
        return especialidadeService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Especialidade> update(@PathVariable("id") Long id,
            @RequestBody EspecialidadeRequest request) {

        especialidadeService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

}

