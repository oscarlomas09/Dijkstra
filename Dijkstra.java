import java.util.ArrayList;
import java.util.Stack;
public class Dijkstra{
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
    s = new Vertex((char) 97);
    d = new Vertex((char) (97+(amt-1)));
    init();
  }
  Dijkstra(int amt, int start, int end){
    if(start < 0 || start >= amt) {
      System.out.println("Source node " + ((char) 97 + start) + " does not exist.");
      return;
    } else if(end < 0 || end >= amt) {
      System.out.println("Destination node " + ((char) 97 + end) + " does not exist.");
      return;
    }
    g = new Graph(amt, complete);
    _V = g.getVertices();
    _E = g.getEdges();
    s = new Vertex((char) (97 + start));
    d = new Vertex((char) (97+ end));
    init();
  }
  Dijkstra(int amt, int start, int end, boolean _c){
    complete = _c;
    if(start < 0 || start >= amt) {
      System.out.println("Source node " + ((char) 97 + start) + " does not exist.");
      return;
    } else if(end < 0 || end >= amt) {
      System.out.println("Destination node " + ((char) 97 + end) + " does not exist.");
      return;
    }
    g = new Graph(amt, complete);
    _V = g.getVertices();
    _E = g.getEdges();
    init();
  }
  private void init(){
    int hasStart = findVertex(s);
    int hasEnd = findVertex(d);
    if(hasStart == -1) {
      System.out.println("Source node " + s.id + " does not exist.");
      return;
    } else if (hasEnd == -1) {
      System.out.println("Source node " + d.id + " does not exist.");
      return;
    }
    // set distance of source node to 0
    _V.get(hasStart).distance = 0;
    runAlg();
  }
  private void runAlg(){
    ArrayList<Connections> prev = new ArrayList<Connections>();
    ArrayList<Vertex> original = new ArrayList<Vertex>(_V); // create a copy of the original vertices
    ArrayList<Vertex> visited = new ArrayList<Vertex>();
    while(_V.size() > 0){
      Vertex u = findMin();
      visited.add(u);
      Connections p = new Connections(u);
      for(int i = 0; i < original.size(); i++){
        Edge e = findEdge(u, original.get(i));
        if(e != null){
          if(u.distance + e.getWeight() < original.get(i).distance){
            original.get(i).distance = u.distance + e.getWeight();
          }
          p.addConnection(original.get(i));
        }
        prev.add(visited.size()-1, p);
      }
      if(u.id == d.id)
        break;
    }
    _V = new ArrayList<Vertex>(original);
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
    Stack<String> S = new Stack<String>();
    S.push(Character.toString(_v.id));
    while(!done){
      for(int i = 0; i < path.size(); i++){
        Connections p = path.get(i);
        if(p.vertex.id == _v.id){
          // find connecting vertex with lowest distance
          Vertex min = new Vertex((char) 123);
          for(int j = 0; j < p.connected.size(); j++){
            if(p.connected.get(j).distance < min.distance){
              min = p.connected.get(j);
            }
          }
          _v = min;
          S.push(Character.toString(_v.id));
          if(min.distance <= 0){
            done = true;
            break;
          }
        }
      }
    }
    while(!S.empty()){
      System.out.print(S.pop() + "-");
    }
    System.out.println(" with a distance of " + distance);
  }
  private int findVertex(Vertex _v){
    for(int i = 0; i < _V.size(); i++){
      if(_V.get(i) != null)  {
        if(_V.get(i).id == _v.id)
          return i;        
      }
    }
    return -1;
  }
  private Edge findEdge(Vertex u, Vertex v){
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
  private Vertex findMin(){
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