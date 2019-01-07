package wmh.project;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Iterator;

public class Node {

    ArrayList<Move> moves;
    double[] hash;

    public Node(ArrayList<Move> previousMoves, SudokuBoard firstBoard)
    {
        moves = previousMoves;
        hash = makeHash(firstBoard);
    }

    void addMove(Move nextMove)
    {
        moves.add(nextMove);
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
        int[][] board;
        int tmp1, tmp2;
        Iterator<Move> iter = moves.iterator();
        board = firstBoard.getFirstBoard();

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


}