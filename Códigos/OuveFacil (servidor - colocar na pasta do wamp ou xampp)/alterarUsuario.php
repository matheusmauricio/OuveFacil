<?php

include_once("conexao.php");
include_once("nomeConexao.php");

    if ($_POST) {
        $codUsuario = $_POST['codUsuario'];
        $nome = $_POST['nome'];
        $login = $_POST['login'];
        $senha = md5($_POST['senha']);
        $cpfCnpj = $_POST['cpfCnpj'];


        $query = "UPDATE `$nomeBanco`.`usuario` SET `nome` = '$nome', `login` = '$login', `senha` = '$senha', `cpfCnpj` = '$cpfCnpj' WHERE `codUsuario` = '$codUsuario'";

        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
