<?php

  include_once("conexao.php");
  include_once("nomeConexao.php");

if ($_POST) {
  $bairro = $_POST['bairro'];

  $query = "SELECT COUNT(*)
  FROM `$nomeBanco`.`denuncia`, `$nomeBanco`.`bairro`
  WHERE denuncia.codBairro = bairro.codBairro AND bairro.nome = '$bairro'";

  $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));


  while ($linha = mysqli_fetch_assoc($queryExec)) {
        $output[] = $linha;

  }
}
  print json_encode($output);
?>
