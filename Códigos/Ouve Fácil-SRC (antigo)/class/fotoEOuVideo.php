<?php

class fotoEOuVideo{
    private $codFotoVideo;
    private $urlFotoVideo;

    public function setFotoVideo($codFotoVideo){
        $this->codFotoVideo = $codFotoVideo;
    }
    public function getFotoVideo(){
        return $this->codFotoVideo;
    }
    public function setUrlFotoVideo($urlFotoVideo){
        $this->urlFotoVideo = $urlFotoVideo;
    }
    public function getUrlFotoVideo(){
        return $this->urlFotoVideo;
    }
}
?>
