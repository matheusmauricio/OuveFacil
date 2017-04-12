package domain;

public class TipoDeFilme {

    private int cdTipoDeFilme;

    private String nome;

    private int diasDePrazo;

    private float preco;

    private Filme[] filme;

    public TipoDeFilme() {
    }

    /**
     * @return the cdTipoDeFilme
     */
    public int getCdTipoDeFilme() {
        return cdTipoDeFilme;
    }

    /**
     * @param cdTipoDeFilme the cdTipoDeFilme to set
     */
    public void setCdTipoDeFilme(int cdTipoDeFilme) {
        this.cdTipoDeFilme = cdTipoDeFilme;
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
     * @return the diasDePrazo
     */
    public int getDiasDePrazo() {
        return diasDePrazo;
    }

    /**
     * @param diasDePrazo the diasDePrazo to set
     */
    public void setDiasDePrazo(int diasDePrazo) {
        this.diasDePrazo = diasDePrazo;
    }

    /**
     * @return the preco
     */
    public float getPreco() {
        return preco;
    }

    /**
     * @param preco the preco to set
     */
    public void setPreco(float preco) {
        this.preco = preco;
    }

    /**
     * @return the filme
     */
    public Filme[] getFilme() {
        return filme;
    }

    /**
     * @param filme the filme to set
     */
    public void setFilme(Filme[] filme) {
        this.filme = filme;
    }

}
