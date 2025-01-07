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

 

  private Long idMedico;

  private Long idPaciente;

  public Agendamento build(LocalDate dataAnterior){

    if (dataAgendmento != null && !dataAgendmento.equals(dataAnterior)) {
      updateData = LocalDate.now();  
   }

    return Agendamento.builder()
    .dataAgendmento(dataAgendmento)
    .status(status)
    .horarioAgendamento(horarioAgendamento)
    .updateData(updateData)
    .idMedico(idMedico)
    .idPaciente(idPaciente)
    .build();
  }
}
