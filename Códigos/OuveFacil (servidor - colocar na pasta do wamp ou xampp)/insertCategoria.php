<?php

include_once("conexao.php");
include_once("nomeConexao.php");

    if ($_POST) {
        $nome = $_POST['nome'];
        $codSubCategoria = $_POST['codSubCategoria'];


        $query = "INSERT INTO `$nomeBanco`.`categoria` VALUES('', '$nome', '$codSubCategoria')";
        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
