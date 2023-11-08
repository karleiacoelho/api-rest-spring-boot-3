package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoConsulta {

    public void validar(DadosAgendamentoConsulta dados){
         var dataConsulta = dados.data();
         var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
         var antesAberturaClinica = dataConsulta.getHour() < 7;
         var depoisEncerramentoClinica = dataConsulta.getHour() > 18;

        if(domingo || antesAberturaClinica || depoisEncerramentoClinica) {
            throw new ValidacaoException(
                    "Data e/ou horário inválidos! Observe os horários de atnedimentos da clínica.");
        }
    }
}

