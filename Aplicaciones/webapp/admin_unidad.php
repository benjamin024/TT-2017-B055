<?php
    include_once("webservices_cliente.php");
    $rutas = selectWhere("id_ruta > 4", "*", "ruta");
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Unidades</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css" />
    <style>
        .modal-backdrop {
        background-color: #7a7a7a;
        }
    </style>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <?php
    if(@$_GET["busca"]){
        $busca = @$_GET["busca"];
        $where = "UPPER(id_unidad) LIKE '%".strtoupper($busca)."%' OR id_ruta = '$busca'";
    }else{
        $where = "1 = 1";
    }
    $unidades = selectWhere($where, "u.*, r.nombre", "unidad u INNER JOIN ruta r ON u.id_ruta = r.id_ruta");
    if(@$_GET["addOk"]){
        if(@$_GET["addOk"] == 1){
            $tituloOk = "Unidad registrada correctamente";
            $mensajeOk = "La información de la unidad fue registrada de manera exitosa";
        }else if(@$_GET["addOk"] == 2){
            $tituloOk = "Ocurrió un error";
            $mensajeOk = "No se pudo registrar la información de la unidad, <a href='form_reg_unidad.php' style='color: #FFF;'>inténtalo nuevamente</a>";
        }
        $modal = "$('#modalOk').modal('toggle')";
        echo "<script>$(document).ready(function(){{$modal}});</script>";
    }
    if(@$_GET["editOk"]){
        if(@$_GET["editOk"] == 1){
            $tituloOk = "Información actualizada correctamente";
            $mensajeOk = "La información de la unidad fue actualizada de manera exitosa";
        }else if(@$_GET["editOk"] == 2){
            $tituloOk = "Ocurrió un error";
            $mensajeOk = "No se pudo actualizar la información de la unidad, inténtalo nuevamente";
        }
        $modal = "$('#modalOk').modal('toggle')";
        echo "<script>$(document).ready(function(){{$modal}});</script>";
    }
    if(@$_GET["deleteOk"]){
        if(@$_GET["deleteOk"] == 1){
            $tituloOk = "Unidad eliminada correctamente";
            $mensajeOk = "La información de la unidad fue eliminada de manera exitosa, no podrá recuperarse";
        }else if(@$_GET["deleteOk"] == 2){
            $tituloOk = "Ocurrió un error";
            $mensajeOk = "No se pudo eliminar la información de la unidad, inténtalo nuevamente";
        }
        $modal = "$('#modalOk').modal('toggle')";
        echo "<script>$(document).ready(function(){{$modal}});</script>";
    }
    ?>
    <script>
        function editar(id){
            $.post("webservices_cliente.php", {accion: "selectWherePost", where: "id_unidad = '"+id+"'", campos: "*", tabla: "unidad"}, function(result){
                var unidad = JSON.parse(JSON.parse(result)[0]);
                $("#responsable").val(unidad.responsable);
                $("#placas").val(unidad.id_unidad);
                $("#ruta option[value="+unidad.id_ruta+"]").prop('selected', 'selected');
                $("#capacidad").val(unidad.capacidad);
                $("#inicio").val(unidad.inicio_operaciones);
                var fin = "";
                if(unidad.fin_operaciones == "1000-01-01" || unidad.fin_operaciones == "0999-12-31"){
                    fin = "";
                }else{
                    fin = unidad.fin_operaciones;
                }
                $("#finalOperaciones").val(fin);
                $('#modalEditar').modal('toggle');
            });
        }

        function eliminarConfirma(id){
            $('#aEliminar').val(id);
            $('#modalConfirma').modal('toggle');
        }

        function eliminar(id){
            $.post("webservices_cliente.php", {accion: "eliminarUnidad", id_unidad: id}, function(result){
                location.href = "admin_unidad.php?deleteOk="+result;
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
                    <div class="card" style="width: 900px;  color: #FFF; background-color: rgba(0,0,0,0.7);">
                        <div class="card-body justify-content-center">
                            <h4 class="card-title" style="text-align: center;">Unidades</h4>
                            
                            <form action="admin_unidad.php" method="get">
                            <div class="input-group input-group-sm mb-3">
                                <input type="text" class="form-control" name="busca" value="<?=@$_GET["busca"]?>" placeholder="Buscar unidad por placas o ruta" aria-label="Buscar unidad por placas o ruta" aria-describedby="basic-addon2">
                                <div class="input-group-append">
                                    <button type="submit" class="btn" style="background-color: #0099CC; color: #FFFFFF;" type="button"><img src="img/ico-buscar.png" height="20px"></button>
                                </div></form><br><br>
                            </div>
                            <?php
                                if(count($unidades) > 0){
                            ?>
                                <table class="table table-bordered table-sm table-hover" >
                                    <thead style="background-color: #0099CC; color: #FFFFFF; height: 20px;">
                                        <th>Placas</th>
                                        <th>Responsable</th>
                                        <th>Capacidad</th>
                                        <th>Ruta</th>
                                        <th>Inicio de operaciones</th>
                                        <th>Final de operaciones</th>
                                        <th></th>
                                    </thead>
                                    <tbody style="color: #FFF; ">
                                        <?php
                                            foreach($unidades as $u){
                                                echo "<tr>";
                                                echo "<td>".$u["id_unidad"]."</td>";
                                                echo "<td>".$u["responsable"]."</td>";
                                                echo "<td>".$u["capacidad"]."</td>";
                                                echo "<td>".$u["nombre"]."</td>";
                                                echo "<td>".$u["inicio_operaciones"]."</td>";
                                                if($u["fin_operaciones"] == "1000-01-01" || $u["fin_operaciones"] == "0999-12-31"){
                                                    $fin = "En operación";
                                                }else{
                                                    $fin = $u["fin_operaciones"];
                                                }
                                                echo "<td>$fin</td>";
                                                echo "<td><a href='admin_mantenimiento.php?unidad=".$u["id_unidad"]."'><img style='cursor: pointer;' src='img/ico-mantenimiento.png' width='25px'></a><img style='cursor: pointer;' src='img/ico-editar.png' onclick=\"editar('".$u["id_unidad"]."');\" width='25px'><img style='cursor: pointer;' src='img/ico-borrar.png' width='25px'  onclick=\"eliminarConfirma('".$u["id_unidad"]."');\" ></td>";
                                                echo "</tr>";
                                            }
                                        ?>
                                    </tbody>
                                </table>
                                <?php
                                }else{
                                    if(!@$_GET["busca"])
                                        echo "<h6 class='card-title' style'text-align: center;'>No hay unidades registradas (<a href='form_reg_unidad.php' style='color: #FFF;'>Registrar unidad</a>)</h6>";
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
                            <h4 class="card-title" style="text-align: center;">Actualizar información de la unidad</h4>
                                <form action="webservices_cliente.php" method="post">
                                    <input type="hidden" name="accion" value="editarUnidad">
                                    <input type="hidden" name="inicio" id="inicio">
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
                                            <input type="text" class="form-control" id="placas" name="placas" placeholder="Ingresa las placas" readonly required>
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
                                            <input type="text" class="form-control" id="capacidad" name="capacidad" placeholder="Ingresa la capacidad" required>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="inicio">Fin de operaciones:</label>
                                            <input type="date" class="form-control" id="termino" name="termino">
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
                            <h4 class="card-title" style="text-align: center;">Eliminar registro de unidad</h4>
                            <p>Si decides eliminar el registro de la unidad, no podrás recuperar su información después. ¿Realmente deseas eliminar el registro de la unidad?</p>
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