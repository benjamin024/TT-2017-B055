/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos;

import algoritmos.Algoritmos;
import com.google.gson.Gson;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import planificador.Planificador;
/**
 *
 * @author Luis R
 */
@WebService(serviceName = "bdActions")
public class bdActions {

    /**
     * This is a sample web service operation
     */
    String query="";
    PreparedStatement accion;
    Statement stmt=null;
    ResultSet rs=null;
    Connection conn=null;
    int resM = 0,resulIns;
    
    public int Conecta()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost/tt?user=root&password=n0m3l0&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
          //  Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //conn = DriverManager.getConnection("jdbc:mysql://18.220.128.11/tt?user=remotoAWS&password=n0m3l0&useSSL=false&useJDBCCompliantTimezoneShift=false&useLegacyDatetimeCode=false");
           // conn = DriverManager.getConnection("jdbc:mysql://localhost/tt?user=root&password=n0m3l0&useSSL=false&useJDBCCompliantTimezoneShift=false&useLegacyDatetimeCode=false");
            //&serverTimezone=GMT-6
            stmt = conn.createStatement();
        }
        catch(Exception e)
        {
            return 0;
        }
        
        return 1;
    }
    
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    //INICIAN INSERTS
    @WebMethod(operationName = "insTarifa") //falta validar que no exista duplicidad, formato de cada dato
    public int insTarifa(@WebParam(name = "desc") String descripcion, @WebParam(name = "cost") float costo) {
        //String aux="";
        
        try{
            //
          //  Class.forName("com.mysql.jdbc.Driver");
           // conn=DriverManager.getConnection(url,usr,pass);
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tt?user=root&password=b3nj4m1n");
            
            if(Conecta()==0)
                    throw new Exception();
           // aux = ;
            resulIns=stmt.executeUpdate("insert into tarifa (descripcion, costo) values('"+descripcion+"',"+costo+")");
            //stmt.executeUpdate(aux);
           // if(rs.next()){
             // aux=rs.getString("Nombre")+","+rs.getString("Apellido");
            if(resulIns>0){
                resM=1;
                conn.close();
            } else{
                throw new Exception();
                //low=false;
            }
        }
         catch(Exception e){
             System.out.println(e);
             resM=0;}
        
        return resM;
    }
    
    @WebMethod(operationName = "insRuta")  //falta validar que no exista duplicidad, formato de cada dato
    public int insRuta(@WebParam(name="nom")String nombre,@WebParam(name="comb")float combustible,@WebParam(name="fCob")String forCobro,@WebParam(name="col")String color,@WebParam(name="frecIda")int frecuenciaIda,@WebParam(name="frecVuel")int frecuenciaVuelta,@WebParam(name="tiemRec")int tiempoRecorrido)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();

                resulIns=stmt.executeUpdate("insert into ruta(nombre,combustible,id_forma_cobro,color,frecuencia_ida,frecuencia_vuelta,tiempo_recorrido) values('"+nombre+"',"+combustible+",'"+forCobro+"','"+color+"',"+frecuenciaIda+","+frecuenciaVuelta+","+tiempoRecorrido+")");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "insRutaEstacion")  //falta validar que no exista duplicidad, formato del transbordo y cada dato
    public int insRutaEstacion(@WebParam(name = "idRu") int idRuta,@WebParam(name="idEst")int idEstacion,@WebParam(name="sigEst")int sigEstacion,@WebParam(name="trans")String transbordo)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();

                resulIns=stmt.executeUpdate("insert into ruta_estacion values('"+idRuta+"','"+idEstacion+"','"+sigEstacion+"','"+transbordo+"')");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "insEstacion")  //falta validar que no exista duplicidad, formato de los datos
    public int insEstacion(@WebParam(name = "nom") String nombre,@WebParam(name="lat")double latitud,@WebParam(name="lon")double longitud)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();

                resulIns=stmt.executeUpdate("insert into estacion (nombre,latitud,longitud) values('"+nombre+"',"+latitud+","+longitud+")");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    
    @WebMethod(operationName = "insFormaCobro")  
    public int insFormaCobro(@WebParam(name = "desc") String descripcion)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();

                resulIns=stmt.executeUpdate("insert into forma_cobro(descripcion) values('"+descripcion+"')");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "insRutaTarifa")  //Falta validar duplicidad
    public int insRutaTarifa(@WebParam(name = "idRu") int idRuta,@WebParam(name = "idTar") int idTarifa)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();

                resulIns=stmt.executeUpdate("insert into ruta_tarifa values('"+idRuta+"','"+idTarifa+"')");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }

    @WebMethod(operationName = "insRutaHorario")  //Falta validar formato de los datos
    public int insRutaHorario(@WebParam(name = "idR") int idRuta,@WebParam(name = "dia") int dia,@WebParam(name = "hInicio") String horaInicio,@WebParam(name = "hFin") String horaFin)
        {
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            
            try{
                
                if(Conecta()==0)
                    throw new Exception();
                
                java.sql.Time timeValue = new java.sql.Time(formatter.parse(horaInicio).getTime());
                java.sql.Time timeValue2 = new java.sql.Time(formatter.parse(horaFin).getTime());
         
                
                resulIns=stmt.executeUpdate("insert into ruta_horario (id_ruta,dia,hora_inicio,hora_termino) values('"+idRuta+"','"+dia+"','"+timeValue+"','"+timeValue2+"')");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        } 
    
  /*  @WebMethod(operationName = "insRutaHorario")  //Falta validar duplicidad
    public int insRutaHorario(@WebParam(name = "idRu") int idRuta,@WebParam(name = "idHor") int idHorario)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();

                resulIns=stmt.executeUpdate("insert into ruta_horario values('"+idRuta+"','"+idHorario+"')");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }*/
    
    @WebMethod(operationName = "insUnidad")  //Falta validar duplicidad, formato de datos en las fechas
    public int insUnidad(@WebParam(name = "idUni") String idUnidad,@WebParam(name = "cap") int capacidad,@WebParam(name = "idRu") int idRuta,@WebParam(name = "iniOper") String iniOperaciones,@WebParam(name = "resp") String responsable,@WebParam(name = "finOper") String finOperaciones,@WebParam(name="lat")double latitud,@WebParam(name="lon")double longitud)
        {
            try{
                if(Conecta()==0)
                    throw new Exception();
                
                resulIns=stmt.executeUpdate("insert into unidad values('"+idUnidad+"','"+capacidad+"','"+idRuta+"','"+iniOperaciones+"','"+responsable+"','"+finOperaciones+"','"+latitud+"','"+longitud+"')");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "insMantenimiento")  //Falta validar formato de datos en las fechas
    public int insMantenimiento(@WebParam(name = "idUni") String idUnidad,@WebParam(name = "fec") String fecha,@WebParam(name = "resp") String responsable,@WebParam(name = "comen") String comentarios,@WebParam(name = "cost") float costo)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();
                
                resulIns=stmt.executeUpdate("insert into mantenimiento(id_unidad,fecha,responsable,comentarios,costo) values('"+idUnidad+"','"+fecha+"','"+responsable+"','"+comentarios+"','"+costo+"')");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "insRegistro")  //Falta validar formato de datos en las fechas
    public int insRegistro(@WebParam(name = "idViaUni") int idViajeUnidad,@WebParam(name = "idEst") int idEstacion,@WebParam(name = "noPas") int noPasajeros,@WebParam(name = "noPasEsp") int noPasajerosEspeciales,@WebParam(name = "fecHor") String fechaHora)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();
                
                resulIns=stmt.executeUpdate("insert into registro(id_viaje_unidad,id_estacion,no_pasajeros,no_pasajeros_especial,fecha_hora) values('"+idViajeUnidad+"','"+idEstacion+"','"+noPasajeros+"','"+noPasajerosEspeciales+"','"+fechaHora+"')");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "insViajeUsuEst")  //Falta validar duplicidad
    public int insViajeUsuEst(@WebParam(name = "idViaUsu") int idViajeUsuario,@WebParam(name = "idEst") int idEstacion,@WebParam(name = "noEst") int noEstacion)
        {
            try{
                if(Conecta()==0)
                    throw new Exception();
                
                resulIns=stmt.executeUpdate("insert into viaje_usuario_estacion values('"+idViajeUsuario+"','"+idEstacion+"','"+noEstacion+"')");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "insViajeUnidad")  //Falta validar duplicidad
    public int insViajeUnidad(@WebParam(name = "idUni") String idUnidad,@WebParam(name = "rfcCon") String rfcConductor,@WebParam(name = "fec") String fecha,@WebParam(name = "hSal") String horaSalida,@WebParam(name = "hTer") String horaTermino,@WebParam(name = "totPas") int totalPasajeros,@WebParam(name = "dir") int direccion)
        {
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            try{
               
                if(Conecta()==0)
                    throw new Exception();
                java.sql.Time timeValue = new java.sql.Time(formatter.parse(horaSalida).getTime());
                java.sql.Time timeValue2 = new java.sql.Time(formatter.parse(horaTermino).getTime());
                
                resulIns=stmt.executeUpdate("insert into viaje_unidad(id_unidad,rfc_conductor,fecha,hora_salida,hora_termino,total_pasajeros,direccion) values('"+idUnidad+"','"+rfcConductor+"','"+fecha+"','"+timeValue+"','"+timeValue2+"',"+totalPasajeros+","+direccion+")");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "insConductor")  //Falta validar duplicidad
    public int insConductor(@WebParam(name = "rfcCon") String rfcConductor,@WebParam(name = "nom") String nombre,@WebParam(name = "suel") float sueldo,@WebParam(name = "noLic") String noLicencia,@WebParam(name = "tel") String telefono)
        {
     
            try{
               
                if(Conecta()==0)
                    throw new Exception();
             
                
                resulIns=stmt.executeUpdate("insert conductor values('"+rfcConductor+"','"+nombre+"','"+sueldo+"','"+noLicencia+"','"+telefono+"')");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
   /* @WebMethod(operationName = "insViajeUsuario")  //Falta validar duplicidad,formato de fecha hora
    public int insViajeUsuario(@WebParam(name = "fecHor") String fechaHora,@WebParam(name = "corrUsu") String correoUsuario)
        {
     
            try{
               
                if(Conecta()==0)
                    throw new Exception();
             
                
                resulIns=stmt.executeUpdate("insert viaje_usuario(fecha_hora,correo_usuario) values('"+fechaHora+"','"+correoUsuario+"')");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }*/
    
    @WebMethod(operationName = "insUsuario",action="http://metodos/insUsuario")  //Falta validar duplicidad
    public int insUsuario(@WebParam(name = "corrUsu") String correoUsuario,@WebParam(name = "nom") String nombre,@WebParam(name = "pass") String password,@WebParam(name = "tip") int tipo, @WebParam(name = "noTar") String noTarjeta)
        {
     
            try{
               
                if(Conecta()==0)
                    throw new Exception();
             
                
                resulIns=stmt.executeUpdate("insert usuario values('"+correoUsuario+"','"+nombre+"','"+password+"','"+tipo+"','"+noTarjeta+"')");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "insComentario")  //Falta validar fecha que es datetime
    public int insComentario(@WebParam(name = "corrUsu") String correoUsuario,@WebParam(name = "com") String comentario,@WebParam(name = "fec") String fecha)
        {
     
            try{
               
                if(Conecta()==0)
                    throw new Exception();
             
                
                resulIns=stmt.executeUpdate("insert comentario(correo_usuario,comentario,fecha) values('"+correoUsuario+"','"+comentario+"',"+fecha+")");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "insAviso")  //Falta validar fecha
    public int insAviso(@WebParam(name = "avi") String aviso,@WebParam(name = "fecPubli") String fechaPublicacion,@WebParam(name = "fecTer") String fechaTermino)
        {
     
            try{
               
                if(Conecta()==0)
                    throw new Exception();
             
                
                resulIns=stmt.executeUpdate("insert aviso(aviso,fecha_publicacion,fecha_termino) values('"+aviso+"','"+fechaPublicacion+"','"+fechaTermino+"')");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    
    //ACABAN INSERTS
    
    //EMPIEZAN CONSULTAS
   
    //ACABAN CONSULTAS
    
    //INICIAN ALTERS
    
    @WebMethod(operationName = "altTarifa") //falta validar que no exista duplicidad, formato de cada dato
    public int altTarifa(@WebParam(name = "idTar") int idTarifa,@WebParam(name = "cost") float costo) {
        //String aux="";
        
        try{
            //
          //  Class.forName("com.mysql.jdbc.Driver");
           // conn=DriverManager.getConnection(url,usr,pass);
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tt?user=root&password=b3nj4m1n");
            
            if(Conecta()==0)
                    throw new Exception();
           // aux = ;
            resulIns=stmt.executeUpdate("update tarifa set costo='"+costo+"' where id_tarifa='"+idTarifa+"'");
            //stmt.executeUpdate(aux);
           // if(rs.next()){
             // aux=rs.getString("Nombre")+","+rs.getString("Apellido");
            if(resulIns>0){
                resM=1;
                conn.close();
            } else{
                throw new Exception();
                //low=false;
            }
        }
         catch(Exception e){
             System.out.println(e);
             resM=0;}
        
        return resM;
    }
    
    @WebMethod(operationName = "altRuta")  //falta validar que no exista duplicidad, formato de cada dato
    public int altRuta(@WebParam(name = "idRu") int idRuta,@WebParam(name="nom")String nombre,@WebParam(name="comb")float combustible,@WebParam(name="fCob")int forCobro,@WebParam(name="col")String color,@WebParam(name="frecIda")int frecuenciaIda,@WebParam(name="frecVuel")int frecuenciaVuelta,@WebParam(name="tiemRec")int tiempoRecorrido)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();

                resulIns=stmt.executeUpdate("update ruta set nombre='"+nombre+"',combustible='"+combustible+"',id_forma_cobro='"+forCobro+"',color='"+color+"',frecuencia_ida='"+frecuenciaIda+"',frecuencia_vuelta='"+frecuenciaVuelta+"',tiempo_recorrido='"+tiempoRecorrido+"' where id_ruta='"+idRuta+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "altRutaEstacion")  //falta validar que no exista duplicidad, formato del transbordo y cada dato
    public int altRutaEstacion(@WebParam(name = "idRu") int idRuta,@WebParam(name="idEst")int idEstacion,@WebParam(name="sigEst")int sigEstacion,@WebParam(name="trans")String transbordo)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();

                resulIns=stmt.executeUpdate("update ruta_estacion set id_estacion='"+idEstacion+"',sig_estacion='"+sigEstacion+"',transbordo='"+transbordo+"' where id_ruta='"+idRuta+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "altEstacion")  //falta validar que no exista duplicidad, formato de los datos
    public int altEstacion(@WebParam(name = "idEst") int idEstacion,@WebParam(name = "nom") String nombre,@WebParam(name="lat")double latitud,@WebParam(name="lon")double longitud)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();

                resulIns=stmt.executeUpdate("update estacion set nombre='"+nombre+"',latitud='"+latitud+"',longitud='"+longitud+"' where id_estacion='"+idEstacion+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    
    @WebMethod(operationName = "altFormaCobro")  
    public int altFormaCobro(@WebParam(name = "idFoCob") int idFormCobro,@WebParam(name = "desc") String descripcion)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();

                resulIns=stmt.executeUpdate("update forma_cobro set descripcion='"+descripcion+"' where id_forma_cobro='"+idFormCobro+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "altRutaTarifa")  //Falta validar duplicidad
    public int altRutaTarifa(@WebParam(name = "idRu") int idRuta,@WebParam(name = "idTar") int idTarifa)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();

                 resulIns=stmt.executeUpdate("update ruta_tarifa set id_tarifa='"+idTarifa+"' where id_ruta='"+idRuta+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }

    @WebMethod(operationName = "altRutaHorario")  //Falta validar formato de los datos
    public int altRutaHorario(@WebParam(name = "idRut") int idRuta,@WebParam(name = "dia") String dia,@WebParam(name = "hInicio") String horaInicio,@WebParam(name = "hFin") String horaFin)
        {
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            
            try{
                
                if(Conecta()==0)
                    throw new Exception();
                
                java.sql.Time timeValue = new java.sql.Time(formatter.parse(horaInicio).getTime());
                java.sql.Time timeValue2 = new java.sql.Time(formatter.parse(horaFin).getTime());
                
                
                resulIns=stmt.executeUpdate("update ruta_horario set hora_inicio='"+timeValue+"',hora_termino='"+timeValue2+"' where id_ruta='"+idRuta+"' && dia='"+dia+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        } 
    /*
    @WebMethod(operationName = "altRutaHorario")  //Falta validar duplicidad
    public int altRutaHorario(@WebParam(name = "idRu") int idRuta,@WebParam(name = "idHor") int idHorario)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();

                resulIns=stmt.executeUpdate("update ruta_horario set id_horario='"+idHorario+"' where id_ruta='"+idRuta+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }*/
    
    @WebMethod(operationName = "altUnidad")  //Falta validar duplicidad, formato de datos en las fechas
    public int altUnidad(@WebParam(name = "idUni") String idUnidad,@WebParam(name = "cap") int capacidad,@WebParam(name = "idRu") int idRuta,@WebParam(name = "iniOper") String iniOperaciones,@WebParam(name = "resp") String responsable,@WebParam(name = "finOper") String finOperaciones,@WebParam(name="lat")double latitud,@WebParam(name="lon")double longitud)
        {
            try{
                if(Conecta()==0)
                    throw new Exception();
                
                resulIns=stmt.executeUpdate("update unidad set capacidad='"+capacidad+"',id_ruta='"+idRuta+"',inicio_operaciones='"+iniOperaciones+"',responsable='"+responsable+"',fin_operaciones='"+finOperaciones+"',latitud='"+latitud+"',longitud='"+longitud+"' where id_unidad='"+idUnidad+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "altMantenimiento")  //Falta validar formato de datos en las fechas
    public int altMantenimiento(@WebParam(name = "idMan") int idMantenimiento,@WebParam(name = "idUni") String idUnidad,@WebParam(name = "fec") String fecha,@WebParam(name = "resp") String responsable,@WebParam(name = "comen") String comentarios,@WebParam(name = "cost") float costo)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();
                
                resulIns=stmt.executeUpdate("update mantenimiento set id_unidad='"+idUnidad+"',fecha='"+fecha+"',responsable='"+responsable+"',comentarios='"+comentarios+"',costo='"+costo+"' where id_mantenimiento='"+idMantenimiento+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "altRegistro")  //Falta validar formato de datos en las fechas
    public int altRegistro(@WebParam(name = "idRegis") int idRegistro,@WebParam(name = "idViaUni") int idViajeUnidad,@WebParam(name = "idEst") int idEstacion,@WebParam(name = "noPas") int noPasajeros,@WebParam(name = "noPasEsp") int noPasajerosEspeciales,@WebParam(name = "fecHor") String fechaHora)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();
                
                resulIns=stmt.executeUpdate("update registro set id_viaje_unidad='"+idViajeUnidad+"',id_estacion='"+idEstacion+"',no_pasajeros='"+noPasajeros+"',no_pasajeros_especial='"+noPasajerosEspeciales+"',fecha_hora='"+fechaHora+"' where id_registro='"+idRegistro+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    /*
    @WebMethod(operationName = "altViajeUsuEst")  //Falta validar duplicidad
    public int altViajeUsuEst(@WebParam(name = "idViaUsu") int idViajeUsuario,@WebParam(name = "idEst") int idEstacion,@WebParam(name = "noEst") int noEstacion)
        {
            try{
                if(Conecta()==0)
                    throw new Exception();
                
                resulIns=stmt.executeUpdate("update viaje_usuario_estacion set id_estacion='"+idEstacion+"',no_estacion='"+noEstacion+"' where id_viaje_usuario='"+idViajeUsuario+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }*/
    
    @WebMethod(operationName = "altViajeUnidad")  //Falta validar duplicidad
    public int altViajeUnidad(@WebParam(name = "idViUni") int idViajeUnidad,@WebParam(name = "idUni") String idUnidad,@WebParam(name = "rfcCon") String rfcConductor,@WebParam(name = "fec") String fecha,@WebParam(name = "hSal") String horaSalida,@WebParam(name = "hTer") String horaTermino,@WebParam(name = "totPas") int totalPasajero,@WebParam(name = "dir") int direccion)
        {
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            try{
               
                if(Conecta()==0)
                    throw new Exception();
                java.sql.Time timeValue = new java.sql.Time(formatter.parse(horaSalida).getTime());
                java.sql.Time timeValue2 = new java.sql.Time(formatter.parse(horaTermino).getTime());
                
                resulIns=stmt.executeUpdate("update viaje_unidad set id_unidad='"+idUnidad+"',rfc_conductor='"+rfcConductor+"',fecha='"+fecha+"',hora_salida='"+timeValue+"',hora_termino='"+timeValue2+"',total_pasajeros="+totalPasajero+",direccion="+direccion+" where id_viaje_unidad='"+idViajeUnidad+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "altConductor")  //Falta validar duplicidad
    public int altConductor(@WebParam(name = "rfcCon") String rfcConductor,@WebParam(name = "nom") String nombre,@WebParam(name = "suel") float sueldo,@WebParam(name = "noLic") String noLicencia,@WebParam(name = "tel") String telefono)
        {
     
            try{
               
                if(Conecta()==0)
                    throw new Exception();
             
                
                resulIns=stmt.executeUpdate("update conductor set nombre='"+nombre+"',sueldo='"+sueldo+"',no_licencia='"+noLicencia+"',telefono='"+telefono+"' where rfc_conductor='"+rfcConductor+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
   /* @WebMethod(operationName = "altViajeUsuario")  //Falta validar duplicidad,formato de fecha hora
    public int altViajeUsuario(@WebParam(name = "idViUsu") int idViajeUsuario,@WebParam(name = "fecHor") String fechaHora,@WebParam(name = "corrUsu") String correoUsuario)
        {
     
            try{
               
                if(Conecta()==0)
                    throw new Exception();
             
                
                resulIns=stmt.executeUpdate("update viaje_usuario set fecha_hora='"+fechaHora+"',correo_usuario='"+correoUsuario+"' where id_viaje_usuario='"+idViajeUsuario+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }*/
    
    @WebMethod(operationName = "altUsuario")  //Falta validar duplicidad
    public int altUsuario(@WebParam(name = "corrUsu") String correoUsuario,@WebParam(name = "nom") String nombre,@WebParam(name = "pass") String password,@WebParam(name = "tip") int tipo, @WebParam(name = "noTar") String noTarjeta)
        {
     
            try{
               
                if(Conecta()==0)
                    throw new Exception();
             
                
                resulIns=stmt.executeUpdate("update usuario set nombre='"+nombre+"',password='"+password+"',tipo='"+tipo+"',no_tarjeta='"+noTarjeta+"' where correo_usuario='"+correoUsuario+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "altComentario")  //Falta validar fecha que es datetime
    public int altComentario(@WebParam(name = "idCom") int idComentario,@WebParam(name = "corrUsu") String correoUsuario,@WebParam(name = "com") String comentario,@WebParam(name = "fec") String fecha)
        {
     
            try{
               
                if(Conecta()==0)
                    throw new Exception();
             
                
                resulIns=stmt.executeUpdate("update comentario set correo_usuario='"+correoUsuario+"',comentario='"+comentario+"',fecha='"+fecha+"' where id_comentario='"+idComentario+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "altAviso")  //Falta validar fecha
    public int altAviso(@WebParam(name = "idAvi") int idAviso,@WebParam(name = "avi") String aviso,@WebParam(name = "fecPubli") String fechaPublicacion,@WebParam(name = "fecTer") String fechaTermino)
        {
     
            try{
               
                if(Conecta()==0)
                    throw new Exception();
             
                
                resulIns=stmt.executeUpdate("update aviso set aviso='"+aviso+"',fecha_publicacion='"+fechaPublicacion+"',fecha_termino='"+fechaTermino+"' where id_aviso='"+idAviso+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    
    //ACABAN ALTERS
    
    //INICIAN BAJAS
    
        
    @WebMethod(operationName = "delTarifa") //falta validar que no exista duplicidad, formato de cada dato
    public int delTarifa(@WebParam(name = "idTar") int idTarifa) {
        //String aux="";
        
        try{
            //
          //  Class.forName("com.mysql.jdbc.Driver");
           // conn=DriverManager.getConnection(url,usr,pass);
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tt?user=root&password=b3nj4m1n");
            
            if(Conecta()==0)
                    throw new Exception();
           // aux = ;
            resulIns=stmt.executeUpdate("delete from tarifa where id_tarifa='"+idTarifa+"'");
            //stmt.executeUpdate(aux);
           // if(rs.next()){
             // aux=rs.getString("Nombre")+","+rs.getString("Apellido");
            if(resulIns>0){
                resM=1;
                conn.close();
            } else{
                throw new Exception();
                //low=false;
            }
        }
         catch(Exception e){
             System.out.println(e);
             resM=0;}
        
        return resM;
    }
    
    @WebMethod(operationName = "delRuta")  //falta validar que no exista duplicidad, formato de cada dato
    public int delRuta(@WebParam(name = "idRu") int idRuta)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();

                resulIns=stmt.executeUpdate("delete from ruta where id_ruta='"+idRuta+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "delRutaEstacion")  //falta validar que no exista duplicidad, formato del transbordo y cada dato
    public int delRutaEstacion(@WebParam(name = "idRu") int idRuta,@WebParam(name="idEst")int idEstacion)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();

                resulIns=stmt.executeUpdate("delete from ruta_estacion where id_estacion='"+idEstacion+"' and id_ruta='"+idRuta+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "delEstacion")  //falta validar que no exista duplicidad, formato de los datos
    public int delEstacion(@WebParam(name = "idEst") int idEstacion)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();

                resulIns=stmt.executeUpdate("delete from estacion where id_estacion='"+idEstacion+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    
    @WebMethod(operationName = "delFormaCobro")  
    public int delFormaCobro(@WebParam(name = "idFoCob") int idFormCobro)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();

                resulIns=stmt.executeUpdate("delete from forma_cobro where id_forma_cobro='"+idFormCobro+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "delRutaTarifa")  //Falta validar duplicidad
    public int delRutaTarifa(@WebParam(name = "idRu") int idRuta,@WebParam(name = "idTar") int idTarifa)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();

                 resulIns=stmt.executeUpdate("delete from ruta_tarifa where  id_ruta='"+idRuta+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }

    @WebMethod(operationName = "delHorario")  //Falta validar formato de los datos
    public int delHorario(@WebParam(name = "idHor") int idHorario)
        {
            
            try{
                
                if(Conecta()==0)
                    throw new Exception();
                

                
                resulIns=stmt.executeUpdate("delete from horario  where id_horario='"+idHorario+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        } 
    
    @WebMethod(operationName = "delRutaHorario")  //Falta validar duplicidad
    public int delRutaHorario(@WebParam(name = "idRu") int idRuta)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();

                resulIns=stmt.executeUpdate("delete from ruta_horario  where id_ruta='"+idRuta+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "delUnidad")  //Falta validar duplicidad, formato de datos en las fechas
    public int delUnidad(@WebParam(name = "idUni") String idUnidad)
        {
            try{
                if(Conecta()==0)
                    throw new Exception();
                
                resulIns=stmt.executeUpdate("delete from unidad  where id_unidad like '"+idUnidad+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "delMantenimiento")  //Falta validar formato de datos en las fechas
    public int delMantenimiento(@WebParam(name = "idMan") int idMantenimiento)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();
                
                resulIns=stmt.executeUpdate("delete from mantenimiento  where id_mantenimiento='"+idMantenimiento+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "delRegistro")  //Falta validar formato de datos en las fechas
    public int delRegistro(@WebParam(name = "idRegis") int idRegistro)
        {
            try{
                
                if(Conecta()==0)
                    throw new Exception();
                
                resulIns=stmt.executeUpdate("delete from registro where id_registro='"+idRegistro+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    /*
    @WebMethod(operationName = "delViajeUsuEst")  //Falta validar duplicidad
    public int delViajeUsuEst(@WebParam(name = "idViaUsu") int idViajeUsuario,@WebParam(name = "idEst") int idEstacion)
        {
            try{
                if(Conecta()==0)
                    throw new Exception();
                
                resulIns=stmt.executeUpdate("delete from viaje_usuario_estacion where id_estacion='"+idEstacion+"' and id_viaje_usuario='"+idViajeUsuario+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }*/
    
    @WebMethod(operationName = "delViajeUnidad")  //Falta validar duplicidad
    public int delViajeUnidad(@WebParam(name = "idViUni") int idViajeUnidad)
        {
            try{
               
                if(Conecta()==0)
                    throw new Exception();
             
                
                resulIns=stmt.executeUpdate("delete from viaje_unidad  where id_viaje_unidad='"+idViajeUnidad+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "delConductor")  //Falta validar duplicidad
    public int delConductor(@WebParam(name = "rfcCon") String rfcConductor)
        {
     
            try{
               
                if(Conecta()==0)
                    throw new Exception();
             
                
                resulIns=stmt.executeUpdate("delete from conductor  where rfc_conductor='"+rfcConductor+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "delViajeUsuario")  //Falta validar duplicidad,formato de fecha hora
    public int delViajeUsuario(@WebParam(name = "idViUsu") int idViajeUsuario)
        {
     
            try{
               
                if(Conecta()==0)
                    throw new Exception();
             
                
                resulIns=stmt.executeUpdate("delete from viaje_usuario where id_viaje_usuario='"+idViajeUsuario+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "delUsuario")  //Falta validar duplicidad
    public int delUsuario(@WebParam(name = "corrUsu") String correoUsuario)
        {
     
            try{
               
                if(Conecta()==0)
                    throw new Exception();
             
                
                resulIns=stmt.executeUpdate("delete from usuario where correo_usuario like '"+correoUsuario+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "delComentario")  //Falta validar fecha que es datetime
    public int delComentario(@WebParam(name = "idCom") int idComentario)
        {
     
            try{
               
                if(Conecta()==0)
                    throw new Exception();
             
                
                resulIns=stmt.executeUpdate("delete from comentario  where id_comentario='"+idComentario+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "delAviso")  //Falta validar fecha
    public int delAviso(@WebParam(name = "idAvi") int idAviso)
        {
     
            try{
               
                if(Conecta()==0)
                    throw new Exception();
             
                
                resulIns=stmt.executeUpdate("delete from aviso where id_aviso='"+idAviso+"'");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
   //ACABAN BAJAS
    
    //Inicia querys genericos

    @WebMethod(operationName = "queryCons",action="http://metodos/queryCons")  
    public String queryCons(@WebParam(name = "campos") String campos,@WebParam(name = "condicion") String condicion,@WebParam(name = "tabla") String tabla) 
        {
            ResultSet resC=null;
            
            String json = "";
            ArrayList<String> result = new ArrayList<String>();
            ArrayList<String> results = new ArrayList<String>();
            try{
               
                if(Conecta()==0)
                    throw new Exception();
             
                if(condicion==null||condicion.equals(""))
                    resC=stmt.executeQuery("select "+campos+" from "+tabla);
                else
                    resC=stmt.executeQuery("select "+campos+" from "+tabla+" where "+condicion);
                
                //Resulset
                
                ResultSetMetaData md = resC.getMetaData();
                int columns = md.getColumnCount();
                

                while (resC.next()) {
                    for(int i=1; i<=columns; i++){
                      result.add("\""+md.getColumnName(i)+"\":\""+resC.getObject(i)+"\"");
                    }
                    String joinAux = String.join(",", result);
                    results.add("{"+joinAux+"}");
                    result.clear();
                }
                
                
                json = new Gson().toJson(results);
                
                System.out.println(json);
                conn.close();
                }
         catch(Exception e){
             System.out.println(e);
             }

            return json;
        }
    
    @WebMethod(operationName = "queryIns")  
    public int queryIns(@WebParam(name = "values") String values,@WebParam(name = "tabla") String tabla)
        {
     
            try{
               
                if(Conecta()==0)
                    throw new Exception();
             
                
                resulIns=stmt.executeUpdate("insert into '"+tabla+"' values('"+values+"')");
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    //Fin querys genericos

    /**
     * Web service operation
     */
    @WebMethod(operationName = "queryUDI")
    public int queryUDI(@WebParam(name = "qr") String qr) {
        try{
               
                if(Conecta()==0)
                    throw new Exception();
             
                
                resulIns=stmt.executeUpdate(qr);
                if(resulIns>0){
                    resM=1;
                    conn.close();
                } else{
                    throw new Exception();
                }
            }
         catch(Exception e){
             System.out.println(e);
             resM=0;}

            return resM;
        }
    
    @WebMethod(operationName = "tiemEst")
    public ArrayList tiempEst(@WebParam(name = "est") int estacion) {
        ArrayList res = new ArrayList();
        try{
               Algoritmos a=new Algoritmos();
               res = a.tiemposEstimados(estacion);
                
            }
         catch(Exception e){
             System.out.println(e);
             return null;}

            return res;
        }
    
    @WebMethod(operationName = "calFrecuencia")
    public int calFrecuencia(@WebParam(name = "rut") int ruta,@WebParam(name = "sent") int sentido) {
        try{
               Algoritmos a=new Algoritmos();
               resM = a.calculaFrecuencia(ruta,sentido);
            }
         catch(Exception e){
             System.out.println(e);
             resM=-1;}

            return resM;
        }
    
    @WebMethod(operationName = "unidMinimas")
    public int unidMinimas(@WebParam(name = "rut") int ruta) {
        try{
               Algoritmos a=new Algoritmos();
               resM = a.obtieneUnidadesMinimas(ruta);
            }
         catch(Exception e){
             System.out.println(e);
             resM=-1;}

            return resM;
        }
    @WebMethod(operationName = "viajeMinExtra",action="http://metodos/viajeMinExtra")
    public String viajeMinExtra(@WebParam(name = "estInicial") String estacionIni,@WebParam(name = "estFinal") String estacionFin,@WebParam(name = "operacion") String oper) {
        try{
               
               
               Planificador p=new Planificador();
               Algoritmos a=new Algoritmos();
               int esIn = a.buscaId(estacionIni);
               int esFi =  a.buscaId(estacionFin);
               int finalRes=0;
               int op = Integer.parseInt(oper);
               int [] camino = p.planificadorUsuarios(op,esIn,esFi);
               ArrayList<Integer> datoExtra=new ArrayList<>();
               ArrayList<Integer> ruta=new ArrayList<>();
               
               for(int i=0;i<camino.length;i++)
                   ruta.add(camino[i]);
               
               if(op==1)
                   datoExtra=a.calculaPesos(ruta);
               else
                   datoExtra=a.calculaPesos(ruta,esIn,esFi);
                   
               for(int i=0;i<datoExtra.size();i++)
               {
                   finalRes=finalRes+datoExtra.get(i);
               }
               
               return String.valueOf(finalRes);
                    
            }
         catch(Exception e){
             System.out.println(e);
             resM=-1;}

            return null;
        }
    
    @WebMethod(operationName = "viajeMin",action="http://metodos/viajeMin")
    public String viajeMin(@WebParam(name = "estInicial") String estacionIni,@WebParam(name = "estFinal") String estacionFin,@WebParam(name = "operacion") String oper) {
        try{
               Planificador p=new Planificador();
               Algoritmos a = new Algoritmos();
               int esIn = a.buscaId(estacionIni);
               int esFi =  a.buscaId(estacionFin);
               System.out.println("oper "+oper);
               System.out.println("es "+esIn);
               System.out.println("es2 "+esFi);
               int op = Integer.parseInt(oper);
               
               int [] camino = p.planificadorUsuarios(op,esIn,esFi);
               
               ArrayList<Integer> ruta=new ArrayList<>();
               
               for(int i=0;i<camino.length-2;i++)
                   ruta.add(camino[i]);
               
               String rutaS="";
               
               for(int i=0;i<camino.length-2;i++)
               {
                   rutaS=rutaS+a.nombrEst(camino[i])+"&";
               }
               
               if(rutaS!=null)
               {
                   rutaS = rutaS.substring(0, rutaS.length()-1);
                   rutaS = rutaS+"#"+camino[camino.length-2]+"&"+camino[camino.length-1];
               }
               
               System.out.println("RUTAS: "+rutaS);
               conn.close();
               return rutaS;
                    
            }
         catch(Exception e){
             System.out.println(e);
             resM=-1;}

            return null;
        }
    
    @WebMethod(operationName = "tiemposEst",action="http://metodos/tiemposEst")
    public String tiemposEst(@WebParam(name = "estInicial") String estacion) {
        try{
             
               Algoritmos a = new Algoritmos();
               int est = a.idEst(estacion);
               String finalCal="none"; 
               
               ArrayList tiempos=new ArrayList<>();
               
               System.out.println("IDEST "+est);
               try
               {
               tiempos = a.tiemposEstimados(est);
               finalCal="";
               
                        for(int i=0;i<tiempos.size();i++)
                            finalCal = finalCal+tiempos.get(i)+"#";

                        if(!finalCal.equals(""))
                            finalCal=finalCal.substring(0,finalCal.length()-1);

                        System.out.println("Unidades: "+finalCal);
                        conn.close();
                }catch(Exception e)
                {
                    return "none";
                }
               return finalCal;
                    
            }
         catch(Exception e){
             System.out.println(e);
             resM=-1;}

            return "none";
        }
    
}
