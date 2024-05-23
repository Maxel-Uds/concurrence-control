package com.project.concurrence.control.controller.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.concurrence.control.model.Transaction;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record TransactionResponse(
        long valor,
        String descricao,
        String tipo,
        @JsonProperty("realizada_em")
        String realizadaEm
) {

    public static TransactionResponse of(final Transaction transaction) {
        return new TransactionResponse(transaction.getValor(), transaction.getDescricao(), transaction.getTipo(), transaction.getCriadoEm().toString());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("valor", valor)
                .append("tipo", tipo)
                .append("descricao", descricao)
                .append("realizada_em", realizadaEm)
                .toString();
    }
}
