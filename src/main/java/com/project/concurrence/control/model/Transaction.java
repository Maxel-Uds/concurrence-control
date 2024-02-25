package com.project.concurrence.control.model;

import com.project.concurrence.control.controller.requests.CreateTransactionRequest;
import com.project.concurrence.control.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long valor;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private TransactionType tipo;
    @Column(insertable = false)
    private LocalDateTime criadoEm;

    public Transaction copyWithId(final Long id) {
        return Transaction.builder()
                .id(id)
                .userId(this.userId)
                .valor(this.valor)
                .descricao(this.descricao)
                .tipo(this.tipo)
                .build();
    }

    public static Transaction toEntity(final CreateTransactionRequest request, final User user) {
        return Transaction.builder()
                .userId(user.getId())
                .valor(request.getValor())
                .descricao(request.getDescricao())
                .tipo(TransactionType.valueOf(request.getTipo()))
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
