package domain;

public class Pessoa {

    private int cdPessoa;

    private String nome;

    private String cpf;

    private Endereco endereco;

    public Pessoa() {
    }

    /**
     * @return the cdPessoa
     */
    public int getCdPessoa() {
        return cdPessoa;
    }

    /**
     * @param cdPessoa the cdPessoa to set
     */
    public void setCdPessoa(int cdPessoa) {
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
