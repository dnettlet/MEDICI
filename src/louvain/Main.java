package louvain;
import java.io.BufferedWriter;  
import java.io.FileWriter;  
import java.io.IOException;  
import java.util.ArrayList;  
  
public class Main {
	
	String inputFileName;
	String outputFileName;
	int target_communities;
		
	double control_communities = 1.0;
	
    public Main() {  
    	this(   "./resources/Default_files/1kby30k.csv",
                "./resources/Input_files/1kby30k_communities.csv");
    }  
        
    public Main(String inputFileName,String outputFileName) {  
    	this(inputFileName,outputFileName,10);
    }
    
    public  Main(String inputFileName,String outputFileName,int target_communities) {  
    	this.inputFileName = inputFileName;
    	this.outputFileName = outputFileName;
    	this.target_communities = Math.max(1,Math.min(target_communities,10));
    }
	
    public void writeOutputJson(String fileName, Louvain a) throws IOException {  
        BufferedWriter bufferedWriter;  
        bufferedWriter = new BufferedWriter(new FileWriter(fileName));  
        bufferedWriter.write("{\n\"nodes\": [\n");  
        for(int i=0;i<a.global_n;i++){  
            bufferedWriter.write("{\"id\": \""+i+"\", \"group\": "+a.global_cluster[i]+"}");  
            if(i+1!=a.global_n)  
            bufferedWriter.write(",");  
            bufferedWriter.write("\n");  
        }  
        bufferedWriter.write("],\n\"links\": [\n");  
          
        for(int i=0;i<a.global_n;i++){  
            for (int j = a.global_head[i]; j != -1; j = a.global_edge[j].next) {  
                int k=a.global_edge[j].v;  
                bufferedWriter.write("{\"source\": \""+i+"\", \"target\": \""+k+"\", \"value\": 1}");     
                if(i+1!=a.global_n||a.global_edge[j].next!=-1)  
                    bufferedWriter.write(",");  
                bufferedWriter.write("\n");  
            }  
        }  
        bufferedWriter.write("]\n}\n");
        bufferedWriter.close();  
    }  
      
    static void writeOutputCluster(String fileName, Louvain a) throws IOException{  
    	BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName)); 
    	int communityid=10;
        for(int i=0; i < a.global_n; i++){  
        	if (a.global_cluster[i] > 9)   // so always get 10 communities because any communities with id 9 and above get assigned as 9 
        		a.global_cluster[i] = 9;
            bufferedWriter.write(i + ";" +  a.global_cluster[i] + "\n");
        }  
        bufferedWriter.close();  
    } 
    
    public boolean generateCommunitiesFile() throws IOException
    {
    	int num_communities = 0;    	    	
    	this.control_communities = 1.0;
    	
    	double start_time= System.currentTimeMillis();
    	double current_time = 0.0;

    	Louvain a = new Louvain();  
    	int counter=0;
    	int previous_num_communities = 0;
    	while (true) {
    		++counter;
    		current_time= System.currentTimeMillis();
    		
    		if(Thread.currentThread().isInterrupted())
    		{
    			System.out.format("louvain communities generation interrupted.");
    			return false;
    		}
    		
    		a.init(this.inputFileName,this.control_communities);
    		num_communities = a.louvain();
    		if (num_communities == this.target_communities)
    			break;
    		 		

    		
    		this.control_communities += num_communities > this.target_communities ?  -0.01 : +0.01;	
    		System.out.format("control_communities: %f   num_communities: %d", this.control_communities, num_communities);
    		
    		if (counter == 10)
    		{
    			previous_num_communities = num_communities;
        		if ((previous_num_communities - num_communities) == 0)
        			break;
    			counter = 0;
    		}
    		double tcount = current_time-start_time;
    		double tlimit = 30 * 1000; // 30 seconds
    		System.out.println("\ncurrent_time - start_time "+tcount+"\n");
    		
    		if (tcount > tlimit)  // time limit of 30 seconds
    			break;
    	}
    
    	writeOutputCluster(this.outputFileName,a); 
        System.out.format("control_communities: %f   num_communities: %d", this.control_communities, num_communities);
        return true;
    }
 
	public String getInputFileName() {
		return inputFileName;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public int getTarget_communities() {
		return target_communities;
	}

	public double getControl_communities() {
		return control_communities;
	}  
  
}  