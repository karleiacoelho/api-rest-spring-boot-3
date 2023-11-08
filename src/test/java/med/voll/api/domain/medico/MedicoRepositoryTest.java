package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.xmlunit.diff.ElementSelector;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {
    @Autowired
    private MedicoRepository repository;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Devolver null quando único médico cadastrado nao estiver disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario1() {
        //Geralmente os métodos são dividos em 3 blocos
        //given ou arrange (DADAS ESSAS INFORMAÇÕES...)
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.ORTOPEDIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        //when ou act (QUANDO EU FAÇO ESSA CHAMADA EXECUTO ESSAS AÇÃO...)
        var medicoLivre = repository.escolherMedicoAleatorioLivreNaData(Especialidade.ORTOPEDIA, proximaSegundaAs10);

        //then ou assert (ENTÃO, ESPERO ESSE RESULTADO.)
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Devolver medico quando estiver disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario2() {
        //Geralmente os métodos são dividos em 3 blocos
        //given ou arrange(DADAS ESSAS INFORMAÇÕES...)
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.ORTOPEDIA);

        //when ou act(QUANDO EU FAÇO ESSA CHAMADA EXECUTO ESSAS AÇÃO...)
        var medicoLivre = repository.escolherMedicoAleatorioLivreNaData(Especialidade.ORTOPEDIA, proximaSegundaAs10);

        //then ou assrt(ENTÃO, ESPERO ESSE RESULTADO.)
        assertThat(medicoLivre).isEqualTo(medico);
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data, null));
    }
    private Medico cadastrarMedico(String nome, String crm, String email, Especialidade especialidade){
        var medico = new Medico(dadosMedico(nome, crm, email, especialidade));
        em.persist(medico);
        return medico;
    }
    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }
    private DadosCadastroMedico dadosMedico(String nome, String crm, String email, Especialidade especialidade){
        return new DadosCadastroMedico(
                nome,
                email,
                especialidade,
                crm,
                "61999999999",
                dadosEndereco()
        );
    }
    private DadosCadastroPaciente dadosPaciente(String nome, String cpf, String email){
        return new DadosCadastroPaciente(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }
    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "1234",
                "null",
                "bairro",
                "Brasilia",
                "DF",
                "12345678"
        );
    }
}