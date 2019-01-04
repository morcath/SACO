package wmh.project;

import java.util.ArrayList;

public class Node {

    ArrayList<Move> moves;

    public Node(ArrayList<Move> previousMoves)
    {
        moves = previousMoves;
    }

    void addMove(Move nextMove)
    {
        moves.add(nextMove);
    }

}