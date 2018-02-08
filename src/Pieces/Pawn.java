package Pieces;

import java.awt.*;

public class Pawn extends ChessPiece {

    public Pawn(Point pos){
        super(pos);
        name = "Pawn";
        baseValue = 100;
    }

    @Override
    public Point[] genPossibleMoves() {
        if(pos.y==1){
            return new Point[]{new Point(pos.x,2),new Point(pos.x,3)};
        }else{
            return new Point[]{new Point(pos.x,pos.y+1)};
        }
    }
}
