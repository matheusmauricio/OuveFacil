package domain;

public class Filme {

    private int cdFilme;

    private String titulo;

    private String genero;

    private float tempoDuracao;

    private Fita[] fita;

    private TipoDeFilme tipoDeFilme;

    public Filme() {
    }

    /**
     * @return the cdFilme
     */
    public int getCdFilme() {
        return cdFilme;
    }

    /**
     * @param cdFilme the cdFilme to set
     */
    public void setCdFilme(int cdFilme) {
        this.cdFilme = cdFilme;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * @return the tempoDuracao
     */
    public float getTempoDuracao() {
        return tempoDuracao;
    }

    /**
     * @param tempoDuracao the tempoDuracao to set
     */
    public void setTempoDuracao(float tempoDuracao) {
        this.tempoDuracao = tempoDuracao;
    }

    /**
     * @return the fita
     */
    public Fita[] getFita() {
        return fita;
    }

    /**
     * @param fita the fita to set
     */
    public void setFita(Fita[] fita) {
        this.fita = fita;
    }

    /**
     * @return the tipoDeFilme
     */
    public TipoDeFilme getTipoDeFilme() {
        return tipoDeFilme;
    }

    /**
     * @param tipoDeFilme the tipoDeFilme to set
     */
    public void setTipoDeFilme(TipoDeFilme tipoDeFilme) {
        this.tipoDeFilme = tipoDeFilme;
    }
    
    @Override
    public String toString(){
        return this.getTitulo();
    }

}
