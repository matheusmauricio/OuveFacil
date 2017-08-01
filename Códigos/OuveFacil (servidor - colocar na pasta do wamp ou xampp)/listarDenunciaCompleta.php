<?php
  include_once("conexao.php");

  if ($_POST) {
    $lat = $_POST['lat'];
    $lng = $_POST['lng'];


    $query = "SELECT denuncia.codDenuncia, denuncia.descricao, denuncia.latitude, denuncia.longitude, denuncia.anonimato,
    denuncia.complementoStatus, denuncia.codBairro, denuncia.codCategoria, denuncia.codUsuario, denuncia.codAdministrador,
    denuncia.codFotoVideo, denuncia.codStatus, usuario.nome as nomeUsuario, administrador.nome as nomeAdministrador,
    bairro.nome as nomeBairro, categoria.nome as nomeCategoria, status.nome as nomeStatus, fotoeouvideo.urlFotoVideo
    FROM `bancoOuveFacil`.`denuncia`, `bancoOuveFacil`.`usuario`, `bancoOuveFacil`.`administrador`, `bancoOuveFacil`.`bairro`,
  `bancoOuveFacil`.`categoria`, `bancoOuveFacil`.`fotoeouvideo`, `bancoOuveFacil`.`status`
    WHERE denuncia.latitude = '$lat' AND denuncia.longitude = '$lng' AND denuncia.codUsuario = usuario.codUsuario
    AND denuncia.codAdministrador = administrador.codAdministrador AND denuncia.codBairro = bairro.codBairro
    AND denuncia.codCategoria = categoria.codCategoria AND denuncia.codStatus = status.codStatus
    AND denuncia.codFotoVideo = fotoeouvideo.codFotoVideo";

    $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));


    while ($linha = mysqli_fetch_assoc($queryExec)) {
          $output[] = $linha;
    }
  }

  print json_encode($output);


    /*$latLng = "lat/lng: (-20.929414, -41.234412)";

    $lat = substr($latLng, 10, -13);
    $long = substr($latLng, 22, -1);

    $lat = -20.831404;
    $long = -41.174105;
  */

?>
