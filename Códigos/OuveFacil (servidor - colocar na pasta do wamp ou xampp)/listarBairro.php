<?php

  include_once("conexao.php");

  $query = "SELECT bairro.codBairro, bairro.nome as nomeBairro, cidade.nome as nomeCidade FROM `bancoOuveFacil`.`bairro`, `bancoOuveFacil`.`cidade` WHERE bairro.codCidade = cidade.codCidade";

  $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));


  while ($linha = mysqli_fetch_assoc($queryExec)) {
        $output[] = $linha;

  }

  print json_encode($output);
?>
