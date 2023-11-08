package med.voll.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private String logradouro;
    private String numero;
    private String bairro;
    private String complemento;
    private String cidade;
    private String uf;
    private String cep;

    public Endereco (DadosEndereco dados) {
        this.logradouro = dados.logradouro();
        this.numero = dados.numero();
        this.bairro = dados.bairro();
        this.complemento = dados.complemento();
        this.cidade = dados.cidade();
        this.uf = dados.uf();
        this.cep = dados.cep();
    }
    public void atualizarInformacoes(DadosEndereco dados) {
        if(this.logradouro != null){
            this.logradouro = dados.logradouro();
        }
        if(this.bairro != null){
            this.bairro = dados.bairro();
        }
        if(this.numero != null){
            this.numero = dados.numero();
        }
        if(this.complemento != null){
            this.complemento = dados.complemento();
        }
        if(this.cidade != null){
            this.cidade = dados.cidade();
        }
        if(this.uf != null){
            this.uf = dados.uf();
        }
        if(this.cep != null){
            this.cep = dados.cep();
        }
    }

}