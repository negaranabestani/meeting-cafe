import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class City {
    private ArrayList<Cafe> cafes=new ArrayList<>();
    private final static Stack<Cafe>dfsStack=new Stack<>();
    private final HashMap<Cafe,Integer> visitedInDijkstra=new HashMap<>();
    private final HashSet<Cafe> finishedInDijkstra=new HashSet<>();
    public void addStreet(int cafe1,int cafe2,int streetLength){
        addCafe(cafe1);
        addCafe(cafe2);
        Cafe c1=findCafe(cafe1);
        Cafe c2=findCafe(cafe2);
        if (c1!=null&&c2!=null) {
            c1.addNeighborCafe(c2, streetLength);
            c2.addNeighborCafe(c1, streetLength);
        }

    }
    private boolean isPlaced(int number){
        for (Cafe c:cafes){
            if (c.number()==number)
                return true;
        }
        return false;
    }
    private void addCafe(int number){
        if (!isPlaced(number)){
            cafes.add(new Cafe(number));
        }
    }
    public Cafe findCafe(int cafe){
        for (Cafe c:cafes){
            if (c.number()==cafe)
                return c;
        }
        return null;
    }
    public void dfsTravel(Cafe start){
        if (!start.isDfsExplored())
                dfsStack.push(start);
        start.setDfsExplored(true);
        //System.out.println(start.number());
        int untraveledNeighbors=0;
            for (Cafe c : start.getNeighbors().keySet()) {
                if (!c.isDfsExplored()) {
                   dfsTravel(c);
                    untraveledNeighbors++;
                }
            }

        if (untraveledNeighbors==0){
            System.out.print(start.number()+" ");
            dfsStack.pop();
            if (!dfsStack.isEmpty()) {
                dfsTravel(dfsStack.peek());
            }
        }
    }
    private void dijkstra(Cafe start,Cafe prev) {
        //System.out.println(start.number());
        if (visitedInDijkstra.isEmpty()) {
            visitedInDijkstra.put(start, 0);
        }

            for (Cafe c : start.getNeighbors().keySet()) {
                if (!finishedInDijkstra.contains(c)) {
                    if (!visitedInDijkstra.containsKey(c)) {
                       // System.out.println("put:"+c.number()+","+start.number());
                        visitedInDijkstra.put(c, visitedInDijkstra.get(start) + c.getNeighbors().get(start));
                    } else if (visitedInDijkstra.get(c) > visitedInDijkstra.get(start) + c.getNeighbors().get(start)) {
                       // System.out.println("rep:"+c.number());
                        visitedInDijkstra.replace(c, visitedInDijkstra.get(start) + c.getNeighbors().get(start));
                    }
                }
            }
            finishedInDijkstra.add(start);
        Cafe next=prev;
        if (prev==null)
            next=start;
        int count=0;
        for (Cafe c : next.getNeighbors().keySet()) {
            if (!finishedInDijkstra.contains(c)) {
                    dijkstra(c,start);
                    count++;
            }
        }
        if (count==0){
            for (Cafe c : start.getNeighbors().keySet()) {
                if (!finishedInDijkstra.contains(c)) {
                    dijkstra(c,start);
                    count++;
                }
            }
        }


    }
    private void print(){
        for (Cafe c: visitedInDijkstra.keySet()){
            System.out.println(c.number()+":"+visitedInDijkstra.get(c));
        }
    }
    private float calculateAvrDisOfCafe(ArrayList<Integer> attenders,Cafe target){
        visitedInDijkstra.clear();
        finishedInDijkstra.clear();
        dijkstra(target,null);
        //System.out.println("targ:"+target.number());
//        print();
        float diffDis=0,counter=0;
        int dis;
        for (int i=0;i<attenders.size();i++){
            //System.out.println(findCafe(attenders.get(i)).number());
            dis=visitedInDijkstra.get(findCafe(attenders.get(i)));
            for (int j=i+1;j<attenders.size();j++){
                counter++;
                //System.out.println(findCafe(j).number());
                //System.out.println(dis+"-"+visitedInDijkstra.get(findCafe(attenders.get(j))));
                diffDis+=Math.abs(dis-visitedInDijkstra.get(findCafe(attenders.get(j))));
            }
        }

        diffDis/=counter;
        return diffDis;
    }
    public Cafe findBestCafe(ArrayList<Integer> attenders){

        Cafe bestCafe=findCafe(0);
        float bestDis=Float.MAX_VALUE;
        float targetDis;
        if (attenders.size()==1)
            return findCafe(attenders.get(0));
        for (Cafe target : cafes) {
            targetDis = calculateAvrDisOfCafe(attenders, target);
           // System.out.println("final "+target.number()+":"+targetDis);
            if (bestDis > targetDis) {
                bestCafe = target;
                bestDis=targetDis;
                //System.out.println(bestCafe.number());
            }
        }

        return bestCafe;
    }

    public ArrayList<Cafe> getCafes() {
        return cafes;
    }
}
