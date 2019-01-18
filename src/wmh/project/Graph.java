package wmh.project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Graph {

    private ArrayList<Node> nodes = new ArrayList<Node>();
    private ArrayList<ArrayList<Double>> graph = new ArrayList<ArrayList<Double>>();
    //private SudokuLevel level;
    //public double initialWeight;

    /*public Graph(SudokuLevel _level)
    {
        level = _level;//TODO: chyba niepotrzebne
    }*/

    public double initialWeight() {//TODO: ona powinna by� losowa i nie zale�e� od poziomu trudno�ci
        /*switch (level) {
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

        }*/
    	Random random = new Random();
    	double result = random.nextDouble(); //zwraca liczb� z przedzia�u [0,1) - czy to na pewno b�dzie "ma�a" pocz�tkowa waga?
    	while(result == 0)
    	{
    		result = random.nextDouble();
    	}
    	return result;
    }

    /*public void changeInitialWeight(double rho)//TODO??
    {
        initialWeight = (1-rho)*initialWeight;
    }

    public void evaporatePheromone(double rho)//TODO ???
    {
        //todo
    }*/

    public void addNode(Node newNode)//przecie� nowy w�ze� nie musi mie� po��czenia ze wszystkimi innymi, po to jest metoda areNeighbours
    {
    	//czy nie wyst�puje ju� w grafie
    	Iterator<Node> nodeIter = nodes.iterator();
    	Node currentNode;
    	while(nodeIter.hasNext())
    	{
    		currentNode = nodeIter.next();
    		if(currentNode.equals(newNode))
    		{
    			return; //taki w�ze� ju� jest w grafie, nie ma co go wstawia�
    		}
    	}
    	
    	//za�o�enie: macierz incydencji jest tak budowana, �e wierzocho�ek �r�d�owy kraw�dzi jest w wierszu,
    	//a docelowy w kolumnie
    	
    	Iterator<ArrayList<Double>> iter = graph.iterator();
        ArrayList<Double> tmp;
        ArrayList<Double> newNodeWeights = new ArrayList<Double>(graph.size() + 1);
        int rowNum = 0;
        //wiersze - w ka�dym dodawany element nowej kolumny
        while(iter.hasNext())
        {
            tmp = iter.next();
            double weight = 0;//je�li nie ma po��czenia z w�z�a spod indeksu rowNum ze wstawianym w�z�em
            
            if(areSuccessorOf(nodes.get(rowNum), newNode))
            {
            	weight = initialWeight();
            }
            
            tmp.add(weight);
            rowNum++;
        }
        //utworzenie wiersza dla nowo dodanego w�z�a
        for (int i=0; i<graph.size(); ++i)
        {
            double weight = 0;
            if(areSuccessorOf(newNode, nodes.get(i)))
            {
            	weight = initialWeight(); ///chocia� czy z nowo utworzonego w�z�a mog� prowadzi� jakie� kraw�dzie do starszych?
            }
            
        	newNodeWeights.add(weight);
        }
        
        newNodeWeights.add(0.0); //waga kraw�dzi mi�dzy dodanym w�z�em a nim samym
        
        graph.add(newNodeWeights);
        

        nodes.add(newNode);
    	
    }

    public int getNodeIndex(Node node)
    {
        Iterator<Node> iter = nodes.iterator();
        Node current;
        int result = 0;

        while(iter.hasNext())
        {
            //todo: searching node and return index if find
        	current = iter.next();
        	if(current.equals(node))
        	{
        		return result;
        	}
        	result++;
        }

        addNode(node);
        result++; //zamiast nodes.size() czy te� size - 1
        return result;
    }

    /*EdgeDirection areNeighbours(Node first, Node second)
    {
        //todo: searching if nodes are neighbours
        return EdgeDirection.None;
    }*/
    
    private boolean areSuccessorOf(Node predecessor, Node successor)
    {
    	ArrayList<Move> predMoves = predecessor.getMoves();
    	ArrayList<Move> succMoves = successor.getMoves();
    	int predLength = predMoves.size();
    	int succLength = succMoves.size();
    	if(succLength - predLength != 1)
    	{
    		return false;
    	}
    	
    	for(int i = 0; i < predLength; i++)
    	{
    		Move p = predMoves.get(i);
    		Move s = succMoves.get(i);
    		if(!p.equalTo(s))
    		{
    			return false;
    		}
    	}
    	//r�ni� si� tylko jednym, ostatnim elementem
    	return true;
    }

}
