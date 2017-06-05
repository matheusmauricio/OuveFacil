<?php

  include_once("conexao.php");

  $query = "SELECT * FROM `bancoOuveFacil`.`administrador`";
  $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

  //$linha = mysqli_fetch_object($queryExec);


  /*while ($linha = mysqli_fetch_object($queryExec)) {
        echo $linha->nome;
        echo $linha->login;
        echo $linha->senha;
        echo $linha->cpfCnpj;
  }
*/


while ($linha = mysqli_fetch_assoc($queryExec)) {
      $output[] = $linha;
}

print json_encode($output);

/*
  $result["errorcode"] = 0;

  $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));
  $linha = mysqli_fetch_object($queryExec);

  $countrow = mysql_affected_rows();
  $items = array();

  while($row = mysql_fetch_object($rs)){
    array_push($items, $row);
  }

  if($countrow > 0){
    $result["errorcode"] ="1";
    $result["data"] = $items;
  } else{
    $result["errormsg"] = "Não existem dados";
  }

  echo json_encode($result);
*/

?>
