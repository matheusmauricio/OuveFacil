<?php

include_once("conexao.php");
include_once("nomeConexao.php");

    if ($_POST) {
        $codSubCategoria = $_POST['codSubCategoria'];


        $query = "DELETE FROM `$nomeBanco`.`subCategoria`  WHERE `codSubCategoria` = '$codSubCategoria'";

        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
