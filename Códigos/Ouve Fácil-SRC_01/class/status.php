<?php

class status{
    private $codStatus;
    private $nome;

    public function setCodStatus($codstatus){
        $this->codStatus = $codStatus;
    }
    public function getCodStatus(){
        return $this->codStatus;
    }
    public function setNome($nome){
        $this->nome = $nome;
    }
    public function getNome(){
        return $this->nome;
    }
}
?>
