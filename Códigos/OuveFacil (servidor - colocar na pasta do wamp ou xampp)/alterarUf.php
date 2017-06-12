<?php

include_once("conexao.php");

    if ($_POST) {
        $sigla = $_POST['sigla'];
        $nome = $_POST['nome'];
        $antigaSigla = $_POST['antigaSigla'];

        $query = "UPDATE `bancoOuveFacil`.`uf` SET `sigla` = '$sigla', `nome` = '$nome' WHERE `sigla` = '$antigaSigla'";

        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
