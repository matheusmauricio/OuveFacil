<?php

  setcookie("logadoAdm", 'nao', time()+3600);
  setcookie("administrador", null, time()+3600);
  //header("location: index.php");
  header("location: loginAdm.php");

?>
