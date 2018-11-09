<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Mantenimientos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css" />
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <?php
        include("webservices_cliente.php");
        $mantenimientos = selectWhere("id_unidad = '".@$_GET["unidad"]."' ORDER BY fecha DESC", "*", "mantenimiento");
        if(@$_GET["addOk"]){
            if(@$_GET["addOk"] == 1){
                $tituloOk = "Mantenimiento agendado correctamente";
                $mensajeOk = "La información del mantenimiento fue registrada de manera exitosa";
            }else if(@$_GET["addOk"] == 2){
                $tituloOk = "Ocurrió un error";
                $mensajeOk = "No se pudo registrar la información del mantenimiento, inténtalo nuevamente";
            }
            $modal = "$('#modalOk').modal('toggle')";
            echo "<script>$(document).ready(function(){{$modal}});</script>";
        }
        if(@$_GET["editOk"]){
            if(@$_GET["editOk"] == 1){
                $tituloOk = "Información actualizada correctamente";
                $mensajeOk = "La información del mantenimiento fue actualizada de manera exitosa";
            }else if(@$_GET["editOk"] == 2){
                $tituloOk = "Ocurrió un error";
                $mensajeOk = "No se pudo actualizar la información del mantenimiento, inténtalo nuevamente";
            }
            $modal = "$('#modalOk').modal('toggle')";
            echo "<script>$(document).ready(function(){{$modal}});</script>";
        }
        if(@$_GET["deleteOk"]){
            if(@$_GET["deleteOk"] == 1){
                $tituloOk = "Mantenimiento cancelado correctamente";
                $mensajeOk = "La información del mantenimiento fue eliminada de manera exitosa, no podrá recuperarse";
            }else if(@$_GET["deleteOk"] == 2){
                $tituloOk = "Ocurrió un error";
                $mensajeOk = "No se pudo eliminar la información del mantenimiento, inténtalo nuevamente";
            }
            $modal = "$('#modalOk').modal('toggle')";
            echo "<script>$(document).ready(function(){{$modal}});</script>";
        }
    ?>
    <script>
        function editar(id){
            $.post("webservices_cliente.php", {accion: "selectWherePost", where: "id_mantenimiento = '"+id+"'", campos: "*", tabla: "mantenimiento"}, function(result){
                var mantenimiento = JSON.parse(JSON.parse(result)[0]);
                $("#id_mantenimiento").val(mantenimiento.id_mantenimiento);
                $("#id_unidad").val(mantenimiento.id_unidad);
                $("#fecha").val(mantenimiento.fecha);
                $('#modalEditar').modal('toggle');
            });
        }

        function eliminarConfirma(id){
            $('#aEliminar').val(id);
            $('#modalConfirma').modal('toggle');
        }

        function eliminar(id){
            $.post("webservices_cliente.php", {accion: "cancelarMantenimiento", id_mantenimiento: id}, function(result){
                location.href = "admin_mantenimiento.php?unidad=<?=@$_GET["unidad"]?>&deleteOk="+result;
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
                            <h4 class="card-title" style="text-align: center;">Mantenimientos para la unidad <?=@$_GET["unidad"]?></h4>
                            <form action="webservices_cliente.php" method="post">
                            <input type="hidden" name="accion" value="agendarMantenimiento">
                            <input type="hidden" name="unidad" value="<?=@$_GET["unidad"]?>">
                            <label for="fecha">Agendar nuevo mantenimiento:</label>
                            <div class="input-group input-group-sm mb-3">
                                <input type="date" class="form-control" name="fecha" placeholder="Fecha de nuevo mantenimiento" aria-label="Fecha de nuevo mantenimiento" aria-describedby="basic-addon2" required>
                                <div class="input-group-append">
                                    <button class="btn" type="submit" style="background-color: #0099CC; color: #FFFFFF;" type="button"><img src="img/ico-plus.png" height="20px"></button>
                                </div></form><br><br>
                            </div>
                                <?php
                                    if(count($mantenimientos) > 0){
                                ?>
                                <table class="table table-bordered table-sm table-hover" >
                                    <thead style="background-color: #0099CC; color: #FFFFFF; height: 20px;">
                                        <th>ID</th>
                                        <th>Fecha</th>
                                        <th>Costo</th>
                                        <th>Responsable</th>
                                        <th>Observaciones</th>
                                        <th></th>
                                    </thead>
                                    <tbody style="color: #FFF; ">
                                        <?php
                                            foreach($mantenimientos as $m){
                                                echo "<tr>";
                                                echo "<td>".$m["id_mantenimiento"]."</td>";
                                                echo "<td>".$m["fecha"]."</td>";
                                                $costo = ($m["costo"] == 0)?"-":"$".$m["costo"];
                                                echo "<td>$costo</td>";
                                                $responsable = ($m["responsable"] == "null")?"-":$m["responsable"];
                                                echo "<td>$responsable</td>";
                                                $comentarios = ($m["comentarios"] == "null")?"-":$m["comentarios"];
                                                echo "<td>$comentarios</td>";
                                                echo "<td style='text-align: center;'>";
                                                if($costo == "-" && $responsable == "-" && $comentarios == "-"){
                                                    echo "<img style='cursor: pointer;' src='img/ico-check.png' onclick=\"editar('".$m["id_mantenimiento"]."');\" width='25px'><img style='cursor: pointer;' src='img/ico-borrar.png' width='25px'  onclick=\"eliminarConfirma('".$m["id_mantenimiento"]."');\" >";
                                                }
                                                echo "</td>";
                                                echo "</tr>";
                                            }
                                        ?>
                                    </tbody>
                                </table>
                                <?php
                                }else{
                                    if(!@$_GET["unidad"])
                                        echo "<h6 class='card-title' style'text-align: center;'>No hay mantenimientos agendados</h6>";
                                    else
                                        echo "<h6 class='card-title' style'text-align: center;'>La unidad no tiene mantenimientos agendados</h6>";
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
                    <div class="card" style="max-width: 500px;  color: #FFF; background-color: rgba(0,0,0,0.85);">
                        <div class="card-body justify-content-center">
                            <h4 class="card-title" style="text-align: center;">Completar información del mantenimiento</h4>
                                <form action="webservices_cliente.php" method="post">
                                    <input type="hidden" name="accion" value="completarMantenimiento">
                                    <input type="hidden" id="id_mantenimiento" name="id_mantenimiento">
                                    <input type="hidden" id="id_unidad" name="id_unidad">
                                    <input type="hidden" id="fecha" name="fecha">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label for="responsable">Responsable:</label>
                                            <input type="text" class="form-control" id="responsable" name="responsable" placeholder="Ingresa el nombre del responsable del mantenimiento" required>
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label for="costo">Costo:</label>
                                            <input type="number" class="form-control" id="costo" name="costo" placeholder="Ingresa el costo del mantenimiento" required>
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label for="comentarios">Comentarios:</label>
                                            <textarea style="resize: none; "class="form-control" id="comentarios" name="comentarios" placeholder="Ingresa los comentarios derivados del mantenimiento" rows="5" required></textarea>
                                        </div>
                                    </div>
                                </div>
                                <center>
                                    <button type="button" class="btn" style="margin-top: 10px; background: transparent; border-color:#FFF; color: #FFFFFF;" data-dismiss="modal">Cancelar</button>
                                    <button class="btn" style="margin-top: 10px; background-color: #0099CC; color: #FFFFFF;">Aceptar</button>
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
                            <h4 class="card-title" style="text-align: center;">Cancelar mantenimiento</h4>
                            <p>Si decides cancelar el mantenimiento, no podrás recuperar su información después. ¿Realmente deseas cancelar el mantenimiento?</p>
                                <center>
                                    <input type="hidden" id="aEliminar">
                                    <button type="button" class="btn" style="margin-top: 10px; background: transparent; border-color:#FFF; color: #FFFFFF;" data-dismiss="modal">Regresar</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <button class="btn" style="margin-top: 10px; background-color: #0099CC; color: #FFFFFF;" onclick="eliminar($('#aEliminar').val());">Cancelar</button>
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