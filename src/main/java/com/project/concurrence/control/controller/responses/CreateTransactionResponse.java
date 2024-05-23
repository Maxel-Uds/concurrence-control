package com.project.concurrence.control.controller.responses;

import com.project.concurrence.control.model.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record CreateTransactionResponse(
        long limite,
        long saldo
) {

    public static CreateTransactionResponse of(final User user) {
        return new CreateTransactionResponse(user.getLimite(), user.getSaldo());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("limite", limite)
                .append("saldo", saldo)
                .toString();
    }
}
