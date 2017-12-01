<?php

  include_once("conexao.php");
  include_once("nomeConexao.php");

  if ($_POST) {
    $codDenuncia = $_POST['codDenuncia'];
    $codUsuario = $_POST['codUsuario'];
    $colaboracao = "Existe";

    $query = "UPDATE `$nomeBanco`.`denuncia` SET `existe` = `existe` + 1
    WHERE `denuncia`.`codDenuncia` = '$codDenuncia'";
    $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    $query2 = "INSERT INTO `$nomeBanco`.`usuariodenuncia` VALUES('', '$codUsuario', '$codDenuncia',
      '$colaboracao')";
    $queryExec2 = mysqli_query($con, $query2) or die ("Erro: " .mysqli_error($con));

  }

 ?>
