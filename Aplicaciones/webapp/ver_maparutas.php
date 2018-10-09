<?php
    include("webservices_cliente.php");
    $estaciones = selectWhere("id_estacion > 50", "*", "estacion");
    $marcadores = array();
    foreach($estaciones as $e){
        $marcadores[] = "['".$e["nombre"]."', ".$e["latitud"].", ".$e["longitud"].", ".$e["id_estacion"]."]";
    }
    $marcadores = "[".implode(",", $marcadores)."]";
    
?>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Mapa de rutas</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css" />
    <script>
      var map;
      function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {
          center: {lat: 21.8833, lng: -102.3},
          zoom: 14
        });

        var marker, i;

        var infowindow = new google.maps.InfoWindow();

        var locations = <?php print_r($marcadores);?>;

        for (i = 0; i < locations.length; i++) {  
        marker = new google.maps.Marker({
            position: new google.maps.LatLng(locations[i][1], locations[i][2]),
            map: map
        });

        google.maps.event.addListener(marker, 'click', (function(marker, i) {
            return function() {
            infowindow.setContent(locations[i][0]);
            infowindow.open(map, marker);
            }
        })(marker, i));
        }
      }
    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA3o8vWFnS-rIPQDbrKsZxPvJLwZGfPi2Q&callback=initMap"
    async defer></script>
</head>
<body style="background-image: url('img/background.jpg'); background-size: 100%;">
    <div class="container-fluid">
        <div class="row">
            <?php include("menu-lat.html"); ?>
            <div class="col-md-10" style="padding: 0px;">
                <div id="map" style="height: 100%;"></div>
            </div>
        </div>
    </div>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>