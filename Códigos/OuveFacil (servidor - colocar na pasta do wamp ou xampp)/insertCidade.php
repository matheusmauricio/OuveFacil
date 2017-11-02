<?php

include_once("conexao.php");
include_once("nomeConexao.php");

    if ($_POST) {
        $nome = $_POST['nome'];
        $sigla = $_POST['sigla'];

        $query = "INSERT INTO `$nomeBanco`.`cidade` VALUES('', '$nome', '$sigla')";
        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
