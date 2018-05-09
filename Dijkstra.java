/*************************************/
/*            Oscar Lomas            */
/*              CSC 401              */
/*            Spring 2018            */
/*          Final Assignment         */
/*************************************/
import java.util.ArrayList;
import java.util.Stack;
public class Dijkstra{
  // object to hande vertex and all of its connections
  private class Connections{
    public Vertex vertex;
    public ArrayList<Vertex> connected;
    Connections(Vertex v){
      vertex = v;
      connected = new ArrayList<Vertex>();
    }
    public void addConnection(Vertex _v){
      if(connected.size() <= 0){
        connected.add(_v);
        return;
      }
      int i;
      // find the appropiate place to insert so that it is sorted
      // this will avoid performing any other sorting algs
      for(i=0; i < connected.size(); i++){
        if(_v.distance < connected.get(i).distance)
          break;
      }
      connected.add(i, _v);
    }
    public String toString(){
      String s = vertex.id + "=> ";
      for(int i = 0; i < connected.size(); i++){
        s += "(" + connected.get(i).id + ", " + connected.get(i).distance + ")";
      }
      return s;
    }
  }
  private ArrayList<Vertex> _V;
  private ArrayList<Edge> _E;
  private Graph g;
  private Vertex s;
  private Vertex d;
  private boolean complete = false;

  Dijkstra(int amt){
    g = new Graph(amt, complete);
    _V = g.getVertices();
    _E = g.getEdges();
  }
  Dijkstra(int amt, boolean _c){
    complete = _c;
    g = new Graph(amt, complete);
    _V = g.getVertices();
    _E = g.getEdges();
  }
  public void setSource(char _s){
    s = new Vertex(_s);
  }
  public void setDestination(char _d){
    d = new Vertex(_d);
  }
  public void setSource(int _s){
    if(_s < 0 || _s >= _V.size()) {
      System.out.println("Source node " + ((char) 97 + _s) + " does not exist.");
      return;
    }
    s = new Vertex((char) (97 + _s));
  }
  public void setDestination(int _d){
    if(_d < 0 || _d >= _V.size()) {
      System.out.println("Destination node " + ((char) 97 + _d) + " does not exist.");
      return;
    }
    d = new Vertex((char) (97 + _d));
  }
  public void init(){
    int hasStart = findVertex(s); // make sure starting vertex exists
    int hasEnd = findVertex(d); // make sure ending vertex exists
    if(hasStart == -1) {
      System.out.println("Source node " + s.id + " does not exist.");
      return;
    } else if (hasEnd == -1) {
      System.out.println("Source node " + d.id + " does not exist.");
      return;
    }
    // set distance of source node to 0
    _V.get(hasStart).distance = 0;
    System.out.println("Looking for shortest path from " + s + " and " + d);
    runAlg();
  }
  private void runAlg(){
    ArrayList<Connections> prev = new ArrayList<Connections>();
    ArrayList<Vertex> original = new ArrayList<Vertex>(_V); // create a copy of the original vertices
    ArrayList<Vertex> visited = new ArrayList<Vertex>();
    // while we have vertices to check
    while(_V.size() > 0){
      Vertex u = findMin(); // find the minimum vertex
      visited.add(u); // mark vertex as visited
      Connections p = new Connections(u); // create object of for all neighbors of this vertex
      for(int i = 0; i < original.size(); i++){
        Edge e = findEdge(u, original.get(i));
        // make sure u and v are neighbors
        if(e != null){
          // if new distance is lower than the previous distance then update
          if(u.distance + e.getWeight() < original.get(i).distance){
            original.get(i).distance = u.distance + e.getWeight();
          }
          p.addConnection(original.get(i)); // add new connection to the parent vertex
        }
        prev.add(visited.size()-1, p); // add connections to the array lust
      }
      // we are done if the current min vertex is our Destination vertex
      if(u.id == d.id)
        break;
    }
    _V = new ArrayList<Vertex>(original); // restore vertex array
    print(prev);
  }
  private void print(ArrayList<Connections> path){
    int start = findVertex(s);
    int end = findVertex(d);
    if(path == null || path.size() <= 0) {
      System.out.println("no paths found");
      return;
    } else if(start == -1 || end == -1){
      System.out.println("no path from " + s.id + " => " + d.id);
      return;
    } else if (_V.get(end).distance == Integer.MAX_VALUE) {
      System.out.println("Vertex " + d.id + " not reachable");
      return;
    }
    // perform until path has completed
    boolean done = false;
    Vertex _v = _V.get(end);
    int distance = _v.distance;
    Stack<String> S = new Stack<String>(); // stack to hold the path
    S.push(Character.toString(_v.id));
    while(!done){
      // for each vertex look for its neighbors
      for(int i = 0; i < path.size(); i++){
        Connections p = path.get(i);
        // make sure it is the vertex we are looking for
        if(p.vertex.id == _v.id){
          Vertex min = new Vertex((char) 123); // temp min vertex with INFINITY distance
          // find connecting vertex with lowest distance
          for(int j = 0; j < p.connected.size(); j++){
            if(p.connected.get(j).distance < min.distance){
              min = p.connected.get(j);
            }
          }
          _v = min; // set new vertex to find in next iteration
          // add this new minimum vertex id to our output string
          S.push(Character.toString(_v.id));
          // once we find the vertex with distance 0, our source vertex, we can stop
          if(min.distance <= 0){
            done = true;
            break;
          }
        }
      }
    }
    System.out.println("\nPATH FOUND!");
    // pop all vetices from stack and print
    while(!S.empty()){
      if(S.size() <= 1) {
        System.out.print(S.pop());         
      } else {
        System.out.print(S.pop() + "-");
      }
    }
    System.out.println(" with a distance of " + distance);
  }
  // find a vertex in the _V list
  private int findVertex(Vertex _v){
    if(_V == null || _V.size() <= 0)
      return -1;
    for(int i = 0; i < _V.size(); i++){
      if(_V.get(i) != null)  {
        if(_V.get(i).id == _v.id)
          return i;        
      }
    }
    return -1;
  }
  // find edge in _E list
  private Edge findEdge(Vertex u, Vertex v){
    if(_E == null || _E.size() <= 0)
      return null;
    Edge edge = null;
    for(int i = 0; i < _E.size(); i++){
      Edge e = _E.get(i);
      if((e.v_s.id == u.id && e.v_d.id == v.id) ||
        (e.v_d.id == u.id && e.v_s.id == v.id)){
          edge = e;
      }
    }
    return edge;
  }
  // find minimum vertex
  private Vertex findMin(){
    if(_V == null || _V.size() <= 0)
      return null;
    Vertex min = new Vertex((char) 123);
    int index = -1;
    for(int i = 0; i < _V.size(); i++){
      Vertex v = _V.get(i);
      if(v.distance <= min.distance){
        min = v;
        index = i;
      }
    }
    if (index > -1) {
      return _V.remove(index);
    }
    return null;
  }
}
