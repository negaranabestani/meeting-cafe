import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class InputManager implements Runnable {
    private Scanner c=new Scanner(System.in);
    private City city=new City();
    private HashSet<Integer> attenders=new HashSet<>();
    private int edgeNum;
    private void initGraph(){
        c.nextInt();
        edgeNum=c.nextInt();
        c.nextLine();
        c.nextLine();
        for (int i=0;i<edgeNum;i++){
            inputEdge(c.nextInt(),c.nextInt(),c.nextInt());
            c.nextLine();
        }
    }
    private void inputEdge(int n1,int n2,int l){
        city.addStreet(n1,n2,l);
    }
    private void commandManager(String command,int value){
        //System.out.println(command);
        if (command.equals("test"))
            city.dfsTravel(city.getCafes().get(0));
        else if (command.equals("join")) {
            attenders.add(value);
            System.out.println(city.findBestCafe(new ArrayList<>(attenders)).number());
        }
        else if (command.equals("left")) {
            attenders.remove((Integer) value);
            System.out.println(city.findBestCafe(new ArrayList<>(attenders)).number());
        }

    }
    @Override
    public void run() {
        int value;
        initGraph();
        String[] command={" "," "};
        while (!command[0].equals("exit")){
            command=c.nextLine().split(" ");
            if (command.length!=2)
                value=0;
            else
                value=Integer.parseInt(command[1]);
            commandManager(command[0],value);
        }

    }
}
