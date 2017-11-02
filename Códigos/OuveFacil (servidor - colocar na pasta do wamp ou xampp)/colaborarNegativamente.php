<?php

  include_once("conexao.php");
  include_once("nomeConexao.php");

  if ($_POST) {
    $codDenuncia = $_POST['codDenuncia'];
    $codUsuario = $_POST['codUsuario'];
    $colaboracao = $_POST['colaboracao'];

    $query = "UPDATE `$nomeBanco`.`denuncia` SET `naoExiste` = `naoExiste` + 1
    WHERE `denuncia`.`codDenuncia` = '$codDenuncia'";
    $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    $query2 = "INSERT INTO `$nomeBanco`.`usuarioDenuncia` VALUES('', '$codUsuario', '$codDenuncia',
      '$colaboracao')";
    $queryExec2 = mysqli_query($con, $query2) or die ("Erro: " .mysqli_error($con));

  }

 ?>
