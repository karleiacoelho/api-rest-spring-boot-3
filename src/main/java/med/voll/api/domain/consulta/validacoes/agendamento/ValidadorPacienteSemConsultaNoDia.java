package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class ValidadorPacienteSemConsultaNoDia implements ValidadorAgendamentoConsulta {
    @Autowired
    private ConsultaRepository repository;
    public void validar(DadosAgendamentoConsulta dados){
         var primeiroHorario = dados.data().withHour(7);
         var ultimoHorario = dados.data().withHour(18);
         var pacientePossuiOutraConsultaNoDia = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);
         var agora = LocalDateTime.now();

         if(pacientePossuiOutraConsultaNoDia) {
             throw new ValidacaoException("Erro! Paciente já tem consulta nesta data.");
         }
    }
}

