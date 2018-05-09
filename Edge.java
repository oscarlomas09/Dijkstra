/*************************************/
/*            Oscar Lomas            */
/*              CSC 401              */
/*            Spring 2018            */
/*          Final Assignment         */
/*************************************/
public class Edge{
  public Vertex v_s;
  public Vertex v_d;
  private int weight;
  Edge(Vertex _s, Vertex _d, int _w){
    v_s = _s;
    v_d = _d;
    weight = _w;
  }
  public void setWeight(int _w){
    weight = _w;
  }
  public int getWeight(){
    return weight;
  }
  public String toString(){
    return "{" + Character.toString(v_s.id) + ", " + Character.toString(v_d.id) + ", " + weight + "}";
  }
}
