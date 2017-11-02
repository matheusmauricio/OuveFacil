<?php

include_once("conexao.php");
include_once("nomeConexao.php");

    if ($_POST) {
        $nome = $_POST['nome'];
        $codCidade = $_POST['codCidade'];

        $query = "INSERT INTO `$nomeBanco`.`bairro` VALUES('', '$nome', '$codCidade')";
        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
