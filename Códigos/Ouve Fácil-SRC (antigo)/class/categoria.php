<?php

class categoria{
    private $codCategoria;
    private $nome;

    public function setCodCategoria($codCategoria){
        $this->codCategoria = $codCategoria;
    }
    public function getCodCategoria(){
        return $this->codCategoria;
    }
    public function setNome($nome){
        $this->nome = $nome;
    }
    public function getNome(){
        return $this->nome;
    }
}
?>
