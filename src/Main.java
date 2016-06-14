import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static ArrayList nodesArray = new ArrayList();

    public static void main(String[] args) throws IOException {
        buildGraph();
        run();
    }

    static void buildGraph() throws IOException {
        String nodesFile = "/home/smmsadrnezh/IdeaProjects/social-propagation/src/Nodes.txt";
        String edgesFile = "/home/smmsadrnezh/IdeaProjects/social-propagation/src/Edges.txt";

        // Read Input Nodes File
        BufferedReader br = new BufferedReader(new FileReader(nodesFile));
        String line;
        while ((line = br.readLine()) != null) {
            String[] splitedLine = line.split("\\s+");
            Double[] rate = {Double.parseDouble(splitedLine[2]), Double.parseDouble(splitedLine[3]), Double.parseDouble(splitedLine[4]), Double.parseDouble(splitedLine[5])};
            Node node = new Node(Integer.parseInt(splitedLine[0]), splitedLine[1], rate);
            nodesArray.add(node);
        }

        // Read Input Edges File
        br = new BufferedReader(new FileReader(edgesFile));
        while ((line = br.readLine()) != null) {
            String[] splitedLine = line.split("\\s+");
            Edge edge = new Edge(Integer.parseInt(splitedLine[0]), Integer.parseInt(splitedLine[1]), Double.parseDouble(splitedLine[2]));
            nodesArray.add(edge);
        }
    }

    static void run() {

    }
}

class Node {
    Integer nodeIndex;
    String nodeClass;
    Double[] rate = new Double[3];

    public Node(Integer nodeIndex, String nodeClass, Double[] rate) {
        this.nodeIndex = nodeIndex;
        this.nodeClass = nodeClass;
        this.rate = rate;
    }

}

class Edge {
    Node srcNode;
    Node destNode;
    Double weight;

    public Edge(Integer srcIndex, Integer destIndex, Double weight) {
//        this.srcNode =
        this.weight = weight;
    }
}