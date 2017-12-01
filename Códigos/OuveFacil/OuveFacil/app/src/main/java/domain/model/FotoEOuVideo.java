package domain.model;

public class FotoEOuVideo {

    private int codFotoVideo;
    private String urlFotoVideo;
    private Denuncia denuncia;

    public int getCodFotoVideo() {
        return codFotoVideo;
    }

    public void setCodFotoVideo(int codFotoVideo) {
        this.codFotoVideo = codFotoVideo;
    }

    public String getUrlFotoVideo() {
        return urlFotoVideo;
    }

    public void setUrlFotoVideo(String urlFotoVideo) {
        this.urlFotoVideo = urlFotoVideo;
    }

    public Denuncia getDenuncia() {
        return denuncia;
    }

    public void setDenuncia(Denuncia denuncia) {
        this.denuncia = denuncia;
    }

    /* @Override
    public String toString() {
        return this.nome;
    }
    */
    
}
