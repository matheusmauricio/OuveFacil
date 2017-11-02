<?php

  include_once("conexao.php");
  include_once("nomeConexao.php");


  if ($_POST) {
    $uf = $_POST['uf'];

    $query = "SELECT cidade.codCidade, cidade.nome as nomeCidade, uf.nome as nomeUf
    FROM `$nomeBanco`.`cidade`, `$nomeBanco`.`uf` WHERE cidade.sigla = uf.sigla AND uf.sigla = '$uf'";

    $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    while ($linha = mysqli_fetch_assoc($queryExec)) {
      $output[] = $linha;
    }
  }

  print json_encode($output);
?>
