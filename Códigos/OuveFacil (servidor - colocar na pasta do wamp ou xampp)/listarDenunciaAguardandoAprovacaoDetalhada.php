<?php

  include_once("conexao.php");
  include_once("nomeConexao.php");

  if ($_POST) {
    $codDenuncia = $_POST['codDenuncia'];

    $query = "SELECT denuncia.codDenuncia, denuncia.descricao, denuncia.latitude, denuncia.longitude, denuncia.anonimato,
    denuncia.complementoStatus, denuncia.codBairro, denuncia.codCategoria, denuncia.codUsuario, denuncia.codAdministrador,
    denuncia.codStatus, usuario.nome as nomeUsuario, administrador.nome as nomeAdministrador, bairro.nome as nomeBairro,
    categoria.nome as nomeCategoria, status.nome as nomeStatus, denuncia.midia1, denuncia.midia2, denuncia.midia3,
    denuncia.midia4, denuncia.existe, denuncia.naoExiste
    FROM `$nomeBanco`.`denuncia`, `$nomeBanco`.`usuario`, `$nomeBanco`.`administrador`, `$nomeBanco`.`bairro`,
  `$nomeBanco`.`categoria`, `$nomeBanco`.`status`
    WHERE denuncia.codUsuario = usuario.codUsuario AND denuncia.codAdministrador = administrador.codAdministrador
    AND denuncia.codBairro = bairro.codBairro AND denuncia.codCategoria = categoria.codCategoria
    AND denuncia.codStatus = status.codStatus AND denuncia.codDenuncia = '$codDenuncia'";
    $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));


    while ($linha = mysqli_fetch_assoc($queryExec)) {
      $output[] = $linha;
    }
  }

  print json_encode($output);

?>
