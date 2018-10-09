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
?>