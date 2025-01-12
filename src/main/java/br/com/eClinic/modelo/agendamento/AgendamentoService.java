package br.com.eClinic.modelo.agendamento;

import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AgendamentoService {
  
   @Autowired
    private AgendamentoRepository repository;

    @Transactional
    public Agendamento save(Agendamento agendamento) {
        agendamento.setHabilitado(Boolean.TRUE);

        return repository.save(agendamento);
    }

    public List<Agendamento> listarTodos() {
        return repository.findAll();
    }

    public Agendamento obterPorID(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Agendamento agendamentoAlterado) {
        Agendamento agendamento = repository.findById(id).get();
        agendamento.setMedico(agendamentoAlterado.getMedico());
        agendamento.setEspecialidade(agendamentoAlterado.getEspecialidade());
        agendamento.setDataAgendmento(agendamentoAlterado.getDataAgendmento());
        agendamento.setHorarioAgendamento(agendamentoAlterado.getHorarioAgendamento());


    

        repository.save(agendamento);
    }





    @Transactional
        public void delete(Long id) {
        Agendamento Agendamento = repository.findById(id).get();
        Agendamento.setHabilitado(Boolean.FALSE);
        repository.save(Agendamento);
   }
}
