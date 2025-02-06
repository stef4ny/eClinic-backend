package br.com.eClinic.modelo.medico;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoUsuario {
    ADMINISTRADOR("ADMINISTRADOR"),
    PACIENTE("PACIENTE");
    //FUNCIONARIO_MEDICO("FUNCIONARIO_MEDICO");

    private String tipo;

    private TipoUsuario(String tipo) {
        this.tipo = tipo;
    }

    @JsonValue
    public String getTipo() {
        return tipo;
    }

}
