package com.project.concurrence.control.controller.responses;

import com.project.concurrence.control.model.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

public record BalanceResponse(
        Long limite,
        Long total,
        LocalDateTime data_extrato
) {

    public static BalanceResponse of(final User user) {
        return new BalanceResponse(user.getLimite(), user.getSaldo(), LocalDateTime.now());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("limite", limite)
                .append("total", total)
                .append("data_extrato", data_extrato)
                .toString();
    }
}
