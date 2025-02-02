package br.com.eClinic.modelo.especialidades;

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
@Table(name = "Especialidade") // a partir dessa classe ele vai virar uma tabela no banco
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Especialidade extends EntidadeAuditavel {

  @Column(nullable = false, length = 100)
  private String nome;

 
}