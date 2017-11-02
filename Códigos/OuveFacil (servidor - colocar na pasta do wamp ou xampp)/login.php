<?php

  include_once("conexao.php");
  include_once("nomeConexao.php");

  if ($_POST) {
      $login = $_POST['login'];
      $senha = md5($_POST['senha']);

      $query = "SELECT usuario.nome, usuario.login, usuario.senha, usuario.cpfCnpj, usuario.codUsuario
      FROM `$nomeBanco`.`usuario` WHERE usuario.login = '$login' AND usuario.senha = '$senha'";
      $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

      while ($linha = mysqli_fetch_assoc($queryExec)) {
        $output[] = $linha;
      }
    }
    print json_encode($output);


    /*if(mysqli_num_rows($queryExec) > 0 ){
        echo "1";
        //print_r ("1");
      }else{
        echo 0;
        //print_r(0);
      }*/

?>
