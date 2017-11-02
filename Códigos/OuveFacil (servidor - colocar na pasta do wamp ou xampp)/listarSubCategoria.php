<?php

  include_once("conexao.php");
  include_once("nomeConexao.php");

  $query = "SELECT * FROM `$nomeBanco`.`subcategoria`";
  $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));


while ($linha = mysqli_fetch_assoc($queryExec)) {
      $output[] = $linha;
}

print json_encode($output);

?>
