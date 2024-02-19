package com.project.concurrence.control.model;

import com.project.concurrence.control.controller.requests.CreateTransactionRequest;
import com.project.concurrence.control.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private Long valor;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private TransactionType tipo;
    @Column(insertable = false)
    private LocalDateTime criadoEm;

    public static Transaction toEntity(final CreateTransactionRequest request, final User user) {
        return Transaction.builder()
                .user(user)
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
