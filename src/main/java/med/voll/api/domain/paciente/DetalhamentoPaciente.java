package med.voll.api.domain.paciente;

import med.voll.api.domain.endereco.Endereco;

public record DetalhamentoPaciente(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone,
        Endereco endereco) {

    public DetalhamentoPaciente(Paciente paciente) {
        this(paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getEndereco());


    }
}
