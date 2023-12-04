import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;


public class project1 {
    final static String FILE1 = "input.txt";
    final static String FILE2 = "input2.txt";
    private static Vector<Vector<Integer>> nodeSet1;
    public Vector<Integer> nodeSet2;
    private static boolean []visted; 
    private static int totalNodes = 0; 
    private static int[][] adjacencyMatrix; 


    
	public static void main(String[] args) {
		 solveDFSandBFS(FILE1);
         solveDigjkstra(FILE2); 
	}

	public static void solveDFSandBFS(String filename) {
        System.out.println("1. 그래프 탐방 수행 결과\n");
        int graphnum=0;
        totalNodes=0;

        try(Scanner scan = new Scanner(new File(filename))){
            while(scan.hasNextLine()) {
                String str = scan.nextLine();
                String[] temp  = str.split(" ");
                graphnum++;

                if(temp.length == 1) {
                    initNodeSet1(temp);
                    readEdgeFromEachNode(scan);
                    printDfs(graphnum);
                    initVisit();
                    
                    
                    //dfs구현
                    
                    initVisit();
                    
                    System.out.println("너비 우선 탐색");
                    //bfs 구현
                    System.out.println("======================\n");
                    }
            }

        }catch(FileNotFoundException e) { 
            System.out.println("그래프 정보가 생성되지 않았습니다. \n파일명을 확인하세요");
        }
    }

   	public static void solveDigjkstra(String filename) {
        int graphnum=0;
        totalNodes=0;
        System.out.println("2. 최단 경로 구하기 수행 결과\n");

        try(Scanner scan = new Scanner(new File(filename))){
            while(scan.hasNextLine()) {
                String str = scan.nextLine();
                String[] temp  = str.split(" ");
                adjacencyMatrix = new int[totalNodes+1][totalNodes+1];
                graphnum++;

                if(temp.length == 1) {
                    initNodeSet2(temp, scan);
                    initVisit();
                    System.out.println("그래프 [" + graphnum + "]");
                    //digjkstra구현
                }
            }

        }catch(FileNotFoundException e) { 
            System.out.println("그래프 정보가 생성되지 않았습니다. \n파일명을 확인하세요");
        }
    }

    public static void initNodeSet1(String[] temp){
        totalNodes = Integer.parseInt(temp[0]); 
        visted = new boolean[totalNodes]; 
        nodeSet1 = new Vector<>(totalNodes + 1); 
        for (int i = 0; i <= totalNodes; i++) {
            nodeSet1.add(new Vector<>());
        }
    }

    public static void initNodeSet2(String[] temp, Scanner scan){
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
                adjacencyMatrix[startNode][endNode] = weight;
            }
        }
    }

    public static void readEdgeFromEachNode(Scanner scan){
        String[] temp;
        String str;
          for(int i = 0; i < totalNodes; i++) {
            str = scan.nextLine();
            temp  = str.split(" ");
            int preNode = Integer.parseInt(temp[0]),nextNode;
            for (int j = 1; j < temp.length; j++) {
                nextNode = Integer.parseInt(temp[j]);
                nodeSet1.get(preNode).add(nextNode);
            }
        }
    }

    public static void printDfs(int graphnum){
        System.out.println("그래프 [" + graphnum + "]");
        System.out.println("-----------------------");
        System.out.println("깊이 우선 탐색");
    }

    public static void initVisit(){
        for(int i = 0; i < totalNodes; i++)
            visted[i] = false;
    }
}