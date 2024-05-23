package com.project.concurrence.control.model;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;
    private long saldo;
    private long limite;

    public User copyWithNewBalance(final long newBalance) {
        return User.builder()
                .id(this.id)
                .saldo(newBalance)
                .limite(this.limite)
                .build();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("limite", limite)
                .append("saldo", saldo)
                .toString();
    }
}
