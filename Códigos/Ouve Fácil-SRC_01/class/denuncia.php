<?php

class denuncia{
    private $codDenuncia;
    private $descricao;
    private $latitude;
    private $longitude;
    private $anonimato;
    private $complementoStatus;

    public function setCodDenuncia($codDenuncia){
        $this->codDenuncia = $codDenuncia;
    }
    public function getCodDenuncia(){
        return $this->codDenuncia;
    }
    public function setDescricao($descricao){
        $this->descricao = $descricao;
    }
    public function getDescricao(){
        return $this->descricao;
    }
    public function setLatitude($latitude){
      $this->latitude = $latitude;
    }
    public function getLatitude(){
      return $this->latitude;
    }
    public function setLongitude($longitude){
      $this->longitude = $longitude;
    }
    public function getLongitude(){
      return $this->longitude;
    }
    public function setAnonimato($anonimato){
      $this->anonimato = $anonimato;
    }
    public function getAnonimato(){
      return $this->anonimato;
    }
    public function setComplementoStatus($complementoStatus){
      $this->complementoStatus = $complementoStatus;
    }
    public function getComplementoStatus(){
      return $this->complementoStatus;
    }

}
?>
