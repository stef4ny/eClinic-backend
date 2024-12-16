package br.com.eClinic.api.administrador;

import br.com.eClinic.modelo.administrador.GerenciadorMedico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GerenciamentoMedicoRequest {

    private String nomeCompleto;

    private String especialidades;

    public GerenciadorMedico build() {

        return GerenciadorMedico.builder()
                .nomeCompleto(nomeCompleto)
                .especialidades(especialidades)
                .build();

    }
}
