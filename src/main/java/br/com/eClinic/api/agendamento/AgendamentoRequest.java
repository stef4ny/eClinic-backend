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

   @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataAgendmento;


  private String status;

  private String motivo;

  private LocalTime horarioAgendamento;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate updateData;

  public Agendamento build(){

    return Agendamento.builder()
        .dataAgendmento(dataAgendmento)
        .status(status)
        .horarioAgendamento(horarioAgendamento)
        .updateData(updateData)
        .build();
  }
}
