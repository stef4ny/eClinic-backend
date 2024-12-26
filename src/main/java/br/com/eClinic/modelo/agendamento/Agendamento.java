package br.com.eClinic.modelo.agendamento;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.SQLRestriction;

import br.com.eClinic.modelo.medico.Medico;
import br.com.eClinic.modelo.paciente.Paciente;
import br.com.eClinic.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Agendamento")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento extends EntidadeAuditavel {

   @ManyToOne
   @JoinColumn(name = "idMedico", nullable = false)
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "idPaciente", nullable = false)
    private Paciente paciente;


    @Column
    private String motivoConsulta;

    @Column 
    private LocalDate dataConsulta;

    @Column
    private LocalTime horaConsulta;

    @Column
    private LocalDate upadateData;

    @Column (length = 100)
    private String status;

    @Column 
    private String linkMeet;
  
}
