<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Registros de viaje</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css" />
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <?php
        include("webservices_cliente.php");
        $id =@$_GET["id"];
        $registros = selectWhere("r.id_viaje_unidad = $id order by r.fecha_hora asc", "e.nombre, r.*", "registro r inner join estacion e on e.id_estacion = r.id_estacion");
    ?>
    
</head>
<body style="background-image: url('img/background.jpg'); background-size: 100%;">
    <div class="container-fluid">
        <div class="row">
            <?php include("menu-lat.html"); ?>
            <div class="col-md-10 offset-md-2">
                <div class="row"  style="position: relative; top: 50px;  width: 98%; max-height:100%; margin: 0px;  justify-content: center;">
                    <div class="card" style="width: 800px;  color: #FFF; background-color: rgba(0,0,0,0.7);">
                        <div class="card-body justify-content-center">
                            <h4 class="card-title" style="text-align: center;">Registros de viaje</h4>
                                <?php
                                    if(count($registros) > 0){
                                ?>
                                <table class="table table-bordered table-sm table-hover" >
                                    <thead style="background-color: #0099CC; color: #FFFFFF; height: 20px;">
                                        <th>Hora</th>
                                        <th>Estación</th>
                                        <th>No. Pasajeros a bordo</th>
                                        <th>No. Pasajeros tarifa preferencial</th>
                                    </thead>
                                    <tbody style="color: #FFF; ">
                                        <?php
                                            foreach($registros as $r){
                                                echo "<tr>";
                                                echo "<td width='12%'>".date("h:i a", strtotime(explode(" ", $r["fecha_hora"])[1]))."</td>";
                                                echo "<td width='50%'>".$r["nombre"]."</td>";
                                                echo "<td>".$r["no_pasajeros"]."</td>";
                                                echo "<td>".$r["no_pasajeros_especial"]."</td>";
                                                echo "</tr>";
                                            }
                                        ?>
                                    </tbody>
                                </table>
                                <?php
                                }else{
                                    echo "<h6 class='card-title' style'text-align: center;'>No hay registros de este viaje</h6>";
                                }
                                ?>
                                <br>
                                <center><a href="admin_viajes.php?ruta=<?=@$_GET["ruta"]."&fecha=".@$_GET["fecha"]?>"><button class="btn" style="margin-top: 10px; background-color: #0099CC; color: #FFFFFF;">Regresar</button></a></center>
                        </div>
                    </div><br>&nbsp;<br><br><br>
                </div>
            </div>
        </div>
    </div>
</body>
</html>