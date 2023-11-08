package med.voll.api.domain.paciente;

public record DadosListagemPaciente(
        Long id,
        String nome,
        String email,
        String cpg
) {
    public DadosListagemPaciente(Paciente paciente){
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getCpf()
        );
    }
}
