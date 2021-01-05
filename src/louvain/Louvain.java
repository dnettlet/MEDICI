package louvain;
  
import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.InputStreamReader;  
import java.util.ArrayList;  
import java.util.Random;  
  
public class Louvain implements Cloneable{  
    int n; 
    int m; 
    int num_communities = 0; // this gets returned to main by louvain which checks if equal to 10
    int cluster[]; 
    Edge edge[]; 
    int head[]; 
    int top; 
    double resolution; 
    double[] node_weight; 
    double totalEdgeWeight; 
    double[] cluster_weight; 
    double eps = 1e-14;
  
    int global_n;
    int global_cluster[];
    Edge[] new_edge;
    int[] new_head;
    int new_top = 0;
    final int iteration_time = 3;
      
    Edge[] global_edge;
    int[] global_head;  
    int global_top=0;  
      
    void addEdge(int u, int v, double weight) {    
        if(edge[top]==null)  
            edge[top]=new Edge();  
        edge[top].v = v;  
        edge[top].weight = weight;  
        edge[top].next = head[u];  
        head[u] = top++;  
    }  
  
    void addNewEdge(int u, int v, double weight) {  
        if(new_edge[new_top]==null)  
            new_edge[new_top]=new Edge();  
        new_edge[new_top].v = v;  
        new_edge[new_top].weight = weight;  
        new_edge[new_top].next = new_head[u];  
        new_head[u] = new_top++;  
    }  
      
    void addGlobalEdge(int u, int v, double weight) {  
        if(global_edge[global_top]==null)  
            global_edge[global_top]=new Edge();  
        global_edge[global_top].v = v;  
        global_edge[global_top].weight = weight;  
        global_edge[global_top].next = global_head[u];  
        global_head[u] = global_top++;  
    }  
  
