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
    public static void solveDijkstra() {
        int graphnum=0;
        totalNodes=0;
        System.out.println("2. 최단 경로 구하기 수행 결과\n");

        try(Scanner scan = new Scanner(new File(FILE))){
            while(scan.hasNextLine()) {
                String str = scan.nextLine(); //다음 줄 읽기
                String[] temp  = str.split(" "); //공백으로 구분하여 배열에 저장
                graphnum++; //그래프 번호 증가

                if(temp.length == 1) { //첫번째 줄이면
                    totalNodes = Integer.parseInt(temp[0]); //노드 개수 저장
                    adjacencyMatrix = new int[totalNodes][totalNodes]; //인접행렬 생성

                    int startNode, endNode, weight; //시작 노드, 끝 노드, 가중치
                    for (int i = 0; i < totalNodes; i++) { //인접행렬 초기화
                        Arrays.fill(adjacencyMatrix[i], Integer.MAX_VALUE); //최대값으로 초기화
                    }
                    for (int i = 1; i <= totalNodes; i++) { //인접행렬 생성
                        String[] tokens = scan.nextLine().split(" "); //공백으로 구분하여 배열에 저장
                        startNode = Integer.parseInt(tokens[0]);//시작 노드 저장

                        for (int j = 1; j < tokens.length - 1; j += 2) { //끝 노드와 가중치 저장
                            endNode = Integer.parseInt(tokens[j]); //끝 노드 저장
                            weight = Integer.parseInt(tokens[j + 1]); //가중치 저장
                            adjacencyMatrix[startNode-1][endNode-1] = weight; //인접행렬에 저장
                            adjacencyMatrix[endNode-1][startNode-1] = weight; //인접행렬에 저장
                        }
                    }
 
                    System.out.println("그래프 [" + graphnum + "]");
                    //digjkstra구현
                    Dijkstra();
                }
            }

        }catch(FileNotFoundException e) {
            System.out.println("그래프 정보가 생성되지 않았습니다. \n파일명을 확인하세요");
        }
    }

    public static void Dijkstra(){
        // 초기화 작업
        int[] dMin = new int[totalNodes];  // 최소 경로의 거리 값을 저장할 배열을 노드 개수만큼 생성한다.
        boolean[] dMinDetermined = new boolean[totalNodes]; // 최소 경로 거리 값이 확정되었는지를 저장하기 위한 배열을 노드 개수만큼 생성한다.
        Arrays.fill(dMin, Integer.MAX_VALUE);   // 최소 경로 거리 값이 아직 정해지지 않았으므로 MAX_VALUE로 초기화
        Arrays.fill(dMinDetermined, false); // 최소 경로 거리 값이 아직 모두 확정되지 않았으므로 false로 초기화

        ArrayList<Integer>[] dMinPath = new ArrayList[totalNodes];  // 경로를 저장할 ArrayList 배열, 이것 또한 노드 개수만큼 생성하고
        for(int i = 0; i < totalNodes; i++){    // 이 반복문을 통해 각 ArrayList를 초기화 해준다.
            dMinPath[i] = new ArrayList<>();
        }
        dMin[0] = 0;    // 노드 1의 최소 경로 거리는 0
        dMinDetermined[0] = true;   // 노드 1의 최소 경로 거리가 정해졌으므로 true
        for(int i = 0; i < totalNodes; i++){    // 이 반복문을 통해 노드 1과 인접한 노드들의 거리값으로 해당 노드들의 최소 경로 거리를 할당
            dMinPath[i].add(1); // 모든 노드의 시작은 1이니까 1 추가
            if(adjacencyMatrix[0][i] != Integer.MAX_VALUE){
                dMin[i] = adjacencyMatrix[0][i];
                dMinPath[i].add(i + 1); // 경로에 자기 자신도 추가
            }
        }

        for(int i = 0; i < totalNodes; i++){    // 실제 다익스트라 알고리즘 수행
            int determinedIdx = findMinIdx(dMin, dMinDetermined);   // 최소 경로 거리 중 가장 작은 값을 가진 노드의 인덱스 가져옴
            if(determinedIdx == -1)break;   // findMinIdx에서 -1이 반환되었다는 것은, 모든 노드의 최소 경로 거리가 확정되었다는 것, 그러므로 break
            dMinDetermined[determinedIdx] = true;   // 해당 노드는 최소 경로 거리가 확정된 것이므로, 바로 true값으로 바꿔줌
            for(int j = 0; j < totalNodes; j++){
                if(adjacencyMatrix[determinedIdx][j] != Integer.MAX_VALUE){ // 지금 determinedIdx의 노드의 인접노드만 골라서
                    int tempLength = dMin[determinedIdx] + adjacencyMatrix[determinedIdx][j];    // 새로운 길이를 만들고
                    if(tempLength < dMin[j]){    // 해당 새로운 길이가 더 작으면 최소 경로 거리를 갱신 해줘야함
                        dMin[j] = tempLength;    // 최소 경로 거리 갱신
                        dMinPath[j] = (ArrayList<Integer>) dMinPath[determinedIdx].clone(); // dterminedIdx의 최소 경로를 복사해옴(clone을 통해 값에 의한 복사)
                        dMinPath[j].add(j + 1); // 갱신하고 나서 자기 자신도 경로에 추가
                    }
                }
            }
        }
        // 결과 출력
        System.out.println("----------------------------");
        System.out.println("시작점: 1");
        for(int i = 1; i < totalNodes; i++){
            System.out.printf("정점 [%d]: ", i + 1);
            for(int j = 0; j < dMinPath[i].size(); j++){
                if(j == 0){
                    System.out.printf("%d", dMinPath[i].get(j));
                }else{
                    System.out.printf(" - %d", dMinPath[i].get(j));
                }
            }
            System.out.printf(", 길이: %d\n", dMin[i]);
        }
        System.out.println("=========================");
        System.out.println();
    }

    // 최소 경로 거리를 가진 idx를 찾아주는 함수
    public static int findMinIdx(int[] dMin, boolean[] dMinDetermined){
        int min = Integer.MAX_VALUE;
        int idx = -1;
        for(int i = 0; i < totalNodes; i++){
            if(dMin[i] < min && !dMinDetermined[i]){
                min = dMin[i];
                idx = i;
            }
        }
        return idx;
    }
}
