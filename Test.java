import java.util.Random;

public class Test {
    public static void main(String[] args){
        Random random = new Random();
        Heapq hq = new Heapq();

        for(int i = 0; i < 10; i++){
            hq.heapPush(new Path(1, 1, random.nextInt(100)));
        }

        while(!hq.isEmpty()){
            System.out.println(hq.heapPop().weight);
        }
    }
}
