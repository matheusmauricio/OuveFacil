<?php

class uf{
    private $sigla;
    private $nome;

    public function setSiglaUf($sigla){
        $this->sigla = $sigla;
    }
    public function getSiglaUf(){
        return $this->sigla;
    }
    public function setNome($nome){
        $this->nome = $nome;
    }
    public function getNome(){
        return $this->nome;
    }
}
?>
