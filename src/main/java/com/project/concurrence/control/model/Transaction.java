package com.project.concurrence.control.model;

import com.project.concurrence.control.controller.requests.CreateTransactionRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
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
    private String tipo;
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
