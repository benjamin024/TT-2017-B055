<?php
    include("webservices_cliente.php");
    $conductores = selectWhere("1 = 1", "*", "conductor");
    if(!is_array($conductores)){
        $aux = array();
        $aux[] = $conductores;
        $conductores = $aux;
    }
?>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Conductores</title>
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
                            <h4 class="card-title" style="text-align: center;">Conductores</h4>
                            <div class="input-group input-group-sm mb-3">
                                <input type="text" class="form-control" placeholder="Buscar un conductor por RFC o nombre" aria-label="Buscar un conductor por RFC o nombre" aria-describedby="basic-addon2">
                                <div class="input-group-append">
                                    <button class="btn" style="background-color: #0099CC; color: #FFFFFF;" type="button"><img src="img/ico-buscar.png" height="20px"></button>
                                </div><br><br>
                                <table class="table table-bordered table-sm table-hover" >
                                    <thead style="background-color: #0099CC; color: #FFFFFF; height: 20px;">
                                        <th>RFC</th>
                                        <th>Nombre</th>
                                        <th>No. de licencia</th>
                                        <th>Teléfono</th>
                                        <th>Sueldo</th>
                                    </thead>
                                    <tbody style="color: #FFF; ">
                                        <?php
                                            foreach($conductores as $conductor){
                                                $c = explode(",", $conductor);
                                                echo "<tr>";
                                                echo "<td>".$c[0]."</td>";
                                                echo "<td>".$c[1]."</td>";
                                                echo "<td>".$c[3]."</td>";
                                                echo "<td>".$c[4]."</td>";
                                                echo "<td>$".$c[2]."</td>";
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