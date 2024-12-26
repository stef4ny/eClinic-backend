package br.com.eClinic.api.agendamento;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.eClinic.modelo.agendamento.Agendamento;
import br.com.eClinic.util.entity.EntidadeAuditavel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoRequest extends EntidadeAuditavel {

    private Long IdMedico ;

    private Long IdPaciente;

    @Length(max = 100, message = "O motivo da consulta deverá ter no máximo {max} caracteres")
    private String motivoConsulta;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataConsulta;

    private LocalTime horaConsulta;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate upadateData;

    private String status;

    private String linkMeet;

    public Agendamento build(){

      return Agendamento.builder()
      .motivoConsulta(motivoConsulta)
      .dataConsulta(dataConsulta)
      .horaConsulta(horaConsulta)
      .upadateData(upadateData)
      .status(status)
      .linkMeet(linkMeet)
      .build();

    }

    
}
