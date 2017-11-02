<?php

  include_once("../conexao.php");
  include_once("../nomeConexao.php");

  if ($_POST) {
    $codDenuncia = $_POST['codDenuncia'];
    $codAdministrador = $_POST['codAdministrador'];

    $query = "UPDATE `$nomeBanco`.`denuncia` SET `codStatus` = '2', `codAdministrador` = '$codAdministrador'
    WHERE `codDenuncia` = '$codDenuncia'";
    $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));
  }

  header("location: novasDemandas.php");

  ?>
