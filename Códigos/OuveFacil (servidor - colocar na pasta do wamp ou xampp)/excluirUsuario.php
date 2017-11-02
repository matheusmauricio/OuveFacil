<?php

include_once("conexao.php");
include_once("nomeConexao.php");

    if ($_POST) {
        $codUsuario = $_POST['codUsuario'];


        $query = "DELETE FROM `$nomeBanco`.`usuario`  WHERE `codUsuario` = '$codUsuario'";

        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
