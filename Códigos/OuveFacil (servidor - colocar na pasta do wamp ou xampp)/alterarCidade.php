<?php

include_once("conexao.php");
include_once("nomeConexao.php");

    if ($_POST) {
        $codCidade = $_POST['codCidade'];
        $nome = $_POST['nome'];
        $sigla = $_POST['sigla'];

        $query = "UPDATE `$nomeBanco`.`cidade` SET `nome` = '$nome', `sigla` = '$sigla' WHERE `codCidade` = '$codCidade'";

        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
