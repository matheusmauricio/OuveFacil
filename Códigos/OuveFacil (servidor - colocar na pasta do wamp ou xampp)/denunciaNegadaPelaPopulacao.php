<?php

  include_once("conexao.php");
  include_once("nomeConexao.php");

  if ($_POST) {
      $codDenuncia = $_POST['codDenuncia'];
      $codStatus = 3; // É o código referente ao status "Concluída por negação popular"

      $query = "UPDATE `$nomeBanco`.`denuncia` SET `codStatus` = '$codStatus'
      WHERE `codDenuncia` = '$codDenuncia'";

      $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

  }
?>
