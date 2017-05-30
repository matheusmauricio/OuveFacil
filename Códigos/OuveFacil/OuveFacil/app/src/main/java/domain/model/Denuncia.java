package domain.model;

import java.util.ArrayList;

public class Denuncia {

    private int codDenuncia;
    private String descricao;
    private double latitude;
    private double longitude;
    private boolean anonimato;
    private String complementoStatus;
    private ArrayList<FotoEOuVideo> fotoEOuVideo = new ArrayList();
    private Usuario usuario;
    private Bairro bairro;
    private Categoria categoria;
    private Administrador administrador;
    private Status status;

    public int getCodDenuncia() {
        return codDenuncia;
    }

    public void setCodDenuncia(int codDenuncia) {
        this.codDenuncia = codDenuncia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isAnonimato() {
        return anonimato;
    }

    public void setAnonimato(boolean anonimato) {
        this.anonimato = anonimato;
    }

    public String getComplementoStatus() {
        return complementoStatus;
    }

    public void setComplementoStatus(String complementoStatus) {
        this.complementoStatus = complementoStatus;
    }

    public ArrayList<FotoEOuVideo> getFotoEOuVideo() {
        return fotoEOuVideo;
    }

    public void setFotoEOuVideo(ArrayList<FotoEOuVideo> fotoEOuVideo) {
        this.fotoEOuVideo = fotoEOuVideo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /*    @Override
    public String toString() {
        return this.nome;
    }
     */
}
