<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Rutas</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css" />
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <?php
        include("webservices_cliente.php");
        $rutas = selectWhere("id_ruta > 4", "*", "ruta");

        if(@$_GET["addOk"]){
            if(@$_GET["addOk"] == 1){
                $tituloOk = "Ruta registrada correctamente";
                $mensajeOk = "La información de la ruta fue registrada de manera exitosa";
            }else if(@$_GET["addOk"] == 2){
                $tituloOk = "Ocurrió un error";
                $mensajeOk = "No se pudo registrar la información de la ruta, <a href='form_reg_ruta.php' style='color: #FFF;'>inténtalo nuevamente</a>";
            }
            $modal = "$('#modalOk').modal('toggle')";
            echo "<script>$(document).ready(function(){{$modal}});</script>";
        }
        if(@$_GET["editOk"]){
            if(@$_GET["editOk"] == 1){
                $tituloOk = "Información actualizada correctamente";
                $mensajeOk = "La información de la ruta fue actualizada de manera exitosa";
            }else if(@$_GET["editOk"] == 2){
                $tituloOk = "Ocurrió un error";
                $mensajeOk = "No se pudo actualizar la información de la ruta, inténtalo nuevamente";
            }
            $modal = "$('#modalOk').modal('toggle')";
            echo "<script>$(document).ready(function(){{$modal}});</script>";
        }
        if(@$_GET["deleteOk"]){
            if(@$_GET["deleteOk"] == 1){
                $tituloOk = "Aviso eliminado correctamente";
                $mensajeOk = "La información de la ruta fue eliminada de manera exitosa, no podrá recuperarse";
            }else if(@$_GET["deleteOk"] == 2){
                $tituloOk = "Ocurrió un error";
                $mensajeOk = "No se pudo eliminar la información de la ruta, inténtalo nuevamente";
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
                            <h4 class="card-title" style="text-align: center;">Rutas</h4>
                                <?php
                                    if(!$rutas){
                                        echo "<h6 class='card-title' style'text-align: center;'>No hay rutas registradas</h6>";
                                    }else{
                                ?>
                                <table class="table table-bordered table-sm table-hover" >
                                    <thead style="background-color: #0099CC; color: #FFFFFF; height: 20px;">
                                        <th>ID</th>
                                        <th>Ruta</th>
                                        <th>Color</th>
                                        <th>Combustible por corrida</th>
                                        <th>Forma de pago</th>
                                        <th></th>
                                    </thead>
                                    <tbody style="color: #FFF; ">
                                        <?php
                                            foreach($rutas as $r){
                                                echo "<tr>";
                                                echo "<td>".$r["id_ruta"]."</td>";
                                                echo "<td>".$r["nombre"]."</td>";
                                                echo "<td style='background-color: ".$r["color"]."'>&nbsp;</td>";
                                                echo "<td>".$r["combustible"]." litros</td>";
                                                echo "<td>".selectWhere("id_forma_cobro = ".$r["id_forma_cobro"], "descripcion", "forma_cobro")[0]["descripcion"]."</td>";
                                                echo "<td><a href='admin_estacionesruta.php?ruta=".$r["id_ruta"]."'><img style='cursor: pointer;' src='img/ico-ver.png' width='25px'></a><img style='cursor: pointer;' src='img/ico-editar.png' onclick=\"editar('".$r["id_ruta"]."');\" width='25px'><img style='cursor: pointer;' src='img/ico-borrar.png' width='25px'  onclick=\"eliminarConfirma('".$r["id_ruta"]."');\" ></td>";
                                                echo "</tr>";
                                            }
                                        ?>
                                    </tbody>
                                </table>
                                <?php
                                    }
                                ?>
                                <center><a href="form_reg_ruta.php"><button class="btn" style="margin-top: 10px; background-color: #0099CC; color: #FFFFFF;" data-dismiss="modal">Registrar nueva ruta</button></a></center>
                            </div>
                        </div>
                    </div><br>&nbsp;<br><br><br>
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
                            <h4 class="card-title" style="text-align: center;">Eliminar registro de la ruta</h4>
                            <p>Si decides eliminar el registro de la ruta, no podrás recuperar su información después. ¿Realmente deseas eliminar el registro de la ruta?</p>
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