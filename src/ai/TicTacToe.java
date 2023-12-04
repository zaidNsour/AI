package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class TicTacToe {
  
class State{
int[][]board=new int[3][3];
int stateCount=0,lastAction=-1,utility=0;

State copy(){
State s=new State();
for(int i=0;i<3;i++)
  System.arraycopy(this.board[i], 0, s.board[i], 0, 3);
s.stateCount=this.stateCount;
s.lastAction=this.lastAction;
s.utility=this.utility;
  return s;
}

} 

final static int MIN=-1,MAX=1;
State currentState;
int human,pc;
TicTacToe(boolean isHumanFirst){
  if(isHumanFirst){
    human=MAX;
    pc=MIN;
    }
  else{
    human=MIN;
    pc=MAX;
    }
  currentState=new State();
  }  

public void play(int a){
  if( terminate(currentState) || !actions(currentState).contains(a) )
    return;
  currentState=result(currentState,a);
  }

int getActionFromUser(){
  Scanner input=new Scanner(System.in);
  int a;
  while(true){
    a=input.nextInt();
    if( actions(currentState).contains(a) )
      break;
    else
      System.out.println("please insert valid action!");
    }
  return a;
  }

int getActionFromPc(){
  
  if(pc==MIN)
    return minAction(currentState);
  else
    return maxAction(currentState);
  }
int minAction(State s){
  int value=Integer.MAX_VALUE;
  int optimalAction=-1;
  for(int a:actions(s)){
    int result=maxValue( result(s,a) );
    if(result<value){
      value=result;
      optimalAction=a;
      } 
    }
  return optimalAction;
  }

int maxAction(State s){
  int value=Integer.MIN_VALUE;
  int optimalAction=-1;
  for(int a:actions(s)){
    int result=minValue( result(s,a) );
    if(result>value){
      value=result;
      optimalAction=a;
      } 
    }
  return optimalAction;
  }

int minValue(State s){
  if(terminate(s))
    return utility(s);
  int value=Integer.MAX_VALUE;
  for(int a:actions(s))
    value=Math.min(value,maxValue( result(s,a) ) );
  return value; 
  }

int maxValue(State s){
  if(terminate(s))
    return utility(s);
  int value=Integer.MIN_VALUE;
  for(int a:actions(s))
    value=Math.max(value,minValue( result(s,a) ) );
  return value; 
  }

int utility(State s){
  return s.utility;
  }

int utility(){
  return currentState.utility;
  }

int player(State s){
  if(s.stateCount%2==0)
    return MAX;
  else
    return MIN;
  }

int currentPlayer(){
  return player(currentState);
  }

List<Integer>actions(State s){ //we can optimize this
  List<Integer>actions=new ArrayList<>();
  for(int i=0;i<3;i++)
    for(int j=0;j<3;j++)
      if(s.board[i][j]==0)
        actions.add( (i*3) +j);//action value will be from 0 to 8
  return actions;  
  }

State result(State s,int a){
  State newState=s.copy();
  int i=-1,j;
  if(a<3) 
    i=0;
  else if(a<6)
    i=1;
  else if(a<9)
    i=2;
  
  j=a-(3*i);
  newState.stateCount++;
  newState.board[i][j]=player(s);
  newState.lastAction=a;
  return newState;
  }

boolean terminate(State s){
  int a=s.lastAction;
  if(a==-1)
    return false;
  
  int i=-1,j=-1;
  if(a<3) 
    i=0;
  else if(a<6)
    i=1;
  else if(a<9)
    i=2;
  
  j=a-(3*i);
  
  if( ( i==j&&checkDiagonal(s,0) ) || (i==2-j&&checkDiagonal(s,1)) || checkRow(s,i) || checkCol(s,j) ){
    s.utility=s.board[i][j];
    return true;
    }
  else return s.stateCount==9;
  }

boolean terminate(){
return terminate(currentState);
}

boolean checkCol(State s,int c){
  int a=s.lastAction;
  int row=getRowFromAction(a);
  int col=a-(3*row);
  int count=0,val=s.board[row][col];
  if(val==0)
    return false;
  
  for(int i=0;i<3;i++)
    if(s.board[i][c]==val) count++;
  return (count==3);
  }

boolean checkRow(State s,int r){
   int a=s.lastAction;
  int row=getRowFromAction(a);
  int col=a-(3*row);
  int count=0,val=s.board[row][col];
  if(val==0)
    return false;
  
  for(int j=0;j<3;j++)
    if(s.board[r][j]==val) count++;
  return (count==3);
  }

boolean checkDiagonal(State s,int di){
  
  
   int a=s.lastAction;
  int row=getRowFromAction(a);
  int col=a-(3*row);
  
  int count=0,val=s.board[row][col];
  if(val==0)
    return false;
  
  if(di==0){
    for(int i=0;i<3;i++)
      if(s.board[i][i]==val) count++;
    }
  else if(di==1){
    for(int i=0;i<3;i++)
      if(s.board[i][2-i]==val) count++;  
    }
  return (count==3);
  }

int getRowFromAction(int a){
  int r=-1;
  if(a<3) 
    r=0;
  else if(a<6)
    r=1;
  else if(a<9)
    r=2;
  return r;
  }

public void print(){
  for(int i=0;i<3;i++){
    for(int j=0;j<3;j++){
      switch (currentState.board[i][j]) {
        case MAX:
          System.out.print("X"+"  ");
          break;
        case MIN:
          System.out.print("O"+"  ");
          break;
        default: 
          System.out.print("-"+"  ");
          
          break;
        }
      }
      System.out.println("");
    }
  
  }


public void printResult(){
    if(currentState.utility==human)
        System.out.println("You win!");
    else if(currentState.utility==pc)
        System.out.println("You lose!");
    else
        System.out.println("Draw");
      
    }




  
}

 /*
      TicTacToe game=new TicTacToe(true);
      while(true){
      if(game.terminate()){
      game.print();
      game.printResult();
      break;
      }
      else{
      game.print();
      int a=-1;
      if(game.currentPlayer()==game.human){
      System.out.println("your turn");
      a=game.getActionFromUser();
      }
      else if(game.currentPlayer()==game.pc){
      System.out.println("pc turn");
      a=game.getActionFromPc();
      }
      game.play(a);
      }
      }
      
      game.play(2);
      int a=game.getActionFromPc();
      System.out.println(a);
      
      
      System.out.println(game.currentState.stateCount);
      System.out.println(game.currentPlayer());
      System.out.println(game.terminate());
      System.out.println(game.currentState.utility);
      game.print();
      game.printResult();
      */