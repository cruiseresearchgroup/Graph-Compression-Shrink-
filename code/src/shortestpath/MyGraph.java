package shortestpath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import org.jgrapht.GraphPath;

import org.jgrapht.alg.DijkstraShortestPath;

//import org.jgrapht.alg.FloydWarshallShortestPaths;

import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.*;


public class MyGraph {

    public SimpleWeightedGraph<Integer, DefaultWeightedEdge> g = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);// agar khasti sari tar beshe bayad listenable koni
    public SimpleWeightedGraph<Integer, DefaultWeightedEdge> ori_g= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);;
    static final double DEFAULT_EDGE_WEIGHT=19;
    static final int Test_Nu=100;
    //public String[] Old_List_Vertex=new String [10000000];
    public String[] Map_Vertex=new String [10000000];
    public int[] Current_List_Vertex=new int [10000000];
    public  int Old_Nu_Vertex=0;
    public  int Max_Vertex_id=0;
    //public String[] New_List_Vertex=new String [1000000];
    public int New_Number_Vertex=0;
    //public double Thre=0;public double Cu_Thre_U=0;
    public double Cu_Thre_L=0;
    
    public int Nu_Uns_Mer=0;
    
    private NeighborIndex Neighbor_G;
    private double[][] Rand_nodes=new double[Test_Nu][5];
    private DefaultWeightedEdge e1=new DefaultWeightedEdge();
    Runtime runtime = Runtime.getRuntime();



    public void addVertex(int id) {
        if(!g.containsVertex(id)) {
            g.addVertex(id);
            Current_List_Vertex[id]=id;
            Map_Vertex[id]=String.valueOf(id);
            if (id>Max_Vertex_id) Max_Vertex_id=id;
            Old_Nu_Vertex++;
        }
        //graph.addVertex(name);
    }

    public boolean addEdge(int v1,int v2, double W) {
        if(!(g.containsEdge(v1, v2)||g.containsEdge(v2, v1))){
            if(v1==v2)return false;
            e1=g.addEdge(v1, v2);
            g.setEdgeWeight(e1, W);
            return true;
        }
        return false;
        
    }

    public SimpleWeightedGraph<Integer, DefaultWeightedEdge> getGraph() {
        return g;
    }


    void GetReady() {
        ori_g= (SimpleWeightedGraph<Integer, DefaultWeightedEdge>) g.clone();
        
        Neighbor_G=new NeighborIndex(g);

    }

    
    
    
    boolean Merge(int A_id, int merge_mode , int node_selection) {
        

        if((Current_List_Vertex[A_id]!=A_id)|(Current_List_Vertex[A_id]==0))
            return false;
        
        
        Integer[]  A_neighbors;
        Integer[]  B_neighbors;
        Integer[]  Set_P=new Integer [30000];
        Integer[]  Set_Q=new Integer [30000];
        Integer[]  Set_R=new Integer [30000];
	double[]  Set_P_Weight=new double[30000];
	double[]  Set_Q_Weight=new double[30000];
	double[]  Set_Ra_Weight=new double[30000];
        double[]  Set_Rb_Weight=new double[30000];
        
        int min_id=0;
        double min_we = 0;
        String A=String.valueOf(A_id);

        A_neighbors= (Integer[]) Neighbor_G.neighborsOf(A_id).toArray(new Integer[0]).clone();
        int A_ne_number=A_neighbors.length;

        //if((A_ne_number==0)||(A_ne_number==1))return false;
        if((A_ne_number==0))return false;
        double[] A_ne_weight= new double[A_ne_number];
       
        for(int j=0;j<A_ne_number;j++){
            e1=g.getEdge(A_id, A_neighbors[j]);
            A_ne_weight[j]=g.getEdgeWeight(e1);
            if(j==0){
                min_id=0;
                min_we=A_ne_weight[j];
            }
            if(A_ne_weight[j]<min_we){
                min_id=j;
                min_we=A_ne_weight[j];
            }
        }

        double wab = min_we;
        Integer B_id=A_neighbors[min_id];
        String B=String.valueOf(B_id);
        if ((Map_Vertex[B_id].equals(""))||(Current_List_Vertex[B_id]!=B_id))System.out.println(A+"Something is W4-----#######---------------");
        
            
        B_neighbors= (Integer[]) Neighbor_G.neighborsOf(B_id).toArray(new Integer[0]).clone();
        int B_ne_number= B_neighbors.length;

        if(node_selection==1)
            if (Cu_Thre_L<B_ne_number+A_ne_number){
                Nu_Uns_Mer++;
                UpdateThr(1);
                return false;
            }  else
            {
                UpdateThr(-1);
            }
        
        int size_A= new StringTokenizer(" " +Map_Vertex[A_id] + " ", ",").countTokens();
        int size_B= new StringTokenizer(" " +Map_Vertex[B_id] + " ", ",").countTokens();
        
        //if((B_ne_number==0)||(B_ne_number==1))return false;
        double[] B_ne_weight= new double[B_ne_number];
        for(int j=0;j<B_ne_number;j++){
            e1=g.getEdge(B_id, B_neighbors[j]);
            B_ne_weight[j]=g.getEdgeWeight(e1);
        }
        int p=0;int q=0; int r=0;
        for(int j=0;j<A_ne_number;j++){
		if (A_neighbors[j]==B_id) continue;
                if (!Arrays.asList(A_neighbors).contains(B_id)) System.out.println("Something is W2-----#######---------------");
		if (!Arrays.asList(B_neighbors).contains(A_neighbors[j])){
			Set_P[p]=A_neighbors[j];
	            	Set_P_Weight[p]=A_ne_weight[j];
			p++;		
		}	
		else{
                        if(!g.containsVertex(A_neighbors[j]))continue;
			Set_R[r]=A_neighbors[j];
	            	Set_Ra_Weight[r]=A_ne_weight[j];
                        Set_R[r]=A_neighbors[j];
                        e1=g.getEdge(B_id, A_neighbors[j]);
                        Set_Rb_Weight[r]=g.getEdgeWeight(e1);                        
			r++;		
		}	
        }

        for(int j=0;j<B_ne_number;j++){
		if (B_neighbors[j]==A_id) continue;
                if (!Arrays.asList(B_neighbors).contains(A_id)) System.out.println(A+"Something is W1-----#######---------------"+B);
		if (!Arrays.asList(A_neighbors).contains(B_neighbors[j])){
                    Set_Q[q]=B_neighbors[j];
	            Set_Q_Weight[q]=B_ne_weight[j];
                    q++;		
		}
	}
        //if((r==0))return false;//for comparison with Han
        
        g.removeVertex(B_id);
        
        g.removeVertex(A_id);
        
        //Set s2=g.edgesOf(A);
        //g.removeAllEdges(s2);
        
        Map_Vertex[A_id]=Map_Vertex[A_id]+","+Map_Vertex[B_id];

        String C=A;
        StringTokenizer st=new StringTokenizer(Map_Vertex[B_id],",",false);
        while(st.hasMoreTokens()){
            String S=st.nextToken();
            Current_List_Vertex[Integer.valueOf(S)]=A_id;
        }
        Map_Vertex[B_id]="";
        
        int C_id=Integer.valueOf(C);
        g.addVertex(C_id);

        double[] C_ne_weights;

        C_ne_weights=Cal_C_we(Set_P_Weight,Set_Ra_Weight,Set_Rb_Weight,Set_Q_Weight,p,r,q,wab,merge_mode,size_A,size_B);

        int id=0;
        for(int j=0;j<p;j++){
             e1=g.addEdge(C_id, Set_P[j]);
             g.setEdgeWeight(e1,C_ne_weights[id]);
             id++;
        }
        for(int j=0;j<r;j++){
             e1=g.addEdge(C_id, Set_R[j]);
             g.setEdgeWeight(e1,C_ne_weights[id]);
             id++;
        }

        for(int j=0;j<q;j++){
             e1=g.addEdge(C_id, Set_Q[j]);
             g.setEdgeWeight(e1,C_ne_weights[id]);
             id++;
        }
            
        Neighbor_G=new NeighborIndex(g);
        return true;      
        
    }

    private double[] Cal_C_we(double[] Set_P_Weight, double[] Set_Ra_Weight,double[] Set_Rb_Weight, double[] Set_Q_Weight,int p,int r, int q, double wab,int merge_mode,int size_A,int size_B) {
        int N=p+r+q;
 
        double[] C= new double[30000];
        double[] C_Weights= new double[30000];int jj=0;
        double[] C_test_Weights= new double[30000];

        if (merge_mode==1){//Simple merge
            for (int i=0;i<p;i++){C_test_Weights[jj]=Set_P_Weight[i]; jj++;}
            for (int i=0;i<r;i++){C_test_Weights[jj]=Set_Rb_Weight[i]; jj++;}
            for (int i=0;i<q;i++){C_test_Weights[jj]=Set_Q_Weight[i]; jj++;}        
            return C_test_Weights;
        }
        
        if (merge_mode==2){//Han
            for (int i=0;i<p;i++){C_test_Weights[jj]=Set_P_Weight[i]; jj++;}//*((double)size_A/(double)(size_A+size_B))
            for (int i=0;i<r;i++){C_test_Weights[jj]=((double) size_A*Set_Ra_Weight[i]+ (double)size_B*Set_Rb_Weight[i])/(double)(size_A+size_B); jj++;}//(Set_Ra_Weight[i]+ Set_Rb_Weight[i])/2;jj++;}//
            for (int i=0;i<q;i++){C_test_Weights[jj]=Set_Q_Weight[i]; jj++;} //*((double)size_B/(double)(size_A+size_B))       
            return C_test_Weights;
        }
 
        int C_id=0;
        double Sum_P=0;double Sum_Q=0;double Sum_PR=0;double Sum_QR=0;
        for (int i=0;i<p;i++)Sum_P=Sum_P+Set_P_Weight[i];
        for (int i=0;i<q;i++)Sum_Q=Sum_Q+Set_Q_Weight[i];
        for(int j=0;j<r;j++) Sum_PR=Sum_PR+Math.min(Set_Ra_Weight[j], wab+Set_Rb_Weight[j]);
        for(int j=0;j<r;j++) Sum_QR=Sum_QR+Math.min(Set_Rb_Weight[j], wab+Set_Ra_Weight[j]); 
        
        for (int i=0;i<p;i++){//computing Ci-stage 1-P
            C[C_id]=(N-2)*Set_P_Weight[i]+Sum_P+Sum_Q+q*wab;
            C[C_id]=C[C_id]+Sum_PR;
            C_id++;
        }
        for (int i=0;i<r;i++){//computing Ci-stage 2-R
            C[C_id]=Sum_P+Sum_Q+p*Math.min(Set_Ra_Weight[i], wab+Set_Rb_Weight[i])+q*Math.min(Set_Rb_Weight[i], wab+Set_Ra_Weight[i]);
            for(int j=0;j<r;j++) {
                if(i==j)continue;
                C[C_id]=C[C_id]+Math.min(Math.min(Set_Ra_Weight[j]+Set_Rb_Weight[i]+wab,Set_Ra_Weight[i]+Set_Rb_Weight[j]+wab),Math.min(Set_Ra_Weight[j]+Set_Ra_Weight[i],Set_Rb_Weight[i]+Set_Rb_Weight[j]));
            }
            C_id++;
        }
        for (int i=0;i<q;i++){//computing Ci-stage 3-Q
            C[C_id]=(N-2)*Set_Q_Weight[i]+Sum_P+Sum_Q+p*wab;
            C[C_id]=C[C_id]+Sum_QR;
            C_id++;
        }
        
        if (N>2){
            double S=0;
            for (int i=0;i<N;i++)  S=S+C[i];
            S=S/(2*N-2);
            for (int i=0;i<N;i++)C_Weights[i]=((C[i]-S)/(N-2))*1;
        }
        if(N==2){
          C_Weights[0]=C[0]/2;  
          C_Weights[1]=C[1]/2;  
        }
        if(N==1){
            if(p!=0)C_Weights[0]=Set_P_Weight[0]+wab/2;
            if(q!=0)C_Weights[0]=Set_Q_Weight[0]+wab/2;
            if(r!=0)C_Weights[0]=Math.min(Set_Ra_Weight[0],Set_Rb_Weight[0])+wab/2;
        }
        return C_Weights;
    }
    


   

    void UpdateThr(double d) {
         //To change body of generated methods, choose Tools | Templates.
        Cu_Thre_L=Cu_Thre_L+d;
    }

    void save_graph(String mainfile_path) throws FileNotFoundException, UnsupportedEncodingException {

        PrintWriter writer = new PrintWriter(mainfile_path+".out.txt");
        //writer.println("The first line");
       
              
         
            for (int i=0;i<Max_Vertex_id+1;i++)
            if((!"".equals(Map_Vertex[i]))&(Current_List_Vertex[i]==i))
                New_Number_Vertex++;        
        
            writer.println("*Vertices "+(New_Number_Vertex-1));
            
            int[] Current_List_Vertex_Name=Current_List_Vertex.clone();
            int new_name=1;
            for (int i=1;i<Old_Nu_Vertex+1;i++){
                if(Map_Vertex[i]!=""){
                    Current_List_Vertex_Name[i]=new_name;
                    writer.println(new_name+": "+Map_Vertex[i]);
                    
                    new_name++;
                }
            }
            

            writer.println("\n *Arcs");
            Set Ed_Set=g.edgeSet();
            //System.out.println(Ed_Set.size());
            Iterator itEdge = Ed_Set.iterator();

            while (itEdge.hasNext()){
            DefaultWeightedEdge de = (DefaultWeightedEdge) itEdge.next();
            writer.println(Current_List_Vertex_Name[g.getEdgeSource(de)]+" "+Current_List_Vertex_Name[g.getEdgeTarget(de)]+" "+g.getEdgeWeight(de));
            }

        writer.close();
        System.out.println("The output is saved in "+ mainfile_path+".out.txt");
    }



}

    
