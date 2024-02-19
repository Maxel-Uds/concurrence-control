package com.project.concurrence.control.controller.responses;

import com.project.concurrence.control.model.Transaction;
import com.project.concurrence.control.model.enums.TransactionType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

public record TransactionResponse(
        Long valor,
        String descricao,
        TransactionType tipo,
        LocalDateTime realizada_em
) {

    public static TransactionResponse of(final Transaction transaction) {
        return new TransactionResponse(transaction.getValor(), transaction.getDescricao(), transaction.getTipo(), transaction.getCriadoEm());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("valor", valor)
                .append("tipo", tipo)
                .append("descricao", descricao)
                .append("realizada_em", realizada_em)
                .toString();
    }
}
