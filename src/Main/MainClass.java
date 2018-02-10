package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainClass extends JPanel implements MouseListener{

    public JFrame frame;
    /*public short[] board = {00,00,00,00,00,00,00,00,
                              00,00,00,00,00,00,00,00,
                              00,00,00,00,00,00,00,00,
                              00,00,00,00,00,00,00,00,
                              00,11,00,21,00,00,00,00,
                              00,00,00,00,00,00,00,00,
                              00,00,00,00,00,00,00,00,
                              00,00,00,00,00,00,00,00,};*/

        public short[] board = {23,25,24,21,22,24,25,23,
                            26,26,26,26,26,26,26,26,
                            00,00,00,00,00,00,00,00,
                            00,00,00,00,00,00,00,00,
                            00,00,00,00,00,00,00,00,
                            00,00,00,00,00,00,00,00,
                            16,16,16,16,16,16,16,16,
                            13,15,14,11,12,14,15,13};

    public static final short KingW = 21, QueenW  = 22, RookW = 23, BishopW = 24, KnightW = 25, PawnW = 26, KingB = 11, QueenB  = 12, RookB = 13, BishopB = 14, KnightB = 15, PawnB = 16, Empty = 0;
    public short grabbedPiece;
    public boolean movingPiece = false;
    public static boolean isWhiteTurn = true;
    boolean KingWMoved = false, KingBMoved = false, RookW00Moved = false, RookW70Moved = false, RookB07Moved = false, RookB77Moved = false;
    public short x1 = 0, y1 = 0;
    public BufferedImage boardImage, KingWImage, QueenWImage, RookWImage, BishopWImage, KnightWImage, PawnWImage, KingBImage, QueenBImage, RookBImage, BishopBImage, KnightBImage, PawnBImage;
    public String moves = "000000";//a move is a 6 char long string in the format of x1,y1,x2,y2,b or w or e + first letter of piece. example (111306) - piece at b1 moves to b3 captures piece type 06 (White pawn)

    public static void main(String...args){

        MainClass mc = new MainClass();

        while(true){
            mc.frame.repaint();
        }

    }

    public MainClass(){

        try{
            boardImage   = ImageIO.read(new File("res/ChessBoard.png"));
            KingWImage   = ImageIO.read(new File("res/KingW.png"));
            QueenWImage  = ImageIO.read(new File("res/QueenW.png"));
            BishopWImage = ImageIO.read(new File("res/BishopW.png"));
            RookWImage   = ImageIO.read(new File("res/RookW.png"));
            KnightWImage = ImageIO.read(new File("res/KnightW.png"));
            PawnWImage   = ImageIO.read(new File("res/PawnW.png"));
            KingBImage   = ImageIO.read(new File("res/KingB.png"));
            QueenBImage  = ImageIO.read(new File("res/QueenB.png"));
            RookBImage   = ImageIO.read(new File("res/RookB.png"));
            BishopBImage = ImageIO.read(new File("res/BishopB.png"));
            KnightBImage = ImageIO.read(new File("res/KnightB.png"));
            PawnBImage   = ImageIO.read(new File("res/PawnB.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        this.setSize(800, 800);
        this.setVisible(true);
        this.setDoubleBuffered(true);
        this.addMouseListener(this);
        frame = new JFrame("Springroll's Chess Engine");
        frame.setSize(817, 840);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(this);
        frame.repaint();
    }

    public String genMoves(short[] board, boolean whiteToMove){
        String possMoves = "";
         for(int i=0;i<board.length;i++) {
             short px = (short)(i%8);
             short py = (short)(i/8);
            if(whiteToMove){
                int temp = 1;
                switch (board[i]){
                    case KingW:
                        for(int j=-1;j<=1;j++){
                            for(int k=-1;k<=1;k++){
                                temp = 1;
                                try{
                                    while(board[(py+temp*j)*8+(px+temp*k)]==0 && temp<2 && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+"00";
                                        temp++;
                                    }
                                    if(board[(py+temp*j)*8+(px+temp*k)]/10==1 && temp<2 && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+""+board[(py+temp*j)*8+(px+temp*k)];
                                    }
                                }catch(Exception e){
                                    //do nothing
                                }
                            }
                        }
                        break;
                    case QueenW:
                        for(int j=-1;j<=1;j++){
                            for(int k=-1;k<=1;k++){
                                temp = 1;
                                try{
                                    while(board[(py+temp*j)*8+(px+temp*k)]==0 && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+"00";
                                        temp++;
                                    }
                                    if(board[(py+temp*j)*8+(px+temp*k)]/10==1 && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+""+board[(py+temp*j)*8+(px+temp*k)];
                                    }
                                }catch(Exception e){
                                    //do nothing
                                }
                            }
                        }
                        break;
                    case BishopW :
                        for(int j=-1;j<=1;j++){
                            for(int k=-1;k<=1;k++){
                                temp = 1;
                                try{
                                    while(board[(py+temp*j)*8+(px+temp*k)]==0 && j!=0 && k!=0 && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+"00";
                                        temp++;
                                    }
                                    if(board[(py+temp*j)*8+(px+temp*k)]/10==1 && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+""+board[(py+temp*j)*8+(px+temp*k)];
                                    }
                                }catch(Exception e){
                                    //do nothing
                                }
                            }
                        }
                        break;
                    case RookW:
                        for(int j=-1;j<=1;j++){
                            for(int k=-1;k<=1;k++){
                                temp = 1;
                                try{
                                    while(board[(py+temp*j)*8+(px+temp*k)]==0 && ( (j!=0 && k==0) || (j==0 && k!=0) )  && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+"00";
                                        temp++;
                                    }
                                    if(board[(py+temp*j)*8+(px+temp*k)]/10==1 && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+""+board[(py+temp*j)*8+(px+temp*k)];
                                    }
                                }catch(Exception e){
                                    //do nothing
                                }
                            }
                        }
                        break;
                    case KnightW:
                        if(px+1<8 && py+2<8 && ((board[(py+2)*8+(px+1)]==0) || board[(py+2)*8+(px+1)]/10==1)){
                            possMoves+=""+px+""+py+""+(px+1)+""+(py+2)+""+board[(py+2)*8+(px+1)];
                            if(board[(py+2)*8+(px+1)]==0){
                                possMoves+="0";
                            }
                        }
                        if(px-1>=0 && py+2<8 && ((board[(py+2)*8+(px-1)]==0) || board[(py+2)*8+(px-1)]/10==1)){
                            possMoves+=""+px+""+py+""+(px-1)+""+(py+2)+""+board[(py+2)*8+(px-1)];
                            if(board[(py+2)*8+(px-1)]==0){
                                possMoves+="0";
                            }
                        }
                        if(px+2<8 && py+1<8 && ((board[(py+1)*8+(px+2)]==0) || board[(py+1)*8+(px+2)]/10==1)){
                            possMoves+=""+px+""+py+""+(px+2)+""+(py+1)+""+board[(py+1)*8+(px+2)];
                            if(board[(py+1)*8+(px+2)]==0){
                                possMoves+="0";
                            }
                        }
                        if(px-2>=0 && py+1<8 && ((board[(py+1)*8+(px-2)]==0) || board[(py+1)*8+(px-2)]/10==1)){
                            possMoves+=""+px+""+py+""+(px-2)+""+(py+1)+""+board[(py+1)*8+(px-2)];
                            if(board[(py+1)*8+(px-2)]==0){
                                possMoves+="0";
                            }
                        }
                        if(px+2<8 && py-1>=0 && ((board[(py-1)*8+(px+2)]==0) || board[(py-1)*8+(px+2)]/10==1)){
                            possMoves+=""+px+""+py+""+(px+2)+""+(py-1)+""+board[(py-1)*8+(px+2)];
                            if(board[(py-1)*8+(px+2)]==0){
                                possMoves+="0";
                            }
                        }
                        if(px-2>=0 && py-1>=0 && ((board[(py-1)*8+(px-2)]==0) || board[(py-1)*8+(px-2)]/10==1)){
                            possMoves+=""+px+""+py+""+(px-2)+""+(py-1)+""+board[(py-1)*8+(px-2)];
                            if(board[(py-1)*8+(px-2)]==0){
                                possMoves+="0";
                            }
                        }
                        if(px-1>=0 && py-2>=0 && ((board[(py-2)*8+(px-1)]==0) || board[(py-2)*8+(px-1)]/10==1)){
                            possMoves+=""+px+""+py+""+(px-1)+""+(py-2)+""+board[(py-2)*8+(px-1)];
                            if(board[(py-2)*8+(px-1)]==0){
                                possMoves+="0";
                            }
                        }
                        if(px+1<8 && py-2>=0 && ((board[(py-2)*8+(px+1)]==0) || board[(py-2)*8+(px+1)]/10==1)){
                            possMoves+=""+px+""+py+""+(px+1)+""+(py-2)+""+board[(py-2)*8+(px+1)];
                            if(board[(py-2)*8+(px+1)]==0){
                                possMoves+="0";
                            }
                        }
                        break;
                    case PawnW:
                        String lastMove = moves.substring(moves.length()-6, moves.length());
                        if(board[Short.parseShort(lastMove.substring(3,4))*8+Short.parseShort(lastMove.substring(2,3))]==PawnB){
                            if(Short.parseShort(lastMove.substring(1,2))-Short.parseShort(lastMove.substring(3,4))==2){
                              if( (px-Short.parseShort(lastMove.substring(2,3))==1 || px-Short.parseShort(lastMove.substring(2,3))==-1) && py-Short.parseShort(lastMove.substring(3,4))==0){
                                  possMoves+=""+px+""+py+""+(Short.parseShort(lastMove.substring(2,3)))+""+(py+1)+"00";
                              }
                            }
                        }
                        if(py==1 && board[(py+1)*8+px]==0){
                            possMoves+=""+px+""+py+""+(px)+""+(py+1)+"00";
                            if(board[(py+2)*8+px]==0){
                                possMoves+=""+px+""+py+""+(px)+""+(py+2)+"00";
                            }
                        }else if(board[(py+1)*8+px]==0){
                            possMoves+=""+px+""+py+""+(px)+""+(py+1)+"00";
                        }
                        if(px==0){
                            if(board[(py+1)*8+(px+1)]/10==1){
                                possMoves+=""+px+""+py+""+(px+1)+""+(py+1)+""+board[(py+1)*8+(px+1)];
                            }
                        }else if(px==7){
                            if(board[(py+1)*8+(px-1)]/10==1){
                                possMoves+=""+px+""+py+""+(px-1)+""+(py+1)+""+board[(py+1)*8+(px-1)];
                            }
                        }else{
                            if(board[(py+1)*8+(px-1)]/10==1){
                                possMoves+=""+px+""+py+""+(px-1)+""+(py+1)+""+board[(py+1)*8+(px-1)];
                            }
                            if(board[(py+1)*8+(px+1)]/10==1){
                                possMoves+=""+px+""+py+""+(px+1)+""+(py+1)+""+board[(py+1)*8+(px+1)];
                            }
                        }
                        break;
                    default:
                        break;
                }
            }else{
                int temp = 1;
                switch (board[i]){
                    case KingB:
                        for(int j=-1;j<=1;j++){
                            for(int k=-1;k<=1;k++){
                                temp = 1;
                                try{
                                    while(board[(py+temp*j)*8+(px+temp*k)]==0 && temp<2 && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+"00";
                                        temp++;
                                    }
                                    if(board[(py+temp*j)*8+(px+temp*k)]/10==2 && temp<2 && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+""+board[(py+temp*j)*8+(px+temp*k)];
                                    }
                                }catch(Exception e){
                                    //do nothing
                                }
                            }
                        }
                        break;
                    case QueenB:
                        for(int j=-1;j<=1;j++){
                            for(int k=-1;k<=1;k++){
                                temp = 1;
                                try{
                                    while(board[(py+temp*j)*8+(px+temp*k)]==0 && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+"00";
                                        temp++;
                                    }
                                    if(board[(py+temp*j)*8+(px+temp*k)]/10==2 && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+""+board[(py+temp*j)*8+(px+temp*k)];
                                    }
                                }catch(Exception e){
                                    //do nothing
                                }
                            }
                        }
                        break;
                    case RookB:
                        for(int j=-1;j<=1;j++){
                            for(int k=-1;k<=1;k++){
                                temp = 1;
                                try{
                                    while(board[(py+temp*j)*8+(px+temp*k)]==0 && ( (j!=0 && k==0) || (j==0 && k!=0) )  && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+"00";
                                        temp++;
                                    }
                                    if(board[(py+temp*j)*8+(px+temp*k)]/10==2 && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+""+board[(py+temp*j)*8+(px+temp*k)];
                                    }
                                }catch(Exception e){
                                    //do nothing
                                }
                            }
                        }
                        break;
                    case BishopB:
                        for(int j=-1;j<=1;j++){
                            for(int k=-1;k<=1;k++){
                                temp = 1;
                                try{
                                    while(board[(py+temp*j)*8+(px+temp*k)]==0 && j!=0 && k!=0 && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+"00";
                                        temp++;
                                    }
                                    if(board[(py+temp*j)*8+(px+temp*k)]/10==2 && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+""+board[(py+temp*j)*8+(px+temp*k)];
                                    }
                                }catch(Exception e){
                                    //do nothing
                                }
                            }
                        }
                        break;
                    case KnightB:
                        if(px+1<8 && py+2<8 && ((board[(py+2)*8+(px+1)]==0) || board[(py+2)*8+(px+1)]/10==2)){
                            possMoves+=""+px+""+py+""+(px+1)+""+(py+2)+""+board[(py+2)*8+(px+1)];
                            if(board[(py+2)*8+(px+1)]==0){
                                possMoves+="0";
                            }
                        }
                        if(px-1>=0 && py+2<8 && ((board[(py+2)*8+(px-1)]==0) || board[(py+2)*8+(px-1)]/10==2)){
                            possMoves+=""+px+""+py+""+(px-1)+""+(py+2)+""+board[(py+2)*8+(px-1)];
                            if(board[(py+2)*8+(px-1)]==0){
                                possMoves+="0";
                            }
                        }
                        if(px+2<8 && py+1<8 && ((board[(py+1)*8+(px+2)]==0) || board[(py+1)*8+(px+2)]/10==2)){
                            possMoves+=""+px+""+py+""+(px+2)+""+(py+1)+""+board[(py+1)*8+(px+2)];
                            if(board[(py+1)*8+(px+2)]==0){
                                possMoves+="0";
                            }
                        }
                        if(px-2>=0 && py+1<8 && ((board[(py+1)*8+(px-2)]==0) || board[(py+1)*8+(px-2)]/10==2)){
                            possMoves+=""+px+""+py+""+(px-2)+""+(py+1)+""+board[(py+1)*8+(px-2)];
                            if(board[(py+1)*8+(px-2)]==0){
                                possMoves+="0";
                            }
                        }
                        if(px+2<8 && py-1>=0 && ((board[(py-1)*8+(px+2)]==0) || board[(py-1)*8+(px+2)]/10==2)){
                            possMoves+=""+px+""+py+""+(px+2)+""+(py-1)+""+board[(py-1)*8+(px+2)];
                            if(board[(py-1)*8+(px+2)]==0){
                                possMoves+="0";
                            }
                        }
                        if(px-2>=0 && py-1>=0 && ((board[(py-1)*8+(px-2)]==0) || board[(py-1)*8+(px-2)]/10==2)){
                            possMoves+=""+px+""+py+""+(px-2)+""+(py-1)+""+board[(py-1)*8+(px-2)];
                            if(board[(py-1)*8+(px-2)]==0){
                                possMoves+="0";
                            }
                        }
                        if(px-1>=0 && py-2>=0 && ((board[(py-2)*8+(px-1)]==0) || board[(py-2)*8+(px-1)]/10==2)){
                            possMoves+=""+px+""+py+""+(px-1)+""+(py-2)+""+board[(py-2)*8+(px-1)];
                            if(board[(py-2)*8+(px-1)]==0){
                                possMoves+="0";
                            }
                        }
                        if(px+1<8 && py-2>=0 && ((board[(py-2)*8+(px+1)]==0) || board[(py-2)*8+(px+1)]/10==2)){
                            possMoves+=""+px+""+py+""+(px+1)+""+(py-2)+""+board[(py-2)*8+(px+1)];
                            if(board[(py-2)*8+(px+1)]==0){
                                possMoves+="0";
                            }
                        }
                        break;
                    case PawnB:
                        String lastMove = moves.substring(moves.length()-6, moves.length());
                        if(board[Short.parseShort(lastMove.substring(3,4))*8+Short.parseShort(lastMove.substring(2,3))]==PawnW){
                            if(Short.parseShort(lastMove.substring(1,2))-Short.parseShort(lastMove.substring(3,4))==-2){
                                if( (px-Short.parseShort(lastMove.substring(2,3))==1 || px-Short.parseShort(lastMove.substring(2,3))==-1) && py-Short.parseShort(lastMove.substring(3,4))==0){
                                    possMoves+=""+px+""+py+""+(Short.parseShort(lastMove.substring(2,3)))+""+(py-1)+"00";
                                }
                            }
                        }
                        if(py==6 && board[(py-1)*8+px]==0){
                            possMoves+=""+px+""+py+""+(px)+""+(py-1)+"00";
                            if(board[(py-2)*8+px]==0){
                                possMoves+=""+px+""+py+""+(px)+""+(py-2)+"00";
                            }
                        }else if(board[(py-1)*8+px]==0){
                            possMoves+=""+px+""+py+""+(px)+""+(py-1)+"00";
                        }
                        if(px==0){
                            if(board[(py-1)*8+(px+1)]/10==2 && board[(py-1)*8+(px+1)]!=0){
                                possMoves+=""+px+""+py+""+(px+1)+""+(py-1)+""+board[(py-1)*8+(px+1)];
                            }
                        }else if(px==7){
                            if(board[(py-1)*8+(px-1)]/10==2 && board[(py-1)*8+(px-1)]!=0){
                                possMoves+=""+px+""+py+""+(px-1)+""+(py-1)+""+board[(py-1)*8+(px-1)];
                            }
                        }else{
                            if(board[(py-1)*8+(px-1)]/10==2 && board[(py-1)*8+(px-1)]!=0){
                                possMoves+=""+px+""+py+""+(px-1)+""+(py-1)+""+board[(py-1)*8+(px-1)];
                            }
                            if(board[(py-1)*8+(px+1)]/10==2 && board[(py-1)*8+(px+1)]!=0){
                                possMoves+=""+px+""+py+""+(px+1)+""+(py-1)+""+board[(py-1)*8+(px+1)];
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
         }
         return possMoves;
    }

    public void printAr(short[] p){
        for(int i=0;i<8;i++){
            System.out.println(p[i*8]+", "+p[i*8+1]+", "+p[i*8+2]+", "+p[i*8+3]+", "+p[i*8+4]+", "+p[i*8+5]+", "+p[i*8+6]+", "+p[i*8+7]);
        }
    }

    public boolean isLegalMove(boolean whiteToMove, String toMove){
        short ix = Short.parseShort(toMove.substring(0,1));
        short iy = Short.parseShort(toMove.substring(1,2));
        short dx = Short.parseShort(toMove.substring(2,3));
        short dy = Short.parseShort(toMove.substring(3,4));
        short capPiece = Short.parseShort(toMove.substring(4,6));
        short testBoard[] = board.clone();
        String movesNow = genMoves(board,whiteToMove);
        System.out.println(toMove);
        System.out.println(movesNow);
        boolean valid = false;
        for(int i=0;i<movesNow.length();i+=6){
            if(toMove.equals(movesNow.substring(i,i+6))){
                valid = true;
            }
        }
        if(!valid){
            return false;
        }
        playMove(testBoard,ix,iy,dx,dy,capPiece);
        boolean KingCaptured = false;
        String possibleMoves = genMoves(testBoard,!whiteToMove);
        System.out.println(possibleMoves);
        int maxMoves = possibleMoves.length()/6;
        int KingsSafe = 0;
        if(whiteToMove){
            for(int i=0;i<possibleMoves.length();i+=6){
                short pix = Short.parseShort(possibleMoves.substring(i,i+6).substring(0,1));
                short piy = Short.parseShort(possibleMoves.substring(i,i+6).substring(1,2));
                short pdx = Short.parseShort(possibleMoves.substring(i,i+6).substring(2,3));
                short pdy = Short.parseShort(possibleMoves.substring(i,i+6).substring(3,4));
                short pcapPiece = Short.parseShort(possibleMoves.substring(i,i+6).substring(4,6));
                short[] temp = testBoard.clone();
                playMove(temp,pix,piy,pdx,pdy,pcapPiece);
                for(int k=0;k<temp.length;k++){
                    if(temp[k]==KingW){
                        KingsSafe++;
                    }
                }
            }
        }else{
            for(int i=0;i<possibleMoves.length();i+=6){
                short pix = Short.parseShort(possibleMoves.substring(i,i+6).substring(0,1));
                short piy = Short.parseShort(possibleMoves.substring(i,i+6).substring(1,2));
                short pdx = Short.parseShort(possibleMoves.substring(i,i+6).substring(2,3));
                short pdy = Short.parseShort(possibleMoves.substring(i,i+6).substring(3,4));
                short pcapPiece = Short.parseShort(possibleMoves.substring(i,i+6).substring(4,6));
                short[] temp = testBoard.clone();
                playMove(temp,pix,piy,pdx,pdy,pcapPiece);
                for(int k=0;k<temp.length;k++){
                    if(temp[k]==KingB){
                        KingsSafe++;
                    }
                }
            }
        }
        if(KingsSafe==maxMoves){
            KingCaptured = false;
            System.out.println(maxMoves);
            System.out.println(KingsSafe);
        }else{
            KingCaptured = true;
            System.out.println(maxMoves);
            System.out.println(KingsSafe);
        }
        return !KingCaptured;
    }

    public void playMove(short[] pBoard, short ix, short iy, short dx, short dy, short capPiece){
        if( (pBoard[iy*8+ix]==PawnW || pBoard[iy*8+ix]==PawnB) && (dx-ix!=0 && dy-iy!=0) && (pBoard[dy*8+dx]==0) ){//check for en passant
                pBoard[iy*8+dx] = 0;
        }
        pBoard[dy*8+dx] = pBoard[iy*8+ix];
        pBoard[iy*8+ix] = 0;
    }

    public void playMove(short[] pBoard, String playMove){
        short ix = Short.parseShort(playMove.substring(0,1));
        short iy = Short.parseShort(playMove.substring(1,2));
        short dx = Short.parseShort(playMove.substring(2,3));
        short dy = Short.parseShort(playMove.substring(3,4));
        short capPiece = Short.parseShort(playMove.substring(4,6));
        if( (pBoard[iy*8+ix]==PawnW || pBoard[iy*8+ix]==PawnB) && (dx-ix!=0 && dy-iy!=0) && (pBoard[dy*8+dx]==0) ){//check for en passant
            pBoard[iy*8+dx] = 0;
        }
        pBoard[dy*8+dx] = pBoard[iy*8+ix];
        pBoard[iy*8+ix] = 0;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0,0,1920,1080);

        g.drawImage(boardImage,0,0,null);
        //draw pieces
        for(int i=0;i<board.length;i++){
            switch (board[i]){
                case KingW:
                    g.drawImage(KingWImage,(i%8)*100,i/8*100,null);
                    break;
                case QueenW:
                    g.drawImage(QueenWImage,(i%8)*100,i/8*100,null);
                    break;
                case BishopW :
                    g.drawImage(BishopWImage,(i%8)*100,i/8*100,null);
                    break;
                case RookW:
                    g.drawImage(RookWImage,(i%8)*100,i/8*100,null);
                    break;
                case KnightW:
                    g.drawImage(KnightWImage,(i%8)*100,i/8*100,null);
                    break;
                case PawnW:
                    g.drawImage(PawnWImage,(i%8)*100,i/8*100,null);
                    break;
                case KingB:
                    g.drawImage(KingBImage,(i%8)*100,i/8*100,null);
                    break;
                case QueenB:
                    g.drawImage(QueenBImage,(i%8)*100,i/8*100,null);
                    break;
                case RookB:
                    g.drawImage(RookBImage,(i%8)*100,i/8*100,null);
                    break;
                case BishopB:
                    g.drawImage(BishopBImage,(i%8)*100,i/8*100,null);
                    break;
                case KnightB:
                    g.drawImage(KnightBImage,(i%8)*100,i/8*100,null);
                    break;
                case PawnB:
                    g.drawImage(PawnBImage,(i%8)*100,i/8*100,null);
                    break;
                default:
                    break;
            }
        }

    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!movingPiece){
            x1 = (short)(e.getX()/100);
            y1 = (short)(e.getY()/100);
            if(board[y1*8+x1]!=0){
                if(isWhiteTurn){
                    if(board[y1*8+x1]/10==2){
                        movingPiece = true;
                        grabbedPiece = board[y1*8+x1];
                    }
                }else{
                    if(board[y1*8+x1]/10==1){
                        movingPiece = true;
                        grabbedPiece = board[y1*8+x1];
                    }
                }
            }
        }else{
            short x2 = (short)(e.getX()/100);
            short y2 = (short)(e.getY()/100);
            //check if legal
            String attemptMove = "";
            if(board[y2*8+x2]==0){
                attemptMove = (""+x1+""+y1+""+x2+""+y2+""+"00");
            }else{
                attemptMove = (""+x1+""+y1+""+x2+""+y2+""+board[y2*8+x2]);
            }
            if(isLegalMove(isWhiteTurn,attemptMove)){
                //board[y1*8+x1] = 0;
                //board[y2*8+x2] = grabbedPiece;
                playMove(board,attemptMove);
                isWhiteTurn = !isWhiteTurn;
                System.out.println("Legal Move");
                moves+=attemptMove;
            }else{
                System.out.println("Illegal move");
            }
            grabbedPiece = 0;
            movingPiece = false;
            System.out.println("Move made, is it whites turn next:  "+isWhiteTurn);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
