package algoritmos;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Algoritmos {
    private static Conexion bd = new Conexion("tt", "root", "b3nj4m1n");
    private static ResultSet rs;
    
    public static void reduceGrafoATransbordos() throws SQLException{
        if(bd.conecta()){
            //Obtenemos el número de rutas
            rs = bd.consulta("SELECT id_ruta FROM ruta WHERE id_ruta > 4;");
            int numR = 0, i = 0, j = 0;
            ArrayList<Integer> ids = new ArrayList<>();
            while(rs.next()){
                ids.add(rs.getInt("id_ruta"));
                numR++;
            }
            //Definimos la matriz del grafo de tamaño n + 1 donde n es el número de rutas
            int[][] grafoR = new int[numR + 1][numR + 1];
            //Colocamos los id_ruta en la primer fila y la primer columna
            grafoR[0][0] = 0;
            for(i = 1; i < ids.size() + 1; i++){
                grafoR[0][i] = ids.get(i - 1);
                grafoR[i][0] = ids.get(i - 1);
            }
            //Llenamos la matriz con 1 en donde haya transbordos entre rutas
            for(i = 1; i <= ids.size(); i++){
                //De cada ruta obtenemos sus estaciones
                rs = bd.consulta("SELECT id_estacion FROM ruta_estacion WHERE id_ruta = " + ids.get(i - 1));
                ArrayList<Integer> estaciones = new ArrayList<>();
                while(rs.next()){
                    estaciones.add(rs.getInt("id_estacion"));
                }
                //Por cada estación vemos si pertenece a otra ruta
                for(int estacion : estaciones){
                    rs = bd.consulta("SELECT id_ruta FROM ruta_estacion WHERE id_estacion = " + estacion);
                    while(rs.next()){
                        if(rs.getInt("id_ruta") != ids.get(i - 1))
                            grafoR[i][ids.indexOf(rs.getInt("id_ruta")) + 1] = 1;
                    }
                }
            }
            //Finalmente guardamos la matriz en el archivo grafo_transbordos.info
            try {
                File archivo = new File("grafo_transbordos.info");
                BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
                for(i = 0; i < ids.size() + 1; i++){
                    for(j = 0; j < ids.size() + 1; j++){
                        bw.write(grafoR[i][j] + " ");
                    }
                    bw.write("\n");
                }
                bw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            bd.desconecta();
            System.out.println("grafo_transbordos.info actualizado");
        }else{
            System.out.println("Error en la conexión a la base de datos");
        }
    }
    
    public static void generaViajesUnidad(int ruta) throws Exception{
        if(bd.conecta()){
            //Consultamos las unidades que nunca han realizado un viaje o que tienen todos sus viajes terminados
            rs = bd.consulta("select id_unidad from unidad where (id_unidad not in (select id_unidad from viaje_unidad) or id_unidad in (select id_unidad from viaje_unidad where hora_termino is not null) ) and id_ruta = " + ruta);
            ArrayList<String> unidades = new ArrayList<>();
            while(rs.next()){
                unidades.add(rs.getString("id_unidad"));
            }
            if(unidades.size() > 0){
                int ida_vuelta  = new Random(System.currentTimeMillis()).nextInt(2);
                //De esa lista de unidades, elegimos una al azar
                String idUnidad = unidades.get(new Random(System.currentTimeMillis()).nextInt(unidades.size()));
                System.out.println("id_unidad: " + idUnidad);
                //Obtenemos el horario de un dia aleatorio de la semana
                int diaR = new Random(System.currentTimeMillis()).nextInt(6);
                rs = bd.consulta("select hora_inicio, hora_termino from ruta_horario where id_ruta = " + ruta + " and dia = " + diaR);
                DateFormat f = new SimpleDateFormat("hh:mm:ss");
                Date hora_inicio = null;
                Date hora_termino = null;
                int frecuencia_ida = 0, frecuencia_vuelta = 0;
                int tiempoViaje = 0;
                rs.next();
                    hora_inicio = f.parse(rs.getString("hora_inicio"));
                    hora_termino = f.parse(rs.getString("hora_termino"));
                
                long minutosServicio = (hora_termino.getTime() - hora_inicio.getTime()) / 1000 / 60;
                //Obtenemos la frecuencia y el tiempo estimado de recorrido de la ruta
                rs = bd.consulta("select frecuencia_ida, frecuencia_vuelta, tiempo_recorrido from ruta where id_ruta = " + ruta);
                rs.next();
                    frecuencia_ida = rs.getInt("frecuencia_ida");
                    frecuencia_vuelta = rs.getInt("frecuencia_vuelta");
                    tiempoViaje = rs.getInt("tiempo_recorrido");
                //Obtenemos el número de estaciones que componen la ruta
                rs = bd.consulta("select count(*) as num from ruta_estacion where id_ruta = " + ruta);
                int numEstaciones = 0;
                rs.next();
                numEstaciones = rs.getInt("num");
                
                //Obtenemos el número de viajes aproximados al día y el tiempo que tarda de ir de una estación a otra para futuros cálculos
                int numViajes_ida = (int) minutosServicio / frecuencia_ida;
                int numViajes_vuelta = (int) minutosServicio / frecuencia_vuelta;
                int tiempoPEstacion = (int) tiempoViaje / numEstaciones;
                
                //Generamos una fecha para el viaje y una hora de inicio aleatoria
                Date hoy=new Date();
                int numeroDia=0;
                Calendar cal= Calendar.getInstance();
                cal.setTime(hoy);
                numeroDia=cal.get(Calendar.DAY_OF_WEEK) - 1;
                int sumaDias = (diaR - numeroDia < 0) ? diaR - numeroDia + 7 : diaR - numeroDia;
                cal.add(Calendar.DAY_OF_YEAR, sumaDias);
                SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
                String fecha = d.format(cal.getTime());
                cal.setTime(hora_inicio);
                int sumaMinutos_ida = frecuencia_ida * new Random(System.currentTimeMillis()).nextInt(numViajes_ida + 1);
                int sumaMinutos_vuelta = frecuencia_vuelta * new Random(System.currentTimeMillis()).nextInt(numViajes_vuelta + 1);
                if(ida_vuelta == 1)
                    cal.add(Calendar.MINUTE, sumaMinutos_ida);
                else
                    cal.add(Calendar.MINUTE, sumaMinutos_vuelta);
                String hora_inicioS = f.format(cal.getTime());
                System.out.println("fecha: " + fecha);
                System.out.println("hora_salida: " + hora_inicioS);
                
                //Comenzamos los registros de viaje
                ArrayList<Integer> estacionesR = new ArrayList<>();
                rs = bd.consulta("select id_estacion from ruta_estacion where id_ruta = " + ruta);
                while(rs.next()){
                    estacionesR.add(rs.getInt("id_estacion"));
                }
                int id_viaje_unidad = 0;
                if(ida_vuelta == 1){
                    //Insertamos el registro a viaje_unidad
                    bd.actualizacion("insert into viaje_unidad (id_unidad, fecha, hora_salida, direccion) values('" + idUnidad + "', '" + fecha + "', '" + hora_inicioS + "', 1);");
                    //Obtenemos el último id registrado
                    
                    rs = bd.consulta("select MAX(id_viaje_unidad) as id from viaje_unidad;");
                    rs.next();
                    id_viaje_unidad = rs.getInt("id");
                    System.out.println("id_viaje_unidad: " + id_viaje_unidad);
                    int id_estacion = estacionesR.get(0);
                    //Obtenemos la capacidad de la unidad
                    rs = bd.consulta("select capacidad from unidad where id_unidad = '" + idUnidad + "'");
                    rs.next();
                    int capacidad = rs.getInt("capacidad");
                    System.out.println("capacidad: " + capacidad);
                    int no_pasajeros = new Random(System.currentTimeMillis()).nextInt(capacidad + 1);
                    int no_pasajeros_especial = new Random(System.currentTimeMillis()).nextInt(no_pasajeros + 1);
                    String fecha_hora = fecha + " " + hora_inicioS;
                    bd.actualizacion("insert into registro (id_viaje_unidad, id_estacion, no_pasajeros, no_pasajeros_especial, fecha_hora) values(" + id_viaje_unidad + ", " + id_estacion + ", " + no_pasajeros + ", " + no_pasajeros_especial + ", '" + fecha_hora + "');");
                    bd.actualizacion("update viaje_unidad set total_pasajeros = " + no_pasajeros + " where id_viaje_unidad = " + id_viaje_unidad);
                    for(int i = 1; i < estacionesR.size(); i++){
                        id_estacion = estacionesR.get(i);
                        fecha_hora = generaRegistro(id_viaje_unidad, id_estacion, capacidad, tiempoPEstacion);
                        if(i == estacionesR.size() - 1)
                           bd.actualizacion("update viaje_unidad set hora_termino = '" + fecha_hora + "' where id_viaje_unidad =" + id_viaje_unidad);
                    }
                    //Insertamos el registro a viaje_unidad
                    bd.actualizacion("insert into viaje_unidad (id_unidad, fecha, hora_salida, direccion) values('" + idUnidad + "', '" + fecha + "', '" + fecha_hora.split(" ")[1] + "', 0);");
                    //Obtenemos el último id registrado
                    rs = bd.consulta("select MAX(id_viaje_unidad) as id from viaje_unidad;");
                    rs.next();
                    id_viaje_unidad = rs.getInt("id");
                    System.out.println("id_viaje_unidad: " + id_viaje_unidad);
                    id_estacion = estacionesR.get(estacionesR.size() - 1);
                    System.out.println("capacidad: " + capacidad);
                    no_pasajeros = new Random(System.currentTimeMillis()).nextInt(capacidad + 1);
                    no_pasajeros_especial = new Random(System.currentTimeMillis()).nextInt(no_pasajeros + 1);
                    bd.actualizacion("insert into registro (id_viaje_unidad, id_estacion, no_pasajeros, no_pasajeros_especial, fecha_hora) values(" + id_viaje_unidad + ", " + id_estacion + ", " + no_pasajeros + ", " + no_pasajeros_especial + ", '" + fecha_hora + "');");
                    bd.actualizacion("update viaje_unidad set total_pasajeros = " + no_pasajeros + " where id_viaje_unidad = " + id_viaje_unidad);
                    for(int i = estacionesR.size() - 1; i >= 0; i--){
                        id_estacion = estacionesR.get(i);
                        fecha_hora = generaRegistro(id_viaje_unidad, id_estacion, capacidad, tiempoPEstacion);
                        if(i == 0)
                           bd.actualizacion("update viaje_unidad set hora_termino = '" + fecha_hora + "' where id_viaje_unidad =" + id_viaje_unidad);
                    }
                }else{
                    //Insertamos el registro a viaje_unidad
                    bd.actualizacion("insert into viaje_unidad (id_unidad, fecha, hora_salida, direccion) values('" + idUnidad + "', '" + fecha + "', '" + hora_inicioS + "', 0);");
                    //Obtenemos el último id registrado
                    rs = bd.consulta("select MAX(id_viaje_unidad) as id from viaje_unidad;");
                    rs.next();
                    id_viaje_unidad = rs.getInt("id");
                    System.out.println("id_viaje_unidad: " + id_viaje_unidad);
                    int id_estacion = estacionesR.get(estacionesR.size() - 1);
                    //Obtenemos la capacidad de la unidad
                    rs = bd.consulta("select capacidad from unidad where id_unidad = '" + idUnidad + "'");
                    rs.next();
                    int capacidad = rs.getInt("capacidad");
                    System.out.println("capacidad: " + capacidad);
                    int no_pasajeros = new Random(System.currentTimeMillis()).nextInt(capacidad + 1);
                    int no_pasajeros_especial = new Random(System.currentTimeMillis()).nextInt(no_pasajeros + 1);
                    String fecha_hora = fecha + " " + hora_inicioS;
                    bd.actualizacion("insert into registro (id_viaje_unidad, id_estacion, no_pasajeros, no_pasajeros_especial, fecha_hora) values(" + id_viaje_unidad + ", " + id_estacion + ", " + no_pasajeros + ", " + no_pasajeros_especial + ", '" + fecha_hora + "');");
                    bd.actualizacion("update viaje_unidad set total_pasajeros = " + no_pasajeros + " where id_viaje_unidad = " + id_viaje_unidad);
                    for(int i = estacionesR.size() - 1; i >= 0; i--){
                        id_estacion = estacionesR.get(i);
                        fecha_hora = generaRegistro(id_viaje_unidad, id_estacion, capacidad, tiempoPEstacion);
                        if(i == 0)
                           bd.actualizacion("update viaje_unidad set hora_termino = '" + fecha_hora + "' where id_viaje_unidad =" + id_viaje_unidad);
                    }
                    //Insertamos el registro a viaje_unidad
                    bd.actualizacion("insert into viaje_unidad (id_unidad, fecha, hora_salida, direccion) values('" + idUnidad + "', '" + fecha + "', '" + fecha_hora.split(" ")[1] + "', 1);");
                    //Obtenemos el último id registrado
                    
                    rs = bd.consulta("select MAX(id_viaje_unidad) as id from viaje_unidad;");
                    rs.next();
                    id_viaje_unidad = rs.getInt("id");
                    System.out.println("id_viaje_unidad: " + id_viaje_unidad);
                    id_estacion = estacionesR.get(0);
                    System.out.println("capacidad: " + capacidad);
                    no_pasajeros = new Random(System.currentTimeMillis()).nextInt(capacidad + 1);
                    no_pasajeros_especial = new Random(System.currentTimeMillis()).nextInt(no_pasajeros + 1);
                    bd.actualizacion("insert into registro (id_viaje_unidad, id_estacion, no_pasajeros, no_pasajeros_especial, fecha_hora) values(" + id_viaje_unidad + ", " + id_estacion + ", " + no_pasajeros + ", " + no_pasajeros_especial + ", '" + fecha_hora + "');");
                    bd.actualizacion("update viaje_unidad set total_pasajeros = " + no_pasajeros + " where id_viaje_unidad = " + id_viaje_unidad);
                    for(int i = 1; i < estacionesR.size(); i++){
                        id_estacion = estacionesR.get(i);
                        fecha_hora = generaRegistro(id_viaje_unidad, id_estacion, capacidad, tiempoPEstacion);
                        if(i == estacionesR.size() - 1)
                           bd.actualizacion("update viaje_unidad set hora_termino = '" + fecha_hora + "' where id_viaje_unidad =" + id_viaje_unidad);
                    }
                }
                bd.desconecta();
                System.out.println("Viaje simulado correctamente");
            }else{
                System.out.println("Esta ruta no tiene unidades disponibles para realizar viajes");
            }
        }else{
            System.out.println("Error en la conexión a la base de datos");
        }
    }
    
    public static void main(String[] args) {
        try {
            //reduceGrafoATransbordos();
            generaViajesUnidad(6);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String generaRegistro(int id_viaje_unidad, int id_estacion, int capacidad, int tiempoPEstacion) throws Exception {
        rs = bd.consulta("select total_pasajeros from viaje_unidad where id_viaje_unidad = " + id_viaje_unidad);
                    rs.next();
                    int total_pasajeros = rs.getInt("total_pasajeros");
                    rs = bd.consulta("select * from registro where id_viaje_unidad = " + id_viaje_unidad + " ORDER BY id_registro DESC");
                    rs.next();
                    int suben = new Random(System.currentTimeMillis()).nextInt(capacidad - rs.getInt("no_pasajeros") + 1);
                    int no_pasajeros_especial = rs.getInt("no_pasajeros_especial") + ((suben > 0) ? new Random(System.currentTimeMillis()).nextInt(suben) : 0);
                    int bajan = new Random(System.currentTimeMillis()).nextInt(rs.getInt("no_pasajeros") + 1);
                    int no_pasajeros = rs.getInt("no_pasajeros") + suben - bajan;
                    total_pasajeros += suben;
                    DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date hora_fecha = f.parse(rs.getString("fecha_hora"));
                    Calendar cal= Calendar.getInstance();
                    cal.setTime(hora_fecha);
                    int sumaMinutos = 0;
                    int operacion = new Random(System.currentTimeMillis()).nextInt(2);
                    if(operacion == 1)
                        sumaMinutos = tiempoPEstacion + new Random(System.currentTimeMillis()).nextInt((2 * tiempoPEstacion) + 1);
                    else
                        sumaMinutos = tiempoPEstacion - new Random(System.currentTimeMillis()).nextInt(tiempoPEstacion);
                    cal.add(Calendar.MINUTE, sumaMinutos);
                    DateFormat d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
                    String fecha_hora = d.format(cal.getTime());
                    bd.actualizacion("insert into registro (id_viaje_unidad, id_estacion, no_pasajeros, no_pasajeros_especial, fecha_hora) values(" + id_viaje_unidad + ", " + id_estacion + ", " + no_pasajeros + ", " + no_pasajeros_especial + ", '" + fecha_hora + "');");
                    bd.actualizacion("update viaje_unidad set total_pasajeros = " + total_pasajeros + " where id_viaje_unidad = " + id_viaje_unidad);
                    
                    return fecha_hora;
    }
}
