<?php

include_once("conexao.php");
include_once("nomeConexao.php");

    if ($_POST) {
        $codSubCategoria = $_POST['codSubCategoria'];
        $nome = $_POST['nome'];


        $query = "UPDATE `$nomeBanco`.`subcategoria` SET `nome` = '$nome' WHERE `codSubCategoria` = '$codSubCategoria'";

        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
