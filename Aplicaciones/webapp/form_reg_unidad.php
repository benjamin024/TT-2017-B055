<?php
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
                    <div class="card" style="max-width: 500px; height: 390px; color: #FFF; background-color: rgba(0,0,0,0.85);">
                        <div class="card-body justify-content-center">
                            <h4 class="card-title" style="text-align: center;">Registrar unidad</h4>
                                <form action="webservices_cliente.php" method="post">
                                    <input type="hidden" name="accion" value="registraUnidad">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label for="responsable">Nombre del responsable:</label>
                                            <input type="text" class="form-control" id="responsable" name="responsable" placeholder="Ingresa el nombre del responsable" required>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="placas">Placas:</label>
                                            <input type="text" class="form-control" id="placas" name="placas" placeholder="Ingresa las placas" required>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="ruta">Ruta:</label>
                                            <select class="form-control"  name="ruta" id="ruta" required>
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
                                            <label for="capacidad">Capacidad:</label>
                                            <input type="number" class="form-control" id="capacidad" name="capacidad" placeholder="Ingresa la capacidad" required>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="inicio">Inicio de operaciones:</label>
                                            <input type="date" class="form-control" id="inicio" name="inicio" required>
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
</body>
</html>