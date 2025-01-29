package br.com.eClinic.api.chatBot;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eClinic.modelo.agendamento.Agendamento;
import br.com.eClinic.modelo.agendamento.AgendamentoRepository;
import br.com.eClinic.modelo.especialidades.Especialidade;
import br.com.eClinic.modelo.especialidades.EspecialidadeRepository;
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
        AGENDA_ESPECIALIDADES,
        CADASTRO_PACIENTE_NOME,
        CADASTRO_PACIENTE_CPF,
        CADASTRO_PACIENTE_EMAIL,
        CADASTRO_PACIENTE_SENHA,
        CADASTRO_PACIENTE_CONFIRMAR_SENHA;
    }

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;
    
    @Autowired
    PacienteRepository pacienteRepository;
    
    private ChatState chatState = ChatState.MENU;
    String NomeAgend;
    Paciente paciente = new Paciente();
    Agendamento agendamento = new Agendamento();
    Especialidade especialidadeEscolhida = new Especialidade();

    @PostMapping("/chat")
    public ResponseEntity<ChatResponseDto> processMessage(@RequestBody String message) {
        String response = chatbotLogic(message);
        return ResponseEntity.ok(new ChatResponseDto("🤖 " + response));
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
            case AGENDA_ESPECIALIDADES:
                return chatStateAgendEspecialidades(userMessage);
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
        pacienteRepository.save(paciente);
    }

    private String chatStateAgendNome(String userMessage) {
        NomeAgend = userMessage;
        chatState = ChatState.AGENDA_CPF;
        return "Obrigado, " + NomeAgend + ". Agora informe o seu CPF";
    }

    private String chatStateAgendCpf(String userMessage) {
        Paciente pacienteExistente = pacienteRepository.buscarPorCpf(userMessage);

        if (pacienteExistente == null) {
            chatState = ChatState.MENU;
            return "Não encontramos nenhum paciente com o CPF fornecido. Por favor, faça o cadastro para continuar.";
        }

        paciente = pacienteExistente;
        chatState = ChatState.AGENDA_ESPECIALIDADES;
        return "Bem-vindo de volta, " + paciente.getNomeCompleto() + ". Aqui estão as especialidades disponíveis:\n"
                + listarEspecialidadesDisponiveis();
    }

    private String listarEspecialidadesDisponiveis() {
        List<Especialidade> especialidades = especialidadeRepository.findAll();
        
        if (especialidades.isEmpty()) {
            chatState = ChatState.MENU;
            return "Não temos nenhuma especialidade disponível no momento ou estamos enfrentando problemas internos. Por favor, tente novamente mais tarde.";
        }
        
        return especialidades.stream()
                .map(Especialidade::getNome)
                .collect(Collectors.joining("\n"));
    }
    

    private String chatStateAgendEspecialidades(String userMessage) {
        List<Especialidade> especialidades = especialidadeRepository.findAll();
        boolean especialidadeValida = especialidades.stream()
                .anyMatch(especialidade -> especialidade.getNome().equalsIgnoreCase(userMessage));

                if (especialidadeValida) {
                    especialidadeEscolhida = especialidades.stream()
                            .filter(especialidade -> especialidade.getNome().equalsIgnoreCase(userMessage))
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException("Erro ao selecionar especialidade."));
            
                    agendamento.setEspecialidade(especialidadeEscolhida);
                    chatState = ChatState.AGENDA_DATA;
            
                    return "Especialidade " + especialidadeEscolhida.getNome() + " selecionada. Por favor, informe a data e horário no formato 'dd/MM/yyyy HH:mm'.";
                } else {
                    // Retorna mensagem de erro e lista as especialidades disponíveis
                    return "Especialidade inválida. Por favor, escolha uma das opções:\n" + listarEspecialidadesDisponiveis();
                }
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
                System.out.println("Salvando Agendamento");
                paciente.setHabilitado(true);
                especialidadeEscolhida.setHabilitado(true);
                agendamento.setPaciente(paciente);
                agendamento.setEspecialidade(especialidadeEscolhida);
                agendamento.setDataAgendmento(dataAgendamento);
                agendamento.setHorarioAgendamento(horarioAgendamento);
                agendamentoRepository.save(agendamento);
                System.out.println("depois do save");
                return "A data e horário estão disponíveis! Seu agendamento foi realizado com sucesso, " + paciente.getNomeCompleto() + ".";
            } else {
                return "Infelizmente, o horário solicitado não está disponível. Por favor, tente outro horário.";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
    
        // Log para conferir valores
        System.out.println("Data de agendamento: " + dataAgendamento);
        System.out.println("Horário de agendamento: " + horarioAgendamento);
        System.out.println("ID da especialidade: " + especialidadeEscolhida.getId());
    
        // Verificar se existe algum agendamento na mesma data e especialidade
        List<Agendamento> agendamentosNoDiaESpecialidade = agendamentoRepository.findByDataAndEspecialidade(dataAgendamento, especialidadeEscolhida.getId());
    
        // Log para verificar os agendamentos encontrados
        System.out.println("Agendamentos encontrados na mesma data e especialidade: " + agendamentosNoDiaESpecialidade.size());
    
        for (Agendamento agendamento : agendamentosNoDiaESpecialidade) {
            LocalTime horarioExistente = agendamento.getHorarioAgendamento();
            // Calcular diferença em minutos
            long diferencaEmMinutos = Math.abs(horarioExistente.until(horarioAgendamento, ChronoUnit.MINUTES));
    
            // Log para verificar a diferença calculada
            System.out.println("Diferença em minutos: " + diferencaEmMinutos);
    
            if (diferencaEmMinutos < 60) {
                System.out.println("Conflito detectado: o horário está dentro de 1 hora.");
                return false; // Horário conflita com um agendamento existente na mesma especialidade
            }
        }
    
        // Se passou por todas as verificações, está disponível
        return true;
    }
    
}
