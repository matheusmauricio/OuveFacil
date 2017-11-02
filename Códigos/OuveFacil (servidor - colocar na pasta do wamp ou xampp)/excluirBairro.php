<?php

include_once("conexao.php");
include_once("nomeConexao.php");

    if ($_POST) {
        $codBairro = $_POST['codBairro'];


        $query = "DELETE FROM `$nomeBanco`.`bairro`  WHERE `codBairro` = '$codBairro'";

        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
