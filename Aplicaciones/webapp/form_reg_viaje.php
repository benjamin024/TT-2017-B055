<?php
    date_default_timezone_set("America/Mexico_City");
    include("webservices_cliente.php");
    $rutas = selectWhere("id_ruta > 4", "*", "ruta");
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Registrar unidad</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css" />
</head>
<body style="background-image: url('img/background.jpg'); background-size: 100%;">
    <div class="container-fluid">
        <div class="row">
            <?php include("menu-lat.html"); ?>
            <div class="col-md-10 offset-md-2">
                <div class="row align-items-center h-100"  style="position: absolute; top: 250px;  width: 90%; height:100%; margin: 0px;  justify-content: center;">
                    <div class="card" style="max-width: 500px; color: #FFF; background-color: rgba(0,0,0,0.85);">
                        <div class="card-body justify-content-center">
                            <h4 class="card-title" style="text-align: center;">Registrar comienzo de viaje</h4>
                                <form action="webservices_cliente.php" method="post">
                                    <input type="hidden" name="accion" value="registraViaje">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="responsable">Ruta:</label>
                                            <select class="form-control"  name="ruta" id="ruta" onchange="getUnidades(this.value);" required>
                                                <?php
                                                    foreach($rutas as $r){
                                                        echo "<option value='".$r["id_ruta"]."'>".$r["nombre"]."</option>";
                                                    }
                                                ?>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="placas">Unidad:</label>
                                            <select class="form-control"  name="unidad" id="unidad" required>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label for="ruta">Conductor:</label>
                                            <select class="form-control"  name="conductor" id="conductor" required>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="capacidad">Fecha:</label>
                                            <input type="date" class="form-control" id="fecha" name="fecha"  value="<?=date('Y-m-d')?>" required>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="inicio">Hora:</label>
                                            <input type="time" class="form-control" id="hora" name="hora" value="<?=date('H:i')?>" required>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="inicio">Dirección:</label>
                                            <select class="form-control"  name="direccion" id="direccion" required>
                                                <option value="1">Ida</option>
                                                <option value="0">Vuelta</option>
                                            </select>
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
    </div>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script>
        $( document ).ready(function() {
            getUnidades($("#ruta").val());
            getConductores();
        });
        function getUnidades(ruta){
            $.post("webservices_cliente.php", {accion: "selectWherePost", where: "id_ruta = " + ruta , campos: "id_unidad", tabla: "unidad"} , function(result){
                var unidades = JSON.parse(result);
                var option = "";
                for(i = 0; i < unidades.length; i++){
                    var unidad = JSON.parse(unidades[i]);
                    option += "<option value='"+unidad.id_unidad+"'>"+unidad.id_unidad+"</option>";
                }
                
                $("#unidad").html(option);
            });
        }

        function getConductores(){
            $.post("webservices_cliente.php", {accion: "selectWherePost", where: "1" , campos: "rfc_conductor, nombre", tabla: "conductor"} , function(result){
                var conductores = JSON.parse(result);
                var option = "";
                for(i = 0; i < conductores.length; i++){
                    var conductor = JSON.parse(conductores[i]);
                    option += "<option value='"+conductor.rfc_conductor+"'>"+conductor.nombre+"</option>";
                }
                
                $("#conductor").html(option);
            });
        }
    </script>
</body>          
</html>