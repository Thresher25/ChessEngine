package Pieces;

import java.awt.*;

public class Bishop extends ChessPiece {

    public Bishop(Point pos){
        super(pos);
        name = "Bishop";
        baseValue = 325;
    }

    @Override
    public Point[] genPossibleMoves() {
        return new Point[0];
    }
}
