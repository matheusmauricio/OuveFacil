<?php

  include_once("conexao.php");
  include_once("nomeConexao.php");

  date_default_timezone_set('America/Sao_Paulo');

    if ($_POST) {
      //$codBairro = $_POST['codBairro'];
      $bairro = $_POST['bairro'];
      $endereco = $_POST['endereco'];
      $codCategoria = $_POST['codCategoria'];
      $codAdministrador = "2"; //código do Administrador padrão
      $codUsuario = $_POST['codUsuario'];
      $descricao = $_POST['descricao'];
      $latitude = $_POST['latitude'];
      $longitude = $_POST['longitude'];
      $anonimato = $_POST['anonimato'];
      $complementoStatus = $_POST['complementoStatus'];
      $midia1 = $_POST['midia1'];
      $codStatus = $_POST['codStatus'];
      $data = date('Y-m-d');
      $hora = date('H:i:s');
      $imgMime = $_POST['img-mime'];
      $imgImage = $_POST['img-image'];

      // o nome da imagem vai ser a data + a hora que a denúncia foi realizada
      $imgName = 'midias/img-'.date('dmY-His').'.'.$imgMime;


      $query = "INSERT INTO `$nomeBanco`.`denuncia` VALUES('', '$descricao', '$latitude', '$longitude',
        '$anonimato', '$complementoStatus', NULL, '$codCategoria', '$codUsuario', '$codAdministrador',
        '$codStatus', '$data', '$hora', '/$imgName', NULL, NULL, NULL, 0, 0, '$bairro', '$endereco')";
      $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

      //inserção da imagem
      $binary = base64_decode($imgImage);
      $file = fopen($imgName, 'wb');
      fwrite($file, $binary);
      fclose($file);


    }
?>
