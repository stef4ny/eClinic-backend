package br.com.eClinic.api.chatBot;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eClinic.modelo.agendamento.Agendamento;
import br.com.eClinic.modelo.agendamento.AgendamentoRepository;
import br.com.eClinic.modelo.paciente.Paciente;
import br.com.eClinic.modelo.paciente.PacienteRepository;

@RestController
@RequestMapping("/api")
public class ChatbotController {

    private enum ChatState {
        MENU,
        AGENDA_NOME,
        AGENDA_DATA,
        AGENDA_CPF,
        CADASTRO_PACIENTE_NOME,
        CADASTRO_PACIENTE_CPF,
        CADASTRO_PACIENTE_EMAIL,
        CADASTRO_PACIENTE_SENHA,
        CADASTRO_PACIENTE_CONFIRMAR_SENHA;
    }

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    PacienteRepository PacienteRepository;
    private ChatState chatState = ChatState.MENU;
    String NomeAgend;
    Paciente paciente = new Paciente();
    Agendamento agendamento = new Agendamento();

    @PostMapping("/chat")
    public ResponseEntity<String> processMessage(@RequestBody String message) {
        String response = chatbotLogic(message);
        return ResponseEntity.ok(response);
    }

    private String chatbotLogic(String userMessage) {
        switch (chatState) {
            case MENU:
                return chatStateMenu(userMessage);
            case AGENDA_NOME:
                return chatStateAgendNome(userMessage);
            case AGENDA_DATA:
                return chatStateAgendData(userMessage);
            case AGENDA_CPF:
                return chatStateAgendCpf(userMessage);
            case CADASTRO_PACIENTE_NOME:
                return chatStateCadastroPacienteNome(userMessage);
            case CADASTRO_PACIENTE_CPF:
                return chatStateCadastroPacienteCpf(userMessage);
            case CADASTRO_PACIENTE_EMAIL:
                return chatStateCadastroPacienteEmail(userMessage);
            case CADASTRO_PACIENTE_SENHA:
                return chatStateCadastroPacienteSenha(userMessage);
            case CADASTRO_PACIENTE_CONFIRMAR_SENHA:
                return chatStateCadastroPacienteConfirmarSenha(userMessage);
            default:
                return "Desculpe, algo deu errado. Por favor, tente novamente.";
        }
    }

    private String chatStateMenu(String userMessage) {
        if (userMessage.toLowerCase().contains("agend")) {
            chatState = ChatState.AGENDA_NOME;
            return "Ótimo! Por favor, informe o seu nome para começar o agendamento.";
        }
        if (userMessage.toLowerCase().contains("cadastr")) {
            chatState = ChatState.CADASTRO_PACIENTE_NOME;
            return "Ótimo! Vamos começar o cadastro. Por favor, informe o seu nome.";
        }
        return "Como posso lhe ajudar? Sou capaz de fazer as seguintes coisas:\n- Agendamento\n- Cadastro de Paciente";
    }

    private String chatStateCadastroPacienteNome(String userMessage) {
        paciente.setNomeCompleto(userMessage); 
        chatState = ChatState.CADASTRO_PACIENTE_CPF;
        return "Obrigado, " + paciente.getNomeCompleto() + ". Agora, por favor, informe o seu CPF.";
    }

    private String chatStateCadastroPacienteCpf(String userMessage) {
        paciente.setCpf(userMessage);
        chatState = ChatState.CADASTRO_PACIENTE_EMAIL;
        return "Obrigado! Agora informe o seu E-mail.";
    }

    private String chatStateCadastroPacienteEmail(String userMessage) {
        paciente.setEmail(userMessage);
        chatState = ChatState.CADASTRO_PACIENTE_SENHA;
        return "Obrigado! Agora crie uma senha.";
    }

    private String chatStateCadastroPacienteSenha(String userMessage) {
        paciente.setSenha(userMessage);
        chatState = ChatState.CADASTRO_PACIENTE_CONFIRMAR_SENHA;
        return "Ótimo! Por favor, confirme a sua senha.";
    }

