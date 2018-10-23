package algoritmos;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
            System.out.println("grafo_transbordos.info actualizado");
        }else{
            System.out.println("Error en la conexión a la base de datos");
        }
    }
    
    public static void main(String[] args) {
        try {
            reduceGrafoATransbordos();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static int getPosicionArray(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
