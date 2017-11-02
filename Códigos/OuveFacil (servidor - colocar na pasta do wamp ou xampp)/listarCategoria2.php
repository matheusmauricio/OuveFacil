<?php

  include_once("conexao.php");
  include_once("nomeConexao.php");


    $query = "SELECT categoria.codCategoria, categoria.nome as nomeCategoria
    FROM `$nomeBanco`.`categoria`";

    $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    while ($linha = mysqli_fetch_assoc($queryExec)) {
      $output[] = $linha;
    }


  print json_encode($output);
  /*date_default_timezone_set('America/Sao_Paulo');
  echo date('d-m-Y H:i:s');*/
?>
