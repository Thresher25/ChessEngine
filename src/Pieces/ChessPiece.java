package Pieces;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ChessPiece {

    public static int baseValue;
    public BufferedImage imageW, imageB;
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

    public boolean isWhite() {
        return isWhite;
    }

    public abstract Point[] genPossibleMoves();

    public void draw(Graphics g){
        if(isWhite){
            g.drawImage(imageW,pos.x*110+255,pos.y*110+50,null);
        }else{
            g.drawImage(imageB,pos.x*110+255,pos.y*110+50,null);
        }

    }

}
