<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Demanda de servicio</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css" />
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <?php
        include("webservices_cliente.php");
        $rutas = selectWhere("id_ruta > 4", "id_ruta, nombre", "ruta");
    ?>
    
</head>
<body style="background-image: url('img/background.jpg'); background-size: 100%;">
    <div class="container-fluid">
        <div class="row">
            <?php include("menu-lat.html"); ?>
            <div class="col-md-10">
                <div class="row"  style="position: relative; top: 50px;  width: 98%; max-height:100%; margin: 0px;  justify-content: center;">
                    <div class="card" style="width: 1150px;  color: #FFF; background-color: rgba(0,0,0,0.7);">
                        <div class="card-body justify-content-center">
                            <h4 class="card-title" style="text-align: center;">Demanda de servicio</h4>
                                <?php
                                    if(count($rutas) > 0){
                                ?>
                                <table class="table table-bordered table-sm table-hover" >
                                    <thead style="background-color: #0099CC; color: #FFFFFF; height: 20px; text-align: center; font-weight: bold;">
                                        <tr>
                                            <td rowspan="2" width="15%" class="align-middle">Ruta</td>
                                            <td colspan=3 width="35%">Viajes de ida</td>
                                            <td colspan=3 width="35%">Viajes de vuelta</td>
                                            <td rowspan="2" width="15%" class="align-middle">Unidades mínimas para operar</td>
                                        </tr>
                                        <tr>
                                            <td>En curso</td>
                                            <td>Nivel de demanda</td>
                                            <td>Frecuencia de corridas</td>
                                            <td>En curso</td>
                                            <td>Nivel de demanda</td>
                                            <td>Frecuencia de corridas</td>
                                        </tr>
                                    </thead>
                                    <tbody style="color: #FFF; text-align: center;">
                                        <?php
                                            foreach($rutas as $r){
                                                $enCursoIda = selectWhere("u.id_ruta = ".$r["id_ruta"]." and vu.fecha=NOW() and vu.hora_termino is NULL and vu.direccion = 1", "count(*) as num", "viaje_unidad vu inner join unidad u on vu.id_unidad = u.id_unidad")[0]["num"];
                                                $enCursoVuelta = selectWhere("u.id_ruta = ".$r["id_ruta"]." and vu.fecha=NOW() and vu.hora_termino is NULL and vu.direccion = 0", "count(*) as num", "viaje_unidad vu inner join unidad u on vu.id_unidad = u.id_unidad")[0]["num"];
                                                echo "<tr>";
                                                echo "<td>".$r["nombre"]."</td>";
                                                echo "<td>$enCursoIda</td>";
                                                $demandaIda = getDemanda($r["id_ruta"], 1);
                                                $demandaVuelta = getDemanda($r["id_ruta"], 0);
                                                $nivelI = "";
                                                $frecI = "";
                                                $colorI = "";
                                                $nivelV = "";
                                                $frecV = "";
                                                $colorV = "";
                                                switch($demandaIda){
                                                    case 0:
                                                        $nivelI = "Bajo";
                                                        $frecI = "Reducir";
                                                        $colorI = "bg-warning";
                                                        break;
                                                    case 1:
                                                        $nivelI = "Normal";
                                                        $frecI = "Mantener";
                                                        $colorI = "bg-success";
                                                        break;
                                                    case 2:
                                                        $nivelI = "Alto";
                                                        $frecI = "Aumentar";
                                                        $colorI = "bg-danger";
                                                        break;
                                                }
                                                switch($demandaVuelta){
                                                    case 0:
                                                        $nivelV = "Bajo";
                                                        $frecV = "Reducir";
                                                        $colorV = "bg-warning";
                                                        break;
                                                    case 1:
                                                        $nivelV = "Normal";
                                                        $frecV = "Mantener";
                                                        $colorV = "bg-success";
                                                        break;
                                                    case 2:
                                                        $nivelV = "Alto";
                                                        $frecV = "Aumentar";
                                                        $colorV = "bg-danger";
                                                        break;
                                                }
                                                echo "<td class='$colorI'>$nivelI</td>";
                                                echo "<td class='$colorI'>$frecI</td>";
                                                echo "<td>$enCursoVuelta</td>";
                                                echo "<td class='$colorV'>$nivelV</td>";
                                                echo "<td class='$colorV'>$frecV</td>";
                                                echo "<td>".getUnidadesMinimas($r["id_ruta"])."</td>";
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
                        </div>
                    </div><br>&nbsp;<br><br><br>
                </div>
            </div>
        </div>
    </div>
</body>
</html>