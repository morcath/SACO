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
    		System.out.print(nodes.get(i) + " ");
    	}
    	System.out.println();
    }
    
    @Override
    public boolean equals(Object obj)
    {
    	if(obj == null)
    	{
    		return false;
    	}
    	if(!obj.getClass().equals(this.getClass()))
    	{
    		return false;
    	}
    	Path other = (Path) obj;
    	if(other == this)
    	{
    		return true;
    	}
    	if(other.length != this.length)
    	{
    		return false;
    	}
    	
    	int nodesInPath = nodes.size();
    	for(int i = 0; i < nodesInPath; i++)
    	{
    		if(!this.nodes.get(i).equals(other.nodes.get(i)))
    		{
    			return false;
    		}
    	}
    	return true;
    }
    
    public int getNodesNumber()
    {
    	return nodes.size();
    }
    
    //zwraca indeks w�z�a, kt�ry na �cie�ce znajduje si� pod danym indeksem, w ca�ym grafie
    public int getNode(int index)
    {
    	return nodes.get(index);
    }

}