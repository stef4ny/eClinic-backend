package br.com.eClinic.api.administrador.gerenciadorMedico;

import org.hibernate.validator.constraints.Length;

import br.com.eClinic.modelo.administrador.GerenciamentoMedico.GerenciadorMedico;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GerenciamentoMedicoRequest {

    @NotBlank(message = "O nome é de preenchimento obrigatório")
    @Length(max = 100, message = "O Nome deverá ter no máximo {max} caracteres")
    private String nomeCompleto;

    @NotBlank(message = "A especialidades é de preenchimento obrigatorio")
    private String especialidades;

    public GerenciadorMedico build() {

        return GerenciadorMedico.builder()
                .nomeCompleto(nomeCompleto)
                .especialidades(especialidades)
                .build();

    }
}