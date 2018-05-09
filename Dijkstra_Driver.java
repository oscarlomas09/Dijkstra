/*************************************/
/*            Oscar Lomas            */
/*              CSC 401              */
/*            Spring 2018            */
/*          Final Assignment         */
/*************************************/
import java.util.Scanner;
public class Dijkstra_Driver{
  public static void main(String[] args){
    int v_amt = getAmount();
    if(v_amt <= 1) {
      System.out.println("Need to have at least two nodes.");
    } else if(v_amt > 26) {
      System.out.println("Number of nodes cannot exceed 26 [a-z].");    
    } else {
      System.out.println();
      boolean c = fullyConnected(); // is the graph fully connected?
      System.out.println();
      Dijkstra d;
      if(c) {  
        d = new Dijkstra(v_amt, c);
      } else {
        d = new Dijkstra(v_amt);
      }
      char source = getSource(v_amt); // get staring node
      char destination = getDestination(v_amt); // get destination node
      d.setSource(source);
      d.setDestination(destination);
      System.out.println();
      d.init(); // run algorithm
    }
  }
  public static boolean fullyConnected(){
    System.out.println("Do you want a fully connected graph? [y|n]");
    Scanner s = new Scanner(System.in);
    if(s.hasNext()){
      char c = s.next().charAt(0);
      if(c == 'y'){
        System.out.println("=>creating fully connected graph...");    
        return true;
      }
    } else {
      System.out.println("=>creating disconnected graph...");  
      return false;
    }
    System.out.println("=>creating disconnected graph...");  
    return false;
  }
  public static char getSource(int amt){
    System.out.println("\nWhat will be your source node?");
    Scanner s = new Scanner(System.in);
    if(s.hasNext()){
      char c = s.next().charAt(0);
      if((int) c < 97 || (int) c > (97 + amt)){
        System.out.println("\tSource node " + c + " out of range.");
        getSource(amt);
      }
      return c;
    } else{
      return getSource(amt);
    }
  }
  public static char getDestination(int amt){
    System.out.println("\nWhat will be your destination node?");
    Scanner s = new Scanner(System.in);
    if(s.hasNext()){
      char c = s.next().charAt(0);
      if((int) c < 97 || (int) c > (97 + amt)){
        System.out.println("\tdestination node " + c + " out of range.");
        getDestination(amt);
      }
      return c;
    } else{
      return getSource(amt);
    }
  }
  public static int getAmount(){
    Scanner s = new Scanner(System.in);
    System.out.println("How many nodes in the graph?");
    if(s.hasNextInt()){
      return s.nextInt();
    } else{
      return getAmount();
    }
  }
}
