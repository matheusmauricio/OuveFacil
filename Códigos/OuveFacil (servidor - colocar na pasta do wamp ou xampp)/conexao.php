<?php

$server = "localhost";
$user = "root";
$pass = "";
$db = "bancoOuveFacil";

$con = mysqli_connect($server, $user, $pass) or die ("Não foi possível conectar ao Servidor");
//mysqli_set_charset($con, "utf-8");
$con->query("SET NAMES 'utf8'");
$con->query("SET character_set_connection=utf8");
$con->query("SET character_set_client=utf8");
$con->query("SET character_set_results=utf8");

$sel_bd = mysqli_select_db($con, $db) or die ("Erro: " .mysqli_error($con));


?>
