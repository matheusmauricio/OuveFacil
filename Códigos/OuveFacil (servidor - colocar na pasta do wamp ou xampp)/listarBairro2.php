<?php

  include_once("conexao.php");
  include_once("nomeConexao.php");

  if ($_POST) {
    $cidade = $_POST['cidade'];

    $query = "SELECT bairro.codBairro, bairro.nome as nomeBairro, cidade.nome as nomeCidade
    FROM `$nomeBanco`.`bairro`, `$nomeBanco`.`cidade` WHERE bairro.codCidade = cidade.codCidade
    AND cidade.nome = '$cidade'";

    $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    while ($linha = mysqli_fetch_assoc($queryExec)) {
      $output[] = $linha;
    }
  }

  print json_encode($output);
?>
