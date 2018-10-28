package algoritmos;

import java.sql.ResultSet;


public class Dijkstra { 

	private static final int SIN_PADRE = -1; 
        static String ruta = "";
        private static Conexion bd = new Conexion("tt", "root", "b3nj4m1n");
        private static ResultSet rs;
        
        private static int indexOf(int[] array, int value){
            int index = -1;
            for(int i = 0; i < array.length; i++){
                if(array[i] == value)
                    index = i;
            }
            return index;
        }
	
	public static int[] dijkstra(int[][] grafo, int inicio, int destino) throws Exception
	{ 
		int nVertices = grafo[0].length - 1; 
                bd.conecta();
                rs = bd.consulta("SELECT id_ruta FROM ruta_estacion WHERE id_estacion= "+inicio);
                rs.next();
                inicio = rs.getInt("id_ruta");
                

                //obtenemos el id_ruta de la estacion de final
                rs = bd.consulta("SELECT id_ruta FROM ruta_estacion WHERE id_estacion= "+destino);
                rs.next();
                destino=rs.getInt("id_ruta");
                
                
                
                int[] rutas = new int[nVertices];
                
                for(int x = 1; x <= nVertices; x++){
                    rutas[x-1] = grafo[0][x];
                }
                
                inicio = indexOf(rutas, inicio);
                destino = indexOf(rutas, destino);
                
                int grafoN[][] = new int[nVertices][nVertices];
                for(int x = 1; x <= nVertices; x++){
                    for(int y = 1; y <= nVertices; y++){
                        grafoN[x-1][y-1] = grafo[x][y];
                    }
                }

		// distanciasMasCortas[i] almacenará las distancias más cortas entre el vertice de inicio y el vertice i
		int[] distanciasMasCortas = new int[nVertices]; 

                //Agregado[i] almacena true si el vertice i está incluido en el camino más corto o si la distancia más corta entre el inicio y el vertice i está terminada
		boolean[] agregado = new boolean[nVertices]; 

                //Inicializa todas las distancias como infinito y agregado[] como falso
		for (int i = 0; i < nVertices; i++) 
		{ 
			distanciasMasCortas[i] = Integer.MAX_VALUE; 
			agregado[i] = false; 
		} 
		
		// La distancia más corta entre el vértice de inicio y él mismo siempre es 0 
		distanciasMasCortas[inicio] = 0; 

		// Padres[] almacena la ruta del camino más cortos
		int[] padres = new int[nVertices]; 

		// El vértice de inicio no tiene padre
		padres[inicio] = SIN_PADRE; 

		// Encuentra los caminos más cortos para todos los vértices
		for (int i = 1; i < nVertices; i++) 
		{ 
                        // De la lista de vertices no procesados, se elige el vértice más cercano. 
                        // masCercano siempre es igual al vertice de inicio en la primer iteración
			int masCercano = -1; 
			int distanciaMC = Integer.MAX_VALUE; 
			for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) 
			{ 
				if (!agregado[vertexIndex] && distanciasMasCortas[vertexIndex] < distanciaMC) 
				{ 
					masCercano = vertexIndex; 
					distanciaMC = distanciasMasCortas[vertexIndex]; 
				} 
			} 

			// Marcamos el vértice elegido como procesado o agregado
			agregado[masCercano] = true; 

                        // Se actualiza el valor de distancia entre el vértice elegido y los vértices adyacentes
			for (int vi = 0; vi < nVertices; vi++) 
			{ 
                            int distanciaArista = grafoN[masCercano][vi]; 

                            if (distanciaArista > 0 && ((distanciaMC + distanciaArista) < distanciasMasCortas[vi])) 
                            { 
                                    padres[vi] = masCercano; 
                                    distanciasMasCortas[vi] = distanciaMC + distanciaArista; 
                            } 
			} 
		} 

		imprimeSolucion(inicio, destino, distanciasMasCortas, padres, rutas); 
                
                String[] rutaux = ruta.split(" ");
                int[] solucion = new int[rutaux.length + 1];
                for(int i = 0; i < rutaux.length; i++){
                    solucion[i] = Integer.parseInt(rutaux[i]);
                }
                solucion[solucion.length - 1] = distanciasMasCortas[destino];
                
                return solucion;
	} 

	private static void imprimeSolucion(int inicio, int destino, int[] distancias, int[] padres, int[] rutas) 
	{ 
            System.out.print("Vertice\t Distancia\t Ruta"); 

            System.out.print("\n" + inicio + " -> "); 
            System.out.print(destino + " \t\t "); 
            System.out.print(distancias[destino] + "\t\t"); 
            imprimeRuta(destino, padres, rutas); 
	} 
        
	private static void imprimeRuta(int currentVertex, int[] padres, int[] rutas) 
	{ 
		
		// Base case : Source node has 
		// been processed 
		if (currentVertex == SIN_PADRE) 
		{ 
			return; 
		} 
		imprimeRuta(padres[currentVertex], padres, rutas); 
                System.out.print(rutas[currentVertex] + " ");
		ruta += rutas[currentVertex] + " "; 
	}
} 

