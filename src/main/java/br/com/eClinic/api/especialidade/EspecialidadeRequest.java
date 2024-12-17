package br.com.eClinic.api.especialidade;

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

    @NotBlank(message = "A descricao é de preenchimento obrigatorio")
    @Length(max = 300, message = "A descricao deverá ter no máximo {max} caracteres")
    private String descricao;

    public Especialidade build() {

        return Especialidade.builder()
                .nome(nome)
                .descricao(descricao)

                .build();
    }
}