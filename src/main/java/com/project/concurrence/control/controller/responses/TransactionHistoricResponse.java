package com.project.concurrence.control.controller.responses;

import com.project.concurrence.control.model.Transaction;
import com.project.concurrence.control.model.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public record TransactionHistoricResponse(
        BalanceResponse saldo,
        List<TransactionResponse> ultimas_transacoes
) {

    public static TransactionHistoricResponse of(final User user, final List<Transaction> transactions) {
        return new TransactionHistoricResponse(BalanceResponse.of(user), transactions.stream().map(TransactionResponse::of).toList());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("saldo", saldo)
                .append("ultimas_transacoes", ultimas_transacoes)
                .toString();
    }
}
