package Pieces;

import java.awt.*;

public class Knight extends ChessPiece {

    public Knight(Point pos){
        super(pos);
        name = "Knight";
        baseValue = 300;
    }

    @Override
    public Point[] genPossibleMoves() {
        return new Point[0];
    }
}
