package wmh.project;


import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Random;

public class SudokuBoard
{
    private int[][] inputBoard;
    private boolean[][] mask = new boolean[9][9];
    private ArrayList<Pair<Integer, Integer>> emptyFields = new ArrayList<Pair<Integer, Integer>>();
    private int[] startNumbers = {0,0,0,0,0,0,0,0,0};
    private int[][] firstBoard = new int[9][9];
    private SudokuLevel level;

    public SudokuBoard(SudokuLevel _level, int[][] _inputBoard)
    {
        level = _level;
        inputBoard = _inputBoard;
        this.makeMask();
        this.createFirstBoard();

    }

    public void displayBoard()
    {
        for(int x=0; x<9; ++x)
        {
            for (int y = 0; y < 9; ++y)
                System.out.print(firstBoard[x][y]);
            System.out.println();
        }
    }

    private void makeMask()
    {
        for(int x=0; x<9; ++x)
            for (int y = 0; y < 9; ++y)
            {
                mask[x][y] = (inputBoard[x][y] > 0);
                if(mask[x][y])
                {
                	startNumbers[inputBoard[x][y]-1] += 1;
                }    
                else 
                {
                	Pair<Integer, Integer> emptyField = new Pair<Integer, Integer>(x, y);
                	emptyFields.add(emptyField);
                }
            }
    }

    public int[][] getFirstBoard() {
        return firstBoard;
    }

    private void createFirstBoard()
    {
        for(int x=0; x<9; ++x)
            for (int y = 0; y < 9; ++y)
                firstBoard[x][y] = generateDigit(x,y);
    }

    private int generateDigit(int x, int y)
    {
        Random generator = new Random();
        int result;

        if(mask[x][y])
            result = inputBoard[x][y];
        else
        {
            do {
                result = generator.nextInt(9) + 1;
            }while (startNumbers[result-1] >= 9);

            startNumbers[result-1] += 1;
        }

        return result;
    }

    //czy jest rozwi¹zane
    public boolean isSolved(Node currentNode)
    {
        int[][] currentBoard = currentNode.recreateBoard(this);
        
        //sprawdzenie elementów w wierszu
        for(int i = 0; i < 9; i++)
        {
        	int[] digitsFrequencyInRow = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        	for(int j = 0; j < 9; j++)
        	{
        		int digit = currentBoard[i][j];
        		digitsFrequencyInRow[digit - 1]++;
        		if(digitsFrequencyInRow[digit - 1] > 1)
        		{
        			return false;
        		}
        	}
        }
        
        //sprawdzenie elementów w kolumnie
        for(int j = 0; j < 9; j++)
        {
        	int[] digitsFrequencyInColumn = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        	for(int i = 0; i < 9; i++)
        	{
        		int digit = currentBoard[i][j];
        		digitsFrequencyInColumn[digit - 1]++;
        		if(digitsFrequencyInColumn[digit - 1] > 1)
        		{
        			return false;
        		}
        	}
        }
        
        //sprawdzenie elementów w ma³ych kwadratach
        for(int i = 0; i < 9; i += 3)
        {        	
        	for(int j = 0; j < 9; j += 3)
        	{
        		int[] digitsFrequencyInSquare = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        		for(int k = i; k < i + 3; k++)
        		{
        			for(int l = j; l < j + 3; l++)
        			{
        				int digit = currentBoard[k][l];
        				digitsFrequencyInSquare[digit - 1]++;
        				if(digitsFrequencyInSquare[digit - 1] > 1)
        				{
        					return false;
        				}
        			}
        		}
        	}
        }
        
        return true;
    }

    public ArrayList<Pair<Integer, Integer>> getEmptyFields()
    {
    	return emptyFields;
    }
}