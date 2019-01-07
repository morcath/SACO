package wmh.project;

import java.util.ArrayList;
import java.util.Iterator;

public class Graph {

    ArrayList<Node> nodes = new ArrayList<Node>();
    ArrayList<ArrayList<Double>> graph = new ArrayList<ArrayList<Double>>();
    private SudokuLevel level;
    public double initialWeight;

    public Graph(SudokuLevel _level)
    {
        level = _level;
    }

    public void initialWeight() {
        switch (level) {
            case EASY:
                initialWeight = 0;   //todo: what initial weight?
                break;
            case MEDIUM:
                initialWeight = 0;   //todo: what initial weight?
                break;
            case HARD:
                initialWeight = 0;   //todo: what initial weight?
                break;
            default:
                System.exit(-1);

        }
    }

    public void changeInitialWeight(double rho)
    {
        initialWeight = (1-rho)*initialWeight;
    }

    public void evaporatePheromone(double rho)
    {
        //todo
    }

    public void addNode(Node newNode)
    {
        Iterator<ArrayList<Double>> iter = graph.iterator();
        ArrayList<Double> tmp;
        ArrayList<Double> newNodeWeights = new ArrayList<Double>();

        while(iter.hasNext())
        {
            tmp = iter.next();
            tmp.add(initialWeight);
        }

        for (int i=0; i<graph.size(); ++i)
            newNodeWeights.add(initialWeight);

        nodes.add(newNode);
    }

    private int getNodeIndex(Node node)
    {
        Iterator<Node> iter = nodes.iterator();
        int result;

        while(iter.hasNext())
        {
            //todo: searching node and return index if find
        }

        addNode(node);
        result = nodes.size();
        return result;
    }

    EdgeDirection areNeighbours(Node first, Node second)
    {
        //todo: searching if nodes are neighbours
        return EdgeDirection.None;
    }

}
