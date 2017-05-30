package domain.model;

import android.app.Activity;

public class Administrador extends Activity{

    private int codAdministrador;
    private String nome;
    private String login;
    private String senha;
    private String cpfCnpj;


    public Administrador (){

    }

    public Administrador(int codAdministrador, String nome, String login, String senha, String cpfCnpj){
        this.codAdministrador = codAdministrador;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.cpfCnpj = cpfCnpj;
    }


    public int getCodAdministrador() {

        return codAdministrador;
    }

    public void setCodAdministrador(int codAdministrador) {
        this.codAdministrador = codAdministrador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {

        this.nome = nome;
    }

    public String getLogin() {

        return login;
    }

    public void setLogin(String login) {

        this.login = login;
    }

    public String getSenha() {

        return senha;
    }

    public void setSenha(String senha) {

        this.senha = senha;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    @Override
    public String toString() {
        return this.nome; 
    }
    
}