    private String chatStateCadastroPacienteConfirmarSenha(String userMessage) {
        if (paciente.getSenha().equals(userMessage)) {
            chatState = ChatState.MENU;
            try {
                paciente.setHabilitado(true);
                salvarPaciente();
            } catch (Exception e){
                return (e.getMessage());
            }
            return "Cadastro realizado com sucesso! Bem-vindo, " + paciente.getNomeCompleto() + ".";
        } else {
            chatState = ChatState.CADASTRO_PACIENTE_SENHA;
            return "As senhas não coincidem. Por favor, crie uma nova senha.";
        }
    }

    private void salvarPaciente() {
        PacienteRepository.save(paciente);
    }

    private String chatStateAgendNome(String userMessage) {
        NomeAgend = userMessage;
        chatState = ChatState.AGENDA_CPF;
        return "Obrigado, " + NomeAgend + ". Agora informe o seu CPF";
    }

    private String chatStateAgendCpf(String userMessage) {
        // Verificar se existe paciente com o CPF fornecido
        Paciente pacienteExistente = PacienteRepository.buscarPorCpf(userMessage);
    
        if (pacienteExistente == null) {
            // Paciente não encontrado, iniciar cadastro
            chatState = ChatState.MENU;
            return "Não encontramos nenhum paciente com o CPF fornecido. Por favor, faça o cadastro para continuar.";
        }
    
        // Paciente encontrado, continuar com o agendamento
        paciente = pacienteExistente;
        chatState = ChatState.AGENDA_DATA;
        return "Bem-vindo de volta, " + paciente.getNomeCompleto() + ". Agora informe a data e horário desejados no formato 'dd/MM/yyyy HH:mm'.";
    }

    private String chatStateAgendData(String userMessage) {
        try {
            // Define o formato esperado
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
            // Parse da mensagem para LocalDateTime
            LocalDateTime dataHoraAgendamento = LocalDateTime.parse(userMessage, formatter);
    
            // Separar data e horário
            LocalDate dataAgendamento = dataHoraAgendamento.toLocalDate();
            LocalTime horarioAgendamento = dataHoraAgendamento.toLocalTime();
    
            boolean checarSeDataEstaDisponivel = checarSeDataEstaDisponivel(dataAgendamento, horarioAgendamento);
            chatState = ChatState.MENU;
    
            if (checarSeDataEstaDisponivel) {
                paciente.setHabilitado(true);
                agendamento.setPaciente(paciente);
                agendamento.setDataAgendmento(dataAgendamento);
                agendamento.setHorarioAgendamento(horarioAgendamento);
                agendamentoRepository.save(agendamento);
                return "A data e horário estão disponíveis! Seu agendamento foi realizado com sucesso, " + NomeAgend + ".";
            } else {
                return "Infelizmente, o horário solicitado não está disponível. Por favor, tente outro horário.";
            }
        } catch (Exception e) {
            return "Formato inválido. Por favor, informe a data e horário no formato 'dd/MM/yyyy HH:mm'.";
        }
    }
    

    private Boolean checarSeDataEstaDisponivel(LocalDate dataAgendamento, LocalTime horarioAgendamento) {
    // Verificar se o horário está dentro do intervalo permitido
    LocalTime inicioPermitido = LocalTime.of(8, 0); // 08:00
    LocalTime fimPermitido = LocalTime.of(18, 0);  // 18:00

    if (horarioAgendamento.isBefore(inicioPermitido) || horarioAgendamento.isAfter(fimPermitido)) {
        return false; // Horário fora do intervalo permitido
    }

    // Verificar se existe algum agendamento na mesma data com menos de 1 hora de diferença
    List<Agendamento> agendamentosNoDia = agendamentoRepository.findByDataAgendmento(dataAgendamento);

    for (Agendamento agendamento : agendamentosNoDia) {
        LocalTime horarioExistente = agendamento.getHorarioAgendamento();

        // Calcular diferença em horas
        long diferencaEmMinutos = Math.abs(horarioExistente.until(horarioAgendamento, ChronoUnit.MINUTES));
        if (diferencaEmMinutos < 60) {
            return false; // Horário conflita com um existente
        }
    }

    // Se passou por todas as verificações, está disponível
    return true;
}

}
