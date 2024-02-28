package com.project.concurrence.control.controller.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.concurrence.control.model.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public record TransactionHistoricResponse(
        BalanceResponse saldo,
        @JsonProperty("ultimas_transacoes")
        List<TransactionResponse> ultimasTransacoes
) {

    public static TransactionHistoricResponse of(final User user, final List<TransactionResponse> transactions) {
        return new TransactionHistoricResponse(BalanceResponse.of(user), transactions);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("saldo", saldo)
                .append("ultimas_transacoes", ultimasTransacoes)
                .toString();
    }
}
