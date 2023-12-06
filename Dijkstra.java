import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;



public class Dijkstra {
    final static String FILE = "input2.txt";
    private static int totalNodes = 0;
    private static int[][] adjacencyMatrix;
    public static void solveDijkstra(String filename) {
        int graphnum=0;
        totalNodes=0;
        System.out.println("2. 최단 경로 구하기 수행 결과\n");

        try(Scanner scan = new Scanner(new File(filename))){
            while(scan.hasNextLine()) {
                String str = scan.nextLine();
                String[] temp  = str.split(" ");
                graphnum++;

                if(temp.length == 1) {
                    totalNodes = Integer.parseInt(temp[0]);
                    adjacencyMatrix = new int[totalNodes][totalNodes];
                    initNodeSet2(scan);
                    System.out.println("그래프 [" + graphnum + "]");
                    System.out.println(totalNodes);
                    System.out.println();
                    //digjkstra구현
                    Dijkstra();
                }
            }

        }catch(FileNotFoundException e) {
            System.out.println("그래프 정보가 생성되지 않았습니다. \n파일명을 확인하세요");
        }
    }

    public static void initNodeSet2(Scanner scan){
        int startNode, endNode, weight;
        for (int i = 0; i < totalNodes; i++) {
            Arrays.fill(adjacencyMatrix[i], Integer.MAX_VALUE);
        }
        for (int i = 1; i <= totalNodes; i++) {
            String[] tokens = scan.nextLine().split(" ");
            startNode = Integer.parseInt(tokens[0]);

            for (int j = 1; j < tokens.length - 1; j += 2) {
                endNode = Integer.parseInt(tokens[j]);
                weight = Integer.parseInt(tokens[j + 1]);
                adjacencyMatrix[startNode-1][endNode-1] = weight;
                adjacencyMatrix[endNode-1][startNode-1] = weight;
            }
        }
    }
    public static void Dijkstra(){
        int[] d_min = new int[totalNodes];  // d_min 배열
        Arrays.fill(d_min, Integer.MAX_VALUE);
        Heapq hq = new Heapq();
        ArrayList<Integer>[] totalPath = new ArrayList[totalNodes];
        for(int i = 0; i < totalNodes; i++){
            totalPath[i] = new ArrayList<>();
        }
        d_min[0] = 0;
        //  시작노드가 1이므로 1에 인접한 엣지들을 힙큐에 모두 넣어줌
        for(int i = 0; i < totalNodes; i++){
            if(adjacencyMatrix[0][i] != Integer.MAX_VALUE){
                hq.heapPush(new Path(1, i + 1, adjacencyMatrix[0][i]));
            }
        }
        while(!hq.isEmpty()){
            Path path = hq.heapPop();
            if(d_min[path.endNode - 1] < Integer.MAX_VALUE){
                if(d_min[path.endNode - 1] == path.weight){
                    totalPath[path.endNode - 1].add(path.startNode);
                }else{
                    // Ignore
                }
            }else{
                totalPath[path.endNode - 1].add(path.startNode);
                d_min[path.endNode - 1] = path.weight;
                for(int i = 0; i < totalNodes; i++){
                    if(adjacencyMatrix[path.endNode - 1][i] != Integer.MAX_VALUE){
                        hq.heapPush(new Path(path.endNode, i + 1,d_min[path.endNode - 1] + adjacencyMatrix[path.endNode - 1][i]));
                    }
                }
            }
        }
        for(int i = 0; i < totalNodes; i++){
            System.out.println(d_min[i]);
        }

    }

    public static void main(String[] args){
        solveDijkstra(FILE);
    }
}
