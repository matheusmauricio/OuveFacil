<?php

include_once("conexao.php");

    if ($_POST) {
        $codUsuario = $_POST['codUsuario'];


        $query = "DELETE FROM `bancoOuveFacil`.`usuario`  WHERE `codUsuario` = '$codUsuario'";

        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
