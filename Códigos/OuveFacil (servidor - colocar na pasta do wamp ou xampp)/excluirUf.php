<?php

include_once("conexao.php");

    if ($_POST) {
        $sigla = $_POST['sigla'];


        $query = "DELETE FROM `bancoOuveFacil`.`uf`  WHERE `sigla` = '$sigla'";

        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
