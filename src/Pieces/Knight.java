package Pieces;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Knight extends ChessPiece {

    public Knight(Point pos, boolean isWhite){
        super(pos, isWhite);
        name = "Knight";
        baseValue = 300;
        try {
            imageW = ImageIO.read(new File("res/KnightW.png"));
            imageB = ImageIO.read(new File("res/KnightB.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Point[] genPossibleMoves() {

        ArrayList<Point> moves = new ArrayList<>();

        if(pos.x+1<8 && pos.y+2<8){
            moves.add(new Point(pos.x+1,pos.y+2));
        }
        if(pos.x-1>=0 && pos.y+2<8){
            moves.add(new Point(pos.x-1,pos.y+2));
        }
        if(pos.x+2<8 && pos.y+1<8){
            moves.add(new Point(pos.x+2,pos.y+1));
        }
        if(pos.x-2>=0 && pos.y+1<8){
            moves.add(new Point(pos.x-2,pos.y+1));
        }
        if(pos.x+2<8 && pos.y-1>=0){
            moves.add(new Point(pos.x+2,pos.y-1));
        }
        if(pos.x-2>=0 && pos.y-1>=0){
            moves.add(new Point(pos.x-2,pos.y-1));
        }
        if(pos.x-1>=0 && pos.y-2>=0){
            moves.add(new Point(pos.x-1,pos.y-2));
        }
        if(pos.x+1<8 && pos.y-2>=0){
            moves.add(new Point(pos.x+1,pos.y-2));
        }

        Point[] returned = new Point[moves.size()];
        for(int i=0;i<moves.size();i++){
            returned[i] = moves.get(i);
        }
        return returned;
    }
}
