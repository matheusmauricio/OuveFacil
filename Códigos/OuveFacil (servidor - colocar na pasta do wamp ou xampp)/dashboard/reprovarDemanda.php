<?php

  include_once("../conexao.php");
  include_once("../nomeConexao.php");

  if ($_POST) {
    $codDenuncia = $_POST['codDenuncia'];

    $query = "UPDATE `$nomeBanco`.`denuncia` SET `codStatus` = '7' WHERE `codDenuncia` = '$codDenuncia'";
    $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));
  }

  header("location: novasDemandas.php");

  ?>
