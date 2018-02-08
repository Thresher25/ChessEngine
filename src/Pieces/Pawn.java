package Pieces;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Pawn extends ChessPiece {

    public Pawn(Point pos, boolean isWhite){
        super(pos, isWhite);
        name = "Pawn";
        baseValue = 100;
        try {
            imageW = ImageIO.read(new File("res/PawnW.png"));
            imageB = ImageIO.read(new File("res/PawnB.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
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
