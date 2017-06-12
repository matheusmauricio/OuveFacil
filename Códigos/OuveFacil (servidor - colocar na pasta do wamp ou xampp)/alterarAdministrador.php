<?php

include_once("conexao.php");

    if ($_POST) {
        $codAdministrador = $_POST['codAdministrador'];
        $nome = $_POST['nome'];
        $login = $_POST['login'];
        $senha = md5($_POST['senha']);
        $cpfCnpj = $_POST['cpfCnpj'];


        $query = "UPDATE `bancoOuveFacil`.`administrador` SET `nome` = '$nome', `login` = '$login', `senha` = md5('$senha'), `cpfCnpj` = '$cpfCnpj' WHERE `codAdministrador` = '$codAdministrador'";

        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
