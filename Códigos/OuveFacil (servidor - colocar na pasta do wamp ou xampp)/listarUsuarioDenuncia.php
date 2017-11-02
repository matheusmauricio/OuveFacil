<?php

  include_once("conexao.php");
  include_once("nomeConexao.php");
  
  if ($_POST) {
    $codUsuario = $_POST['codUsuario'];
    $codDenuncia = $_POST['codDenuncia'];

    $query = "SELECT * FROM `$nomeBanco`.`usuarioDenuncia` WHERE `codUsuario` = '$codUsuario'
    AND `codDenuncia` = '$codDenuncia'";
    $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    while ($linha = mysqli_fetch_assoc($queryExec)) {
      $output[] = $linha;
    }
  }

  print json_encode($output);

?>
