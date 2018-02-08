package Pieces;

import java.awt.*;

public class Rook extends ChessPiece {

    public Rook(Point pos){
        super(pos);
        name = "Rook";
        baseValue = 500;
    }

    @Override
    public Point[] genPossibleMoves() {
        return new Point[0];
    }
}
