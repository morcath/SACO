package wmh.project;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Iterator;

public class Node {

    public ArrayList<Move> moves;
    double[] hash;

    public Node(ArrayList<Move> previousMoves, SudokuBoard firstBoard)
    {
        //moves = previousMoves; //TODO: kopiowa�?
    	int previousSize = previousMoves.size();
    	moves = new ArrayList<Move>(previousSize + 1);
    	for(int i = 0; i < previousSize; i++)
    	{
    		moves.add(previousMoves.get(i)); //obiekty klasy Move nie s� kopiowane, tylko przepisywane referencje,
    		//bo i tak same nie b�d� z niczym por�wnywane, nie ma co ich mno�y�    		
    	}
    	
        hash = makeHash(firstBoard); //TODO: tutaj robimy hash z poprzedniej wersji planszy, nowego ruchu jeszcze nie ma!
    }

    void addMove(Move nextMove, SudokuBoard firstBoard)
    {

        moves.add(nextMove);
        hash = makeHash(firstBoard);
    }

    public double[] makeHash(SudokuBoard firstBoard)
    {
        int[][] board = recreateBoard(firstBoard);
        double result;
        double[] hashCode = new double[9];


        for(int i=0; i<9; ++i)
        {
            result = 0;
            for (int j = 0; j < 9; ++j)
            {
                result += Math.pow((1.0 * j + 1), 4) * Math.pow((board[i][j]*1.0)/10, 5);
            }
            result = round(result, 5);
            hashCode[i] = result;
        }
        return hashCode;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public int[][] recreateBoard(SudokuBoard firstBoard)
    {
        int[][] board = new int[9][9];//kopia zamiast przepisywania referencji
        int tmp1, tmp2;
        Iterator<Move> iter = moves.iterator();
        
        int[][] first = firstBoard.getFirstBoard(); 
        for(int i = 0; i < 9; i++)
        {
        	for(int j = 0; j < 9; j++)
        	{
        		board[i][j] = first[i][j];
        	}
        }

        Move move;
        while(iter.hasNext())
        {
            move = iter.next();
            tmp1 = board[move.rowFrom][move.colFrom];
            tmp2 = board[move.rowTo][move.colTo];

            board[move.rowTo][move.colTo] = tmp1;
            board[move.rowFrom][move.colFrom] = tmp2;

        }


        return board;
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
    	Node other = (Node) obj;
    	if(other == this)
    	{
    		return true;
    	}

        boolean result = true;
        for(int i = 0; i<this.hash.length; ++i)
        {
            if(other.hash[i] != this.hash[i])
            {
                result = false;
                break;
            }
        }


    	return result;
    	//return other.hash == this.hash; //TODO: a mo�e zamiast hashy sprawdza� jednak sekwencj� ruch�w?
    }
    
    public ArrayList<Move> getMoves()
    {
    	return moves;
    }


}