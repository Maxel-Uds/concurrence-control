package com.project.concurrence.control.model;

import com.project.concurrence.control.controller.requests.CreateTransactionRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Long id;
    private Long userId;
    private Long valor;
    private String descricao;
    private String tipo;
    private String criadoEm;

    public static Transaction toEntity(final CreateTransactionRequest request, final User user) {
        return Transaction.builder()
                .userId(user.getId())
                .valor(request.getValor())
                .descricao(request.getDescricao())
                .tipo(request.getTipo())
                .build();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("valor", valor)
                .append("tipo", tipo)
                .append("descricao", descricao)
                .append("criadoEm", criadoEm)
                .toString();
    }
}
