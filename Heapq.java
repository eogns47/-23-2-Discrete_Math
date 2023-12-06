import java.util.ArrayList;

public class Heapq {
    private ArrayList<Path> queue;

    public Heapq(){
        this.queue = new ArrayList<>();
    }

    public void heapPush(Path path){
        this.queue.add(path);
        for(int i = 0; i < this.queue.size(); i++){
            int swapIdx = queue.size() - i - 1;
            int nodeNum = swapIdx;
            while(nodeNum != 0){
                int parentOfNodeNum = (int)((nodeNum - 1) / 2);
                if(this.queue.get(parentOfNodeNum).weight < this.queue.get(nodeNum).weight){
                    swap(parentOfNodeNum, nodeNum);
                }
                nodeNum -= 1;
            }
            swap(0, swapIdx);
        }
    }

    public void swap(int a, int b){
        Path aPath = this.queue.get(a);
        Path bPath = this.queue.get(b);

        Path aTempPath = new Path(aPath.startNode, aPath.endNode, aPath.weight);

        aPath.startNode = bPath.startNode;
        aPath.endNode = bPath.endNode;
        aPath.weight = bPath.weight;
        bPath.startNode = aTempPath.startNode;
        bPath.endNode = aTempPath.endNode;
        bPath.weight = aTempPath.weight;

    }

    public Path heapPop(){
        return this.queue.remove(0);
    }

    public boolean isEmpty(){
        return this.queue.isEmpty();
    }

}
