<?php

include_once("conexao.php");
include_once("nomeConexao.php");

    if ($_POST) {
        $nome = $_POST['nome'];
        $login = $_POST['login'];
        $senha = md5($_POST['senha']);
        $cpfCnpj = $_POST['cpfCnpj'];


        $query = "INSERT INTO `$nomeBanco`.`administrador` VALUES('', '$nome', '$login', '$senha', '$cpfCnpj')";
        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
