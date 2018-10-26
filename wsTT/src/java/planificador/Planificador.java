package planificador;

import algoritmos.*;
import java.sql.SQLException;

public class Planificador {
    
    Algoritmos a = new Algoritmos();
    
    int[][] planificadorUsuarios(int operacion, int estacion_inicial, int estacion_final) throws Exception{
        //Se reduce 
        a.reduceGrafoATransbordos();
        //Se obtienen los caminos (hacer que el método regrese int[][]
        int[][] grafoCaminos = a.generaRutasTransbordos(estacion_inicial, estacion_final, operacion);
        
        //Se ejecutan los algoritmos de optimizacion
        Dijkstra dk = new Dijkstra();
        int[][] resDk = dk.dijkstra(grafoCaminos, estacion_inicial, estacion_final);
        
        //Elegimos el mejor
        int[][] camino = el mejor;
        
        //Expandemos grafo
        int[][] grafoResultado = a.generaViajeCompleto(estacion_inicial, estacion_final, camino);
        return grafoResuelto;
    }
    
    int planificadorAdministrador(int operacion, int ruta){
        if(operacion == 1){ //optimizar frecuencia
            
        }else{ //Optimizar número de unidades
            int numMin = a.obtieneUnidadesMinimas(ruta);
        }
        return null;
    }
}
