<?php
    include("webservices_cliente.php");
    $estaciones = selectWhere("id_estacion > 50", "*", "estacion");
    $marcadores = array();
    foreach($estaciones as $e){
        $ruta_e = selectWhere("id_estacion = ".$e["id_estacion"], "*", "ruta_estacion");
        $transbordos = array();
        if($ruta_e){
            foreach($ruta_e as $re){
                if(!in_array($re["id_ruta"], $transbordos))
                    $transbordos[] = $re["id_ruta"];
            }
        }
        $marcadores[] = "['".$e["nombre"]."', ".$e["latitud"].", ".$e["longitud"].", ".implode(",", $transbordos)."]";
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

    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
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
            var aux = [];
            for(j = 3; j < locations[i].length; j++){
                aux.push(locations[i][j]);
            }
            if(locations[i][3] === "")
                locations[i][3] = "http://maps.gstatic.com/mapfiles/ridefinder-images/mm_20_green.png";
            locations[i][3] = aux.join();  
            console.log("img/marcadores/img_"+locations[i][3]+".png");
        marker = new google.maps.Marker({
            position: new google.maps.LatLng(locations[i][1], locations[i][2]),
            map: map,
            icon: "img/marcadores/img_"+locations[i][3]+".png"
        });

        google.maps.event.addListener(marker, 'click', (function(marker, i) {
            return function() {
            infowindow.setContent(locations[i][0]);
            infowindow.open(map, marker);
            }
        })(marker, i));

        }
        
        <?php
            $rutas = selectWhere("id_ruta > 4", "*", "ruta");
            foreach($rutas as $ruta){
                $rutaE = selectWhere("re.id_ruta = ".$ruta["id_ruta"], "e.latitud, e.longitud", "ruta_estacion as re INNER JOIN estacion as e ON re.id_estacion = e.id_estacion");
                $aux = array();
                foreach($rutaE as $r){
                    $aux[] = $r["latitud"].",".$r["longitud"];
                }
                $rutaE = implode("|", $aux);
        ?>
                runSnapToRoad("<?=$rutaE?>", "<?=$ruta["color"]?>");
        <?php
            }
        ?>  
        // Snap a user-created polyline to roads and draw the snapped path
        
        function runSnapToRoad(path, color) {

        $.get('https://roads.googleapis.com/v1/snapToRoads', {
            interpolate: true,
            key: "AIzaSyA3o8vWFnS-rIPQDbrKsZxPvJLwZGfPi2Q",
            path: path
        }, function(data) {
            processSnapToRoadResponse(data);
            drawSnappedPolyline(color);
        });
        }

        // Store snapped polyline returned by the snap-to-road service.
        function processSnapToRoadResponse(data) {
        snappedCoordinates = [];
        placeIdArray = [];
        for (var i = 0; i < data.snappedPoints.length; i++) {
            var latlng = new google.maps.LatLng(
                data.snappedPoints[i].location.latitude,
                data.snappedPoints[i].location.longitude);
            snappedCoordinates.push(latlng);
            placeIdArray.push(data.snappedPoints[i].placeId);
        }
        }

        // Draws the snapped polyline (after processing snap-to-road response).
        function drawSnappedPolyline(color) {
        var snappedPolyline = new google.maps.Polyline({
            path: snappedCoordinates,
            strokeColor: color,
            strokeWeight: 3
        });

        snappedPolyline.setMap(map);
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
</body>
</html>