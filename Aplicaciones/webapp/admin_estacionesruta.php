<?php
    include("webservices_cliente.php");
    $ruta = @$_GET["ruta"];
    if(!$ruta){
        $ruta = selectWhere("id_ruta > 4", "MIN(id_ruta) as ruta", "ruta")[0]["ruta"];
    }
    $estaciones = selectWhere("re.id_ruta = $ruta", "DISTINCT e.*", "estacion as e JOIN ruta_estacion as re ON e.id_estacion = re.id_estacion");
?>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Estaciones</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css" />
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA3o8vWFnS-rIPQDbrKsZxPvJLwZGfPi2Q&callback=initMap"
    async defer></script>
    <script>
        function cambiaRuta(r){
            location.href="admin_estacionesruta.php?ruta="+r;
        }

        function abrirMapa(){
            $('#modalEstacion').modal('toggle');
        }

        var map;
        var marker;
        
        function initMap() {
            map = new google.maps.Map(document.getElementById('map'), {
            center: {lat: 21.8833, lng: -102.3},
            zoom: 16
            });      
        }
    </script>
</head>
<body style="background-image: url('img/background.jpg'); background-size: 100%;">
    <div class="container-fluid">
        <div class="row">
            <?php include("menu-lat.html"); ?>
            <div class="col-md-10 offset-md-2">
                <div class="row"  style="position: relative; top: 50px;  width: 98%; max-height:100%; margin: 0px;  justify-content: center;">
                    <div class="card" style="width: 800px;  color: #FFF; background-color: rgba(0,0,0,0.7);">
                        <div class="card-body justify-content-center">
                            <h4 class="card-title" style="text-align: center;">Unidades</h4>
                            <div class="input-group input-group-sm mb-3">
                                <select name="ruta" id="ruta" class="form-control" onchange="cambiaRuta(this.value);">
                                    <option value="-1" disabled>Selecciona una ruta</option>
                                    <?php
                                        $rutas = selectWhere("id_ruta > 4", "*", "ruta");
                                        foreach($rutas as $r){
                                            $selected = "";
                                            if($r["id_ruta"] == $ruta)
                                                $selected = "selected";
                                            echo "<option value='".$r["id_ruta"]."' $selected>".$r["nombre"]."</option>";
                                        }
                                    ?>
                                </select><br><br>
                                <table class="table table-bordered table-sm table-hover" >
                                    <thead style="background-color: #0099CC; color: #FFFFFF; height: 20px;">
                                        <th>ID</th>
                                        <th>Estación</th>
                                        <th>Ver en mapa</th>
                                    </thead>
                                    <tbody style="color: #FFF; ">
                                        <?php
                                            foreach($estaciones as $e){
                                                echo "<tr>";
                                                echo "<td>".$e["id_estacion"]."</td>";
                                                echo "<td>".$e["nombre"]."</td>";
                                                echo "<td style='text-align: center; cursor: pointer;'><img src='img/ico-mapa.png' width='25px' onclick=\"abrirMapa('".$e["latitud"]."','".$e["longitud"]."')\"></td>";
                                                echo "</tr>";
                                            }
                                        ?>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div><br>&nbsp;<br><br><br>
                </div>
            </div>
        </div>
        <!-- The Modal -->
        <div class="modal fade" id="modalEstacion" style="margin-top: 80px;">
            <div class="modal-dialog">
                <div class="modal-content">
                    
                <div class="card" style="width: 800px; color: #FFF; background-color: #242520;">
                        <div class="card-body justify-content-center">
                            <div id="map" style=" height: 500px; width: 750px;"></div>
                        </div>
                    </div>
                </div>

                </div>
            </div>
        </div>
    </div>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>