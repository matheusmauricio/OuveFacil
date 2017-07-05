<?php

  include_once("conexao.php");

  if ($_POST) {
    $subCategoria = $_POST['subCategoria'];

    $query = "SELECT categoria.codCategoria, categoria.nome as nomeCategoria, subcategoria.nome as nomeSubCategoria
    FROM `bancoOuveFacil`.`categoria`, `bancoOuveFacil`.`subCategoria`
    WHERE categoria.codSubCategoria = subCategoria.codSubCategoria AND subCategoria.nome = '$subCategoria' ";

    $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    while ($linha = mysqli_fetch_assoc($queryExec)) {
      $output[] = $linha;
    }
  }

  print json_encode($output);
?>
