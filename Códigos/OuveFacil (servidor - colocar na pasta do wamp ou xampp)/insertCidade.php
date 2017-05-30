<?php

include_once("conexao.php");

    if ($_POST) {
        $nome = $_POST['nome'];
        $sigla = $_POST['sigla'];

        $query = "INSERT INTO `bancoOuveFacil`.`cidade` VALUES('', '$nome', '$sigla')";
        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
