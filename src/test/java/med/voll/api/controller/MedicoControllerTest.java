package med.voll.api.controller;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJson;
    @Autowired
    private JacksonTester<DetalhamentoMedico> dadosDetalhamentoMedicoJson;
    @MockBean
    private MedicoRepository repository;

    @Test
    @DisplayName("Devolver HTTP 400 para infos INVÁLIDAS")
    @WithMockUser
    void cadastroMedicos_cenario1() throws Exception {

        var response = mvc.perform(post("/medicos")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    @DisplayName("Devolver HTTP 200 para infos VÁLIDAS")
    @WithMockUser
    void cadastroMedicos_cenario2() throws Exception {

        var dadosCadastro = new DadosCadastroMedico(
                "Medico",
                "123456",
                Especialidade.CARDIOLOGIA,
                "12344432121",
                "medico@voll.med",
                dadosEndereco());

        when(repository.save(any())).thenReturn(new Medico(dadosCadastro));

        var response = mvc.perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroMedicoJson.write(dadosCadastro).getJson()))
                     .andReturn().getResponse();

        var detalhamentoMedico = new DetalhamentoMedico(
                null,
                dadosCadastro.nome(),
                dadosCadastro.crm(),
                dadosCadastro.especialidade(),
                dadosCadastro.email(),
                dadosCadastro.telefone(),
                new Endereco(dadosCadastro.endereco()));

        var jsonEsperado = dadosDetalhamentoMedicoJson.write(detalhamentoMedico).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    private DadosEndereco dadosEndereco() {
               return new DadosEndereco(
                "rua xpto",
                "123",
                null,
                "bairro",
                "Brasilia",
                "DF",
                "00000000");
    }
}