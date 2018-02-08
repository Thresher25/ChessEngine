package Pieces;

import java.awt.*;

public abstract class ChessPiece {

    public static int baseValue;
    public static String name;
    protected Point pos;

    public ChessPiece(Point pos){
        this.pos = pos;
    }

    public void moveTo(Point desiredLoc){
        pos = desiredLoc;
    }

    public Point getPos() {
        return pos;
    }

    public abstract Point[] genPossibleMoves();


}
