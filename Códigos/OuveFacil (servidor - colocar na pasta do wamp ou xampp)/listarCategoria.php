<?php

  include_once("conexao.php");

  $query = "SELECT categoria.codCategoria, categoria.nome as nomeCategoria, subcategoria.nome as nomeSubCategoria FROM `bancoOuveFacil`.`categoria`, `bancoOuveFacil`.`subCategoria` WHERE categoria.codSubCategoria = subcategoria.codSubCategoria";

  $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));


  while ($linha = mysqli_fetch_assoc($queryExec)) {
        $output[] = $linha;

  }

  print json_encode($output);
?>
