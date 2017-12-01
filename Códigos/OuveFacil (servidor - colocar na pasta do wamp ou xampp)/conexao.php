<?php

  /*$server = "localhost";
  $user = "root";
  $pass = "";
  $db = "bancoOuveFacil";
  */

  /*$server = "mysql.hostinger.com.br";
  $user = "u603039932_mm";
  $pass = "mmdoido001";
  $db = "u603039932_banco";
  */

  $server = "localhost";
  $user = "id3656342_admin";
  $pass = "mmdoido001";
  $db = "id3656342_bancoouvefacil";


  $con = mysqli_connect($server, $user, $pass) or die ("Não foi possível conectar ao Servidor");
  //mysqli_set_charset($con, "utf8");
  $con->query("SET NAMES 'utf8'");
  $con->query("SET character_set_connection=utf8");
  $con->query("SET character_set_client=utf8");
  $con->query("SET character_set_results=utf8");

  $sel_bd = mysqli_select_db($con, $db) or die ("Erro: " .mysqli_error($con));


?>
