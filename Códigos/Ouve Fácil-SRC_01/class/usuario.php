<?php

class usuario{
    private $codUsuario;
    private $nome;
    private $login;
    private $senha;
    private $cpfCnpj;

    public function setCodUsuario($codUsuario){
        $this->codUsuario = $codUsuario;
    }
    public function getCodUsuario(){
        return $this->codUsuario;
    }
    public function setNome($nome){
        $this->nome = $nome;
    }
    public function getNome(){
        return $this->nome;
    }
    public function setLogin($login){
        $this->login = $login;
    }
    public function getLogin(){
        return $this->login;
    }
    public function setSenha($senha){
        $this->senha = $senha;
    }
    public function getSenha(){
        return $this->senha;
    }
    public function setCpfCnpj($cpfCnpj){
        $this->cpfCnpj = $cpfCnpj;
    }
    public function getCpfCnpj(){
        return $this->cpfCnpj;
    }
}
?>
