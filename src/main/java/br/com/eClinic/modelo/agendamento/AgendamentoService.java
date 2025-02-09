package br.com.eClinic.modelo.agendamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import br.com.eClinic.service.EmailService;  comentado para sendEmail
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

    public List<Agendamento> listarTodosPorCpf(String cpf) {
        return repository.findAllporCpf(cpf);
    }

    public Agendamento obterPorID(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Agendamento agendamentoAlterado) {
        Agendamento agendamento = repository.findById(id).get();
        agendamento.setMedico(agendamentoAlterado.getMedico());
        agendamento.setPaciente(agendamentoAlterado.getPaciente());
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

  public List<Agendamento> filtrarAgendamentos(Long id, String nomeCompleto, String nome, LocalDate dataAgendamento, LocalTime horarioAgendamento) {

    List<Agendamento> listaAgendamentos = repository.findAll();

   

     if ((nomeCompleto != null && !"".equals(nomeCompleto)) &&
               (nome == null || "".equals(nome)) &&
               (dataAgendamento == null && horarioAgendamento == null)) {
      
        listaAgendamentos = repository.consultarPorNomeMedico(nomeCompleto);

    } else if (
        (nomeCompleto == null || "".equals(nomeCompleto)) &&
        (nome != null && !"".equals(nome)) &&
        (dataAgendamento == null && horarioAgendamento == null)) {
      
        listaAgendamentos = repository.consultarPorNome(nome);

    } else if (
        (nomeCompleto == null || "".equals(nomeCompleto)) &&
        (nome == null || "".equals(nome)) &&
        (dataAgendamento != null && horarioAgendamento != null)) {
    
        listaAgendamentos = repository.consultarPorDataEHora(dataAgendamento, horarioAgendamento);
    }
    return  listaAgendamentos;
}



}