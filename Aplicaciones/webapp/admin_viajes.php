<?php
    include("webservices_cliente.php");
    $ruta = @$_GET["ruta"];
    if(!$ruta){
        $ruta = selectWhere("id_ruta > 4", "MIN(id_ruta) as ruta", "ruta")[0]["ruta"];
    }
    $fecha =@$_GET["fecha"];
    if(!$fecha){
        $fecha = date("Y-m-d");
    }
    
    $viajes = selectWhere("u.id_ruta = $ruta and fecha = '$fecha' order by hora_salida desc", "c.nombre, vu.*", "viaje_unidad vu inner join conductor c on c.rfc_conductor = vu.rfc_conductor inner join unidad u on vu.id_unidad = u.id_unidad");
    //print_r($viajes);
?>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Viajes</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css" />
    <script>
        function actualizaInfo(){
            var ruta = document.getElementById("ruta").value;
            var fecha = document.getElementById("fecha").value;
            window.location = "admin_viajes.php?ruta="+ruta+"&fecha="+fecha;
        }
    </script>
</head>
<body style="background-image: url('img/background.jpg'); background-size: 100%;">
    <div class="container-fluid">
        <div class="row">
            <?php include("menu-lat.html"); ?>
            <div class="col-md-10 offset-md-2">
                <div class="row"  style="position: relative; top: 50px;  width: 98%; max-height:100%; margin: 0px;  justify-content: center;">
                    <div class="card" style="width: 950px;  color: #FFF; background-color: rgba(0,0,0,0.7);">
                        <div class="card-body justify-content-center">
                            <h4 class="card-title" style="text-align: center;">Viajes</h4>
                            <div class="input-group input-group-sm mb-3">
                                <div class="col-md-6" style="padding-left: 0px;">
                                    <select name="ruta" id="ruta" class="form-control" onchange="actualizaInfo();">
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
                                    </select>
                                </div>
                                <div class="col-md-6" style="padding-right: 0px;">
                                    <input type="date" class="form-control" name="fecha" id="fecha" value="<?=$fecha?>" onchange="actualizaInfo();">
                                </div>
                                </div>
                                <?php
                                    if($viajes){
                                ?>
                                <table class="table table-bordered table-sm table-hover" >
                                    <thead style="background-color: #0099CC; color: #FFFFFF; height: 20px;">
                                        <th>ID</th>
                                        <th>Unidad</th>
                                        <th>Conductor</th>
                                        <th>Dirección</th>
                                        <th>Hora de salida</th>
                                        <th>Hora de término</th>
                                        <th>Total de pasajeros</th>
                                        <th></th>
                                    </thead>
                                    <tbody style="color: #FFF; ">
                                        <?php
                                            foreach($viajes as $v){
                                                echo "<tr>";
                                                echo "<td>".$v["id_viaje_unidad"]."</td>";
                                                echo "<td>".$v["id_unidad"]."</td>";
                                                echo "<td>".$v["nombre"]."</td>";
                                                echo "<td>".(($v["direccion"])?"Ida":"Vuelta")."</td>";
                                                echo "<td>".date("h:i a", strtotime($v["hora_salida"]))."</td>";
                                                echo "<td>".(($v["hora_termino"] != "null")?date("h:i a", strtotime($v["hora_termino"])):"Viaje en curso")."</td>";
                                                echo "<td>".$v["total_pasajeros"]."</td>";
                                                echo "<td style='text-align: center;'><a href='admin_registros_viaje.php?id=".$v["id_viaje_unidad"]."&ruta=$ruta&fecha=$fecha'><img style='cursor: pointer;' src='img/ico-ver.png' width='25px'></a></td>";
                                                echo "</tr>";
                                            }
                                        ?>
                                    </tbody>
                                </table>
                                <?php
                                    }else
                                    echo "<h6 class='card-title' style'text-align: center;'>Aún no hay viajes registrados</h6>";
                                ?>
                        </div>
                    </div><br>&nbsp;<br><br><br>
                </div>
            </div>
        </div>
    </div>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>