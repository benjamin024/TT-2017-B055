<?php
    include_once("webservices_cliente.php");

    generaMarcador(@$_GET["id"]);


    function generaMarcador($idEstacion){
        $ruta_e = selectWhere("id_estacion = $idEstacion", "*", "ruta_estacion");
        if($ruta_e){
            $transbordos = array();
            foreach($ruta_e as $re){
                if(!in_array($re["id_ruta"], $transbordos))
                    $transbordos[] = $re["id_ruta"];
            }
            $porcentaje = 100/ count($transbordos);
            $arreglo = "[";
            foreach($transbordos as $t){
                $color = selectWhere("id_ruta = $t", "color", "ruta")[0]["color"];
                $arreglo .= "{nombre:\"$t\", porcentaje:\"$porcentaje\", color:\"$color\"},";
            }
            $arreglo .= "]";
            echo "$arreglo<br>";
            generaImagen($arreglo);
            echo "<script>window.close();</script>";
        }
    }

    function generaImagen($datos){
?>
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <meta http-equiv="X-UA-Compatible" content="ie=edge">
            <title>Document</title>
        </head>
        <body>
                <svg id = "lienzoSVG" width="20" height="20"></svg>
                <script src="js/jquery-3.3.1.min.js"></script>
                <script>
                var lienzoSVG = document.getElementById("lienzoSVG");
                var cx = 10;
                var cy = 10;
                var r = 10;

                var nombreImg = "";

                var oGrafico = <?=$datos?>;

                function coords(a){
                    var arad = (Math.PI / 180) * a;
                    var coords = {
                    "x" : cx+r * Math.cos(arad),
                    "y" : cy+r * Math.sin(arad)
                    };
                    return coords;
                }

                function dGajo(ap,af){
                var Xap = coords(ap).x;
                var Yap = coords(ap).y;
                var Xaf = coords(af).x;
                var Yaf = coords(af).y;

                var parametro_d  = 
                "M" + cx + ", " + cy+
                " L"+Xap+","+Yap+ 
                " A"+r+","+r+" 0 0, 1 "+Xaf+","+Yaf+ 
                " z";
                return parametro_d;
                }

                function nuevoGajo(ap,af,color){
                var nuevoGajo=document.createElementNS("http:\/\/www.w3.org/2000/svg","path");		
                nuevoGajo.setAttributeNS(null,"d", dGajo(ap,af));
                nuevoGajo.setAttributeNS(null,"fill", color); 
                nuevoGajo.setAttributeNS(null,"stroke", "#fff"); 
                nuevoGajo.setAttributeNS(null,"stroke-width", "0"); 
                lienzoSVG.appendChild(nuevoGajo);
                } 

                var ap = Array(); // angulos de partida
                var af = Array(); // angulos finales
                    
                if(oGrafico.length > 1){
                    var auxR = [];
                    for( var i=0; i < oGrafico.length; i++ ){
                        var porcentaje = oGrafico[ i ].porcentaje;
                        var color = oGrafico[ i ].color;
                        // calcula el valor del ángulo en radianes
                        af[i] = ((porcentaje*360)/100);
                        
                        if( i>0 ){
                            af[ i ] += af[ i-1 ]
                            ap[ i ] = af[ i-1 ];
                            } else { ap[ i ] = 0; }
                        nuevoGajo(ap[ i ],af[ i ],color);
                        auxR.push(oGrafico[i].nombre);
                    }
                    nombreImg = auxR.join();
                }else{
                    var circulo=document.createElementNS("http:\/\/www.w3.org/2000/svg","circle");		
                    circulo.setAttributeNS(null,"cx"    , cx);
                    circulo.setAttributeNS(null,"cy"    , cy);
                    circulo.setAttributeNS(null,"r"     , r);
                    circulo.setAttributeNS(null,"fill"  , oGrafico[0].color);
                    circulo.setAttributeNS(null,"stroke","none");
                    lienzoSVG.appendChild(circulo);

                    nombreImg = oGrafico[0].nombre;
                }

                var imagen = "";

                var svg = document.querySelector( "svg" );
                var svgData = new XMLSerializer().serializeToString( svg );

                var canvas = document.createElement( "canvas" );
                var ctx = canvas.getContext( "2d" );

                canvas.setAttribute('width', '20');
                canvas.setAttribute('height', '20');

                var img = document.createElement( "img" );
                img.setAttribute( "src", "data:image/svg+xml;base64," + btoa( svgData ) );

                img.onload = function() {
                    ctx.drawImage( img, 0, 0 );
                    
                    // Now is done
                    var imagen = canvas.toDataURL( "image/png" );
                    console.log(imagen);

                    $.post("saveImage.php", {img: imagen, nombre: nombreImg}, function(result){
                        console.log(result);
                    });
                    
                };
                
                </script>
        </body>
        </html>
<?php
    }
?>