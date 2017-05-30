<?php

  include_once("conexao.php");

  $query = "SELECT * FROM `bancoOuveFacil`.`categoria`";
  $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));
  

?>
