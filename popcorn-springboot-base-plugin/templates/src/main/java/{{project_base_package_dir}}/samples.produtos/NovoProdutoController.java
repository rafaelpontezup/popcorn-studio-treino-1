package {{project_base_package}}.samples.produtos;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NovoProdutoController {

    @PostMapping("/api/v1/produtos")
    public ResponseEntity<?> cadastra(@RequestBody @Valid NovoProdutoRequest request) {

        // executa lógica de negócio

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

}