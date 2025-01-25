package br.com.eClinic.api.agendamento;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.eClinic.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


import org.hibernate.annotations.SQLRestriction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "disponibilidade")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Disponibilidade  extends EntidadeAuditavel{

  @JsonFormat(pattern = "dd/MM/yyyy")
  @Column
   private LocalDate data;

   @Column
   private LocalTime hora;
}