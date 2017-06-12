<?php

include_once("conexao.php");

    if ($_POST) {
        $codCategoria = $_POST['codCategoria'];
        $nome = $_POST['nome'];
        $codSubCategoria = $_POST['codSubCategoria'];

        $query = "UPDATE `bancoOuveFacil`.`categoria` SET `nome` = '$nome', `codSubCategoria` = '$codSubCategoria' WHERE `codCategoria` = '$codCategoria'";

        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
