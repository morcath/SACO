package wmh.project;

import java.util.ArrayList;

public class Path {

    private ArrayList<Integer> nodes;
    private int length = -1;

    public Path(int sourceIndex)
    {
    	nodes = new ArrayList<Integer>();
    	nodes.add(sourceIndex);
    }
    
    public void addNode(int nodeIndex)
    {
    	nodes.add(nodeIndex);
    }
    
    public void removeLoops()
    {
        for(int i = 0; i < nodes.size(); i++)
        {
        	Integer current = nodes.get(i);
        	for(int j = i + 1; j < nodes.size(); )
        	{
        		if(nodes.get(j).equals(current))
        		{
        			//usuni�cie element�w powtarzaj�cych si�
        			for(int k = j; k > i; k--)
        			{
        				nodes.remove(k);
        			}
        			j = i + 1;
        		}
        		else
        		{
        			j++;
        		}
        	}
        }    	
    	
    	length = nodes.size() - 1;
    }

    public double evaluationFunction() //liczba kraw�dzi na �cie�ce (po usuni�ciu p�tli)
    {
        if(length == -1)
        {
        	removeLoops();
        }
        return length;
    }
    
    public void display()
    {
    	for(int i = 0; i < nodes.size(); i++)
    	{
    		System.out.println(nodes.get(i));
    	}
    }

}