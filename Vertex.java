/*************************************/
/*            Oscar Lomas            */
/*              CSC 401              */
/*            Spring 2018            */
/*          Final Assignment         */
/*************************************/
public class Vertex{
  public char id;
  public int distance;
  Vertex(char _id){
    id = _id;
    distance = Integer.MAX_VALUE;
  }
  public String toString(){
    return Character.toString(id);
  }
}
