package wmh.project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Graph {

    private ArrayList<Node> nodes = new ArrayList<Node>();
    private ArrayList<ArrayList<Double>> graph = new ArrayList<ArrayList<Double>>();
    
    public double initialWeight() {
    	Random random = new Random();
    	double result = random.nextDouble(); //zwraca liczbê z przedzia³u [0,1)
    	while(result == 0)
    	{
    		result = random.nextDouble();
    	}
    	return result;
    }

    public void evaporatePheromone(double rho)
    {
        int nodesNum = graph.size();
        for(int i = 0; i < nodesNum; i++)
        {
        	ArrayList<Double> row = graph.get(i);
        	for(int j = 0; j < nodesNum; j++)
        	{
        		Double weight = row.get(j);
        		weight = (1 - rho) * weight;
        		row.set(j, weight);
        	}
        }
    }
    
    public void updatePheromone(int rowIndex, int colIndex, double delta)
    {
    	ArrayList<Double> row = graph.get(rowIndex);
    	Double currentPheromone = row.get(colIndex);
    	currentPheromone += delta;
    	row.set(colIndex, currentPheromone);
    }

    //zwraca indeks nowo utworzonego wêz³a na liœcie nodes
    public int addNode(Node newNode, SudokuBoard input)
    {
    	//czy nie wystêpuje ju¿ w grafie
    	int nodesNum = nodes.size();
    	Node currentNode;
    	for(int i = 0; i < nodesNum; i++)
    	{
    		currentNode = nodes.get(i);
    		if(currentNode.equals(newNode))
    		{
    			return i; //taki wêze³ ju¿ jest w grafie, nie ma co go wstawiaæ
    		}
    	}
    	
    	//za³o¿enie: macierz incydencji jest tak budowana, ¿e wierzcho³ek zród³owy krawêdzi jest w wierszu,
    	//a docelowy w kolumnie
    	
    	Iterator<ArrayList<Double>> iter = graph.iterator();
        ArrayList<Double> tmp;
        ArrayList<Double> newNodeWeights = new ArrayList<Double>(graph.size() + 1);
        int rowNum = 0;
        //wiersze - w ka¿dym dodawany element nowej kolumny
        while(iter.hasNext())
        {
            tmp = iter.next();
            double weight = 0;//je¿eli nie ma po³¹czenia wêz³a spod indeksu rowNum ze wstawianym wêz³em
            
            if(isSuccessorOf(nodes.get(rowNum), newNode, input))
            {
            	weight = initialWeight();
            }
            
            tmp.add(weight);
            rowNum++;
        }
        //utworzenie wiersza dla nowo dodanego wêz³a
        for (int i=0; i<graph.size(); ++i)
        {
            double weight = 0;
            if(isSuccessorOf(nodes.get(i), newNode, input))
            {
            	weight = initialWeight();
            }
            
        	newNodeWeights.add(weight);
        }
        
        newNodeWeights.add(0.0); //waga krawêdzi miêdzy dodanym wêz³em a nim samym
        
        graph.add(newNodeWeights);
        

        nodes.add(newNode);
        return nodes.size() - 1;    	
    }
  
    private boolean isSuccessorOf(Node first, Node second, SudokuBoard input)
    {
    	int[][] firstBoard = first.recreateBoard(input);
    	int[][] secondBoard = second.recreateBoard(input);
    	int diffs = 0;
    	for(int i = 0; i < 9; i++)
    	{
    		for(int j = 0; j < 9; j++)
    		{
    			if(firstBoard[i][j] != secondBoard[i][j])
    			{
    				diffs++;
    			}
    		}
    	}
    	return diffs == 2;
    }

    public Node getNode(int index)
    {
    	return nodes.get(index);
    }
    
    public double getWeight(int source, int target)
    {
    	ArrayList<Double> sourceRow = graph.get(source);
    	return sourceRow.get(target);
    }
    
    public void display()
    {
    	int size = graph.size();
    	System.out.print("\t");
    	for(int i = 0; i < size; i++)
    	{
    		System.out.print(i + " ");
    	}
    	System.out.print("\n\t");
    	for(int i = 0; i < size; i++)
    	{
    		System.out.print("---");
    	}
    	System.out.println();
    	
    	for(int i = 0; i < size; i++)
    	{
    		System.out.print(i + " |\t");
    		ArrayList<Double> row = graph.get(i);
    		for(int j = 0; j < size; j++)
    		{
    			System.out.print(row.get(j) + " ");
    		}
    		System.out.println();
    	}
    }
}
