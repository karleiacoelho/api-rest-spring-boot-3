package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
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

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest //Se usa para subir o contexto completo do Spring para simular a classe Controller.
@AutoConfigureMockMvc //Se usa o Moc para testes de unidade. Simula uma requisição para a Controller,
                        //sem precisar chamar o Service, Repository, etc. O objetivo é somente testar se a
                        //Controller tá se coomportando como o esperado.
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;
    //JacksonTester é anotação usada para simular o Json (aqui, de entrada) dispensando a escrita manual.
    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;
    //Aqui, de saída)
    @MockBean
    private AgendaDeConsultas agendaDeConsultas;
    //Diz ao Spring que, quando precisar injetar este objeto no controller,
    // deve aplicar o mock, e não injetar uma agendaDeConsultas de verdade (sem isso, o select disparado no bd real).

    @Test
    @WithMockUser //Anotação que simula usuário logado.
    @DisplayName("Devolver código HTTP 400 para infos forem inválidas")
    void agendar_cenario1() throws Exception {

        var response = mvc.perform(post("/consultas"))
            .andReturn().getResponse();

     assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @WithMockUser //Anotação que simula usuário logado.
    @DisplayName("Devolver código HTTP 200 para infos válidas")
    void agendar_cenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.ORTOPEDIA;

        //Mockito -
        var dadosDetalhamento = new DadosDetalhamentoConsulta(
                null,
                2l,
                5l,
                data);
        when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);

        var response = mvc
                .perform(
                        post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON) //Para levar o cabeçalho na requisição.
                        .content(dadosAgendamentoConsultaJson.write(
                                new DadosAgendamentoConsulta(2l, 5l, data, especialidade)
                        ).getJson())
                      )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalhamentoConsultaJson
                .write(dadosDetalhamento)
                .getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}