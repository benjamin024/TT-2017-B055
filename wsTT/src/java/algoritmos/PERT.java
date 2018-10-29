package algoritmos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class PERT {
    
    private static Conexion bd = new Conexion("tt", "root", "b3nj4m1n");
    private static ResultSet rs;
    
    public static int[] metodoPERT(int[][]grafo,int estacion_ini,int estacion_fin) throws SQLException
    {
        //encontrando la ruta inicial y la final
        if(bd.conecta())
        {
            
            int rutaInicial=0,rutaFinal=0;
            rs = bd.consulta("SELECT id_ruta FROM ruta_estacion WHERE id_estacion= "+estacion_ini);
            while(rs.next()){
                rutaInicial=rs.getInt("id_ruta");
            }
            
            //obtenemos el id_ruta de la estacion de final
            rs = bd.consulta("SELECT id_ruta FROM ruta_estacion WHERE id_estacion= "+estacion_fin);
            while(rs.next()){
                rutaFinal=rs.getInt("id_ruta");
            }
        /*for(int i=0;i<dimension;i++)
        {
            flagIni=0;
            flagFin=0;
            
            for(int j=0;j<dimension;j++)
                {
                    if(grafo[i][j]==1)
                        flagFin=1;
                    
                    if(grafo[j][i]==1)
                        flagIni=1;
                }
            
            if(flagIni==0)
                rutaInicial=grafo[0][i];
            
            if(flagFin==0)
                rutaFinal=grafo[i][0];
        }*/
        
        //INICIA EL ALGORITMO DFS
                    Stack<String> stack = new Stack<>();
                    ArrayList<Integer> path = new ArrayList<>();
                    ArrayList<Integer> pathOptimo = new ArrayList<>();
                     ArrayList<Integer> next = new ArrayList<>();
                    ArrayList<Integer> rutasGen = new ArrayList<>();
                    int nodoActual=0,flagNext,contador=grafo.length;
                    int contadorPath=0;
                    
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
                    
                        //se sacan los nodos vecinos SE HIZO MODIFICACION AQUÍ, REVISAR EN EL ORIGINAL
                        for(int i=1;i<contador;i++)
                            {
                                if(grafo[i][0]==nodoActual)
                                    {
                                        for(int j=1;j<contador-1;j++)
                                        {
                                            if(grafo[i][j]>0)
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
                                    
                                    //System.out.println("\nCamino encontrado: \n");
                                    int sumaPesos=0,nodoAnt=0,nodoSig=0;
                                    
                                   /* for(int x=0;x<rutasGen.size();x++)
                                    {
                                        System.out.print(rutasGen.get(x)+",");
                                    }*/
                                    
                                    //obtenemos los pesos
                                    for(int x=0;x<rutasGen.size();x++)
                                    {
                                        nodoAnt=rutasGen.get(x);
                                        if(x!=rutasGen.size()-1)
                                        {
                                            nodoSig=rutasGen.get(x+1);
                                            for(int y=0;y<contador;y++)
                                            {
                                                if(grafo[y][0]==nodoAnt)
                                                    {
                                                        for(int w=0;w<contador;w++)
                                                            {
                                                                if(grafo[0][w]==nodoSig)
                                                                {
                                                                    sumaPesos=sumaPesos+grafo[y][w];
                                                                    w=contador;
                                                                }
                                                            }
                                                        y=contador;
                                                    }
                                            }
                                        }
                                    }
                                    
                                    //System.out.print(" PEso: "+sumaPesos);
                                    
                                    if(contadorPath>0)
                                    {
                                        //System.out.println("A comparar: "+pathOptimo.get(pathOptimo.size()-1));
                                        if(sumaPesos<pathOptimo.get(pathOptimo.size()-1))
                                        {
                                          //  System.out.println("Entra1");
                                            pathOptimo.clear();
                                            for(int x=0;x<rutasGen.size();x++)
                                                pathOptimo.add(rutasGen.get(x));
                                            pathOptimo.add(sumaPesos);
                                        }
                                        
                                        /*System.out.println("Opt despues");
                                        for(int p=0;p<pathOptimo.size();p++)
                                            System.out.print(" "+pathOptimo.get(i));*/
                                    }
                                    else
                                    {
                                        for(int x=0;x<rutasGen.size();x++)
                                            pathOptimo.add(rutasGen.get(x));
                                        pathOptimo.add(sumaPesos);
                                        
                                       /* System.out.println("Opt despues2");
                                        for(int p=0;p<pathOptimo.size();p++)
                                            System.out.print(" "+pathOptimo.get(i));*/
                                    }
                                    
                                    contador++;
                                    contadorPath++;
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
                    /*
                   System.out.println("Optimo : \n");
                    for(int x=0;x<pathOptimo.size();x++)
                        {
                            System.out.print(pathOptimo.get(x)+",");
                        }*/
                    
                    if(!pathOptimo.isEmpty())
                    {
                        int[]resultado=new int[pathOptimo.size()];

                        for(int x=0;x<pathOptimo.size();x++)
                            {
                                resultado[x]=pathOptimo.get(x);
                            }
                        
                        return resultado;
                    }
                    else 
                        return null;
        }
        else
        {
            System.out.println("No se pudo conectar a la base");
            return null;
        }
    }
}
