package domain;

public class Cidade {
 
	private int cdCidade;
	 
	private String nome;
	 
	private Bairro[] bairro;
	 
	private UF uF;

    /**
     * @return the cdCidade
     */
    public int getCdCidade() {
        return cdCidade;
    }

    /**
     * @param cdCidade the cdCidade to set
     */
    public void setCdCidade(int cdCidade) {
        this.cdCidade = cdCidade;
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
     * @return the bairro
     */
    public Bairro[] getBairro() {
        return bairro;
    }

    /**
     * @param bairro the bairro to set
     */
    public void setBairro(Bairro[] bairro) {
        this.bairro = bairro;
    }

    /**
     * @return the uF
     */
    public UF getuF() {
        return uF;
    }

    /**
     * @param uF the uF to set
     */
    public void setuF(UF uF) {
        this.uF = uF;
    }
	 
}
 
