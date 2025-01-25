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

        if (agendamento.getIdMedico() == null || agendamento.getIdPaciente() == null) {
            throw new IllegalArgumentException("Médico e Paciente são obrigatórios para o agendamento.");
        }

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
        Agendamento Agendamento = repository.findById(id).get();
        Agendamento.setDataAgendmento(agendamentoAlterado.getDataAgendmento());
        Agendamento.setStatus(agendamentoAlterado.getStatus());
        Agendamento.setMotivo(agendamentoAlterado.getMotivo());
        Agendamento.setHorarioAgendamento(agendamentoAlterado.getHorarioAgendamento());
        Agendamento.setUpdateData(agendamentoAlterado.getUpdateData());


         // Verifica a data
         if (!Agendamento.getDataAgendmento().equals(agendamentoAlterado.getDataAgendmento())) {
            Agendamento.setDataAgendmento(agendamentoAlterado.getDataAgendmento());
            Agendamento.setUpdateData(agendamentoAlterado.getUpdateData());  
        }


        repository.save(Agendamento);
    }

    @Transactional
        public void delete(Long id) {
        Agendamento Agendamento = repository.findById(id).get();
        Agendamento.setHabilitado(Boolean.FALSE);
        repository.save(Agendamento);
   }
}