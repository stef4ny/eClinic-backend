package br.com.eClinic.modelo.acesso;

import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;

import br.com.eClinic.util.entity.EntidadeNegocio;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Perfil")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Perfil extends EntidadeNegocio implements GrantedAuthority {
    
    public static final String ROLE_PACIENTE = "PACIENTE";
    public static final String ROLE_FUNCIONARIO_MEDICO = "ROLE_FUNCIONARIO_MEDICO"; // READ, WRITE, UPDATE.
   //public static final String ROLE_ADMIN = "ROLE_ADMIN"; // READ, DELETE, WRITE, UPDATE.
  
   private String nome;
  
   @Override
   public String getAuthority() {
       return this.nome;
   }

}