package algoritmos;

import java.io.*;
import java.util.*;
 
public class BellmanFord {
 
    private LinkedList<Aristas> aristas;
    private float etiquetas[];
    private int predecesor[];
    private int numeroVertices, totalAristas, nodoOrigen;
    private final int INFINITY = 999;
 
    private static class Aristas {
 
        int origen, destino;
        float coste;
 
        public Aristas(int a, int b, float c) {
            origen = a;
            destino = b;
            coste = c;
        }
 
        @Override
        public String toString() {
            return "Aristas{" + "origen=" + origen + ", destino=" + destino + ", coste=" + coste + '}';
        }
    }
 
    public BellmanFord(int[][] grafo, int inicio, int destino) throws IOException {
        float item;
        aristas = new LinkedList<Aristas>();
        
        numeroVertices = grafo[0].length - 1;
        
        int[] rutas = new int[numeroVertices];
                
        for(int x = 1; x <= numeroVertices; x++){
            rutas[x-1] = grafo[0][x];
        }

        int grafoN[][] = new int[numeroVertices][numeroVertices];
        for(int x = 1; x <= numeroVertices; x++){
            for(int y = 1; y <= numeroVertices; y++){
                grafoN[x-1][y-1] = grafo[x][y];
            }
        }
        
        for (int i = 0; i < numeroVertices; i++) {
            for (int j = 0; j < numeroVertices; j++) {
                if (i != j) {
                    item = (float) grafoN[i][j];
                    if (item != 0) {
                        aristas.add(new Aristas(i, j, item));
                    }
                }
            }
        }
        totalAristas = aristas.size();
        System.out.println("");
        etiquetas = new float[numeroVertices];
        predecesor = new int[numeroVertices];
        nodoOrigen = inicio;
    }
 
    private void relajoArista() {
        int i, j;
        for (i = 0; i < numeroVertices; ++i) {
            etiquetas[i] = INFINITY;
        }
        etiquetas[nodoOrigen] = 0;
        for (i = 0; i < numeroVertices - 1; ++i) {
            for (j = 0; j < totalAristas; ++j) {
                System.out.println(aristas.get(j));
                if (etiquetas[aristas.get(j).origen] + aristas.get(j).coste < etiquetas[aristas.get(j).destino]) {
                    etiquetas[aristas.get(j).destino] = etiquetas[aristas.get(j).origen] + aristas.get(j).coste;
                    predecesor[aristas.get(j).destino] = aristas.get(j).origen;
                }
            }
            for (int p = 0; etiquetas.length < p; p++) {
                System.out.println("\t" + etiquetas[p]);
            }
        }
    }
 
    private boolean ciclo() {
        int j;
        for (j = 0; j < totalAristas; ++j) {
            if (etiquetas[aristas.get(j).origen] + aristas.get(j).coste < etiquetas[aristas.get(j).destino]) {
                return false;
            }
        }
        return true;
    }
 
    public static void main(String args[]) throws IOException {
        int[][] grafo = {
                                {0,5,6,7,8},
                                {5,0,1,1,0},
                                {6,0,0,1,0},
                                {7,0,0,0,0},
                                {8,0,0,0,0}
                            };
        BellmanFord bellman = new BellmanFord(grafo, 0, 2);
        bellman.relajoArista();
        if (bellman.ciclo()) {
            for (int i = 0; i < bellman.numeroVertices; i++) {
                System.out.println("Coste desde " + bellman.nodoOrigen + " a " + (i + 1) + " =>" + bellman.etiquetas[i]);
            }
            for (int i = 0; i < bellman.numeroVertices; i++) {
                System.out.println("El predecesor de " + (i + 1) + " es " + (bellman.predecesor[i] + 1));
            }
        } else {
            System.out.println("Hay un ciclo negativo");
        }
    }
}