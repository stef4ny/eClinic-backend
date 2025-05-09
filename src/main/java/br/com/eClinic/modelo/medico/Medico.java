package br.com.eClinic.modelo.medico;

import java.time.LocalDate;
import org.hibernate.annotations.SQLRestriction;

import br.com.eClinic.modelo.acesso.Usuario;
import br.com.eClinic.modelo.especialidades.Especialidade;
import br.com.eClinic.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // ele torna a classe em uma entidade manipulada pela JPA
@Table(name = "Medico") // a partir dessa classe ele vai virar uma tabela no banco
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Medico extends EntidadeAuditavel {

  @ManyToOne
  private Especialidade especialidade;

  @OneToOne
  @JoinColumn(nullable = false)
  private Usuario usuario;

   /*@Column(nullable = false)
   @Enumerated(EnumType.STRING)
   private TipoUsuario tipo;*/

  @Column(nullable = false, length = 100)
  private String nomeCompleto;

  @Column
  private LocalDate dataNascimento;

  @Column(nullable = false, length = 100)
  private String email;

  @Column(nullable = false, length = 100)
  private String senha;

  @Column(nullable = false, length = 100)
  private String enderecoCidade;

  @Column(nullable = false, length = 2)
  private String enderecoUf;

  @Column(length = 100)
  private String descricao;

  @Column(nullable = false, unique = true)
  private String crm;

  @Column(nullable = false)
  private String linkMeet;

}