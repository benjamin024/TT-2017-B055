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
    <script>
        function cambiaRuta(r){
            location.href="admin_estacionesruta.php?ruta="+r;
        }
    </script>
</head>
<body style="background-image: url('img/background.jpg'); background-size: 100%;">
    <div class="container-fluid">
        <div class="row">
            <?php include("menu-lat.html"); ?>
            <div class="col-md-10">
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
                                        <th>Latitud</th>
                                        <th>Longitud</th>
                                    </thead>
                                    <tbody style="color: #FFF; ">
                                        <?php
                                            foreach($estaciones as $e){
                                                echo "<tr>";
                                                echo "<td>".$e["id_estacion"]."</td>";
                                                echo "<td>".$e["nombre"]."</td>";
                                                echo "<td>".$e["latitud"]."</td>";
                                                echo "<td>".$e["longitud"]."</td>";
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
    </div>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>