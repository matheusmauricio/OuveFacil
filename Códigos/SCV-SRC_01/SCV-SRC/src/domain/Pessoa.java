package domain;

public class Pessoa {
 
	private String cdPessoa;
	 
	private String nome;
	 
	private String cpf;
	 
	private Endereco endereco;

    /**
     * @return the cdPessoa
     */
    public String getCdPessoa() {
        return cdPessoa;
    }

    /**
     * @param cdPessoa the cdPessoa to set
     */
    public void setCdPessoa(String cdPessoa) {
        this.cdPessoa = cdPessoa;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the endereco
     */
    public Endereco getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
	 
}
 
