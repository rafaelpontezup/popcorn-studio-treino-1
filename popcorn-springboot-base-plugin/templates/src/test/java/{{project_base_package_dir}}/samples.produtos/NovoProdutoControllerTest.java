package {{project_base_package}}.samples.produtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class NovoProdutoControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper mapper;

    @Test
    @DisplayName("deve cadastrar novo produto")
    void t1() throws Exception {
        // scenario
        NovoProdutoRequest request = new NovoProdutoRequest(
                "iPad Mini",
                "iPad Mini 6a geração",
                3500.99
        );

        // action and validation
        mockMvc.perform(post("/api/v1/produtos")
                        .contentType(APPLICATION_JSON)
                        .content(toJson(request))
                        .header(ACCEPT_LANGUAGE, "en"))
                .andExpect(status().isCreated())
        ;
    }

    @Test
    @DisplayName("não deve cadastrar novo produto quando parametros invalidos")
    void t2() throws Exception {
        // scenario
        NovoProdutoRequest request = new NovoProdutoRequest(
                "",
                "a".repeat(2001),
                -0.01
        );

        // action and validation
        mockMvc.perform(post("/api/v1/produtos")
                        .contentType(APPLICATION_JSON)
                        .content(toJson(request))
                        .header(ACCEPT_LANGUAGE, "en"))
                .andExpect(status().isBadRequest())
        ;
    }

    /**
     * Converts an object payload to {@code String} in JSON format
     */
    public String toJson(Object payload) throws JsonProcessingException {
        return mapper.writeValueAsString(payload);
    }

}