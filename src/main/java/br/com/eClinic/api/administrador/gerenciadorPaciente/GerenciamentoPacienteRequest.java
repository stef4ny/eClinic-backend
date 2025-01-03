package br.com.eClinic.api.administrador.gerenciadorPaciente;

import org.hibernate.validator.constraints.Length;

import br.com.eClinic.modelo.administrador.GerenciamentoPaciente.GerenciadorPaciente;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GerenciamentoPacienteRequest {

  @NotBlank(message = "O nome é de preenchimento obrigatório")
  @Length(max = 100, message = "O Nome deverá ter no máximo {max} caracteres")
  private String nomeCompleto;

  public GerenciadorPaciente build() {

    return GerenciadorPaciente.builder()
        .nomeCompleto(nomeCompleto)
        .build();
  }

}
