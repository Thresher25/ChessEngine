package Pieces;

import Main.MainClass;
import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Board {

    public BufferedImage image;
    public ArrayList<ChessPiece> inPlay = new ArrayList<>();
    public ArrayList<ChessPiece> captured = new ArrayList<>();
    public ChessPiece[][] gameBoard = new ChessPiece[8][8];

    public Board(){
        inPlay.add(new King(new Point(4,0),true));
        inPlay.add(new King(new Point(4,7),false));
        inPlay.add(new Queen(new Point(3,0),true));
        inPlay.add(new Queen(new Point(3,7),false));
        inPlay.add(new Rook(new Point(0,0),true));
        inPlay.add(new Rook(new Point(7,0),true));
        inPlay.add(new Rook(new Point(0,7),false));
        inPlay.add(new Rook(new Point(7,7),false));
        inPlay.add(new Bishop(new Point(2,0),true));
        inPlay.add(new Bishop(new Point(5,0),true));
        inPlay.add(new Bishop(new Point(2,7),false));
        inPlay.add(new Bishop(new Point(5,7),false));
        inPlay.add(new Knight(new Point(1,0),true));
        inPlay.add(new Knight(new Point(6,0),true));
        inPlay.add(new Knight(new Point(1,7),false));
        inPlay.add(new Knight(new Point(6,7),false));
        inPlay.add(new Pawn(new Point(0,1),true));
        inPlay.add(new Pawn(new Point(1,1),true));
        inPlay.add(new Pawn(new Point(2,1),true));
        inPlay.add(new Pawn(new Point(3,1),true));
        inPlay.add(new Pawn(new Point(4,1),true));
        inPlay.add(new Pawn(new Point(5,1),true));
        inPlay.add(new Pawn(new Point(6,1),true));
        inPlay.add(new Pawn(new Point(7,1),true));
        inPlay.add(new Pawn(new Point(0,6),false));
        inPlay.add(new Pawn(new Point(1,6),false));
        inPlay.add(new Pawn(new Point(2,6),false));
        inPlay.add(new Pawn(new Point(3,6),false));
        inPlay.add(new Pawn(new Point(4,6),false));
        inPlay.add(new Pawn(new Point(5,6),false));
        inPlay.add(new Pawn(new Point(6,6),false));
        inPlay.add(new Pawn(new Point(7,6),false));

        for(int i=0;i<inPlay.size();i++){
            gameBoard[inPlay.get(i).pos.x][inPlay.get(i).pos.y] = inPlay.get(i);
        }

        try{
            image = ImageIO.read(new File("res/ChessBoard.png"));
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public boolean isPieceAt(int x, int y){
        boolean pieceAt = false;
        for(int i=0;i<gameBoard.length;i++){
            for(int j=0;j<gameBoard[0].length;j++) {
                if (gameBoard[i][j] != null){
                    if (gameBoard[i][j].getPos().equals(new Point(x, y))) {
                        pieceAt = true;
                    }
                }
            }
        }
        return pieceAt;
    }

    public ChessPiece getPiece(int x, int y){
        return gameBoard[x][y];
    }

    public boolean isLegalMove(ChessPiece ch, int x, int y){
        boolean legal = false;
        Point[] possibleMoves = ch.genPossibleMoves();
        for(int i=0;i<possibleMoves.length;i++){
            if(possibleMoves[i].equals(new Point(x,y)) && ch.isWhite()==MainClass.isWhiteTurn){
                legal = true;
                if(MainClass.isWhiteTurn){
                    MainClass.isWhiteTurn = false;
                }else{
                    MainClass.isWhiteTurn = true;
                }
            }
        }

        return legal;
    }

    public void playMove(int ix, int iy, int dx, int dy) {
        if (gameBoard[ix][iy] != null){
            if (isLegalMove(gameBoard[ix][iy], dx, dy)) {
                if (gameBoard[dx][dy]==null) {
                    gameBoard[ix][iy].moveTo(new Point(dx, dy));
                    gameBoard[dx][dy] = gameBoard[ix][iy];
                    gameBoard[ix][iy] = null;
                } else {
                    gameBoard[ix][iy].moveTo(new Point(dx, dy));
                    captured.add(gameBoard[dx][dy]);
                    inPlay.remove(gameBoard[dx][dy]);
                    gameBoard[dx][dy] = gameBoard[ix][iy];
                    gameBoard[ix][iy] = null;
                }
            }
         }
    }

    public void draw(Graphics g){
        g.drawImage(image,230,50,null);
        for(int i=0;i<inPlay.size();i++){
            inPlay.get(i).draw(g);
        }
    }

}
