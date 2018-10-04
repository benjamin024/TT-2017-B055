<?php
    include("webservices_cliente.php");
    $unidades = selectWhere("1 = 1", "*", "unidad");
    if(!is_array($unidades)){
        $aux = array();
        $aux[] = $unidades;
        $unidades = $aux;
    }
?>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Unidades</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css" />
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
                                <input type="text" class="form-control" placeholder="Buscar unidad por placas o ruta" aria-label="Buscar unidad por placas o ruta" aria-describedby="basic-addon2">
                                <div class="input-group-append">
                                    <button class="btn" style="background-color: #0099CC; color: #FFFFFF;" type="button"><img src="img/ico-buscar.png" height="20px"></button>
                                </div><br><br>
                                <table class="table table-bordered table-sm table-hover" >
                                    <thead style="background-color: #0099CC; color: #FFFFFF; height: 20px;">
                                        <th>Placas</th>
                                        <th>Responsable</th>
                                        <th>Capacidad</th>
                                        <th>Ruta</th>
                                        <th>Inicio de operaciones</th>
                                        <th>Final de operaciones</th>
                                    </thead>
                                    <tbody style="color: #FFF; ">
                                        <?php
                                            foreach($unidades as $unidad){
                                                $u = explode(",", $unidad);
                                                echo "<tr>";
                                                echo "<td>".$u[0]."</td>";
                                                echo "<td>".$u[4]."</td>";
                                                echo "<td>".$u[1]."</td>";
                                                echo "<td>".$u[2]."</td>";
                                                echo "<td>".$u[3]."</td>";
                                                if($u[5] == "1000-01-01"){
                                                    $fin = "En operación";
                                                }else{
                                                    $fin = $u[5];
                                                }
                                                echo "<td>$fin</td>";
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