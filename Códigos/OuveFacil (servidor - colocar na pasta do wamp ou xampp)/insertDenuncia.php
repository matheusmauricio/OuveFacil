<?php

include_once("conexao.php");

    if ($_POST) {
      $codBairro = $_POST['codBairro'];
      $codCategoria = $_POST['codCategoria'];
      $codAdministrador = $_POST['codAdministrador'];
      $codUsuario = $_POST['codUsuario'];
      $descricao = $_POST['descricao'];
      $latitude = $_POST['latitude'];
      $longitude = $_POST['longitude'];
      $anonimato = $_POST['anonimato'];
      $complementoStatus = $_POST['complementoStatus'];
      $codFotoVideo = $_POST['codFotoVideo'];
      $codStatus = $_POST['codStatus'];

      $query = "INSERT INTO `bancoOuveFacil`.`denuncia` VALUES('', '$descricao', '$latitude', '$longitude',
        '$anonimato', '$complementoStatus', '$codBairro', '$codCategoria', '$codUsuario', '$codAdministrador',
        '$codFotoVideo', '$codStatus')";
      $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    }
?>
