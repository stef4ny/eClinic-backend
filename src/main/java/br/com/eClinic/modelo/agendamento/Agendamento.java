package br.com.eClinic.modelo.agendamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eClinic.service.EmailService;  




@Service
public class AgendamentoService {
  
   @Autowired
    private AgendamentoRepository repository;

    

    @Autowired
    private EmailService emailService; 

    @Transactional
public Agendamento save(Agendamento agendamento) {
    agendamento.setHabilitado(Boolean.TRUE);

    // Obtenha o e-mail do paciente associado ao agendamento
    String email = agendamento.getPaciente().getEmail();
    if (email == null || email.isEmpty()) {
        throw new IllegalArgumentException("E-mail do paciente não encontrado.");
    }

    // Envia o e-mail
    emailService.enviarEmailTexto(email, "eClinc Recuperação de senha", "http://localhost:5173/recuperaçãodesenha");

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
