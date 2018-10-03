<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Registrar conductor</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css" />
</head>
<body style="background-image: url('img/background.jpg'); background-size: 100%;">
    <div class="container-fluid">
        <div class="row">
            <?php include("menu-lat.html"); ?>
            <div class="col-md-10">
                <div class="row align-items-center h-100"  style="position: absolute; top: 0px;  width: 90%; height:100%; margin: 0px;  justify-content: center;">
                    <div class="card" style="max-width: 500px; height: 390px; color: #FFF; background-color: rgba(0,0,0,0.85);">
                        <div class="card-body justify-content-center">
                            <h4 class="card-title" style="text-align: center;">Registrar conductor</h4>
                                <form action="webservices_cliente.php" method="post">
                                    <input type="hidden" name="accion" value="registraConductor">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label for="nombre">Nombre completo:</label>
                                            <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Ingresa el nombre completo del conductor" required>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="rfc">RFC:</label>
                                            <input type="text" class="form-control" id="rfc" name="rfc" placeholder="Ingresa el RFC" required>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="licencia">No. de licencia:</label>
                                            <input type="number" class="form-control" id="licencia" name="licencia" placeholder="Ingresa la licencia" required>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="telefono">Teléfono:</label>
                                            <input type="text" class="form-control" id="telefono" name="telefono" placeholder="Ingresa el teléfono" required>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="sueldo">Sueldo:</label>
                                            <input type="number" class="form-control" id="sueldo" name="sueldo" placeholder="Ingresa el sueldo" required>
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