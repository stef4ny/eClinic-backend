package br.com.eClinic.api.especialidade;



import br.com.eClinic.modelo.especialidades.Especialidade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadeRequest {

    private String nome;
    private String descricao;

public Especialidade build() {

    return Especialidade.builder()
    .nome(nome)
    .descricao(descricao)

    .build();
}
}