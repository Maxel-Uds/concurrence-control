package com.project.concurrence.control.controller.requests;

import com.project.concurrence.control.model.enums.TransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;

@Getter
public class CreateTransactionRequest {

    @NotNull(message = "O valor da transação é obrigatório")
    @Min(value = 1, message = "O valor da transação deve ser maior que 0")
    private Long valor;
    @NotEmpty(message = "A descrição da transação é obrigatória")
    @Length(min = 1, max = 10, message = "O tamanho da chave deve ser de no mínimo 1 caractere e no máximo 10")
    private String descricao;
    @NotNull(message = "O tipo é obrigatório")
    private TransactionType tipo;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("valor", valor)
                .append("tipo", tipo)
                .append("descricao", descricao)
                .toString();
    }
}
