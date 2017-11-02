<?php

include_once("conexao.php");
include_once("nomeConexao.php");

    if ($_POST) {
        $sigla = $_POST['sigla'];
        $nome = $_POST['nome'];


        $query = "INSERT INTO `$nomeBanco`.`uf` VALUES('$sigla', '$nome')";
        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
