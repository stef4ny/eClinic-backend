package br.com.eClinic.modelo.meet;

import br.com.eClinic.modelo.agendamento.Agendamento;
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
@Table(name = "MeetLink")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleMeetLink extends EntidadeAuditavel {

  

    @ManyToOne
    private Agendamento agendamento;

    @Column(nullable = false)
    private String linkMeet;

}