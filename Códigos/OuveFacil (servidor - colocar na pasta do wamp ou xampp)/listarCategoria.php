<?php

  include_once("conexao.php");

  $query = "SELECT categoria.codCategoria, categoria.nome as catNome, subcategoria.nome as subCatNome FROM `bancoOuveFacil`.`categoria`, `bancoOuveFacil`.`subCategoria` WHERE categoria.codSubCategoria = subcategoria.codSubCategoria";

  $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

  //$linha = mysqli_fetch_object($queryExec);


  while ($linha = mysqli_fetch_object($queryExec)) {
        echo $linha->codCategoria;
        echo $linha->catNome;
        echo $linha->subCatNome;
  }

?>
