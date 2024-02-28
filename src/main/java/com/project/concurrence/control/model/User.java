package com.project.concurrence.control.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    private Long id;
    private Long saldo;
    private Long limite;

    public User copyWithNewBalance(final Long newBalance) {
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
