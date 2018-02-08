package Pieces;

import java.awt.*;

public class Queen extends ChessPiece {

    public Queen(Point pos){
        super(pos);
        name = "Queen";
        baseValue = 900;
    }

    @Override
    public Point[] genPossibleMoves() {
        return new Point[0];
    }
}
