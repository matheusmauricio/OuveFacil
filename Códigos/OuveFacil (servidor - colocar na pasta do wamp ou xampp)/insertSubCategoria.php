<?php

include_once("conexao.php");
include_once("nomeConexao.php");

    if ($_POST) {
        $nome = $_POST['nome'];


        $query = "INSERT INTO `$nomeBanco`.`subCategoria` VALUES('', '$nome')";
        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
