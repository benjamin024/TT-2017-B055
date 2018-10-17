<?php
    $img = @$_POST["img"];
    $nombre =@$_POST["nombre"];

    list($type, $img) = explode(';', $img);
    list(, $img)      = explode(',', $img);
    $img = base64_decode($img);

    if(!file_exists("img/marcadores/img_".$nombre.".png")){
        if(file_put_contents("img/marcadores/img_".$nombre.".png", $img))
            echo "OK! :D";
        else
            echo "Error :c";
    }else{
        echo "ya existe :P";
    }
        

?>