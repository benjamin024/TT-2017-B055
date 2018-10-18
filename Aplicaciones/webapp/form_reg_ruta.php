<?php
    $hex = "#";
	$hex.= str_pad(dechex(rand(0,255)), 2, "0", STR_PAD_LEFT);
	$hex.= str_pad(dechex(rand(0,255)), 2, "0", STR_PAD_LEFT);
    $hex.= str_pad(dechex(rand(0,255)), 2, "0", STR_PAD_LEFT);
    
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
    <title>Registrar ruta</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css" />
    <link href="css/multiple-select.css" rel="stylesheet"/>
    <link rel="stylesheet" href="css/jquery-ui.css">
    <style>
        #sortable { list-style-type: none; margin: 0; padding: 0; width: 100%; }
        #sortable li { cursor: move; margin: 0 3px 3px 3px; padding: 0.4em; padding-left: 1.5em; height: 35px; border-radius: 5px; }
        .delete{
            position: absolute;
            right: 25px;
            margin-top: 0px;
            cursor: pointer;
        }
    </style>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/multiple-select.js"></script>
    <script src="js/jquery-ui.js"></script>
    <script>
      var map;
      var marker;
      var numEstaciones = 0;
      var markerClic;
      function registrarEstacion(){
            numEstaciones = parseInt($("#numEstaciones").val()) + 1;
            $("#numEstaciones").val(numEstaciones);
        
            var formEstacion = document.getElementById("formEstaciones");
            formEstacion.innerHTML += "<input type='hidden' name='estacion_"+numEstaciones+"' id='estacion_"+numEstaciones+"' value='"+$("#nombreEs").val()+"--"+$("#locationEs").val()+"'>";
            
            var lista = document.getElementById("sortable");
            lista.innerHTML += "<li class='ui-state-default'>"+$("#nombreEs").val()+"<img src='img/ico-borrar-black.png' class='delete' height='22px'></li>";
            $('#modalEstacion').modal('toggle');
            
            $("#sortable .delete").click(function() {
                $(this).parent().remove();
                numEstaciones -= 1;
                $("#numEstaciones").val(numEstaciones);
            });

            markerClic.setPosition(new google.maps.LatLng(0,0));
            $("#nombreEs").val("");
            $("#locationEs").val("");
        }

        function placeMarker(location, direccion=0) {
            var lat = location.lat();
            var lng = location.lng();

            var punto = new google.maps.LatLng(lat, lng);
            
            if(!direccion){
                var geocoder = new google.maps.Geocoder;
                geocoder.geocode({
                    'location': punto 
                }, function(results, status) {
                    // si la solicitud fue exitosa
                    if (status === google.maps.GeocoderStatus.OK) {
                        // si encontró algún resultado.
                        if (results[1]) {
                        $("#nombreEs").val(results[1].formatted_address.split(",")[0]);
                        }
                    }
                });
            }

            $("#locationEs").val(lat+","+lng);

        if ( markerClic ) {
            markerClic.setPosition(location);
        } else {
            markerClic = new google.maps.Marker({
            position: location,
            map: map
            });
        }

        }

      function crearMarcador(t, lat, lng, img){
          var marker;
        marker = new google.maps.Marker({
            position: new google.maps.LatLng(lat, lng),
            map: map,
            icon: "img/marcadores/img_"+img+".png",
            title: t
        });
        
        
        google.maps.event.addListener(marker, 'click', function(event) {
            var coordenadas = marker.getPosition();
            var lat = coordenadas.lat();
            var lng = coordenadas.lng();
            console.log(lat+", "+lng);
            placeMarker(coordenadas, 1);
            $('#nombreEs').val(marker.getTitle());
            $('#nombreEs').attr('readonly', true);

        });

        return marker;
      }
      function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {
          center: {lat: 21.8833, lng: -102.3},
          zoom: 14
        });

        var i;

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
            console.log(locations[i][0]+"="+locations[i][1]+", "+locations[i][2]);
            
            crearMarcador(locations[i][0], locations[i][1], locations[i][2], locations[i][3]);



        }

        google.maps.event.addListener(map, 'click', function(event) {
        
            var coordenadas = event.latLng;
            
            placeMarker(event.latLng);

            $('#nombreEs').attr('readonly', false);
            
        });
        
        

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
            <div class="col-md-10">
            <div class="row align-items-center h-100"  style="position: absolute; top: 0px;  width: 90%; height:100%; margin: 0px;  justify-content: center;">
                    <div class="card" style="max-width: 650px; color: #FFF; background-color: rgba(0,0,0,0.85);">
                        <div class="card-body justify-content-center">
                            <h4 class="card-title" style="text-align: center;">Registrar ruta</h4>
                                <form action="webservices_cliente.php" method="post">
                                    <input type="hidden" name="accion" value="crearAviso">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="nombre" style="font-weight: bold; font-size: 18px;">Nombre de la ruta:</label>
                                            <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Ingresa el nombre" required>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="nombre"  style="font-weight: bold; font-size: 18px;">Color:</label>
                                            <div id="colorDiv" style="width: 100%; height: 40px; border-radius: 5px; background-color: <?=$hex?>; cursor: pointer;" onclick="$('#colorHidden').click();">&nbsp;</div>
                                            <input type="color" name="color" id="colorHidden" value="<?=$hex?>" hidden onchange="cambiaColor(this.value)">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="forma" style="font-weight: bold; font-size: 18px;">Forma de cobro:</label><br>
                                            <select multiple="multiple" style="width: 89%; height: 40px;">
                                                <?php
                                                    $formas = selectWhere("1 = 1", "*", "forma_cobro");
                                                    foreach($formas as $f){
                                                        echo "<option value='".$f["id_forma_cobro"]."'>&nbsp;&nbsp;".$f["descripcion"]."</option>";
                                                    }
                                                ?>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="combustible" style="font-weight: bold; font-size: 18px;">Combustible por corrida:</label>
                                            <input type="number" class="form-control" id="combustible" name="combustible" placeholder="Litros" required>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="forma" style="font-weight: bold; font-size: 18px;">Tarifas:</label><br>
                                            <?php
                                                $tarifas = selectWhere("1 = 1", "*", "tarifa");
                                                foreach($tarifas as $t){
                                                    echo "<input type='checkbox' name='tarifas[]' value='".$t["id_tarifa"]."'> ".$t["descripcion"]." ($".$t["costo"].")<br>";
                                                }
                                            ?>
                                            <div style="cursor: pointer;" onclick = "$('#modalTarifa').modal('toggle')"><img src="img/ico-plus.png" width="20px" alt=""> Agregar tarifa</div>
                                        </div>
                                        <label for="forma" style="font-weight: bold; font-size: 18px;">Estaciones:</label><br>
                                            <div id="formEstaciones">
                                                <input type="hidden" name="numEstaciones" id="numEstaciones" value="0">
                                            </div>
                                            <ul id="sortable">
                                                
                                            </ul>
                                            <div style="cursor: pointer;" onclick = "$('#modalEstacion').modal('toggle')"><img src="img/ico-plus.png" width="20px" alt=""> Agregar estación</div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="frecuencia" style="font-weight: bold; font-size: 18px;">Frecuencia de corridas:</label>
                                            <input type="number" class="form-control" id="frecuencia" name="frecuencia" placeholder="Minutos" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="forma" style="font-weight: bold; font-size: 18px;">Horarios:</label><br>
                                            <table>
                                                <tr><td></td><td style="text-align: center;">Inicio</td><td style=" text-align: center;">Término</td></tr>
                                            <?php
                                                $dias = array("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo");
                                                for($i = 0; $i < count($dias); $i++){
                                                    echo "<tr><td>".$dias[$i].": </td><td style='padding: 2px;'><input class='form-control' type='time' name='dia_$i'></td><td style='padding: 2px;'><input class='form-control' type='time' name='dia_$i'></td></tr>";
                                                }
                                            ?>                                                
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <center><button class="btn" style="margin-top: 10px; background-color: #0099CC; color: #FFFFFF;">Registrar</button></center>
                                </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- The Modal -->
        <div class="modal fade" id="modalEstacion" style="margin-top: 80px;">
            <div class="modal-dialog">
                <div class="modal-content">
                    
                <div class="card" style="width: 800px; color: #FFF; background-color: #242520;">
                        <div class="card-body justify-content-center">
                            <h4 class="card-title" style="text-align: center;">Registrar estación</h4>
                            <div class="form-group">
                                <input type="text" class="form-control" id="nombreEs" name="nombre" placeholder="Ingresa el nombre de la estación" required>
                                <input type="hidden" id="locationEs">
                            </div>
                            <div id="map" style=" height: 500px; width: 750px;"></div>
                            <center>
                                <input type="hidden" id="aEliminar">
                                <button type="button" class="btn" style="margin-top: 10px; background: transparent; border-color:#FFF; color: #FFFFFF;" data-dismiss="modal">Cancelar</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <button class="btn" style="margin-top: 10px; background-color: #0099CC; color: #FFFFFF;" onclick="registrarEstacion()">Registrar</button>
                            </center>
                        </div>
                    </div>
                </div>

                </div>
            </div>
        </div>
    </div>
    
    <script>
        $('select').multipleSelect({
            placeholder: "Selecciona las formas de cobro",
            selectAll: false,
            allSelected: false
        });
        $('.ms-choice').attr('style', 'height: 40px;');
        $('.placeholder').attr('style', 'padding-top: 10px;');

        $( function() {
            $( "#sortable" ).sortable({
                scroll: false
            });
            $( "#sortable" ).disableSelection();
        } );

        function cambiaColor(color){
            $("#colorDiv").attr("style", "width: 100%; height: 40px; border-radius: 5px; background-color: "+color+"; cursor: pointer;");
        }
    </script>
</body>
</html>