<?php

class subCategoria{
    private $codSubCategoria;
    private $nome;

    public function setCodSubCategoria($codSubCategoria){
        $this->codSubCategoria = $codSubCategoria;
    }
    public function getCodSubCategoria(){
        return $this->codSubCategoria;
    }
    public function setNome($nome){
        $this->nome = $nome;
    }
    public function getNome(){
        return $this->nome;
    }
}
?>
