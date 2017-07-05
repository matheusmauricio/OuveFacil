<?php

  include_once("conexao.php");

  $query = "SELECT denuncia.codDenuncia, categoria.nome as nomeCategoria, bairro.nome as nomeBairro, cidade.nome as nomeCidade, 
  cidade.sigla
  FROM `bancoOuveFacil`.`denuncia`, `bancoOuveFacil`.`bairro`, `bancoOuveFacil`.`cidade`, `bancoOuveFacil`.`categoria`
  WHERE denuncia.codStatus = 2 AND denuncia.codBairro = bairro.codBairro AND bairro.codCidade = cidade.codCidade
  AND denuncia.codCategoria = categoria.codCategoria";

  $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));


  while ($linha = mysqli_fetch_assoc($queryExec)) {
        $output[] = $linha;

  }

  print json_encode($output);
?>
