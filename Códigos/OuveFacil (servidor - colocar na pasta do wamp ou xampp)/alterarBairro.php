<?php

include_once("conexao.php");

    if ($_POST) {
        $codBairro = $_POST['codBairro'];
        $nome = $_POST['nome'];
        $codCidade = $_POST['codCidade'];

        $query = "UPDATE `bancoOuveFacil`.`bairro` SET `nome` = '$nome', `codCidade` = '$codCidade' WHERE `codBairro` = '$codBairro'";

        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
