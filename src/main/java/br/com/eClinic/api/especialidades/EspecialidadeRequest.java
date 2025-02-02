package br.com.eClinic.api.especialidades;

import org.hibernate.validator.constraints.Length;

import br.com.eClinic.modelo.especialidades.Especialidade;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadeRequest {

    @NotBlank(message = "O nome é de preenchimento obrigatório")
    @Length(max = 100, message = "O Nome deverá ter no máximo {max} caracteres")
    private String nome;


    public Especialidade build() {

        return Especialidade.builder()
                .nome(nome)
                .build();
    }
}