package Pieces;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Bishop extends ChessPiece {

    public Bishop(Point pos, boolean isWhite){
        super(pos, isWhite);
        name = "Bishop";
        baseValue = 325;
        try {
            imageW = ImageIO.read(new File("res/BishopW.png"));
            imageB = ImageIO.read(new File("res/BishopB.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Point[] genPossibleMoves() {
        ArrayList<Point> moves = new ArrayList<>();

        for(int i=1;i<8;i++){
            if(pos.x-i>=0){
                if(pos.y-i>=0){
                    moves.add(new Point(pos.x-i,pos.y-i));
                }
                if(pos.y+i<8){
                    moves.add(new Point(pos.x-i,pos.y+i));
                }
            }
            if(pos.x+i<8){
                if(pos.y-i>=0){
                    moves.add(new Point(pos.x+i,pos.y-i));
                }
                if(pos.y+i<8){
                    moves.add(new Point(pos.x+i,pos.y+i));
                }
            }
        }
        Point[] returned = new Point[moves.size()];
        for(int i=0;i<moves.size();i++){
            returned[i] = moves.get(i);
        }
        return returned;
    }
}
