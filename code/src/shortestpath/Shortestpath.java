/*
"Hints:

Sample input file address:   "H:\Datasets\graph.txt"

Input format
Each row of the input file denotes an undirected edge. 
The first two entries of each row denote the nodes of the edge and the third value is the edge weight. (e.g.  5 7 8.2 means there is an edge between node 5 and 7 and the weight is 8.2)
The seprator between the numbers is space.
The nodes must be labeled from 1 to the maximum number of nodes.
The compression ratio is the (Original graph size)/(Coarse graph size).

Amin Sadri
Jan 2018
 */

package shortestpath;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.jgrapht.*;
import org.jgrapht.alg.FloydWarshallShortestPaths;
import org.jgrapht.graph.*;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import sun.security.util.Length;

/**
 *
 * @author Amin Sadri
 */
public class Shortestpath {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException 
        {   
            String [] nextLine=new String[5];
            double [] Rand_We=new double[10001]; int I_Rand_We=0;
            String line; Scanner scanner = new Scanner(System.in);
            MyGraph MG=new MyGraph();
            long edge_nu=0;
            System.out.println("Please enter the address of the input file:" );
            String file_add = scanner.nextLine();
            //"H:\\Work\\Datasets-competition\\Graph\\1690_SP.net"
            File mainfile = new File (file_add);
            FileReader f=new FileReader(mainfile);
            //String path = mainfile.getPath();
            BufferedReader bufRdr = new BufferedReader(f);
            int Node_Nu=0;

            while(true){
                Node_Nu=MG.Old_Nu_Vertex;
                line = bufRdr.readLine();
                if (line==null) break;
                StringTokenizer st=new StringTokenizer(line," ",false);// \t
                //nextLine [0]=st.nextToken();
                nextLine [1]=st.nextToken();
                nextLine [2]=st.nextToken();
                nextLine [3]=st.nextToken();//"100";
                MG.addVertex(Integer.valueOf(nextLine [1]));
                MG.addVertex(Integer.valueOf(nextLine [2]));
                if (MG.addEdge(Integer.valueOf(nextLine [1]),Integer.valueOf(nextLine [2]), Double.valueOf(nextLine [3])))      edge_nu++;
                
            }
            
            MG.GetReady();
            int Nod_nu_Original=MG.Old_Nu_Vertex;
            System.out.println("The file is loaded. Number of nodes:"+String.valueOf(Nod_nu_Original)+ " edges:"+String.valueOf(edge_nu));
            
            long startTime = System.currentTimeMillis();
            System.out.print("Please enter the compression ratio:" );
            double CR= Double.parseDouble(scanner.nextLine());
            int k=(int)((1-1/CR)*(Nod_nu_Original));
            //Random rn = new Random();
            int a_ind=0;int n_r=0;int Merge_step;
            if (Nod_nu_Original % 23!=0) 
                Merge_step=23;
            else
                Merge_step=53;
            
            int merge_mode=0;//0=Shrink, 1=simple merge, 2=Han
            int node_selection=0;//1=true 
            for (int i=0;k>0;i++){
                a_ind=((i*Merge_step+1)%Nod_nu_Original);
                n_r++;
                if (MG.Merge(a_ind,merge_mode,node_selection)) k--;

            }
            
            System.out.println("Compression Ratio="+CR); 
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println("Coarsening time:"+elapsedTime+" ms");
                
            MG.save_graph(mainfile.getPath());
            

            

    }


 
}


