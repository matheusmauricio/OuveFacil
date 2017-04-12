package domain;

public class Endereco {

    private String rua;

    private int numero;

    private Bairro bairro;

    private Pessoa pessoa;

    public Endereco() {
    }

    /**
     * @return the nome
     */
    public String getRua() {
        return rua;
    }

    /**
     * @param nome the nome to set
     */
    public void setRua(String nome) {
        this.rua = nome;
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * @return the bairro
     */
    public Bairro getBairro() {
        return bairro;
    }

    /**
     * @param bairro the bairro to set
     */
    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    /**
     * @return the pessoa
     */
    public Pessoa getPessoa() {
        return pessoa;
    }

    /**
     * @param pessoa the pessoa to set
     */
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

}
