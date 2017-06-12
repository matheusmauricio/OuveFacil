<?php

  include_once("conexao.php");

  $query = "SELECT cidade.codCidade, cidade.nome as nomeCidade, uf.nome as nomeUf FROM `bancoOuveFacil`.`cidade`, `bancoOuveFacil`.`uf` WHERE cidade.sigla = uf.sigla";

  $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));


  while ($linha = mysqli_fetch_assoc($queryExec)) {
        $output[] = $linha;

  }

  print json_encode($output);
?>
