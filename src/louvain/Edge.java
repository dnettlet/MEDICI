package louvain;
public class Edge implements Cloneable{  
    int v;
    double weight;  
    int next; 
    Edge(){}  
    public Object clone(){  
        Edge temp=null;  
        try{    
            temp = (Edge)super.clone();
        }catch(CloneNotSupportedException e) {    
            e.printStackTrace();    
        }     
        return temp;  
    }  
}  