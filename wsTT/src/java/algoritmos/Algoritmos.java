package algoritmos;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.Stack;

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
    
    public static void generaRutasTransbordos(int estacionInicial, int estacionFinal) throws SQLException{
        if(bd.conecta()){
            String FILENAME = "grafo_transbordos.info";
            //String FILENAME = "D:\\Luis R\\Documents\\TT1\\algoritmos\\matriz.txt";
            BufferedReader br = null;
            FileReader fr = null;
            int rutaInicial=0, rutaFinal=0;
            //obtenemos el id_ruta de la estacion de origen
            rs = bd.consulta("SELECT id_ruta FROM ruta_estacion WHERE id_estacion= "+estacionInicial);
            while(rs.next()){
                rutaInicial=rs.getInt("id_ruta");
            }
            
            //obtenemos el id_ruta de la estacion de final
            rs = bd.consulta("SELECT id_ruta FROM ruta_estacion WHERE id_estacion= "+estacionFinal);
            while(rs.next()){
                rutaFinal=rs.getInt("id_ruta");
            }
            //rutaInicial=2;
            //rutaFinal=6;
            System.out.println("Inicial: "+rutaInicial);
            System.out.println("Final: "+rutaFinal);

            try {
                    fr = new FileReader(FILENAME);
                    br = new BufferedReader(fr);

                    String sCurrentLine;
                    int contador = 0;
                    ArrayList<String> filas = new ArrayList<>();

                    while ((sCurrentLine = br.readLine()) != null) {
                            contador++;
                            filas.add(sCurrentLine);
                    }
                    
                    int[][] grafo = new int[contador][contador];
                    int[][] matrizResultado = new int[contador][contador];

                       
                    for(int i=0;i<filas.size();i++)
                            {
                                String [] auxFila=filas.get(i).split(" ");
                                
                                for(int j=0;j<auxFila.length;j++)
                                {
                                    if(i==0 || j==0)
                                        matrizResultado[i][j] = Integer.parseInt(auxFila[j]);
                                    else
                                        matrizResultado[i][j] = 0;
                                    
                                    grafo[i][j] = Integer.parseInt(auxFila[j]);
                                }
                                        
                            }

                    for(int i=0;i<contador;i++)
                            {
                                for(int j=0;j<contador;j++)
                                        System.out.print(grafo[i][j]+"|");
                                System.out.print("\n");
                            }
                    
                   
                    
                    
                    //INICIA EL ALGORITMO DFS
                    Stack<String> stack = new Stack<>();
                    ArrayList<Integer> path = new ArrayList<>();
                     ArrayList<Integer> next = new ArrayList<>();
                    ArrayList<Integer> rutasGen = new ArrayList<>();
                    int nodoActual=0,flagNext;
                    
                    stack.push(String.valueOf(rutaInicial));
                    
                    while(!stack.isEmpty()){
                        flagNext=0;
                        path.clear();
                        rutasGen.clear();
                        next.clear();
                        String [] pathSave = stack.pop().split(",");
                        
                        for(int i=0;i<pathSave.length;i++)
                            path.add(Integer.parseInt(pathSave[i]));
                        
                        
                        nodoActual = path.get(path.size()-1);
                        
                        //se sacan los nodos vecinos
                        for(int i=1;i<contador-1;i++)
                            {
                                if(grafo[i][0]==nodoActual)
                                    {
                                        for(int j=1;j<contador-1;j++)
                                        {
                                            if(grafo[i][j]==1)
                                                next.add(grafo[0][j]);
                                        }
                                        i=contador;//igualamos al contador para que se salga del for
                                    }
                            }
                        
                        //se restan los nodos vecinos menos path
                        if(!next.isEmpty())
                        {
                            //primero se encuentran los indices a remover
                            for(int i=next.size()-1;i>=0;i--)
                            {
                                for(int j=0;j<path.size();j++)
                                    {
                                        if(Objects.equals(next.get(i), path.get(j)))
                                        {
                                            next.remove(i);
                                            j=path.size();
                                        }
                                        
                                        if(next.isEmpty())
                                        {
                                            j=path.size();
                                            i=-1;
                                            flagNext=1;
                                        }
                                    }
                            }
                        }
                        else
                            flagNext=1;
                        
                        //entra si el Arraylist next no esta vacio
                        if(flagNext==0)
                        {
                            for(int i=0;i<next.size();i++)
                            {   
                                //if para ver si es el nodo de la ruta final y se crea el camino
                                if(next.get(i)==rutaFinal)
                                {
                                    for(int j=0;j<path.size();j++)
                                        rutasGen.add(path.get(j));
                                   
                                    
                                    rutasGen.add(next.get(i));
                                    
                                    System.out.println("Camino encontrado: \n");
                                    
                                    for(int x=0;x<rutasGen.size();x++)
                                        System.out.print(rutasGen.get(x)+",");
                                    
                                    //Ingresando valores a la matriz
                                    
                                    int filaActual=0,rutActual,vecinoAnt,vecinoPost,caso;
                                    
                                    for(int k=0;k<rutasGen.size();k++)
                                    {
                                        //primero buscamos la fila donde esta la ruta de cada posicion del arraylist
                                        for(int n=1;n<contador;n++)
                                        {
                                            if(matrizResultado[n][0]==rutasGen.get(k))
                                            {
                                                filaActual=n;
                                                n=contador;
                                            }
                                        }
                                        //buscamos los nodos a los que esta unida, si son el primero solo busca el siguiente, si es el ultimo el anterior, los demas buscan siguiente y anterior
                                        if(k==0)
                                            {
                                                //vecinoAnt=-1;
                                                vecinoPost=rutasGen.get(k+1);
                                            }
                                        else 
                                            {
                                                if(k==rutasGen.size()-1)
                                                    {
                                                        //vecinoAnt=rutasGen.get(k-1);
                                                        vecinoPost=-1;
                                                    }
                                                else
                                                    {
                                                        //vecinoAnt=rutasGen.get(k-1);
                                                        vecinoPost=rutasGen.get(k+1);
                                                    }
                                            }
                                        
                                        //Comenzamos a poner 1 donde se unan los nodos
                                        for(int y=1;y<contador;y++)
                                        {
                                            if(matrizResultado[0][y]==vecinoPost)
                                                matrizResultado[filaActual][y]=1;
                                        }
                                    }
                                    
                                    //finalizacion de entrada de valores a la matriz
                                }
                                else
                                {
                                    String pathAux="";
                                    
                                    for(int j=0;j<path.size();j++)
                                        pathAux=pathAux+String.valueOf(path.get(j))+",";
                                    
                                    pathAux=pathAux+String.valueOf(next.get(i));
                                    stack.push(pathAux);
                                }
                            }
                        }
                        
                    }
                    
                    
                    //FINALIZA EL ALGORITMO DFS
                    
                    System.out.print("\nMatriz resultante \n");
                     for(int i=0;i<contador;i++)
                            {
                                for(int j=0;j<contador;j++)
                                        System.out.print(matrizResultado[i][j]+"|");
                                System.out.print("\n");
                            }

                } catch (IOException e) {

                        e.printStackTrace();

                } finally {

                        try {

                                if (br != null)
                                        br.close();

                                if (fr != null)
                                        fr.close();

                        } catch (IOException ex) {

                            ex.printStackTrace();
                        }
                }
        }else{
            System.out.println("Error en la conexión a la base de datos");
        }
    }
    
        public static void calculaFrecuencia() throws SQLException{
        if(bd.conecta()){
            
        
        }else{
            System.out.println("Error en la conexión a la base de datos");
        }
    }
    
    public static void generaViajeCompleto(int estacionInicial, int estacionFinal,ArrayList<Integer> camino) throws SQLException{
        if(bd.conecta()){
            
            int rutaInicial=0, rutaFinal=0;

            rutaInicial=camino.get(0);

            rutaFinal=camino.get(camino.size()-1);
            System.out.println("Inicial: "+rutaInicial);
            System.out.println("Final: "+rutaFinal);
            
            int rutaActual=0,rutaSiguiente=0,estPartida=estacionInicial,estTope=0;
            ArrayList<Integer> estacionesRuta = new ArrayList<>();

            for(int i=0;i<camino.size();i++)
            {
                rutaActual=camino.get(i);
                
                if(i!=camino.size()-1)
                    {
                        rutaSiguiente=camino.get(i+1);
                        //se hace un select de donde se unen las rutas
                        rs = bd.consulta("select r.id_estacion from ruta_estacion r join ruta_estacion e on r.id_estacion=e.id_estacion where r.id_ruta="+rutaActual+" and e.id_ruta="+rutaSiguiente+" group by r.id_estacion;");
                        while(rs.next()){
                            estTope=rs.getInt("id_estacion");
                            break;
                        }
                    }
                else
                    estTope=estacionFinal;

                int topeAux=0,partidaAux=0,flagInicio=0,contador=0;
                
                rs = bd.consulta("select * from ruta_estacion where id_ruta="+rutaActual+";");
                
                while(rs.next()){
                        if(rs.getInt("id_estacion")==estPartida && flagInicio!=1)
                        {
                            flagInicio=1;
                            topeAux=estTope;
                            partidaAux=estPartida;
                        }
                    
                        if(rs.getInt("id_estacion")==estTope && flagInicio!=1)
                        {
                            flagInicio=1;
                            topeAux=estPartida;
                            partidaAux=estTope;
                        }
                        
                        if(flagInicio==1)
                            {
                                if(rs.getInt("id_estacion")==topeAux || rs.getInt("id_estacion")==partidaAux)
                                    contador++;
                                
                                estacionesRuta.add(rs.getInt("id_estacion"));
                            }
                        
                        if(contador==2)
                            break;
                            
                    }

                
                estPartida = estTope;
            }
            
            
            for(int i=0;i<estacionesRuta.size();i++)
                System.out.print(" "+estacionesRuta.get(i));        
     
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
    
    public static int obtieneUnidadesMinimas(int ruta) throws SQLException{
        int minimo = 0;
        //Obtenemos tiempo de recorrido y frecuencias de viaje de la ruta
        if(bd.conecta()){
            rs = bd.consulta("SELECT tiempo_recorrido, frecuencia_ida, frecuencia_vuelta FROM ruta WHERE id_ruta = " + ruta);
            rs.next();
            int tiempo_viaje = rs.getInt("tiempo_recorrido");
            int frecuencia_ida = rs.getInt("frecuencia_ida");
            int frecuencia_vuelta = rs.getInt("frecuencia_vuelta");
            //Utilizamos la siguiente fórmula para obtener el número minimo de unidades por sentido de ruta
            // min = 2 * (t / f) donde t es el tiempo de viaje y f la frecuencia del sentido de la ruta
            int min_ida = (int) Math.ceil(2 * (tiempo_viaje / frecuencia_ida));
            int min_vuelta = (int) Math.ceil(2 * (tiempo_viaje / frecuencia_vuelta));
            System.out.println("min_ida: " + min_ida);
            System.out.println("min_vuelta: " + min_vuelta);
            //Para obtener el mínimo total de unidades para operar la ruta, calcularemos el promedio entre ambos mínimos
            minimo = (int) Math.ceil((min_ida + min_vuelta) / 2);
            System.out.println("minimo: " + minimo);
        }else{
            System.out.println("Error en la conexión a la base de datos");
            minimo = -1;
        }
        return minimo;
    }
    
    public static void main(String[] args) {
        try {
            //reduceGrafoATransbordos();
            //generaViajesUnidad(6);
            //obtieneUnidadesMinimas(8);
            //Dijkstra d = new Dijkstra();
            
            generaRutasTransbordos(96, 57);
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
