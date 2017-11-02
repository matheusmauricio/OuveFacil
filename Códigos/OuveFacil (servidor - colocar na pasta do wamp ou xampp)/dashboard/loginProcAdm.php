<?php

  include_once("../conexao.php");
  include_once("../nomeConexao.php");

  if ($_POST) {
      $login = $_POST['login'];
      $senha = md5($_POST['senha']);

      $query = "SELECT administrador.nome, administrador.login, administrador.senha, administrador.cpfCnpj,
       administrador.codAdministrador FROM `$nomeBanco`.`administrador`
      WHERE administrador.login = '$login' AND administrador.senha = '$senha'";
      $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

      while ($linha = mysqli_fetch_assoc($queryExec)) {
        $output[] = $linha;
      }
    }
    //print json_encode($output);


    if($output != null){
      header("location: logadoAdm.php");
      setcookie("administrador", serialize($output[0]['nome']), time()+3600);
    } else {
      //header("location: index.php");
      header(sprintf('location: %s', $_SERVER['HTTP_REFERER']));
    }

  ?>
