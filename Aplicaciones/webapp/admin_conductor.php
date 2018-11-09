<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Conductores</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css" />
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <?php
        include("webservices_cliente.php");
        if(@$_GET["busca"]){
            $busca = @$_GET["busca"];
            $where = "UPPER(rfc_conductor) LIKE '%".strtoupper($busca)."%' OR LOWER(nombre) LIKE '%".strtolower($busca)."%'";
        }else{
            $where = "1 = 1";
        }
        $conductores = selectWhere($where, "*", "conductor");
        if(@$_GET["addOk"]){
            if(@$_GET["addOk"] == 1){
                $tituloOk = "Conductor registrado correctamente";
                $mensajeOk = "La información del conductor fue registrada de manera exitosa";
            }else if(@$_GET["addOk"] == 2){
                $tituloOk = "Ocurrió un error";
                $mensajeOk = "No se pudo registrar la información del conductor, <a href='form_reg_conductor.php' style='color: #FFF;'>inténtalo nuevamente</a>";
            }
            $modal = "$('#modalOk').modal('toggle')";
            echo "<script>$(document).ready(function(){{$modal}});</script>";
        }
        if(@$_GET["editOk"]){
            if(@$_GET["editOk"] == 1){
                $tituloOk = "Información actualizada correctamente";
                $mensajeOk = "La información del conductor fue actualizada de manera exitosa";
            }else if(@$_GET["editOk"] == 2){
                $tituloOk = "Ocurrió un error";
                $mensajeOk = "No se pudo actualizar la información del conductor, inténtalo nuevamente";
            }
            $modal = "$('#modalOk').modal('toggle')";
            echo "<script>$(document).ready(function(){{$modal}});</script>";
        }
        if(@$_GET["deleteOk"]){
            if(@$_GET["deleteOk"] == 1){
                $tituloOk = "Conductor eliminado correctamente";
                $mensajeOk = "La información del conductor fue eliminada de manera exitosa, no podrá recuperarse";
            }else if(@$_GET["deleteOk"] == 2){
                $tituloOk = "Ocurrió un error";
                $mensajeOk = "No se pudo eliminar la información del conductor, inténtalo nuevamente";
            }
            $modal = "$('#modalOk').modal('toggle')";
            echo "<script>$(document).ready(function(){{$modal}});</script>";
        }
    ?>
    <script>
        function editar(rfc){
            $.post("webservices_cliente.php", {accion: "selectWherePost", where: "rfc_conductor = '"+rfc+"'", campos: "*", tabla: "conductor"}, function(result){
                var conductor = JSON.parse(JSON.parse(result)[0]);
                $("#nombre").val(conductor.nombre);
                $("#rfc").val(conductor.rfc_conductor);
                $("#licencia").val(conductor.no_licencia);
                $("#telefono").val(conductor.telefono);
                $("#sueldo").val(conductor.sueldo);
                $('#modalEditar').modal('toggle');
            });
        }

        function eliminarConfirma(id){
            $('#aEliminar').val(id);
            $('#modalConfirma').modal('toggle');
        }

        function eliminar(id){
            $.post("webservices_cliente.php", {accion: "eliminarConductor", rfc_conductor: id}, function(result){
                location.href = "admin_conductor.php?deleteOk="+result;
            });
        }
    </script>
