package algoritmos;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class Algoritmos {
    private static ResultSet rs;
    
    
    
    public static void generaRutasTransbordos(int estacionInicial, int estacionFinal) throws SQLException{
        if(bd.conecta()){
            String FILENAME = "D:\\Luis R\\Documents\\TT-2017-B055\\wsTT\\grafo_transbordos.info";
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
                                                vecinoAnt=-1;
                                                vecinoPost=rutasGen.get(k+1);
                                            }
                                        else 
                                            {
                                                if(k==rutasGen.size()-1)
                                                    {
                                                        vecinoAnt=rutasGen.get(k-1);
                                                        vecinoPost=-1;
                                                    }
                                                else
                                                    {
                                                        vecinoAnt=rutasGen.get(k-1);
                                                        vecinoPost=rutasGen.get(k+1);
                                                    }
                                            }
                                        
                                        //Comenzamos a poner 1 donde se unan los nodos
                                        for(int y=1;y<contador;y++)
                                        {
                                            if(matrizResultado[0][y]==vecinoAnt || matrizResultado[0][y]==vecinoPost)
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
            //reduceGrafoATransbordos();
            generaRutasTransbordos(57,77);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static int getPosicionArray(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
