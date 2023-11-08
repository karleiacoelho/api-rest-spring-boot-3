package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorAntecenciaCancelamento implements ValidadorCancelamentoConsulta {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Override
    public void validar(DadosCancelamentoConsulta dados){

         var dataConsulta = consultaRepository.getReferenceById(dados.idConsulta());
         var agora = LocalDateTime.now();
         var diferencaEmHoras = Duration.between(agora, dataConsulta.getData()).toHours();

         if(diferencaEmHoras < 24) {
             throw new ValidacaoException("Erro! Observe a antecedência mínima para agendamento.");
         }

    }
}

