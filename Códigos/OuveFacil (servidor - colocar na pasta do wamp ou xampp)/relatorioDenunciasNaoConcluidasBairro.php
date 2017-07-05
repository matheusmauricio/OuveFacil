<?php

  include_once("conexao.php");

  if ($_POST) {
    $bairro = $_POST['bairro'];
    //$bairro = 'Morro Grande';
  $query = "SELECT denuncia.codDenuncia as codDenuncia, categoria.nome as nomeCategoria, bairro.nome as nomeBairro,
  cidade.nome as nomeCidade, cidade.sigla as sigla
  FROM `bancoOuveFacil`.`denuncia`, `bancoOuveFacil`.`bairro`, `bancoOuveFacil`.`cidade`, `bancoOuveFacil`.`categoria`
  WHERE denuncia.codStatus = 2 AND denuncia.codBairro = bairro.codBairro AND bairro.codCidade = cidade.codCidade
  AND denuncia.codCategoria = categoria.codCategoria AND bairro.nome = '$bairro'";

  $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));


  while ($linha = mysqli_fetch_assoc($queryExec)) {
        $output[] = $linha;

  }
}
  print json_encode($output);
?>
