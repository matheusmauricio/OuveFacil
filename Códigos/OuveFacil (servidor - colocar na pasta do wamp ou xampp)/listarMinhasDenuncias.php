<?php

  include_once("conexao.php");
  include_once("nomeConexao.php");

  if ($_POST) {
    $codUsuario = $_POST['codUsuario'];

    $query = "SELECT denuncia.codDenuncia, denuncia.descricao, denuncia.latitude, denuncia.longitude,
    denuncia.anonimato, denuncia.complementoStatus, denuncia.codCategoria, denuncia.codUsuario,
    denuncia.codAdministrador, denuncia.midia1, denuncia.codStatus, usuario.nome as nomeUsuario,
     administrador.nome as nomeAdministrador, categoria.nome as nomeCategoria, status.nome as nomeStatus,
     denuncia.hora, DATE_FORMAT(denuncia.data, '%d/%m/%Y') as data, denuncia.existe, denuncia.naoExiste,
      usuario.codUsuario as codUsuario
    FROM `$nomeBanco`.`denuncia`, `$nomeBanco`.`usuario`, `$nomeBanco`.`administrador`,
    `$nomeBanco`.`categoria`, `$nomeBanco`.`status`
   WHERE denuncia.codUsuario = '$codUsuario' AND denuncia.codUsuario = usuario.codUsuario AND
   denuncia.codAdministrador = administrador.codAdministrador AND denuncia.codCategoria = categoria.codCategoria
   AND denuncia.codStatus = status.codStatus";
    $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

    while ($linha = mysqli_fetch_assoc($queryExec)) {
      $output[] = $linha;
    }
  }

  print json_encode($output);

?>
