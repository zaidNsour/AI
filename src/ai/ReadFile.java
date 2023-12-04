
package ai;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ReadFile {
  BufferedReader br;
  List<point>points;
  
  
  
  public  List<point> readFromTxt(String path) throws FileNotFoundException, IOException{
    points=new ArrayList<>();
    br=new BufferedReader(new FileReader(path));
    String line=br.readLine();
    while(line!=null){  
      String[]record=line.split(" ");
      int d=record.length;
      point p=new point(d);
      for(int i=0;i<d;i++){
        p.x[i]=Double.parseDouble(record[i]);
        }
     points.add(p);
     line=br.readLine();
      }
    br.close();
    return points;
    
    }
  
  
}
