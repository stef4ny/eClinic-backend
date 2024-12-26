package br.com.eClinic.modelo.agendamento;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.transaction.Transactional;

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
        agendamento.setMotivoConsulta(agendamentoAlterado.getMotivoConsulta());
        agendamento.setDataConsulta(agendamentoAlterado.getDataConsulta());
        agendamento.setHoraConsulta(agendamentoAlterado.getHoraConsulta());
        agendamento.setUpadateData(agendamentoAlterado.getUpadateData());
        agendamento.setStatus(agendamentoAlterado.getStatus());
        agendamento.setLinkMeet(agendamentoAlterado.getLinkMeet());

          repository.save(agendamento);

}
}



 
