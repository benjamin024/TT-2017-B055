<?php
    ini_set('soap.wsdl_cache_enabled', 0);
    ini_set('soap.wsdl_cache_ttl', 900);
    ini_set('default_socket_timeout', 15);

    $servicio="http://localhost:8080/wsTT/bdActions?wsdl"; //url del servicio
    $parametros=array(); //parametros de la llamada
    $client = new SoapClient($servicio, $parametros);

    $ACCION = @$_POST["accion"];

    if($ACCION == "registraUnidad"){
        echo "<br>Entré a registraUnidad<br>";
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
        return $result["return"];
    }
?>