package domain.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import domain.util.Image;

public class Denuncia {

    private int codDenuncia;
    private String descricao;
    private double latitude;
    private double longitude;
    private boolean anonimato;
    private String complementoStatus;
    private String midia1;
    private String midia2;
    private String midia3;
    private String midia4;
    private Usuario usuario;
    private Bairro bairro;
    private Categoria categoria;
    private Administrador administrador;
    private Status status;
    private String method;
    private Image image;
    private Integer existe;
    private Integer naoExiste;
    private String data;
    private String hora;

    public Denuncia(){
        this.image = new Image();
    }

    /*public Denuncia(String midia1, String method, String name, String email, int age, Image image){
        this.midia1 = midia1;
        this.method = method;
        this.name = name;
        this.email = email;
        this.age = age;
        this.image = image;
    } */

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getMidia1() {
        return midia1;
    }

    public void setMidia1(String midia1) {
        this.midia1 = midia1;
    }

    public String getMidia2() {
        return midia2;
    }

    public void setMidia2(String midia2) {
        this.midia2 = midia2;
    }

    public String getMidia3() {
        return midia3;
    }

    public void setMidia3(String midia3) {
        this.midia3 = midia3;
    }

    public String getMidia4() {
        return midia4;
    }

    public void setMidia4(String midia4) {
        this.midia4 = midia4;
    }

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

    public Integer getExiste() {
        return existe;
    }

    public void setExiste(Integer existe) {
        this.existe = existe;
    }

    public Integer getNaoExiste() {
        return naoExiste;
    }

    public void setNaoExiste(Integer naoExiste) {
        this.naoExiste = naoExiste;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    /*    @Override
    public String toString() {
        return this.nome;
    }
     */
}
