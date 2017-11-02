<?php

include_once("conexao.php");

    if ($_POST) {
        $codCidade = $_POST['codCidade'];


        $query = "DELETE FROM `$nomeBanco`.`cidade`  WHERE `codCidade` = '$codCidade'";

        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
