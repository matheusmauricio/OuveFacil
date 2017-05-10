<?php

class bairro{
    private $codBairro;
    private $nome;

    public function setCodBairro($codBairro){
        $this->codBairro = $codBairro;
    }
    public function getCodBairro(){
        return $this->codBairro;
    }
    public function setNome($nome){
        $this->nome = $nome;
    }
    public function getNome(){
        return $this->nome;
    }
}
?>
