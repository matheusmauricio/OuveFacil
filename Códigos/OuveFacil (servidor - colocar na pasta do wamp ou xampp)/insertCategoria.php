<?php

include_once("conexao.php");

    if ($_POST) {
        $nome = $_POST['nome'];
        $codSubCategoria = $_POST['codSubCategoria'];


        $query = "INSERT INTO `bancoOuveFacil`.`categoria` VALUES('', '$nome', '$codSubCategoria')";
        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
