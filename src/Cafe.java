import java.util.HashMap;

public class Cafe {
    private int number;
    private boolean dfsExplored;
    private HashMap<Cafe,Integer> neighbors;
    public Cafe(int number){
        this.number=number;
        neighbors=new HashMap<>();
        dfsExplored=false;
    }
    public void addNeighborCafe(Cafe cafe,int distance){
        if (!neighbors.containsKey(cafe)){
            neighbors.put(cafe,distance);
        }else
            neighbors.replace(cafe,distance);
    }

    public HashMap<Cafe, Integer> getNeighbors() {
        return neighbors;
    }

    public void setDfsExplored(boolean dfsExplored) {
        this.dfsExplored = dfsExplored;
    }

    public boolean isDfsExplored() {
        return dfsExplored;
    }

    public int number() {
        return number;
    }
}
