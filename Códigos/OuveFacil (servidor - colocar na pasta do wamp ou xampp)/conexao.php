<?php

$server = "localhost";
$user = "root";
$pass = "";
$db = "bancoOuveFacil";

$con = mysqli_connect($server, $user, $pass) or die ("Não foi possível conectar ao Servidor");

$sel_bd = mysqli_select_db($con, $db) or die ("Erro: " .mysqli_error($con));


?>
