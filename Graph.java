/*************************************/
/*            Oscar Lomas            */
/*              CSC 401              */
/*            Spring 2018            */
/*          Final Assignment         */
/*************************************/
import java.util.ArrayList;
import java.util.Random;
class Graph{
  private ArrayList<Vertex> V;
  private ArrayList<Edge> E;
  private boolean connected = false;
  Graph(int v_amt){   
    createVertices(v_amt);
    connectVertices();
    printVertices();
    printEdges();
  }
  Graph(int v_amt, boolean _connected){
    connected = _connected;
    createVertices(v_amt);
    connectVertices();
    printVertices();
    printEdges();
  }
  // create vertices
  private void createVertices(int amt){
    V = new ArrayList<Vertex>();
    int i = 0;
    while(i < amt){
      char letter = (char) (i+97);
      Vertex v = new Vertex(letter);
      V.add(v);
      i++;
    }
  }
  private void connectVertices(){
    E = new ArrayList<Edge>();
    double p = 3; //30% probability

    if(V == null || V.size() <= 0)
      return;
    
    Random rand = new Random();
    int weight = 1;
    for(int i = 0; i < V.size(); i++){
      for(int j = 0; j < V.size(); j++){
        // for each vertex and all other vertex
        if(i != j){
          if(V.get(i) != null && V.get(j) != null){
            weight = rand.nextInt(9) + 1;
            Edge e = new Edge(V.get(i), V.get(j), weight);
            if(!edgeExists(e)){
              // check previous options: Fully connected then probability of edge is 1, else use previous p
              if(connected) {
                E.add(e);
              } else {
                if(rand.nextInt(9) < p){
                  E.add(e);
                }
              }
            }
          }
        }
      }
    }
    // make sure some edges were created
    if(V.size() <= 0 || V == null)
      connectVertices();

    // connect first and last node
    Vertex first = V.get(0);
    Vertex last = V.get(V.size()-1);
    weight = rand.nextInt(9) + 1;
    Edge e = new Edge(first, last, weight);
    if(!edgeExists(e)){
      // check previous options: Fully connected then probability of edge is 1, else use previous p
      if(connected) {
        E.add(e);
      } else {
        if(rand.nextInt(9) < p){
          E.add(e);
        }
      }
    } 
  }
  public void printVertices(){
    System.out.print("Vertices: (");
    for(int i = 0; i < V.size(); i++){
      if(i < V.size()-1) {
        System.out.print(V.get(i).id + ", ");
      } else {
        System.out.print(V.get(i).id);
      }
    }
    System.out.println(")");    
  }
  public void printEdges(){
    System.out.print("Edges: (");
    for(int i = 0; i < E.size(); i++){
      Edge e = E.get(i);
      if(i < E.size()-1) {
        System.out.print("{" + e.v_s.id + ", " + e.v_d.id + ", " + e.getWeight() + "}, ");
      } else {
        System.out.print("{" + e.v_s.id + ", " + e.v_d.id + ", " + e.getWeight() + "}");
      }
    }
    System.out.println(")");   
  }
  private boolean edgeExists(Edge edge){
    if(E == null || E.size() <= 0)
      return false;
    for(int i = 0; i < E.size(); i++){
      Edge e = E.get(i);
      if(e != null && edge != null){
        if((e.v_s.id == edge.v_s.id && e.v_d.id == edge.v_d.id) ||
          (e.v_d.id == edge.v_s.id && e.v_s.id == edge.v_d.id))
           return true;
      }
    }
    return false;
  }
  public ArrayList<Edge> getEdges(){
    return E;
  }
  public ArrayList<Vertex> getVertices(){
    return V;
  }
}
