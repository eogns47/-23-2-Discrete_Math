import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class DfsAndBfs {

    public static void solveDfsandBfs(){
        System.out.println("1. 그래프 탐방 수행 결과\n");
        boolean[][] graph = null; // 부울 행렬로 그래프 인접행렬 표현
        boolean []visted; // 해당 노드의 방문 여부 저장하는 배열
        int graphnum=0; //몇 번째 그래프인지
        String File = "input1.txt";

        try(Scanner scan = new Scanner(new File(File))){
            while(scan.hasNextLine()) {
                String str = scan.nextLine();
                String[] temp  = str.split(" ");
                graphnum++; //그래프 하나 스캔할 때마다 그래프 넘버 증가
                int node_num = Integer.parseInt(temp[0]);; //그래프의 노드(정점)의 개수
                if(temp.length == 1) { //그래프 노드 정보는 숫자 한개만 존재
                    graph = new boolean[node_num][node_num]; //그래프 생성
                    visted = new boolean[node_num]; //깊이와 너비 우선 탐색에 쓰일 배열 선언
                    for(int i=0; i<node_num; i++){ // 인접 행렬 초기화
                        for(int j=0; i<node_num; i++){
                            graph[i][j] = false;
                        }
                    }
                    for(int i=0; i<node_num; i++){ //노드의 개수만큼 파일을 읽으며 그래프 정보 추가
                        str = scan.nextLine();
                        temp = str.split(" ");
                        for(int j=0; j<temp.length; j++){
                            graph[Integer.parseInt(temp[0]) - 1][Integer.parseInt(temp[j]) -1] = true;
                            graph[Integer.parseInt(temp[j]) -1][Integer.parseInt(temp[0]) - 1] = true;
                        }
                    }
                    System.out.println("그래프 ["+graphnum+"]");
                    System.out.println("-----------------------");
                    System.out.println("깊이 우선 탐색");
                    for(int i=0; i<visted.length; i++){ //DFS를 위한 노드 방문 정보 초기화
                        visted[i] = false;
                    }
                    dfs(graph, 0, visted);
                    System.out.println();
                    System.out.println("너비 우선 탐색");
                    for(int i=0; i<visted.length; i++){ //BFS를 위한 노드 방문 정보 초기화
                        visted[i] = false;
                    }
                    bfs(graph, 0, visted);
                    System.out.println();
                    System.out.println("=======================\n");
                }
            }

        }catch(FileNotFoundException e) {
            System.out.println("그래프 정보가 생성되지 않았습니다. \n파일명을 확인하세요");
        }
    }

    public static void dfs(boolean[][] graph, int node, boolean []visted) {
        visted[node] = true; //노드 방문 처리
        int check = checkCount(visted);
        if(check == visted.length){
            System.out.print((node+1));
        }else {
            System.out.print((node + 1) + " - "); //그래프 상에서 노드는 인덱스가 0으로 시작하기 때문
        }
        for(int i = 0; i < graph.length; i++) {
            if(graph[node][i]) { //노드의 인접 정보가 있을 때만 체크
                if(!visted[i]) { //방문하지 않은 노드면
                    dfs(graph, i, visted); //DFS 수행
                }
            }
        }
    }

    public static void bfs(boolean[][] graph, int startNode, boolean[] visited) {
        ArrayList<Integer> visitList = new ArrayList<>(); // 방문할 노드 리스트
        visitList.add(startNode); // 시작 노드 추가

        while (!visitList.isEmpty()) {
            int node = visitList.remove(0); // 리스트에서 첫 번째 노드 제거 및 반환

            if (!visited[node]) {
                visited[node] = true; // 노드 방문 처리
                int check = checkCount(visited);
                if(check == visited.length){
                    System.out.print((node+1));
                }else {
                    System.out.print((node + 1) + " - "); // 노드 출력 (인덱스를 1로 조정)
                }
                // 인접한 노드들을 방문 리스트에 추가
                for (int i = 0; i < graph.length; i++) {
                    if (graph[node][i] && !visited[i]) {
                        visitList.add(i);
                    }
                }
            }
        }
    }


    public static int checkCount(boolean[] visited){ //몇개의 노드를 방문했는지 체크
        int check = 0;
        for(int i=0; i<visited.length; i++){
            if(visited[i]){
                check++;
            }
        }
        return check;
    }
}