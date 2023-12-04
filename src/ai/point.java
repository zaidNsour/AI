/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import java.text.DecimalFormat;

/**
 *
 * @author DELL
 */
public class point {
  
  int cluster=-1;
  double[]x;
  int d;
  private static final DecimalFormat df = new DecimalFormat("#.###");
  point(){
  this.d=2;
  }
  point(int d){
  this.d=d;
  x=new double[d];
  }
  double distance(point p){
    if(this.d!=p.d)
      throw new IllegalArgumentException("Dimension of points must be equals!");
    double dist=0;
    for(int i=0;i<d;i++){
      dist+=Math.pow( (this.x[i]-p.x[i]) ,2 );
      }
    dist=Math.sqrt(dist);
    return dist;
    }
  
  @Override
  public String toString(){
   String str="("; 
   for(int i=0;i<d;i++){
     if(i==d-1)
       str+=df.format(x[i]);
     else
       str+=df.format(x[i])+",";
    }
   str+=")";
   return str;
   }
   
  
    
  }
  

