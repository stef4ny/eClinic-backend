package br.com.eClinic.modelo.agendamento;
import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.SQLRestriction;

import br.com.eClinic.modelo.medico.Medico;
import br.com.eClinic.modelo.especialidades.Especialidade;

import br.com.eClinic.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "agendamento")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Agendamento extends EntidadeAuditavel {


  @ManyToOne
  private Medico medico;

  @ManyToOne
  private Especialidade especialidade;


  @Column (nullable = false)
  private LocalDate dataAgendmento;

  @Column 
  private String status;

  @Column  (nullable = false)
  private String motivo;
  
  @Column
  private LocalTime horarioAgendamento;

  @Column (nullable = false)
  private LocalDate updateData;

  @Column
  private Long idMedico;

  @Column
  private Long idPaciente;


  
}