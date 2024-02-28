package com.project.concurrence.control.controller.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.concurrence.control.model.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

public record BalanceResponse(
        Long limite,
        Long total,
        @JsonProperty("data_extrato")
        LocalDateTime dataExtrato
) {

    public static BalanceResponse of(final User user) {
        return new BalanceResponse(user.getLimite(), user.getSaldo(), LocalDateTime.now());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("limite", limite)
                .append("total", total)
                .append("data_extrato", dataExtrato)
                .toString();
    }
}
