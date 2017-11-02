<?php

include_once("conexao.php");
include_once("nomeConexao.php");

    if ($_POST) {
        $codAdministrador = $_POST['codAdministrador'];


        $query = "DELETE FROM `$nomeBanco`.`administrador`  WHERE `codAdministrador` = '$codAdministrador'";

        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
