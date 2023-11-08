package med.voll.api.domain.medico;

import med.voll.api.domain.endereco.Endereco;

public record DetalhamentoMedico(Long id,
                                 String nome,
                                 String crm,
                                 Especialidade especialidade,
                                 String email,
                                 String telefone,
                                 Endereco endereco) {
    public DetalhamentoMedico(Medico medico) {
        this(medico.getId(),
                medico.getNome(),
                medico.getCrm(),
                medico.getEspecialidade(),
                medico.getEmail(),
                medico.getTelefone(),
                medico.getEndereco());
    }
}
