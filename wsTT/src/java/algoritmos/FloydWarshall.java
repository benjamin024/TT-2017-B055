package algoritmos;

import java.sql.ResultSet;

public class FloydWarshall
{
    private static final int M = Integer.MAX_VALUE;
    private static String rutaS = "";
    private static Conexion bd = new Conexion("18.220.128.11", "tt", "remotoAWS", "n0m3l0");
    private static ResultSet rs;
    private static int[] rutas;
    
    private static int indexOf(int[] array, int value){
        int index = -1;
        for(int i = 0; i < array.length; i++){
            if(array[i] == value)
                index = i;
        }
        return index;
    }
    
    private static void imprimeRuta(int[][] ruta, int v, int u)
    {
        if (ruta[v][u] == v)
            return;
 
        imprimeRuta(ruta, v, ruta[v][u]);
        rutaS += rutas[ruta[v][u]] + " ";
        System.out.print(rutas[ruta[v][u]] + " ");
    }
 

    private static void imprimeSolucion(int[][] costo, int[][] ruta, int v, int u)
    {

        if (u != v && ruta[v][u] != -1)
        {
            rutaS = rutas[v] + " ";
            imprimeRuta(ruta, v, u);
            rutaS += rutas[u];
            System.out.println("\nVertice\t Distancia\t Ruta"); 
            System.out.println(v + " -> " + u + "\t\t" + costo[v][u] + "\t\t" + rutaS);
        }
    }
 
    public int[] FloydWarshall(int[][] matriz, int inicio, int destino) throws Exception
    {
        int N = matriz.length - 1;
        
        bd.conecta();
        rs = bd.consulta("SELECT id_ruta FROM ruta_estacion WHERE id_estacion= "+inicio);
        rs.next();
        inicio = rs.getInt("id_ruta");


        //obtenemos el id_ruta de la estacion de final
        rs = bd.consulta("SELECT id_ruta FROM ruta_estacion WHERE id_estacion= "+destino);
        rs.next();
        destino=rs.getInt("id_ruta");

        rutas = new int[N];

        for(int x = 1; x <= N; x++){
            rutas[x-1] = matriz[0][x];
        }
        
        inicio = indexOf(rutas, inicio);
        destino = indexOf(rutas, destino);

        int grafoN[][] = new int[N][N];
        for(int x = 1; x <= N; x++){
            for(int y = 1; y <= N; y++){
                if(matriz[x][y] != 0)
                    grafoN[x-1][y-1] = matriz[x][y];
                else
                   grafoN[x-1][y-1] = M; 
            }
        }
        
        // costo[] almacenará los costos más pequeños y ruta[] almacenará los caminos más cortos
        int[][] costo = new int[N][N];
        int[][] ruta = new int[N][N];
 
        // inicializa costo[] and ruta[]
        for (int v = 0; v < N; v++)
        {
            for (int u = 0; u < N; u++)
            {
                //Inicialmente el costo será igual al peso de la arista
                costo[v][u] = grafoN[v][u];
 
                if (v == u)
                    ruta[v][u] = 0;
                else if (costo[v][u] != Integer.MAX_VALUE)
                    ruta[v][u] = v;
                else
                    ruta[v][u] = -1;
            }
        }
 
        //Se ejecuta Floyd-Warshall
        for (int k = 0; k < N; k++)
        {
            for (int v = 0; v < N; v++)
            {
                for (int u = 0; u < N; u++)
                {

                    //Si el vértice k está en el ruta más corta de v a u, actualizar el valor de costo[v][u] y ruta[v][u]
 
                    if (costo[v][k] != Integer.MAX_VALUE && costo[k][u] != Integer.MAX_VALUE && (costo[v][k] + costo[k][u] < costo[v][u]))
                    {
                        costo[v][u] = costo[v][k] + costo[k][u];
                        ruta[v][u] = ruta[k][u];
                    }
                }

                //Si los elementos diagonales son negativos, el grafo contiene un ciclo de pesos negativo
                if (costo[v][v] < 0)
                {
                    System.out.println("Ciclo de pesos negativo encontrado.");
                    return null;
                }
            }
        }
 
        imprimeSolucion(costo, ruta, inicio, destino);
        
        String[] rutaux = rutaS.split(" ");
        int[] solucion = new int[rutaux.length + 1];
        for(int i = 0; i < rutaux.length; i++){
            solucion[i] = Integer.parseInt(rutaux[i]);
        }
        solucion[solucion.length - 1] = costo[inicio][destino];
        
        return solucion;
    }
}