package br.com.eClinic.api.agendamento;

import java.time.LocalDate;
import java.time.LocalTime;
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


import br.com.eClinic.modelo.agendamento.Agendamento;
import br.com.eClinic.modelo.agendamento.AgendamentoService;
import br.com.eClinic.modelo.especialidades.EspecialidadeService;
import br.com.eClinic.modelo.medico.MedicoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/agendamento")
@CrossOrigin
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService ;

    @Autowired
    private MedicoService medicoService;

    @Autowired 
    private EspecialidadeService especialidadeService;



    @PostMapping
        public ResponseEntity<Agendamento> save(@RequestBody @Valid AgendamentoRequest request) {


        Agendamento agendamentoNovo = request.build();
        agendamentoNovo.setMedico(medicoService.obterPorID(request.getIdMedico()));
        agendamentoNovo.setEspecialidade(especialidadeService.obterPorID(request.getIdEspecialidade()));
        Agendamento agendamento = agendamentoService.save(agendamentoNovo);




        return new ResponseEntity<Agendamento>(agendamento, HttpStatus.CREATED);
    }

    @GetMapping
        public List<Agendamento> listarTodos() {
        return agendamentoService.listarTodos();
    }

    @GetMapping("/{id}")
        public Agendamento obterPorID(@PathVariable Long id) {
        return agendamentoService.obterPorID(id);
    }

    @PutMapping("/{id}")
        public ResponseEntity<Agendamento> update(@PathVariable("id") Long id, @RequestBody AgendamentoRequest request) {
        
        Agendamento agendamento = request.build();
        agendamento.setMedico(medicoService.obterPorID(request.getIdMedico()));
        agendamento.setEspecialidade(especialidadeService.obterPorID(request.getIdEspecialidade()));
        agendamentoService.update(id, agendamento);

     

        
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
       public ResponseEntity<Void> delete(@PathVariable Long id) {
        agendamentoService.delete(id);
       return ResponseEntity.ok().build();
   }

    @PostMapping("/filtrar")
   public List<Agendamento> filtrarAgendamentos(
           @RequestParam(value = "id", required = false) Long id,
           @RequestParam(value = "nomeCompleto", required = false) String nomeCompleto,
           @RequestParam(value = "nome", required = false) String nome,
           @RequestParam(value = "dataAgendmento", required = false) LocalDate dataAgendamento,
           @RequestParam(value = "horarioAgendamento", required = false) LocalTime horarioAgendamento ) {

       return agendamentoService.filtrarAgendamentos(id, nomeCompleto, nome, dataAgendamento, horarioAgendamento );
   }



}





