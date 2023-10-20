package {{project_base_package}}.samples.produtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record NovoProdutoRequest(
        @NotBlank @Size(max = 60) String nome,
        @NotBlank @Size(max = 2000) String descricao,
        @NotNull @Positive Double preco
) {
}