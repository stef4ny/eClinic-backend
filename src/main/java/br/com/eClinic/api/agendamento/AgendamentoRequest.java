package br.com.eClinic.api.agendamento;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.eClinic.modelo.agendamento.Agendamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoRequest {

  private Long idMedico;

  private Long idEspecialidade;

   @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataAgendmento;

  private LocalTime horarioAgendamento;




  public Agendamento build(){


    return Agendamento.builder()
    .dataAgendmento(dataAgendmento)
    .horarioAgendamento(horarioAgendamento)
    .build();
  }
}
