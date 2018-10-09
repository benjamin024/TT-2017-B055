<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Avisos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css" />
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <?php
        include("webservices_cliente.php");
        $avisos = selectWhere("1 = 1", "*", "aviso");

        if(@$_GET["addOk"]){
            if(@$_GET["addOk"] == 1){
                $tituloOk = "Aviso registrado correctamente";
                $mensajeOk = "La información del aviso fue registrada de manera exitosa";
            }else if(@$_GET["addOk"] == 2){
                $tituloOk = "Ocurrió un error";
                $mensajeOk = "No se pudo registrar la información del aviso, <a href='form_reg_aviso.php' style='color: #FFF;'>inténtalo nuevamente</a>";
            }
            $modal = "$('#modalOk').modal('toggle')";
            echo "<script>$(document).ready(function(){{$modal}});</script>";
        }
        if(@$_GET["editOk"]){
            if(@$_GET["editOk"] == 1){
                $tituloOk = "Información actualizada correctamente";
                $mensajeOk = "La información del aviso fue actualizada de manera exitosa";
            }else if(@$_GET["editOk"] == 2){
                $tituloOk = "Ocurrió un error";
                $mensajeOk = "No se pudo actualizar la información del aviso, inténtalo nuevamente";
            }
            $modal = "$('#modalOk').modal('toggle')";
            echo "<script>$(document).ready(function(){{$modal}});</script>";
        }
        if(@$_GET["deleteOk"]){
            if(@$_GET["deleteOk"] == 1){
                $tituloOk = "Aviso eliminado correctamente";
                $mensajeOk = "La información del aviso fue eliminada de manera exitosa, no podrá recuperarse";
            }else if(@$_GET["deleteOk"] == 2){
                $tituloOk = "Ocurrió un error";
                $mensajeOk = "No se pudo eliminar la información del aviso, inténtalo nuevamente";
            }
            $modal = "$('#modalOk').modal('toggle')";
            echo "<script>$(document).ready(function(){{$modal}});</script>";
        }
    ?>
    <script>
        function editar(id){
            $.post("webservices_cliente.php", {accion: "selectWherePost", where: "id_aviso = '"+id+"'", campos: "*", tabla: "aviso"}, function(result){
                var aviso = JSON.parse(JSON.parse(result)[0]);
                $("#id_aviso").val(aviso.id_aviso);
                $("#aviso").val(aviso.aviso);
                $("#licencia").val(aviso.no_licencia);
                $("#publicacion").val(aviso.fecha_publicacion);
                $("#vigencia").val(aviso.fecha_termino);
                $('#modalEditar').modal('toggle');
            });
        }

        function eliminarConfirma(id){
            $('#aEliminar').val(id);
            $('#modalConfirma').modal('toggle');
        }

        function eliminar(id){
            $.post("webservices_cliente.php", {accion: "eliminarAviso", id_aviso: id}, function(result){
                location.href = "admin_avisos.php?deleteOk="+result;
            });
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
                            <h4 class="card-title" style="text-align: center;">Avisos</h4>
                                <?php
                                    if(!$avisos){
                                        echo "<h6 class='card-title' style'text-align: center;'>No hay avisos registrados</h6>";
                                    }else{
                                ?>
                                <table class="table table-bordered table-sm table-hover" >
                                    <thead style="background-color: #0099CC; color: #FFFFFF; height: 20px;">
                                        <th>ID</th>
                                        <th>Fecha de publicación</th>
                                        <th>Aviso</th>
                                        <th>Vigencia</th>
                                        <th></th>
                                    </thead>
                                    <tbody style="color: #FFF; ">
                                        <?php
                                            foreach($avisos as $a){
                                                echo "<tr>";
                                                echo "<td>".$a["id_aviso"]."</td>";
                                                echo "<td>".$a["aviso"]."</td>";
                                                echo "<td>".$a["fecha_publicacion"]."</td>";
                                                $hoy = strtotime(date("Y-m-d"));
                                                $termino = strtotime($a["fecha_termino"]);
                                                if($termino < $hoy){
                                                    $fin = "Vencido (".$a["fecha_termino"].")";
                                                }else{
                                                    $fin = $a["fecha_termino"];
                                                }
                                                echo "<td>$fin</td>";
                                                echo "<td><img style='cursor: pointer;' src='img/ico-editar.png' onclick=\"editar('".$a["id_aviso"]."');\" width='25px'><img style='cursor: pointer;' src='img/ico-borrar.png' width='25px'  onclick=\"eliminarConfirma('".$a["id_aviso"]."');\" ></td>";
                                                echo "</tr>";
                                            }
                                        ?>
                                    </tbody>
                                </table>
                                <?php
                                    }
                                ?>
                            </div>
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
                            <h4 class="card-title" style="text-align: center;">Actualizar aviso</h4>
                                <form action="webservices_cliente.php" method="post">
                                    <input type="hidden" name="accion" value="editarAviso">
                                    <input type="hidden" id="id_aviso" name="id_aviso">
                                    <input type="hidden" id="publicacion" name="publicacion">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label for="vigencia">Vigencia:</label>
                                            <input type="date" class="form-control" id="vigencia" name="vigencia" placeholder="Ingresa la fecha de término del aviso" required>
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label for="aviso">Aviso:</label>
                                            <textarea style="resize: none; "class="form-control" id="aviso" name="aviso" placeholder="Ingresa el aviso" rows="5" required></textarea>
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