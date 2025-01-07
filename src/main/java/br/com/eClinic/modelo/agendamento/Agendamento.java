package br.com.eClinic.modelo.agendamento;
import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.SQLRestriction;
import br.com.eClinic.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

  @Column (nullable = false)
  private LocalDate dataAgendmento;

  @Column 
  private String status;

  @Column
  private String motivo;
  
  @Column
  private LocalTime horarioAgendamento;

  @Column (nullable = false)
  private LocalDate updateData;


  
}
