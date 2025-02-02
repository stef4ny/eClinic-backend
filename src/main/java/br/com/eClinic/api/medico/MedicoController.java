package br.com.eClinic.api.medico;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eClinic.modelo.especialidades.EspecialidadeService;
import br.com.eClinic.modelo.medico.Medico;
import br.com.eClinic.modelo.medico.MedicoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/medicos")
@CrossOrigin
public class MedicoController {
    
    @Autowired
    private MedicoService medicoService;


    @Autowired
    private  EspecialidadeService especialidadeService;
    
  
    @PostMapping
    public ResponseEntity<Medico> save(@RequestBody @Valid MedicoRequest request) {
        

        Medico medicoNovo = request.build();
        medicoNovo.setEspecialidade(especialidadeService.obterPorID(request.getIdEspecialidade()));
        Medico medico = medicoService.save(medicoNovo);
       
        return new ResponseEntity<Medico>(medico, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Medico> listarTodos() {
        return medicoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Medico obterPorID(@PathVariable Long id) {
        return medicoService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico> update(@PathVariable("id") Long id, @RequestBody MedicoRequest request) {


        Medico medico = request.build();
        medico.setEspecialidade(especialidadeService.obterPorID(request.getIdEspecialidade()));
        medicoService.update(id, medico);
        return ResponseEntity.ok().build();
    }

}