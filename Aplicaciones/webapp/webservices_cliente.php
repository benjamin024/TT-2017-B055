<?php
    ini_set('soap.wsdl_cache_enabled', 0);
    ini_set('soap.wsdl_cache_ttl', 900);
    ini_set('default_socket_timeout', 15);

    $servicio="http://localhost:8080/wsTT/bdActions?wsdl"; //url del servicio
    $parametros=array(); //parametros de la llamada
    $client = new SoapClient($servicio, $parametros);

    $ACCION = @$_POST["accion"];

    if($ACCION == "registraUnidad"){
        $parametros["idUni"] =@ $_POST["placas"];
        $parametros["cap"] =@ $_POST["capacidad"];
        $parametros["idRu"] =@ $_POST["ruta"];
        $parametros["iniOper"] =@ $_POST["inicio"];
        $parametros["resp"] =@ $_POST["responsable"];
        $parametros["finOper"] =@ "1000-01-01";
        $parametros["lat"] =@ "";
        $parametros["lon"] =@ "";

        $result = $client->insUnidad($parametros);
        $result = (array) $result;
        
        if(!$result["return"])
            $result["return"] == 2;
        
        header("location: admin_unidad.php?addOk=".$result["return"]);
        exit(1);
    }

    if($ACCION == "editarUnidad"){
        $parametros["idUni"] =@ $_POST["placas"];
        $parametros["cap"] =@ $_POST["capacidad"];
        $parametros["idRu"] =@ $_POST["ruta"];
        $parametros["resp"] =@ $_POST["responsable"];
        $parametros["iniOper"] =@ $_POST["inicio"];
        $parametros["finOper"] =@ (!$_POST["termino"])?"1000-01-01":$_POST["termino"];
        $parametros["lat"] =@ "";
        $parametros["lon"] =@ "";

        $result = $client->altUnidad($parametros);
        $result = (array) $result;

        if(!$result["return"])
            $result["return"] == 2;
        
        header("location: admin_unidad.php?editOk=".$result["return"]);
        exit(1);
    }

    if($ACCION == "eliminarUnidad"){
        $parametros["idUni"] =@ $_POST["id_unidad"];

        $result = $client->delUnidad($parametros);
        $result = (array) $result;

        if(!$result["return"])
            $result["return"] == 2;
        
        print_r($result["return"]);
        exit(1);
    }

    if($ACCION == "registraConductor"){
        $parametros["rfcCon"] =@ $_POST["rfc"];
        $parametros["nom"] =@ $_POST["nombre"];
        $parametros["suel"] =@ $_POST["sueldo"];
        $parametros["noLic"] =@ $_POST["licencia"];
        $parametros["tel"] =@ $_POST["telefono"];

        $result = $client->insConductor($parametros);
        $result = (array) $result;

        if(!$result["return"])
            $result["return"] == 2;
        
        header("location: admin_conductor.php?addOk=".$result["return"]);
        exit(1);
    }

    if($ACCION == "editarConductor"){
        $parametros["rfcCon"] =@ $_POST["rfc"];
        $parametros["nom"] =@ $_POST["nombre"];
        $parametros["suel"] =@ $_POST["sueldo"];
        $parametros["noLic"] =@ $_POST["licencia"];
        $parametros["tel"] =@ $_POST["telefono"];

        $result = $client->altConductor($parametros);
        $result = (array) $result;
        
        if(!$result["return"])
            $result["return"] == 2;
        
        header("location: admin_conductor.php?editOk=".$result["return"]);
        exit(1);
    }

    if($ACCION == "eliminarConductor"){
        $parametros["rfcCon"] =@ $_POST["rfc_conductor"];

        $result = $client->delConductor($parametros);
        $result = (array) $result;

        if(!$result["return"])
            $result["return"] == 2;
        
        print_r($result["return"]);
        exit(1);
    }

    if($ACCION == "crearAviso"){
        $parametros["avi"] =@ $_POST["aviso"];
        $parametros["fecPubli"] = date("Y-m-d");
        $parametros["fecTer"] =@ $_POST["vigencia"];
        $result = $client->insAviso($parametros);
        $result = (array) $result;
        header("location: admin_avisos.php?addOk=".$result["return"]);
        exit(1);
    }

    if($ACCION == "editarAviso"){
        $parametros["idAvi"] =@ $_POST["id_aviso"];
        $parametros["avi"] =@ $_POST["aviso"];
        $parametros["fecPubli"] =@ $_POST["publicacion"];
        $parametros["fecTer"] =@ $_POST["vigencia"];
        $result = $client->altAviso($parametros);
        $result = (array) $result;
        header("location: admin_avisos.php?editOk=".$result["return"]);
        exit(1);
    }

    if($ACCION == "eliminarAviso"){
        $parametros["idAvi"] =@ $_POST["id_aviso"];

        $result = $client->delAviso($parametros);
        $result = (array) $result;

        if(!$result["return"])
            $result["return"] == 2;
        
        print_r($result["return"]);
        exit(1);
    }

    if($ACCION == "agendarMantenimiento"){
        $parametros["idUni"] = @$_POST["unidad"];
        $parametros["fec"] = @$_POST["fecha"];
        $parametros["resp"] = null;
        $parametros["comen"] = null;
        $parametros["cost"] = null;
        
        $result = $client->insMantenimiento($parametros);
        $result = (array) $result;
        header("location: admin_mantenimiento.php?unidad=".@$_POST["unidad"]."&addOk=".$result["return"]);
        exit(1);
    }

    if($ACCION == "completarMantenimiento"){
        $parametros["idMan"] = @$_POST["id_mantenimiento"];
        $parametros["idUni"] = @$_POST["id_unidad"];
        $parametros["fec"] = @$_POST["fecha"];
        $parametros["resp"] = @$_POST["responsable"];
        $parametros["comen"] = @$_POST["comentarios"];
        $parametros["cost"] = @$_POST["costo"];
        
        $result = $client->altMantenimiento($parametros);
        $result = (array) $result;
        header("location: admin_mantenimiento.php?unidad=".@$_POST["id_unidad"]."&editOk=".$result["return"]);
        exit(1);
    }

    if($ACCION == "cancelarMantenimiento"){
        $parametros["idMan"] = @$_POST["id_mantenimiento"];
        
        $result = $client->delMantenimiento($parametros);
        $result = (array) $result;

        if(!$result["return"])
            $result["return"] == 2;
        
        print_r($result["return"]);
        exit(1);
    }

    function selectWhere($where, $campos, $tabla){
        GLOBAL $client;

        $parametros["campos"] = $campos;
        $parametros["condicion"] = $where;
        $parametros["tabla"]  = $tabla;

        $result = $client->queryCons($parametros);
        $result = (array) $result;

        $result =@ $result["return"];
        $result = json_decode($result, true);

        $datos = array();
        if($result){
            foreach($result as $u){
                $u = json_decode($u, true);
                $aux = array();
                foreach($u as $key => $value){
                    $aux[$key] = trim($value);
                }
                $datos[] = $aux;
            }
        }
        return $datos;
    }

    if($ACCION == "selectWherePost"){
        $where =@$_POST["where"];
        $campos =@$_POST["campos"];
        $tabla =@$_POST["tabla"];

        $parametros["campos"] = $campos;
        $parametros["condicion"] = $where;
        $parametros["tabla"]  = $tabla;

        $result = $client->queryCons($parametros);
        $result = (array) $result;

        $result =@ $result["return"];

        print_r($result); //implode("~",$result);
        exit(1);
    }

    if($ACCION == "registrarTarifa"){
        $parametros["desc"] =@$_POST["descripcion"];
        $parametros["cost"] =@$_POST["costo"];

        $result = $client->insTarifa($parametros);

        if($result){
            $idMax = selectWhere("1 = 1", "MAX(id_tarifa) as id", "tarifa")[0]["id"];
            echo $idMax;
        }else
            echo $idMax;
        exit(1);
    }

    if($ACCION == "registrarRuta"){
        
        $nombreR =@$_POST["nombre"];
        $color =@$_POST["color"];
        $formasC =@$_POST["formasCobro"];
        $combustible =@$_POST["combustible"];
        $tarifas =@$_POST["tarifas"];
        $numEstaciones=@$_POST["numEstaciones"];
        $ordenEstaciones=@$_POST["ordenEstaciones"];
        $frecuencia =@$_POST["frecuencia"];
        $tiempoViaje =@$_POST["tiempoV"];
        
        $rutaData["nom"] = $nombreR;
        $rutaData["comb"] = $combustible;
        $rutaData["fCob"] = $formasC;
        $rutaData["col"] = $color;
        $rutaData["frecIda"] = $frecuencia;
        $rutaData["frecVuel"] = $frecuencia;
        $rutaData["tiemRec"] = $tiempoViaje;

        if($client->insRuta($rutaData)){
            $idR = selectWhere("1 = 1", "MAX(id_ruta) as id", "ruta")[0]["id"];
            foreach($tarifas as $t){
                $rtData["idRu"] = $idR;
                $rtData["idTar"] = $t;
                $client->insRutaTarifa($rtData);
            }
            for($i = 0; $i < 7; $i++){
                $rhData["idR"] = $idR;
                $rhData["dia"] = $i;
                $rhData["hInicio"] =@$_POST["ini_$i"];
                $rhData["hFin"] =@$_POST["fin_$i"];
                $client->insRutaHorario($rhData);
            }
            $ordenEstaciones = explode(",", $ordenEstaciones);
            $indices = array();
            foreach($ordenEstaciones as $oe){
                $aux = explode("--",@$_POST["estacion_$oe"]);
                $existe = selectWhere("nombre LIKE '".$aux[0]."'", "id_estacion", "estacion")[0]["id_estacion"];
                if($existe){
                    $indices[] = $existe;
                }else{
                    $esData["nom"] = $aux[0];
                    $esData["lat"] = explode(",",$aux[1])[0];
                    $esData["lon"] = explode(",",$aux[1])[1];
                    $client->insEstacion($esData);
                    $indices[] = selectWhere("1 = 1", "MAX(id_estacion) AS id", "estacion")[0]["id"];
                }
            }
            for($i = 0; $i < $numEstaciones; $i++){
                if(($i + 1) >= $numEstaciones)
                    $siguiente = 0;
                else
                    $siguiente = $indices[$i + 1];

                $reData["idRu"] = $idR;
                $reData["idEst"] = $indices[$i];
                $reData["sigEst"] = $siguiente;
                $client->insRutaEstacion($reData);
                echo "<script>window.open('generaimgs.php?id=".$indices[$i]."', '_blank');</script>";
            }
            echo "<script>location.href ='admin_rutas.php?addOk=1';</script>";
        }else{
            echo "<script>location.href ='admin_rutas.php?addOk=2';</script>";
        }
        exit(1);
    }

    if($ACCION == "eliminarRuta"){
        $idR =@$_POST["id_ruta"];

        $parametros["idRu"] = $idR;
        $result = $client->delRuta($parametros);
        
        $parametros = array();
        $parametros["qr"] = "DELETE FROM ruta_tarifa WHERE id_ruta = $idR";
        $result = $client->queryUDI($parametros);
        
        $parametros = array();
        $parametros["idRu"] = $idR;
        $result = $client->delRutaHorario($parametros);

        $aEliminar = array();
        $est_rut = selectWhere("id_ruta = $idR", "id_estacion", "ruta_estacion");
        foreach($est_rut as $er){
            $numR = selectWhere("id_estacion = ".$er["id_estacion"], "count(*) as num", "ruta_estacion")[0]["num"];
            if($numR == 1)
                $aEliminar[] = $er["id_estacion"];
        }

        foreach($aEliminar as $ae){
            $parametros = array();
            $parametros["idEst"] = $ae;
            $result = $client->delEstacion($parametros);
        }        

        $parametros = array();
        $parametros["qr"] = "DELETE FROM ruta_estacion WHERE id_ruta = $idR";
        $result = $client->queryUDI($parametros);

        echo 1;

        exit(1);
    }

    if($ACCION == "registraViaje"){
        $id_unidad =@$_POST["unidad"];
        $rfc_conductor =@$_POST["conductor"];
        $fecha =@$_POST["fecha"];
        $hora_salida =@$_POST["hora"].":00";
        $direccion =@$_POST["direccion"];

        $parametros["qr"] = "INSERT INTO viaje_unidad(id_unidad, rfc_conductor, fecha, hora_salida, direccion) VALUES('$id_unidad', '$rfc_conductor', '$fecha', '$hora_salida', $direccion);";
        
        $result = $client->queryUDI($parametros);
        $result = (array) $result;
        header("location: form_reg_viaje.php");
        exit(1);
    }

    function getDemanda($idRuta, $direccion){
        GLOBAL $client;
        $parametros["rut"] = $idRuta;
        $parametros["sent"] = $direccion;
        
        $result = $client->calFrecuencia($parametros);
        $result = (array) $result;
        
        return $result["return"];
    }

    function getUnidadesMinimas($idRuta){
        GLOBAL $client;
        $parametros["rut"] = $idRuta;
        
        $result = $client->unidMinimas($parametros);
        $result = (array) $result;
        
        return $result["return"];
    }
?>