<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Iniciar sesión</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css" />
</head>
<body style="background-image: url('img/index_bg.jpg'); background-size: 100%;">
    <div class="container-fluid" style="padding: 0px !important;">
        <div class="row align-items-center h-100"  style="position: absolute; top: 0px;  width: 100%; z-index: -1; height:100%; margin: 0px;  justify-content: center;">
            <div class="card" style="min-width: 35%; height: 350px; color: #FFF; background-color: rgba(0,0,0,0.7);">
                <div class="card-body justify-content-center">
                    <h4 class="card-title" style="text-align: center;">Iniciar sesión</h4>
                        <form action="login.php" method="post">
                        <div class="form-group">
                            <label for="usuario">Nombre de usuario</label>
                            <input type="text" class="form-control" id="usuario" name="usuario" placeholder="Ingresa el nombre de usuario" required>
                        </div>
                        <div class="form-group">
                            <label for="password">Contraseña</label>
                            <input type="password" class="form-control" id="pass" name="pass" placeholder="Ingresa tu contraseña" required>
                        </div>
                        <div class="form-check">
                            <input type="checkbox" class="form-check-input" id="recuerda" name="recuerda">
                            <label class="form-check-label" for="recuerda">Mantener sesión iniciada</label>
                        </div><br>
                        <center><button class="btn" style="background-color: #0099CC; color: #FFFFFF;">Aceptar</button></center>
                        </form>
                </div>
            </div>
        </div>
    </div>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>