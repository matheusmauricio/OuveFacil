<?php

class cidade{
    private $codCidade;
    private $nome;

    public function setCodCidade($codCidade){
        $this->codCidade = $codCidade;
    }
    public function getCodCidade(){
        return $this->codCidade;
    }
    public function setNome($nome){
        $this->nome = $nome;
    }
    public function getNome(){
        return $this->nome;
    }
}
?>