    void init(String filePath, double control_communities) {  
        try {  
            String encoding = "UTF-8";  
            File file = new File(filePath);  
            if (file.isFile() && file.exists()) { 
                
            	int numOfNodes = 0;
                int numOfEdges = 0;
                {            
                	InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); 
                	BufferedReader br = new BufferedReader(read); 
                	
                	for(String line = br.readLine(); line !=null; line = br.readLine()) {
                		String linedata[] = line.split(";");
                		int node1 = Integer.parseInt(linedata[0]);
                		int node2 = Integer.parseInt(linedata[1]);
                		numOfNodes = Math.max(Math.max(numOfNodes, node1),node2);
                		numOfEdges += 1;
                    }
                	br.close();
                	numOfNodes += 1; // Node id starts at 0
                }            	
            	
            	InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);  
                String lineTxt = null;  
                lineTxt = bufferedReader.readLine();  
                                
 
                  
                global_n = n = numOfNodes;  
                m = numOfEdges * 2;  
                
                edge = new Edge[m];  
                head = new int[n];  
                for (int i = 0; i < n; i++)  
                    head[i] = -1;  
                top = 0;  
                  
                global_edge=new Edge[m];  
                global_head = new int[n];  
                for(int i=0;i<n;i++)  
                    global_head[i]=-1;  
                global_top=0;  
                global_cluster = new int[n];  
                for (int i = 0; i < global_n; i++)  
                    global_cluster[i] = i;  
                node_weight = new double[n];  
                totalEdgeWeight = 0.0;  
                while ((lineTxt = bufferedReader.readLine()) != null) {  
                    String cur[] = lineTxt.split(";");  
                    int u = Integer.parseInt(cur[0]);  
                    int v = Integer.parseInt(cur[1]);  
                    double curw;  
                    if (cur.length > 2) {  
                        curw = Double.parseDouble(cur[2]);  
                    } else {  
                        curw = 1.0;  
                    }  
                    addEdge(u, v, curw);  
                    addEdge(v, u, curw);  
                    addGlobalEdge(u,v,curw);  
                    addGlobalEdge(v,u,curw);  
                    totalEdgeWeight += 2 * curw;  
                    node_weight[u] += curw;  
                    if (u != v) {  
                        node_weight[v] += curw;  
                    }  
                }  
                resolution = (1 / totalEdgeWeight); // * 0.001; 
                resolution = resolution * control_communities; // this is the factor to get the right number of clusters, initialized as 1.0
                											   // then steps up/down by 0.01 until 10 communities are obtained
                System.out.println("resolution:"+resolution); 
                read.close();  
            } else {  
                System.out.println("ja1");  
            }  
        } catch (Exception e) {  
            System.out.println("ja2");  
            e.printStackTrace();  
        }  
    }  
  
    void init_cluster() {  
        cluster = new int[n];  
        for (int i = 0; i < n; i++) { 
            cluster[i] = i;  
        }  
        num_communities = n;
        System.out.format("\n n:%d\n",n);
    }  
  
    boolean try_move_i(int i) { 
        double[] edgeWeightPerCluster = new double[n];  
        for (int j = head[i]; j != -1; j = edge[j].next) {  
            int l = cluster[edge[j].v]; 
            edgeWeightPerCluster[l] += edge[j].weight;  
        }  
        int bestCluster = -1; 
        double maxx_deltaQ = 0.0; 
        boolean[] vis = new boolean[n];  
        cluster_weight[cluster[i]] -= node_weight[i];  
        for (int j = head[i]; j != -1; j = edge[j].next) {  
            int l = cluster[edge[j].v];
            if (vis[l]) 
                continue;  
            vis[l] = true;  
            double cur_deltaQ = edgeWeightPerCluster[l];  
            cur_deltaQ -= node_weight[i] * cluster_weight[l] * resolution;  
            if (cur_deltaQ > maxx_deltaQ) {  
                bestCluster = l;  
                maxx_deltaQ = cur_deltaQ;  
            }  
            edgeWeightPerCluster[l] = 0;  
        }  
        if (maxx_deltaQ < eps) {  
            bestCluster = cluster[i];  
        }  
         //System.out.println(maxx_deltaQ);  
        cluster_weight[bestCluster] += node_weight[i];  
        if (bestCluster != cluster[i]) { 
            cluster[i] = bestCluster;  
            return true;  
        }  
        return false;  
    }  
  
    void rebuildGraph() { 
 
        int[] change = new int[n];  
        int change_size=0;  
        boolean vis[] = new boolean[n];  
        for (int i = 0; i < n; i++) {  
            if (vis[cluster[i]])  
                continue;  
            vis[cluster[i]] = true;  
            change[change_size++]=cluster[i];  
        }  
        int[] index = new int[n]; 
        for (int i = 0; i < change_size; i++)  
            index[change[i]] = i;  
        int new_n = change_size; 
        new_edge = new Edge[m];  
        new_head = new int[new_n];  
        new_top = 0;  
        double new_node_weight[] = new double[new_n];
        for(int i=0;i<new_n;i++)  
            new_head[i]=-1;  
          
        ArrayList<Integer>[] nodeInCluster = new ArrayList[new_n];
        for (int i = 0; i < new_n; i++)  
            nodeInCluster[i] = new ArrayList<Integer>();  
        for (int i = 0; i < n; i++) {  
            nodeInCluster[index[cluster[i]]].add(i);  
        }  
        for (int u = 0; u < new_n; u++) { 
            boolean visindex[] = new boolean[new_n]; 
            double delta_w[] = new double[new_n]; 
            for (int i = 0; i < nodeInCluster[u].size(); i++) {  
                int t = nodeInCluster[u].get(i);  
                for (int k = head[t]; k != -1; k = edge[k].next) {  
                    int j = edge[k].v;  
                    int v = index[cluster[j]];  
                    if (u != v) {  
                        if (!visindex[v]) {  
                            addNewEdge(u, v, 0);  
                            visindex[v] = true;  
                        }   
                        delta_w[v] += edge[k].weight;  
                    }  
                }  
                new_node_weight[u] += node_weight[t];  
            }  
            for (int k = new_head[u]; k != -1; k = new_edge[k].next) {  
                int v = new_edge[k].v;  
                new_edge[k].weight = delta_w[v];  
            }  
        }  
  
        int[] new_global_cluster = new int[global_n];  
        for (int i = 0; i < global_n; i++) {  
            new_global_cluster[i] = index[cluster[global_cluster[i]]];  
        }  
        for (int i = 0; i < global_n; i++) {  
            global_cluster[i] = new_global_cluster[i];  
        }  
        top = new_top;  
        for (int i = 0; i < m; i++) {  
            edge[i] = new_edge[i];  
        }  
        for (int i = 0; i < new_n; i++) {  
            node_weight[i] = new_node_weight[i];  
            head[i] = new_head[i];  
        }  
        n = new_n;  
        init_cluster();  
    }  
  
    void print() {  
        for (int i = 0; i < global_n; i++) {  
            System.out.println(i + ": " + global_cluster[i]);  
        }  
        System.out.println("-------");  
    }  
  
    int louvain() {  
        init_cluster();  
        int count = 0;   
        boolean update_flag; 
        do {  
        //    print();   
            count++;  
            cluster_weight = new double[n];  
            for (int j = 0; j < n; j++) { 
                cluster_weight[cluster[j]] += node_weight[j];  
            }  
            int[] order = new int[n]; 
            for (int i = 0; i < n; i++)  
                order[i] = i;  
            Random random = new Random();  
            for (int i = 0; i < n; i++) {  
                int j = random.nextInt(n);  
                int temp = order[i];  
                order[i] = order[j];  
                order[j] = temp;  
            }  
            int enum_time = 0; 
            int point = 0; 
            update_flag = false; 
            do {  
                int i = order[point];  
                point = (point + 1) % n;  
                if (try_move_i(i)) {
                    enum_time = 0;  
                    update_flag = true;  
                } else {  
                    enum_time++;  
                }  
            } while (enum_time < n);  
            if (count > iteration_time || !update_flag) 
                break;  
            rebuildGraph(); 
        } while (true);  
    return num_communities;
    }  
}  