package wmh.project;

import javafx.util.Pair;

import java.util.ArrayList;

public class Ant {

    private ArrayList<Path> paths = new ArrayList<Path>();
    
//mo�e nie dawa� w nast�pnikach mo�liwo�ci przej�cia do poprzednika
//    ArrayList<Integer> generateSuccessors(int currentNodeID, Graph graph, SudokuBoard sudokuBoard)
//    {
//        //todo: generate all next states
//        Node currenNode = graph.nodes.get(currentNodeID);
//
//
//    }

//    Pair<Integer, Double> calcualteProbabilities(double alpha, Graph graph)
//    {
//        //todo: probabilities of chose next state
//    }

    void selectSuccessor(int currentNode)
    {
        //todo
    }

    Path buildPath(int iteration)
    {
        int sourceIndex = 0;
    	Path path = new Path(sourceIndex);
        //todo
        return path;
    }

    void updatePheromone(double rho, Graph graph, int iteration)//TODO: przy za�o�eniu, �e iteracje s� numerowane od 0
    {
        //todo
    }


}
