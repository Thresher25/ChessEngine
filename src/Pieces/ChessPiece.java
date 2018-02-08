package Pieces;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ChessPiece {

    public static int baseValue;
    public static BufferedImage imageW, imageB;
    public static String name;
    public boolean isWhite;
    protected Point pos;

    public ChessPiece(Point pos, boolean isWhite){
        this.pos = pos;
        this.isWhite = isWhite;
    }

    public void moveTo(Point desiredLoc){
        pos = desiredLoc;
    }

    public Point getPos() {
        return pos;
    }

    public abstract Point[] genPossibleMoves();


}
