package planificador;

import algoritmos.*;
import java.util.ArrayList;
import java.util.Collections;

public class Planificador {
    
    Algoritmos a = new Algoritmos();
    
    int[] indexOf(ArrayList<Integer> list, int valor){
        ArrayList<Integer> aux = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            int actual = list.get(i);
            if(actual == valor){
                aux.add(i);
            }
        }
        
        int[] indices = new int[aux.size()];
        for(int i = 0; i < aux.size(); i++){
            indices[i] = aux.get(i);
        }
        
        return indices;
    }
    
    public int[] planificadorUsuarios(int operacion, int estacion_inicial, int estacion_final) throws Exception{
        //Se reduce 
        a.reduceGrafoATransbordos();
        //Se obtienen los caminos (hacer que el método regrese int[][]
        int[][] grafoCaminos = a.generaRutasTransbordos(estacion_inicial, estacion_final, operacion);
        int peso1=0,peso2=0;
        //Se ejecutan los algoritmos de optimizacion
       // Dijkstra dk = new Dijkstra();
      //  int[] resDk = dk.dijkstra(grafoCaminos, estacion_inicial, estacion_final);
        /*
        PERT p = new PERT();
        int[] resP = p.metodoPERT(grafoCaminos,estacion_inicial,estacion_final);
        System.out.println();
        System.out.println("PERT "+resP.length);
        for(int i = 0;i<resP.length;i++)
            System.out.print(" "+resP[i]);*/
        
        FloydWarshall fw = new FloydWarshall();
        int[] resFW = fw.FloydWarshall(grafoCaminos, estacion_inicial, estacion_final);
        
        peso1 = resFW[resFW.length-1];
        
        ArrayList<Integer> auxCaminoRuta = new ArrayList<>();
        ArrayList<Integer> auxCaminoPeso = new ArrayList<>();
        
        for(int z=0;z<resFW.length-1;z++)
            auxCaminoRuta.add(resFW[z]);
        
        if(operacion==1)
            auxCaminoPeso = a.calculaPesos(auxCaminoRuta,estacion_inicial,estacion_final);
        else
            auxCaminoPeso = a.calculaPesos(auxCaminoRuta);
        
        for(int y=0;y<auxCaminoPeso.size();y++)
            peso2 = peso2+auxCaminoPeso.get(y);
           
        ArrayList<Integer> resultados_algoritmos = new ArrayList<>();
        int[] camino = {};
      //  resultados_algoritmos.add(resDk[resDk.length - 1]);
     /* resultados_algoritmos.add(40);
        resultados_algoritmos.add(resP[resP.length - 1]);
        resultados_algoritmos.add(resFW[resFW.length - 1]);
        
        System.out.println("Resultados");
        
        for(int i=0;i<resultados_algoritmos.size();i++)
            System.out.println(resultados_algoritmos.get(i));
        
        //Elegimos el resultado que más nos conviene dependiendo el resultado
        int valorMin = 999;
        for(int res : resultados_algoritmos){
            if(res < valorMin)
                valorMin = res;
        }
        
        int[] camino = {};
        
        int resIguales = Collections.frequency(resultados_algoritmos, valorMin);
        
        if(resIguales <= 1){ //Cuando solo un algoritmo tiene el resultado menor
            int index = resultados_algoritmos.indexOf(valorMin);
            switch(index){
                case 0:
               //     camino = resDk;
                    break;
                case 1:
                    camino = resP;
                    break;
                case 2:
                    camino = resFW;
                    break;
            }
        }else{ //Más de un algoritmo tiene el resultado menor
            int[] indices = indexOf(resultados_algoritmos, valorMin);
            int auxIndex = 0;
            int numNodMin = 9999;
            for(int i = 0; i < indices.length; i++){
                int auxNumNod = 0;
                switch(indices[i]){
                    case 0:
                  //      auxNumNod = resDk.length - 1;
                        break;
                    case 1:
                        auxNumNod = resP.length - 1;
                        break;
                    case 2:
                        camino = resFW;
                        break;
                }
                if(auxNumNod < numNodMin){
                    numNodMin = auxNumNod;
                    auxIndex = indices[i];
                }
            }
            switch(auxIndex){
                case 0:
                //    camino = resDk;
                    break;
                case 1:
                    camino = resP;
                    break;
                case 2:
                    camino = resFW;
                    break;
            }
        }*/
        
        camino = resFW;
        
        System.out.println("\n\nCAMINO");
        for(int i = 0; i < camino.length; i++){
            System.out.print(camino[i] + " ");
        }
        System.out.println("\n\n");
        //Expandemos grafo
        int[] grafoResultado = a.generaViajeCompleto(estacion_inicial, estacion_final, camino);
        int posAux = grafoResultado.length;
        int [] grafoRes = new int[posAux+2];
        //grafoResultado[posAux] =peso1;
        //grafoResultado[posAux+1] =peso2;
        for(int i = 0; i < grafoResultado.length; i++){
            grafoRes[i]=grafoResultado[i];
        }
        
        if(operacion==0)
        {
            grafoRes[posAux]=peso1;
            grafoRes[posAux+1]=peso2;
        }
        else
        {
            grafoRes[posAux]=peso2;
            grafoRes[posAux+1]=peso1;
        }
            
        
        System.out.println("\n\nCAMINO PESOS");
        for(int i = 0; i < grafoRes.length; i++){
            System.out.print(grafoRes[i] + " ");
        }
        return grafoRes;
    }
    
    public int planificadorAdministrador(int operacion, int ruta) throws Exception{
        if(operacion == 1){ //optimizar frecuencia
            
        }else{ //Optimizar número de unidades
            int numMin = a.obtieneUnidadesMinimas(ruta);
        }
        return 0;
    }
    
    public static void main(String[] args) throws Exception {
        Planificador p = new Planificador();
        int[] resultado = p.planificadorUsuarios(1, 51, 92);
        System.out.println("");
        System.out.println("");
        for(int i = 0; i < resultado.length; i++){
                System.out.print(resultado[i]+" ");
            
            
        }
        System.out.println("");
    }

}
