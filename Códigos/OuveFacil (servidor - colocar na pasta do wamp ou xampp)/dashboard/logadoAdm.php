<?php

  setcookie("logadoAdm", 'sim', time()+3600);

  //header("location: index.php");

  if(sprintf('location: %s', $_SERVER['HTTP_REFERER']) == "location: http://127.0.0.1/OuveFacil/dashboard/loginAdm.php"){
    header(sprintf('location: homeAdm.php'));
  }else{
  header(sprintf('location: %s', $_SERVER['HTTP_REFERER']));
  }
/*
  if(sprintf('location: %s', $_SERVER['HTTP_REFERER']) == "location: http://matheusmauricio.esy.es/dashboard/loginAdm.php"){
    header(sprintf('location: homeAdm.php'));
  }else{
  header(sprintf('location: %s', $_SERVER['HTTP_REFERER']));
  }
*/
?>
