package Pieces;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class King extends ChessPiece {

    public King(Point pos, boolean isWhite){
        super(pos, isWhite);
        name = "King";
        baseValue = 65536;//high number so the king doesn't ever get traded away
        try {
            imageW = ImageIO.read(new File("res/KingW.png"));
            imageB = ImageIO.read(new File("res/KingB.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Point[] genPossibleMoves() {
        Point[] moves;
        int reduced = 0;
        if(  (pos.x==0 && pos.y==0) || (pos.x==7 && pos.y==7) || (pos.x==0 && pos.y==7) || (pos.x==7 && pos.y==0)  ){
            moves = new Point[3];

            if(pos.x==0 && pos.y==0){
                moves[0] = new Point(0,1);
                moves[1] = new Point(1,0);
                moves[2] = new Point(1,1);
            }else if(pos.x==7 && pos.y==7){
                moves[0] = new Point(7,6);
                moves[1] = new Point(6,7);
                moves[2] = new Point(6,6);
            }else if(pos.x==0 && pos.y==7){
                moves[0] = new Point(0,6);
                moves[1] = new Point(1,7);
                moves[2] = new Point(1,6);
            }else{
                moves[0] = new Point(7,6);
                moves[1] = new Point(6,7);
                moves[2] = new Point(6,6);
            }

        }else if(pos.x==7 || pos.x==0){
            moves = new Point[5];

            if(pos.x==0){
                moves[0] = new Point(0,pos.y+1);
                moves[1] = new Point(0,pos.y-1);
                moves[2] = new Point(1,pos.y);
                moves[3] = new Point(1,pos.y+1);
                moves[4] = new Point(1,pos.y-1);
            }else{
                moves[0] = new Point(7,pos.y+1);
                moves[1] = new Point(7,pos.y-1);
                moves[2] = new Point(6,pos.y);
                moves[3] = new Point(6,pos.y+1);
                moves[4] = new Point(6,pos.y-1);
            }

        }else if(pos.y==0 || pos.y==7){
            moves = new Point[5];

            if(pos.y==0){
                moves[0] = new Point(pos.x+1,0);
                moves[1] = new Point(pos.x-1,0);
                moves[2] = new Point(pos.x,1);
                moves[3] = new Point(pos.x+1,1);
                moves[4] = new Point(pos.x-1,1);
            }else{
                moves[0] = new Point(pos.x+1,7);
                moves[1] = new Point(pos.x-1,7);
                moves[2] = new Point(pos.x,6);
                moves[3] = new Point(pos.x+1,6);
                moves[4] = new Point(pos.x-1,6);
            }

        }else{
            moves = new Point[8];

            moves[0] = new Point(pos.x-1,pos.y);
            moves[1] = new Point(pos.x-1,pos.y+1);
            moves[2] = new Point(pos.x-1,pos.y-1);
            moves[3] = new Point(pos.x,pos.y+1);
            moves[4] = new Point(pos.x,pos.y-1);
            moves[5] = new Point(pos.x+1,pos.y);
            moves[6] = new Point(pos.x+1,pos.y+1);
            moves[7] = new Point(pos.x+1,pos.y-1);

        }

        return moves;
    }



}
