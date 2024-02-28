package com.project.concurrence.control.controller.requests;

import com.project.concurrence.control.controller.customValidators.EnumValidator;
import com.project.concurrence.control.controller.customValidators.LongValueValidator;
import com.project.concurrence.control.model.enums.TransactionType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
public class CreateTransactionRequest {

    @Positive
    @LongValueValidator(message = "O valor da transação é obrigatório")
    private String valor;
    @NotEmpty(message = "A descrição da transação é obrigatória")
    @Length(min = 1, max = 10, message = "O tamanho da chave deve ser de no mínimo 1 caractere e no máximo 10")
    private String descricao;
    @EnumValidator(message = "O tipo é obrigatório", enumClazz = TransactionType.class)
    private String tipo;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("valor", valor)
                .append("tipo", tipo)
                .append("descricao", descricao)
                .toString();
    }

    public Long getValor() {
        return Long.parseLong(valor);
    }
}
