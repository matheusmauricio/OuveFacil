package domain;

public class Bairro {
 
	private int cdBairro;
	 
	private String nome;
	 
	private Endereco[] endereco;
	 
	private Cidade cidade;

    /**
     * @return the cdBairro
     */
    public int getCdBairro() {
        return cdBairro;
    }

    /**
     * @param cdBairro the cdBairro to set
     */
    public void setCdBairro(int cdBairro) {
        this.cdBairro = cdBairro;
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
     * @return the endereco
     */
    public Endereco[] getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(Endereco[] endereco) {
        this.endereco = endereco;
    }

    /**
     * @return the cidade
     */
    public Cidade getCidade() {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
	 
}
 
