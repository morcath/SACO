package wmh.project;


import java.util.Random;

public class SudokuBoard
{
    private int[][] inputBoard;
    private boolean[][] mask = new boolean[9][9];
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
                    startNumbers[inputBoard[x][y]-1] += 1;
            }
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

    public boolean isSolved(Node currentNode, Move nextMove)
    {
        //todo
        return true;
    }


}