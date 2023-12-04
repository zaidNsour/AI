package ai;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class KMeansClustering {
   
int k,d,m;
List<point>points;
point[]centroid;
int[]centroidSize;
double[][]sumX;
ArrayList<point>[]clusters;
boolean solved;
List<point[]>centroidsList;
List<ArrayList<point>[]>clustersList;
List<Double>averageScores;
private static final DecimalFormat df = new DecimalFormat("#.###");

public KMeansClustering(List<point>points,point[]centroid){
  this.k=centroid.length;
  this.centroid=centroid;
  setup(points);
}

public KMeansClustering(List<point>points,int k){
  if(k==1)
    throw new IllegalArgumentException("k must be greater than 1");
  this.k=k;   
  setup(points);
  pickRandomCentroidFromPoints();
}

private void setup(List<point>points){
   this.m=points.size();
   this.points=points;
   clusters = new ArrayList[k];
   for(int i=0;i<k;i++)
      clusters[i]=new ArrayList<>();
  this.d=points.get(0).d;
  this.sumX=new double[k][d];
  this.centroidSize=new int[k];
  solved=false;
  this.centroidsList=new ArrayList<>();
  this.clustersList=new ArrayList<>();
  this.averageScores=new ArrayList<>();
  }

private void pickRandomCentroidFromPoints(){
  centroid=new point[k];
  List<Integer>numbers=new ArrayList<>();
  for(int i=0;i<m;i++)
    numbers.add(i);
  Collections.shuffle(numbers);
  for(int i=0;i<k;i++)
    centroid[i]=copyOf( points.get( numbers.get(i) ) );
  }

private point copyOf(point p){
  point q=new point(d);
  for(int i=0;i<d;i++)
    q.x[i]=p.x[i];
  q.cluster=-1;
  return q;
  } 

public void solve(){
  
  while(solved==false){ 
    solved=true;
    for(point p:points){
      double minDist=Double.POSITIVE_INFINITY;
      int minIndex=-1;
      for(int i=0;i<k;i++){
        if( distance(p,centroid[i])<minDist ){
          minDist=distance(p,centroid[i]);
          minIndex=i;
          }
        }
      if(p.cluster==-1 || p.cluster!=minIndex){
        solved=false;
        p.cluster=minIndex;
        }
      }
    if(solved==false){
      updateCentroidsList();
      updateCentroid();
      findClusters();
      updateClustersList();
      averageScores.add(averageSilhouetteScore());
    }
    else
      centroid=centroidsList.get(centroidsList.size()-1); 
    }
  }

private void updateCentroid(){
  for(point p:points){
    centroidSize[p.cluster]++;
    for(int i=0;i<d;i++){
      sumX[p.cluster][i]+=p.x[i];
      }
    }
  for(int i=0;i<k;i++)
    for(int j=0;j<d;j++){
      centroid[i].x[j]=(sumX[i][j]/centroidSize[i]);
      } 
  }

private void updateCentroidsList(){
  point[]temp=new point[k];
  for(int i=0;i<k;i++){
    temp[i]=new point(d);
    for(int j=0;j<d;j++){
      
     temp[i].x[j]=centroid[i].x[j];
    }
  } 
  centroidsList.add(temp);
  }

private void updateClustersList(){
   ArrayList<point>[]temp= new ArrayList[k];
   for(int i=0;i<k;i++){
    temp[i]=(ArrayList<point>) clusters[i].clone();
      
    }
  
  clustersList.add(temp);
  }

public ArrayList<point>[]clusters(){
  return clusters; 
  }

private void findClusters(){
  for(int i=0;i<k;i++)
    clusters[i].clear();
  for(point p:points)
    clusters[p.cluster].add(p); 
  }

public double averageSilhouetteScore(){
  double score=0;
  for(point p:points)
    score+= silhouetteScore(p);
  score=score/m;
  return score;
  }

public double silhouetteScore(point x){
    
   return ( b(x)-a(x) )/Math.max( b(x),a(x) );
  }

double a(point x){
  double a=0;  
  for(point p:clusters[x.cluster]){
    a+=distance(x,p);
    }
  a=a/clusters[x.cluster].size();
  return a;
}
double b(point x){
   int nearCluster=-1;
   double b=0,minDist=Double.POSITIVE_INFINITY,dist=0;
   
   /*
  for(int i=0;i<k;i++){
    if(i==x.cluster) continue;
    dist=distance(x,centroid[i]); //calc the distance between point and each centroid 
    if(dist<minDist){ 
      nearCluster=i;
      minDist=dist;
      }
    }
    */
   
   for(int i=0;i<k;i++){
    if(i==x.cluster) continue;
    
    for(point p:clusters[i]){
      dist+=distance(x,p);
      }
    dist=dist/clusters[i].size(); //calc the average distance between point and each cluster points
    if(dist<minDist){
      nearCluster=i;
      minDist=dist;
      }
    }
   for(point p:clusters[nearCluster]){
    b+=distance(x,p);
    }
   b=b/clusters[nearCluster].size();
   return b;
}

public void printClusters(){
  int count=0;
  for(int i=0;i<clusters.length;i++){
    System.out.println("cluster "+count+": "+clusters[i]);
    count++;
    }
  }

public void printCentroid(){
  int count=0;
  for(point p:centroid){
    System.out.print("centroid "+count+":"+p+" ");
    count++;
    }
  System.out.println("");
  } 

public void displayIterations(){ //ArrayList<ArrayList<point>[]>clustersList;
  
  for(int i=0;i<centroidsList.size();i++){
    System.out.println("Iteration "+i+": ");
    for(int j=0;j<k;j++){
      System.out.println( "  cluster"+j+": \n    centroid:"+ centroidsList.get(i)[j] +
      "\n    points:"+clustersList.get(i)[j] );
      }
    System.out.println("\naverage silhouetteScore: "+ df.format( averageScores.get(i) ) );
    System.out.println("");
    }
  }

private double distance(point a,point b){
  return a.distance(b);
  }

}