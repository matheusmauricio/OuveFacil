<?php

include_once("conexao.php");
include_once("nomeConexao.php");

if ($_POST) {
  $status = $_POST['status'];

  $query = "SELECT COUNT(*), bairro.nome FROM `$nomeBanco`.`denuncia`, `$nomeBanco`.`status`, `$nomeBanco`.`bairro`
  WHERE status.nome = '$status' AND status.codStatus = denuncia.codStatus AND denuncia.codBairro = bairro.codBairro
  GROUP BY bairro.nome";

  $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

  while ($linha = mysqli_fetch_assoc($queryExec)) {
    $output[] = $linha;

  }
}
  print json_encode($output);
?>