</head>
<body style="background-image: url('img/background.jpg'); background-size: 100%;">
    <div class="container-fluid">
        <div class="row">
            <?php include("menu-lat.html"); ?>
            <div class="col-md-10 offset-md-2">
                <div class="row"  style="position: relative; top: 50px;  width: 98%; max-height:100%; margin: 0px;  justify-content: center;">
                    <div class="card" style="width: 800px;  color: #FFF; background-color: rgba(0,0,0,0.7);">
                        <div class="card-body justify-content-center">
                            <h4 class="card-title" style="text-align: center;">Conductores</h4>
                            <form action="admin_conductor.php" method="get">
                            <div class="input-group input-group-sm mb-3">
                                <input type="text" class="form-control" name="busca" placeholder="Buscar un conductor por RFC o nombre" aria-label="Buscar un conductor por RFC o nombre" aria-describedby="basic-addon2">
                                <div class="input-group-append">
                                    <button class="btn" type="submit" style="background-color: #0099CC; color: #FFFFFF;" type="button"><img src="img/ico-buscar.png" height="20px"></button>
                                </div></form><br><br>
                                </div>
                                <?php
                                    if(count($conductores) > 0){
                                ?>
                                <table class="table table-bordered table-sm table-hover" >
                                    <thead style="background-color: #0099CC; color: #FFFFFF; height: 20px;">
                                        <th>RFC</th>
                                        <th>Nombre</th>
                                        <th>No. de licencia</th>
                                        <th>Teléfono</th>
                                        <th>Sueldo</th>
                                        <th></th>
                                    </thead>
                                    <tbody style="color: #FFF; ">
                                        <?php
                                            foreach($conductores as $c){
                                                echo "<tr>";
                                                echo "<td>".$c["rfc_conductor"]."</td>";
                                                echo "<td>".$c["nombre"]."</td>";
                                                echo "<td>".$c["no_licencia"]."</td>";
                                                echo "<td>".$c["telefono"]."</td>";
                                                echo "<td>$".$c["sueldo"]."</td>";
                                                echo "<td><img style='cursor: pointer;' src='img/ico-editar.png' onclick=\"editar('".$c["rfc_conductor"]."');\" width='25px'><img style='cursor: pointer;' src='img/ico-borrar.png' width='25px'  onclick=\"eliminarConfirma('".$c["rfc_conductor"]."');\" ></td>";
                                                echo "</tr>";
                                            }
                                        ?>
                                    </tbody>
                                </table>
                                <?php
                                }else{
                                    if(!@$_GET["busca"])
                                        echo "<h6 class='card-title' style'text-align: center;'>No hay conductores registrados (<a href='form_reg_conductor.php' style='color: #FFF;'>Registrar conductor</a>)</h6>";
                                    else
                                        echo "<h6 class='card-title' style'text-align: center;'>No se encontraron coinciencias</h6>";
                                }
                                
                                ?>
                        </div>
                    </div><br>&nbsp;<br><br><br>
                </div>
            </div>
        </div>

        <!-- The Modal -->
        <div class="modal fade" id="modalEditar" style="margin-top: 80px;">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="card" style="max-width: 500px; height: 390px; color: #FFF; background-color: rgba(0,0,0,0.85);">
                        <div class="card-body justify-content-center">
                            <h4 class="card-title" style="text-align: center;">Actualizar información del conductor</h4>
                                <form action="webservices_cliente.php" method="post">
                                    <input type="hidden" name="accion" value="editarConductor">
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
                                            <input type="text" class="form-control" id="rfc" readonly name="rfc" placeholder="Ingresa el RFC" required>
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
                                <center>
                                    <button type="button" class="btn" style="margin-top: 10px; background: transparent; border-color:#FFF; color: #FFFFFF;" data-dismiss="modal">Cancelar</button>
                                    <button class="btn" style="margin-top: 10px; background-color: #0099CC; color: #FFFFFF;">Actualizar</button>
                                </center>
                                </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- The Modal -->
        <div class="modal fade" id="modalOk" style="margin-top: 80px;">
            <div class="modal-dialog">
                <div class="modal-content">
                    
                <div class="card" style="max-width: 500px; color: #FFF; background-color: rgba(0,0,0,0.85);">
                        <div class="card-body justify-content-center">
                            <h4 class="card-title" style="text-align: center;"><?=$tituloOk?></h4>
                            <p><?=$mensajeOk?></p>
                                <center>
                                    <button class="btn" style="margin-top: 10px; background-color: #0099CC; color: #FFFFFF;" data-dismiss="modal">Aceptar</button>
                                </center>
                                </form>
                        </div>
                    </div>
                </div>

                </div>
            </div>
        </div>

        <!-- The Modal -->
        <div class="modal fade" id="modalConfirma" style="margin-top: 80px;">
            <div class="modal-dialog">
                <div class="modal-content">
                    
                <div class="card" style="max-width: 500px; color: #FFF; background-color: rgba(0,0,0,0.85);">
                        <div class="card-body justify-content-center">
                            <h4 class="card-title" style="text-align: center;">Eliminar registro de conductor</h4>
                            <p>Si decides eliminar el registro del conductor, no podrás recuperar su información después. ¿Realmente deseas eliminar el registro del conductor?</p>
                                <center>
                                    <input type="hidden" id="aEliminar">
                                    <button type="button" class="btn" style="margin-top: 10px; background: transparent; border-color:#FFF; color: #FFFFFF;" data-dismiss="modal">Cancelar</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <button class="btn" style="margin-top: 10px; background-color: #0099CC; color: #FFFFFF;" onclick="eliminar($('#aEliminar').val());">Eliminar</button>
                                </center>
                                </form>
                        </div>
                    </div>
                </div>

                </div>
            </div>
        </div>
    </div>
</body>
</html>