package br.com.eClinic.util.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
//import br.com.eClinic.util.entity.EntidadeNegocio;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Version;

public class EntidadeAuditavel extends EntidadeNegocio {
    @JsonIgnore
    @Version
    private Long versao;

    @JsonIgnore
    @CreatedDate
    private LocalDate dataCriacao;

    @JsonIgnore
    @LastModifiedDate
    private LocalDate dataUltimaModificacao;

    @JsonIgnore
    @Column
   private Long criadoPor; // Id do usuário que o criou

    @JsonIgnore
    @Column
   private Long ultimaModificacaoPor; // Id do usuário que fez a última alteração
}
