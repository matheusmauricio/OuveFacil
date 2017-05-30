<?php

include_once("conexao.php");

    if ($_POST) {
        $nome = $_POST['nome'];


        $query = "INSERT INTO `bancoOuveFacil`.`subCategoria` VALUES('', '$nome')";
        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
