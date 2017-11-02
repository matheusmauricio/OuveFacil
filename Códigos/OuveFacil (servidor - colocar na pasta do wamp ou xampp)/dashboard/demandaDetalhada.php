<?php

  if(isset($_GET['codigo'])){
	   $codDenuncia = $_GET['codigo'];
  }

  include_once("../listarDenunciaCompleta2.php");
  include_once("../listarAdministrador2.php");

  //echo $output[0]['codDenuncia'];

  ?>

<!doctype html>
<html lang="pt-br">
	<head>
    <script type="text/javascript">
      function trocaImagemExpositor(url){

        expositorImagem.src=url;

        novaImagem.href = expositorImagem.src;
      }


    </script>

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0"/>
		<title>Demanda Detalhada - Ouve Fácil</title>
		<!-- <link rel="shortcut icon" href="images/favicon.ico"> -->
    <link rel="icon" href="../../favicon.ico">
		<link rel='stylesheet' href='dist/css/bootstrap.min.css' type='text/css' media='all'/>
		<link rel='stylesheet' href='dist/css/swatches-and-photos.css' type='text/css' media='all'/>
		<link rel='stylesheet' href='dist/css/prettyPhoto.css' type='text/css' media='all'/>
		<link rel='stylesheet' href='dist/css/jquery.selectBox.css' type='text/css' media='all'/>
		<link rel='stylesheet' href='dist/css/font-awesome.min.css' type='text/css' media='all'/>
		<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Karla:400,400italic,700,700italic%7CCrimson+Text:400,400italic,600,600italic,700,700italic' type='text/css' media='all'/>
		<!-- <link rel='stylesheet' href='dist/css/elegant-icon.css' type='text/css' media='all'/> -->
		<link rel='stylesheet' href='dist/css/style.css' type='text/css' media='all'/>
		<link rel='stylesheet' href='dist/css/commerce.css' type='text/css' media='all'/>
		<link rel='stylesheet' href='dist/css/custom.css' type='text/css' media='all'/>
		<link rel='stylesheet' href='dist/css/magnific-popup.css' type='text/css' media='all'/>

		<!-- Bootstrap core CSS -->
		<link href="dist/css/bootstrap.min.css" rel="stylesheet">

		<!-- Custom styles for this template -->
		<link href="jumbotron.css" rel="stylesheet">

	</head>
	<body>

    <?php

      if(isSet($_COOKIE['logadoAdm']) && ($_COOKIE['logadoAdm'] == 'sim')){
        include_once("cabecalho.php");
      ?>

		<br />
		<br />
		<br />
		<br />

		<div id="wrapper" class="wide-wrap">
			<div class="content-container no-padding">
				<div class="container-full">
					<div class="row">
						<div class="col-md-12">
							<div class="main-content">
								<div class="commerce">
									<div class="style-1 product">
										<div class="container">
											<div class="row summary-container">
												<div class="col-md-7 col-sm-6 entry-image">
													<div class="single-product-images">
														<div class="single-product-images-slider">
															<div class="caroufredsel product-images-slider" data-synchronise=".single-product-images-slider-synchronise" data-scrollduration="500" data-height="variable" data-scroll-fx="none" data-visible="1" data-circular="1" data-responsive="1">
																<div class="caroufredsel-wrap">
																	<ul class="caroufredsel-items">
																		<li class="caroufredsel-item">
																			<a href="<?php echo ".." . $output[0]['midia1'];?>" id="novaImagem" data-rel="magnific-popup-verticalfit">
																				<img width="600" height="685" id="expositorImagem" src=<?php echo ".." . $output[0]['midia1']; ?> alt=""/>
																			</a>
																		</li>
																	</ul>
																	<a href="#" class="caroufredsel-prev"></a>
																	<a href="#" class="caroufredsel-next"></a>
																</div>
															</div>
														</div>
														<div class="single-product-thumbnails">
															<div class="caroufredsel product-thumbnails-slider" data-visible-min="2" data-visible-max="4" data-scrollduration="500" data-direction="up" data-height="100%" data-circular="1" data-responsive="0">
																<div class="caroufredsel-wrap">
																	<ul class="single-product-images-slider-synchronise caroufredsel-items">
																		<?php
																			for($j = 1; $j <= 4; $j++){
																		?>
																		<li class="caroufredsel-item selected">
																			<div class="thumb">
																				<a onclick="trocaImagemExpositor('<?php echo ".." . $output[0]['midia'.$j]; ?>')" data-rel="0">
																					<img width="300" height="300" src=<?php echo ".." . $output[0]['midia'.$j]; ?> alt=""/>
																				</a>
																			</div>
																		</li>
																		<?php
																			}
																		 ?>
																	</ul>
																</div>
															</div>
														</div>
													</div>
												</div>
												<div class="col-md-5 col-sm-6 entry-summary">
													<div class="summary">
														<h1 class="product_title entry-title">Demanda nº <?php echo $output[0]['codDenuncia']; ?></h1>
														<div class="product-excerpt">
															<p>
																<!--DESCRIÇÃO.-->
																<?php
                                  $i = 0;
  																echo "Categoria: " . $output[0]['nomeCategoria'];
  																echo "<br /> Descrição: " . $output[0]['descricao'];
  																echo "<br /> Data: " . $output[0]['data'];
                                  echo "<br /> Hora: " . $output[0]['hora'];
                                  echo "<br /> Designar Administrador: ";

																?>
                                <select name="administrador" id="administrador" onchange="teste()">
                                  <?php foreach($output2 as $aux) { ?>
                                <option value=<?php echo $output2[$i]['codAdministrador'] ?>><?php echo $output2[$i]['nome'] ?></option>
                                <?php
                                  $i++;
                                  }
                                ?>
                                </select>
                                </div>
															</p>

  														<form class="cart" name="form1" method="post" action="aprovarDemanda.php">
  															<div class="add-to-cart-table">
                                  <input name="codDenuncia" type="hidden" value="<?php echo $output[0]['codDenuncia']?>" />
                                  <input id="idText" name="codAdministrador" type="hidden" value="" />
  																<button type="submit" class="button">Aprovar</button>
  															</div>
  														</form>

                              <script type="text/javascript">


                                teste = function(){
                                  var select = document.getElementById('administrador');
                                  var text = document.getElementById('idText');
                                  text.value = select.value;
                                }

                                window.onload = function() {
                                  var select = document.getElementById('administrador');
                                  var text = document.getElementById('idText');
                                  text.value = select.value;
                                };


                              </script>

                            <form class="cart" name="form2" method="post" action="reprovarDemanda.php">
															<div class="add-to-cart-table">
                                <input name="codDenuncia" type="hidden" value="<?php echo $output[0]['codDenuncia']?>" />
																<button type="submit" class="button">Reprovar</button>
															</div>
														</form>

													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


        <?php
          }else{
        ?>

        <div class="summary">
          <h1 class="product_title entry-title">Por favor efetue o <u><a href="loginAdm.php">login</a></u> para continuar navegando!</h1>
        </div>

        <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
        <br /><br /><br /><br /><br /><br /><br /><br />



        <?php
          }
        ?>

		<script type='text/javascript' src='dist/js/jquery.js'></script>
		<script type='text/javascript' src='dist/js/jquery-migrate.min.js'></script>
		<script type='text/javascript' src='dist/js/easing.min.js'></script>
		<script type='text/javascript' src='dist/js/imagesloaded.pkgd.min.js'></script>
		<script type='text/javascript' src='dist/js/bootstrap.min.js'></script>
		<script type='text/javascript' src='dist/js/superfish-1.7.4.min.js'></script>
		<script type='text/javascript' src='dist/js/jquery.appear.min.js'></script>
		<script type='text/javascript' src='dist/js/script.js'></script>
		<script type='text/javascript' src='dist/js/swatches-and-photos.js'></script>
		<script type='text/javascript' src='dist/js/jquery.cookie.min.js'></script>
		<script type='text/javascript' src='dist/js/jquery.prettyPhoto.js'></script>
		<script type='text/javascript' src='dist/js/jquery.prettyPhoto.init.min.js'></script>
		<script type='text/javascript' src='dist/js/jquery.selectBox.min.js'></script>
		<script type='text/javascript' src='dist/js/jquery.touchSwipe.min.js'></script>
		<script type='text/javascript' src='dist/js/jquery.transit.min.js'></script>
		<script type='text/javascript' src='dist/js/jquery.carouFredSel.js'></script>
		<script type='text/javascript' src='dist/js/jquery.magnific-popup.js'></script>
		<script type='text/javascript' src='dist/js/core.min.js'></script>
		<script type='text/javascript' src='dist/js/widget.min.js'></script>
		<script type='text/javascript' src='dist/js/mouse.min.js'></script>
		<script type='text/javascript' src='dist/js/slider.min.js'></script>
		<script type='text/javascript' src='dist/js/jquery-ui-touch-punch.min.js'></script>
		<script type='text/javascript' src='dist/js/price-slider.js'></script>

	</body>
</html>
