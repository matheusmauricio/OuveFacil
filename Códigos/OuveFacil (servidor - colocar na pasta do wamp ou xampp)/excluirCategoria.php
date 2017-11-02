<?php

include_once("conexao.php");
include_once("nomeConexao.php");

    if ($_POST) {
        $codCategoria = $_POST['codCategoria'];


        $query = "DELETE FROM `$nomeBanco`.`categoria`  WHERE `codCategoria` = '$codCategoria'";

        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
