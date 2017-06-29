<?php

include_once("conexao.php");

    if ($_POST) {
        $login = $_POST['login'];
        //$senha = md5($_POST['senha']);


        $query = "SELECT * FROM `bancoOuveFacil`.`usuario` WHERE login = '$login'";
        $queryExec = mysqli_query($con, $query) or die ("Erro: " .mysqli_error($con));

        while ($linha = mysqli_fetch_assoc($queryExec)) {
              $output[] = $linha;
        }

        print json_encode($output);

        if(mysql_num_rows($queryExec) > 0 ){
            echo "1";
          }else{
            echo 0;
          }
    }
?>
