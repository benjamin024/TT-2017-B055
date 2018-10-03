<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Pantalla principal</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css" />
</head>
<body style="background-image: url('img/background.jpg'); background-size: 100%;">
    <div class="container-fluid">
        <div class="row">
            <?php include("menu-lat.html"); ?>
            <div class="col-md-10">
                <?php
                    ini_set('soap.wsdl_cache_enabled', 0);
                    ini_set('soap.wsdl_cache_ttl', 900);
                    ini_set('default_socket_timeout', 15);

                    $servicio="http://localhost:8080/wsTT/bdActions?wsdl"; //url del servicio
                    $parametros=array(); //parametros de la llamada
                    $client = new SoapClient($servicio, $parametros);
                    $result = $client->conecta($parametros);//llamamos al métdo que nos interesa con los parámetros
                    $result = (array) $result;
                    print_r($result["return"]);
                ?>
            </div>
        </div>
    </div>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>