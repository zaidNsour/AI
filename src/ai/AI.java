
package ai;

import java.io.IOException;
import java.util.List;

public class AI {
  
  public static void main(String[] args) throws IOException  {
    
    /*
    List<point> points;
    List<point> c;
    point[]centroids;
    KMeansClustering solver;
    
    ReadFile reader=new ReadFile();
    points=reader.readFromTxt("C:/Users/DELL/Desktop/data.txt");
    c=reader.readFromTxt("C:/Users/DELL/Desktop/centroids.txt");  
    centroids=new point[c.size()];
      for(int i=0;i<c.size();i++)
        centroids[i]=c.get(i);
    
    solver=new KMeansClustering(points,centroids);
    solver.solve();
    solver.displayIterations(); 
    
    */
    
     
    List<point> points;
    KMeansClustering solver;
    ReadFile reader=new ReadFile();
    for(int k=2;k<5;k++){
      System.out.println("K="+k);
      
      points=reader.readFromTxt("C:/Users/DELL/Desktop/data.txt");
      solver=new KMeansClustering(points,k);
       solver.solve();
      solver.displayIterations();
    }
      
      
      
      
     
      
      
      
      
      
      
      
      
    
    
    
     
     
     
   
    
    
     
    
    
    
    
    
  }
  
}
