package Main;

import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MainClass extends JPanel implements MouseListener, ActionListener{

    public JFrame frame;
    public static short[] board = {00,00,00,00,00,00,00,00,
                                   00,00,00,00,00,00,00,26,
                                   00,00,11,00,00,00,00,00,
                                   00,00,00,00,00,00,00,00,
                                   00,22,00,00,00,00,00,00,
                                   00,00,00,00,00,00,00,00,
                                   16,00,00,00,00,00,00,16,
                                   00,00,00,000,00,21,00,00,};


    //public static short[] board = {23,25,24,22,21,24,25,23,
    //                               26,26,26,26,26,26,26,26,
    //                               00,00,00,00,00,00,00,00,
    //                               00,00,00,00,00,00,00,00,
    //                               00,00,00,00,00,00,00,00,
    //                               00,00,00,00,00,00,00,00,
    //                               16,16,16,16,16,16,16,16,
    //                               13,15,14,12,11,14,15,13};
    public static short[] origBoard = board;
    public static final short KingW = 21, QueenW  = 22, RookW = 23, BishopW = 24, KnightW = 25, PawnW = 26, KingB = 11, QueenB  = 12, RookB = 13, BishopB = 14, KnightB = 15, PawnB = 16, Empty = 0;
    public short grabbedPiece;
    public boolean movingPiece = false;
    public static boolean isWhiteTurn = true;
    public static boolean AIPlaysWhite = false;
    public ChessAI chessAI;
    public boolean buttonClicked = false;
    public static short pawnPromoteTo = 0;
    public JButton queenButton, rookButton, bishopButton, knightButton, aiWhiteButton, aiBlackButton;
    public static boolean KingWMoved = false, KingBMoved = false, RookW00Moved = false, RookW70Moved = false, RookB07Moved = false, RookB77Moved = false;
    public short x1 = 0, y1 = 0, x2=0, y2=0;
    public BufferedImage boardImage, KingWImage, QueenWImage, RookWImage, BishopWImage, KnightWImage, PawnWImage, KingBImage, QueenBImage, RookBImage, BishopBImage, KnightBImage, PawnBImage;
    public static String moves = "000000";//a move is a 6 char long string in the format of x1,y1,x2,y2,b or w or e + first letter of piece. example (111306) - piece at b1 moves to b3 captures piece type 06 (White pawn)

    public static void main(String...args)throws Exception{

        MainClass mc = new MainClass();

        while(true){
            Thread.sleep(15);
            mc.frame.repaint();
            mc.update();
        }

    }

    public MainClass(){

        try{
            ClassLoader loader = MainClass.class.getClassLoader();
            boardImage   = ImageIO.read(loader.getResource("ChessBoard.png"));
            KingWImage   = ImageIO.read(loader.getResource("KingW.png"));
            QueenWImage  = ImageIO.read(loader.getResource("QueenW.png"));
            BishopWImage = ImageIO.read(loader.getResource("BishopW.png"));
            RookWImage   = ImageIO.read(loader.getResource("RookW.png"));
            KnightWImage = ImageIO.read(loader.getResource("KnightW.png"));
            PawnWImage   = ImageIO.read(loader.getResource("PawnW.png"));
            KingBImage   = ImageIO.read(loader.getResource("KingB.png"));
            QueenBImage  = ImageIO.read(loader.getResource("QueenB.png"));
            RookBImage   = ImageIO.read(loader.getResource("RookB.png"));
            BishopBImage = ImageIO.read(loader.getResource("BishopB.png"));
            KnightBImage = ImageIO.read(loader.getResource("KnightB.png"));
            PawnBImage   = ImageIO.read(loader.getResource("PawnB.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
        this.setLayout(null);
        queenButton = new JButton("Promote to Queen");
        queenButton.setBounds(0,0,150,150);
        queenButton.setActionCommand("prQueen");
        queenButton.addActionListener(this);
        rookButton = new JButton("Promote to Rook");
        rookButton.setBounds(150,0,150,150);
        rookButton.setActionCommand("prRook");
        rookButton.addActionListener(this);
        bishopButton = new JButton("Promote to Bishop");
        bishopButton.setBounds(300,0,150,150);
        bishopButton.setActionCommand("prBishop");
        bishopButton.addActionListener(this);
        knightButton = new JButton("Promote to Knight");
        knightButton.setBounds(450,0,150,150);
        knightButton.setActionCommand("prKnight");
        knightButton.addActionListener(this);
        queenButton.setVisible(false);
        rookButton.setVisible(false);
        bishopButton.setVisible(false);
        knightButton.setVisible(false);
        aiWhiteButton = new JButton("AI Plays White");
        aiWhiteButton.setVisible(true);
        aiWhiteButton.setBounds(0,0,800,400);
        aiWhiteButton.setActionCommand("AIWhite");
        aiWhiteButton.addActionListener(this);
        aiBlackButton = new JButton("AI Plays Black");
        aiBlackButton.setVisible(true);
        aiBlackButton.setBounds(0,400,800,400);
        aiBlackButton.setActionCommand("AIBlack");
        aiBlackButton.addActionListener(this);

        this.setSize(800, 800);
        this.setVisible(true);
        this.setDoubleBuffered(true);
        this.addMouseListener(this);
        this.add(queenButton);
        this.add(rookButton);
        this.add(bishopButton);
        this.add(knightButton);
        this.add(aiWhiteButton);
        this.add(aiBlackButton);
        frame = new JFrame("Springroll's Chess Engine");
        frame.setSize(817, 840);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(this);
        frame.repaint();
    }

    public static String genMoves(short[] board, boolean whiteToMove){
        String possMoves = "";
        String capMoves = "";
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
                                        capMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+""+board[(py+temp*j)*8+(px+temp*k)];
                                    }
                                }catch(Exception e){
                                    //do nothing
                                }
                            }
                        }
                        if(!KingWMoved && !RookW00Moved && board[1]==0 && board[2]==0 && board[3]==0 && (board[4]==KingW && board[0]==RookW) ){
                            possMoves+=""+px+""+py+""+(px-2)+""+py+"00";
                        }else if(!KingWMoved && !RookW70Moved && board[5]==0 && board[6]==0 && (board[4]==KingW && board[7]==RookW) ){
                            possMoves+=""+px+""+py+""+(px+2)+""+py+"00";
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
                                        capMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+""+board[(py+temp*j)*8+(px+temp*k)];
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
                                        if(temp*k!=0 && temp*j!=0){
                                            possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+"00";
                                        }
                                        temp++;
                                    }
                                    if(board[(py+temp*j)*8+(px+temp*k)]/10==1 && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        if(temp*k!=0 && temp*j!=0){
                                            capMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+""+board[(py+temp*j)*8+(px+temp*k)];
                                        }
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
                                        if( (temp*k==0 && temp*j!=0) || (temp*k!=0 && temp*j==0) ){
                                            possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+"00";
                                        }
                                        temp++;
                                    }
                                    if(board[(py+temp*j)*8+(px+temp*k)]/10==1 && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        if( (temp*k==0 && temp*j!=0) || (temp*k!=0 && temp*j==0) ){
                                            capMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+""+board[(py+temp*j)*8+(px+temp*k)];
                                        }
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
                        if(board[Short.parseShort(lastMove.substring(3,4))*8+Short.parseShort(lastMove.substring(2,3))]==PawnB){//check for an en passent
                            if(Short.parseShort(lastMove.substring(1,2))-Short.parseShort(lastMove.substring(3,4))==2){
                              if( (px-Short.parseShort(lastMove.substring(2,3))==1 || px-Short.parseShort(lastMove.substring(2,3))==-1) && py-Short.parseShort(lastMove.substring(3,4))==0){
                                  capMoves+=""+px+""+py+""+(Short.parseShort(lastMove.substring(2,3)))+""+(py+1)+"00";
                              }
                            }
                        }
                        if(py==1 && board[(py+1)*8+px]==0){
                            possMoves+=""+px+""+py+""+(px)+""+(py+1)+"00";
                            if(board[(py+2)*8+px]==0){
                                possMoves+=""+px+""+py+""+(px)+""+(py+2)+"00";
                            }
                        }else if(board[(py+1)*8+px]==0){
                            if(py==6){
                                possMoves+=""+px+""+py+""+(px)+""+(py+1)+"22";
                                possMoves+=""+px+""+py+""+(px)+""+(py+1)+"23";
                                possMoves+=""+px+""+py+""+(px)+""+(py+1)+"24";
                                possMoves+=""+px+""+py+""+(px)+""+(py+1)+"25";
                            }else{
                                possMoves+=""+px+""+py+""+(px)+""+(py+1)+"00";
                            }
                        }
                        if(px==0){
                            if(board[(py+1)*8+(px+1)]/10==1){
                                if(py==6){
                                    possMoves+=""+px+""+py+""+(px+1)+""+(py+1)+"22";
                                    possMoves+=""+px+""+py+""+(px+1)+""+(py+1)+"23";
                                    possMoves+=""+px+""+py+""+(px+1)+""+(py+1)+"24";
                                    possMoves+=""+px+""+py+""+(px+1)+""+(py+1)+"25";
                                }else{
                                    capMoves+=""+px+""+py+""+(px+1)+""+(py+1)+""+board[(py+1)*8+(px+1)];
                                }
                            }
                        }else if(px==7){
                            if(board[(py+1)*8+(px-1)]/10==1){
                                if(py==6){
                                    possMoves+=""+px+""+py+""+(px-1)+""+(py+1)+"22";
                                    possMoves+=""+px+""+py+""+(px-1)+""+(py+1)+"23";
                                    possMoves+=""+px+""+py+""+(px-1)+""+(py+1)+"24";
                                    possMoves+=""+px+""+py+""+(px-1)+""+(py+1)+"25";
                                }else{
                                    capMoves+=""+px+""+py+""+(px-1)+""+(py+1)+""+board[(py+1)*8+(px-1)];
                                }
                            }
                        }else{
                            if(board[(py+1)*8+(px-1)]/10==1){
                                if(py==6){
                                    possMoves+=""+px+""+py+""+(px-1)+""+(py+1)+"22";
                                    possMoves+=""+px+""+py+""+(px-1)+""+(py+1)+"23";
                                    possMoves+=""+px+""+py+""+(px-1)+""+(py+1)+"24";
                                    possMoves+=""+px+""+py+""+(px-1)+""+(py+1)+"25";
                                }else{
                                    capMoves+=""+px+""+py+""+(px-1)+""+(py+1)+""+board[(py+1)*8+(px-1)];
                                }
                            }
                            if(board[(py+1)*8+(px+1)]/10==1){
                                if(py==6){
                                    possMoves+=""+px+""+py+""+(px+1)+""+(py+1)+"22";
                                    possMoves+=""+px+""+py+""+(px+1)+""+(py+1)+"23";
                                    possMoves+=""+px+""+py+""+(px+1)+""+(py+1)+"24";
                                    possMoves+=""+px+""+py+""+(px+1)+""+(py+1)+"25";
                                }else{
                                    capMoves+=""+px+""+py+""+(px+1)+""+(py+1)+""+board[(py+1)*8+(px+1)];
                                }
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
                                        capMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+""+board[(py+temp*j)*8+(px+temp*k)];
                                    }
                                }catch(Exception e){
                                    //do nothing
                                }
                            }
                        }
                        if(!KingBMoved && !RookB07Moved && board[(7)*8+1]==0 && board[(7)*8+2]==0 && board[(7)*8+3]==0 && (board[7*8+4]==KingB && board[7*8]==RookB) ){
                            possMoves+=""+px+""+py+""+(px-2)+""+py+"00";
                        }
                        if(!KingBMoved && !RookB77Moved && board[(7)*8+5]==0 && board[(7)*8+6]==0 && (board[7*8+4]==KingB && board[7*8+7]==RookB) ){
                            possMoves+=""+px+""+py+""+(px+2)+""+py+"00";
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
                                        capMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+""+board[(py+temp*j)*8+(px+temp*k)];
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
                                        if( (temp*k==0 && temp*j!=0) || (temp*k!=0 && temp*j==0) ){
                                            possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+"00";
                                        }
                                        temp++;
                                    }
                                    if(board[(py+temp*j)*8+(px+temp*k)]/10==2 && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        if( (temp*k==0 && temp*j!=0) || (temp*k!=0 && temp*j==0) ){
                                            capMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+""+board[(py+temp*j)*8+(px+temp*k)];
                                        }
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
                                        if(temp*k!=0 && temp*j!=0){
                                            possMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+"00";
                                        }
                                        temp++;
                                    }
                                    if(board[(py+temp*j)*8+(px+temp*k)]/10==2 && ((py+temp*j)>=0 && (py+temp*j)<8 && (px+temp*k)>=0 && (px+temp*k)<8) ){
                                        if(temp*k!=0 && temp*j!=0){
                                            capMoves+=""+px+""+py+""+(px+temp*k)+""+(py+temp*j)+""+board[(py+temp*j)*8+(px+temp*k)];
                                        }
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
                        if(board[Short.parseShort(lastMove.substring(3,4))*8+Short.parseShort(lastMove.substring(2,3))]==PawnW){//check for an en passent
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
                            if(py==1){
                                possMoves+=""+px+""+py+""+(px)+""+(py-1)+"12";
                                possMoves+=""+px+""+py+""+(px)+""+(py-1)+"13";
                                possMoves+=""+px+""+py+""+(px)+""+(py-1)+"14";
                                possMoves+=""+px+""+py+""+(px)+""+(py-1)+"15";
                            }else{
                                possMoves+=""+px+""+py+""+(px)+""+(py-1)+"00";
                            }
                        }
                        if(px==0){
                            if(board[(py-1)*8+(px+1)]/10==2 && board[(py-1)*8+(px+1)]!=0){
                                if(py==1){
                                    possMoves+=""+px+""+py+""+(px+1)+""+(py-1)+"12";
                                    possMoves+=""+px+""+py+""+(px+1)+""+(py-1)+"13";
                                    possMoves+=""+px+""+py+""+(px+1)+""+(py-1)+"14";
                                    possMoves+=""+px+""+py+""+(px+1)+""+(py-1)+"15";
                                }else{
                                    capMoves+=""+px+""+py+""+(px+1)+""+(py-1)+""+board[(py-1)*8+(px+1)];
                                }
                            }
                        }else if(px==7){
                            if(board[(py-1)*8+(px-1)]/10==2 && board[(py-1)*8+(px-1)]!=0){
                                if(py==1){
                                    possMoves+=""+px+""+py+""+(px-1)+""+(py-1)+"12";
                                    possMoves+=""+px+""+py+""+(px-1)+""+(py-1)+"13";
                                    possMoves+=""+px+""+py+""+(px-1)+""+(py-1)+"14";
                                    possMoves+=""+px+""+py+""+(px-1)+""+(py-1)+"15";
                                }else{
                                    capMoves+=""+px+""+py+""+(px-1)+""+(py-1)+""+board[(py-1)*8+(px-1)];
                                }
                            }
                        }else{
                            if(board[(py-1)*8+(px-1)]/10==2 && board[(py-1)*8+(px-1)]!=0){
                                if(py==1){
                                    possMoves+=""+px+""+py+""+(px-1)+""+(py-1)+"12";
                                    possMoves+=""+px+""+py+""+(px-1)+""+(py-1)+"13";
                                    possMoves+=""+px+""+py+""+(px-1)+""+(py-1)+"14";
                                    possMoves+=""+px+""+py+""+(px-1)+""+(py-1)+"15";
                                }else{
                                    capMoves+=""+px+""+py+""+(px-1)+""+(py-1)+""+board[(py-1)*8+(px-1)];
                                }
                            }
                            if(board[(py-1)*8+(px+1)]/10==2 && board[(py-1)*8+(px+1)]!=0){
                                if(py==1){
                                    possMoves+=""+px+""+py+""+(px+1)+""+(py-1)+"12";
                                    possMoves+=""+px+""+py+""+(px+1)+""+(py-1)+"13";
                                    possMoves+=""+px+""+py+""+(px+1)+""+(py-1)+"14";
                                    possMoves+=""+px+""+py+""+(px+1)+""+(py-1)+"15";
                                }else{
                                    capMoves+=""+px+""+py+""+(px+1)+""+(py-1)+""+board[(py-1)*8+(px+1)];
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
         }
         possMoves = capMoves+""+possMoves;
         return possMoves;
    }

    public static String genAllLegalMoves(short[] pboard, boolean whiteToMove, boolean playerCheck){
        String legalMoves = "";
        String candidateMoves = genMoves(pboard,whiteToMove);

        for (int i = 0; i < candidateMoves.length(); i+=6) {
            String pTempMove = candidateMoves.substring(i,i+6);
            if(isLegalMove(whiteToMove, pTempMove, pboard, playerCheck)){
                legalMoves+=pTempMove;
            }
        }
        return legalMoves;
    }

    public void printAr(short[] p){
        for(int i=0;i<8;i++){
            System.out.println(p[i*8]+", "+p[i*8+1]+", "+p[i*8+2]+", "+p[i*8+3]+", "+p[i*8+4]+", "+p[i*8+5]+", "+p[i*8+6]+", "+p[i*8+7]);
        }
    }

    public static boolean KingInCheck(short[] pboard, boolean whitesKing){
        boolean checked = false;
        int kingPos = 0;
        for (int i = 0; i < pboard.length; i++) {
            if(whitesKing){
                if(pboard[i]==KingW)
                    kingPos = i;
            }else{
                if(pboard[i]==KingB)
                    kingPos = i;
            }
        }
        int kingX = (short)(kingPos%8);
        int kingY = (short)(kingPos/8);
        for (int i=0;i<pboard.length;i++){
            if(checked){
                break;
            }
            int x = (i%8);
            int y = (i/8);
            int difx = x-kingX;
            int dify = y-kingY;
            if(whitesKing){
                switch(pboard[i]){
                    case PawnB:
                        if(y-kingY==1 && (x-kingX==-1 || x-kingX==1) ){
                            return true;
                        }
                        break;
                    case KnightB:
                        if( ( (dify==2 || dify==-2) && (difx==1 || difx==-1) ) || ( (difx==2 || difx==-2) && (dify==1 || dify==-1) )){
                            return true;
                        }
                        break;
                    case BishopB:
                        if(difx==dify || difx==-dify){
                            boolean blocked = false;
                            if(dify<0){
                                if(difx<0){//bishop at bottom left
                                    for(int k=1;k<=-difx;k++){
                                        if(pboard[(y+k)*8+(x+k)]!=0 && pboard[(y+k)*8+(x+k)]!=KingW){
                                            blocked = true;
                                        }
                                    }
                                }else{//bishop at bottom right
                                    for(int k=1;k<=difx;k++){
                                        if(pboard[(y+k)*8+(x-k)]!=0 && pboard[(y+k)*8+(x-k)]!=KingW){
                                            blocked = true;
                                        }
                                    }
                                }
                            }else{
                                if(difx<0){//bishop at top left
                                    for(int k=1;k<=dify;k++){
                                        if(pboard[(y-k)*8+(x+k)]!=0 && pboard[(y-k)*8+(x+k)]!=KingW){
                                            blocked = true;
                                        }
                                    }
                                }else{//bishop at top right
                                    for(int k=1;k<=difx;k++){
                                        if(pboard[(y-k)*8+(x-k)]!=0 && pboard[(y-k)*8+(x-k)]!=KingW){
                                            blocked = true;
                                        }
                                    }
                                }
                            }
                            if(!blocked){
                                return true;
                            }
                        }
                        break;
                    case RookB:
                        if(difx==0 && dify!=0){
                            boolean blocked = false;
                            if(dify<0){//rook below king
                                for(int k=1;k<=-dify;k++){
                                    if(pboard[(y+k)*8+x]!=0 && pboard[(y+k)*8+x]!=KingW){
                                        blocked = true;
                                    }
                                }
                            }else{//rook above king
                                for(int k=1;k<=dify;k++){
                                    if(pboard[(y-k)*8+x]!=0 && pboard[(y-k)*8+x]!=KingW){
                                        blocked = true;
                                    }
                                }
                            }
                            if(!blocked){
                                return true;
                            }
                        }else if(difx!=0 && dify==0){
                            boolean blocked = false;
                            if(difx<0){//rook to the left of king
                                for(int k=1;k<=-difx;k++){
                                    if(pboard[(y)*8+(x+k)]!=0 && pboard[(y)*8+(x+k)]!=KingW){
                                        blocked = true;
                                    }
                                }
                            }else{//rook to the right of king
                                for(int k=1;k<=difx;k++){
                                    if(pboard[(y)*8+(x-k)]!=0 && pboard[(y)*8+(x-k)]!=KingW){
                                        blocked = true;
                                    }
                                }
                            }
                            if(!blocked){
                                return true;
                            }
                        }
                        break;
                    case QueenB:
                        if(difx==dify || difx==-dify){
                            boolean blocked = false;
                            if(dify<0){
                                if(difx<0){//queen at bottom left
                                    for(int k=1;k<=-difx;k++){
                                        if(pboard[(y+k)*8+(x+k)]!=0 && pboard[(y+k)*8+(x+k)]!=KingW){
                                            blocked = true;
                                        }
                                    }
                                }else{//queen at bottom right
                                    for(int k=1;k<=difx;k++){
                                        if(pboard[(y+k)*8+(x-k)]!=0 && pboard[(y+k)*8+(x-k)]!=KingW){
                                            blocked = true;
                                        }
                                    }
                                }
                            }else{
                                if(difx<0){//queen at top left
                                    for(int k=1;k<=dify;k++){
                                        if(pboard[(y-k)*8+(x+k)]!=0 && pboard[(y-k)*8+(x+k)]!=KingW){
                                            blocked = true;
                                        }
                                    }
                                }else{//queen at top right
                                    for(int k=1;k<=difx;k++){
                                        if(pboard[(y-k)*8+(x-k)]!=0 && pboard[(y-k)*8+(x-k)]!=KingW){
                                            blocked = true;
                                        }
                                    }
                                }
                            }
                            if(!blocked){
                                return true;
                            }
                        }
                        if(difx==0 && dify!=0){
                            boolean blocked = false;
                            if(dify<0){//queen below king
                                for(int k=1;k<=-dify;k++){
                                    if(pboard[(y+k)*8+x]!=0 && pboard[(y+k)*8+x]!=KingW){
                                        blocked = true;
                                    }
                                }
                            }else{//queen above king
                                for(int k=1;k<=dify;k++){
                                    if(pboard[(y-k)*8+x]!=0 && pboard[(y-k)*8+x]!=KingW){
                                        blocked = true;
                                    }
                                }
                            }
                            if(!blocked){
                                return true;
                            }
                        }else if(difx!=0 && dify==0){
                            boolean blocked = false;
                            if(difx<0){//queen to the left of king
                                for(int k=1;k<=-difx;k++){
                                    if(pboard[(y)*8+(x+k)]!=0 && pboard[(y)*8+(x+k)]!=KingW){
                                        blocked = true;
                                    }
                                }
                            }else{//queen to the right of king
                                for(int k=1;k<=difx;k++){
                                    if(pboard[(y)*8+(x-k)]!=0 && pboard[(y)*8+(x-k)]!=KingW){
                                        blocked = true;
                                    }
                                }
                            }
                            if(!blocked){
                                return true;
                            }
                        }
                        break;
                    case KingB:
                        if( (difx==0 || difx==1 || difx==-1) && (dify==0 || dify==1 || dify==-1) ){
                            return true;
                        }
                        break;
                }
            }else{
                switch(pboard[i]){
                    case PawnW:
                        if(y-kingY==-1 && (x-kingX==-1 || x-kingX==1) ){
                            return true;
                        }
                        break;
                    case KnightW:
                        if( ( (dify==2 || dify==-2) && (difx==1 || difx==-1) ) || ( (difx==2 || difx==-2) && (dify==1 || dify==-1) )){
                            return true;
                        }
                        break;
                    case BishopW:
                        if(difx==dify || difx==-dify){
                            boolean blocked = false;
                            if(dify<0){
                                if(difx<0){//bishop at bottom left
                                    for(int k=1;k<-difx;k++){
                                        if(pboard[(y+k)*8+(x+k)]!=0 && pboard[(y+k)*8+(x+k)]!=KingB){
                                            blocked = true;
                                        }
                                    }
                                }else{//bishop at bottom right
                                    for(int k=1;k<difx;k++){
                                        if(pboard[(y+k)*8+(x-k)]!=0 && pboard[(y+k)*8+(x-k)]!=KingB){
                                            blocked = true;
                                        }
                                    }
                                }
                            }else{
                                if(difx<0){//bishop at top left
                                    for(int k=1;k<dify;k++){
                                        if(pboard[(y-k)*8+(x+k)]!=0 && pboard[(y-k)*8+(x+k)]!=KingB){
                                            blocked = true;
                                        }
                                    }
                                }else{//bishop at top right
                                    for(int k=1;k<difx;k++){
                                        if(pboard[(y-k)*8+(x-k)]!=0 && pboard[(y-k)*8+(x-k)]!=KingB){
                                            blocked = true;
                                        }
                                    }
                                }
                            }
                            if(!blocked){
                                return true;
                            }
                        }
                        break;
                    case RookW:
                        if(difx==0 && dify!=0){
                            boolean blocked = false;
                            if(dify<0){//rook below king
                                for(int k=1;k<=-dify;k++){
                                    if(pboard[(y+k)*8+x]!=0 && pboard[(y+k)*8+x]!=KingB){
                                        blocked = true;
                                    }
                                }
                            }else{//rook above king
                                for(int k=1;k<=dify;k++){
                                    if(pboard[(y-k)*8+x]!=0 && pboard[(y-k)*8+x]!=KingB){
                                        blocked = true;
                                    }
                                }
                            }
                            if(!blocked){
                                return true;
                            }
                        }else if(difx!=0 && dify==0){
                            boolean blocked = false;
                            if(difx<0){//rook to the left of king
                                for(int k=1;k<=-difx;k++){
                                    if(pboard[(y)*8+(x+k)]!=0 && pboard[(y)*8+(x+k)]!=KingB){
                                        blocked = true;
                                    }
                                }
                            }else{//rook to the right of king
                                for(int k=1;k<=difx;k++){
                                    if(pboard[(y)*8+(x-k)]!=0 && pboard[(y)*8+(x-k)]!=KingB){
                                        blocked = true;
                                    }
                                }
                            }
                            if(!blocked){
                                return true;
                            }
                        }
                        break;
                    case QueenW:
                        if(difx==dify || difx==-dify){
                            boolean blocked = false;
                            if(dify<0){
                                if(difx<0){//queen at bottom left
                                    for(int k=1;k<=-difx;k++){
                                        if(pboard[(y+k)*8+(x+k)]!=0 && pboard[(y+k)*8+(x+k)]!=KingB){
                                            blocked = true;
                                        }
                                    }
                                }else{//queen at bottom right
                                    for(int k=1;k<=difx;k++){
                                        if(pboard[(y+k)*8+(x-k)]!=0 && pboard[(y+k)*8+(x-k)]!=KingB){
                                            blocked = true;
                                        }
                                    }
                                }
                            }else{
                                if(difx<0){//queen at top left
                                    for(int k=1;k<=dify;k++){
                                        if(pboard[(y-k)*8+(x+k)]!=0 && pboard[(y-k)*8+(x+k)]!=KingB){
                                            blocked = true;
                                        }
                                    }
                                }else{//queen at top right
                                    for(int k=1;k<=difx;k++){
                                        if(pboard[(y-k)*8+(x-k)]!=0 && pboard[(y-k)*8+(x-k)]!=KingB){
                                            blocked = true;
                                        }
                                    }
                                }
                            }
                            if(!blocked){
                                return true;
                            }
                        }
                        if(difx==0 && dify!=0){
                            boolean blocked = false;
                            if(dify<0){//queen below king
                                for(int k=1;k<=-dify;k++){
                                    if(pboard[(y+k)*8+x]!=0 && pboard[(y+k)*8+x]!=KingB){
                                        blocked = true;
                                    }
                                }
                            }else{//queen above king
                                for(int k=1;k<=dify;k++){
                                    if(pboard[(y-k)*8+x]!=0 && pboard[(y-k)*8+x]!=KingB){
                                        blocked = true;
                                    }
                                }
                            }
                            if(!blocked){
                                return true;
                            }
                        }else if(difx!=0 && dify==0){
                            boolean blocked = false;
                            if(difx<0){//queen to the left of king
                                for(int k=1;k<=-difx;k++){
                                    if(pboard[(y)*8+(x+k)]!=0 && pboard[(y)*8+(x+k)]!=KingB){
                                        blocked = true;
                                    }
                                }
                            }else{//queen to the right of king
                                for(int k=1;k<=difx;k++){
                                    if(pboard[(y)*8+(x-k)]!=0 && pboard[(y)*8+(x-k)]!=KingB){
                                        blocked = true;
                                    }
                                }
                            }
                            if(!blocked){
                                return true;
                            }
                        }
                        break;
                    case KingW:
                        if( (difx==0 || difx==1 || difx==-1) && (dify==0 || dify==1 || dify==-1) ){
                            return true;
                        }
                        break;
                }
            }
        }
        return checked;
    }

    public static boolean isLegalMove(boolean whiteToMove, String toMove, short[] board, boolean playerCheck){
        short ix = Short.parseShort(toMove.substring(0,1));
        short iy = Short.parseShort(toMove.substring(1,2));
        short dx = Short.parseShort(toMove.substring(2,3));
        short dy = Short.parseShort(toMove.substring(3,4));
        short capPiece = Short.parseShort(toMove.substring(4,6));
        short testBoard[] = board.clone();
        boolean valid = false;
        if(playerCheck) {
            String movesNow = genMoves(board, whiteToMove);
            //System.out.println(toMove);
            //System.out.println(movesNow);
            for (int i = 0; i < movesNow.length(); i += 6) {
                if (toMove.equals(movesNow.substring(i, i + 6))) { //Is this move even possible to make (by moving piece rules only)
                    valid = true;
                }
            }
        }else{
            valid = true;
        }
        if( (board[iy*8+ix]==KingW || board[iy*8+ix]==KingB) && valid && (dx-ix==-2 || dx-ix==2) ){//if the move is a castle, and it is permitte
            String tempMoves = genMoves(testBoard,!whiteToMove);// then check if the castle is allowed, aka no castling out of checks
            int tempSafeKings = 0;
            int tempMaxMoves = tempMoves.length()/6;
            for(int i=0;i<tempMoves.length();i+=6){
                short pix = Short.parseShort(tempMoves.substring(i,i+6).substring(0,1));
                short piy = Short.parseShort(tempMoves.substring(i,i+6).substring(1,2));
                short pdx = Short.parseShort(tempMoves.substring(i,i+6).substring(2,3));
                short pdy = Short.parseShort(tempMoves.substring(i,i+6).substring(3,4));
                short pcapPiece = Short.parseShort(tempMoves.substring(i,i+6).substring(4,6));
                short[] temp = testBoard.clone();
                playMove(temp,pix,piy,pdx,pdy,pcapPiece);
                if(whiteToMove){
                    for(int k=0;k<temp.length;k++){
                        if(temp[k]==KingW){
                            tempSafeKings++;
                        }
                    }
                }else{
                    for(int k=0;k<temp.length;k++){
                        if(temp[k]==KingB){
                            tempSafeKings++;
                        }
                    }
                }
            }
            if(tempSafeKings!=tempMaxMoves){
                valid=false;
            }
            boolean movingRight;
            if(dx-ix==2){
                movingRight = true;
            }else{
                movingRight = false;
            }
            short temptestBoard[] = board.clone();
            if(movingRight){
                playMove(temptestBoard,ix,iy,(short)(dx-1),dy,capPiece);
            }else{
                playMove(temptestBoard,ix,iy,(short)(dx+1),dy,capPiece);
            }
           tempMoves = genMoves(testBoard,!whiteToMove);
            tempSafeKings = 0;
            tempMaxMoves = tempMoves.length()/6;
            for(int i=0;i<tempMoves.length();i+=6){
                short pix = Short.parseShort(tempMoves.substring(i,i+6).substring(0,1));
                short piy = Short.parseShort(tempMoves.substring(i,i+6).substring(1,2));
                short pdx = Short.parseShort(tempMoves.substring(i,i+6).substring(2,3));
                short pdy = Short.parseShort(tempMoves.substring(i,i+6).substring(3,4));
                short pcapPiece = Short.parseShort(tempMoves.substring(i,i+6).substring(4,6));
                short[] temp = temptestBoard.clone();
                playMove(temp,pix,piy,pdx,pdy,pcapPiece);
                if(whiteToMove){
                    for(int k=0;k<temp.length;k++){
                        if(temp[k]==KingW){
                            tempSafeKings++;
                        }
                    }
                }else{
                    for(int k=0;k<temp.length;k++){
                        if(temp[k]==KingB){
                            tempSafeKings++;
                        }
                    }
                }
            }
            if(tempSafeKings!=tempMaxMoves){
                valid=false;
            }
        }
        if(!valid){
            return false;
        }
            if(KingInCheck(testBoard,whiteToMove)){
                playMove(testBoard,ix,iy,dx,dy,capPiece);
                if(KingInCheck(testBoard,whiteToMove)){
                    return false;
                }else{
                    return true;
                }
            }else{
                return true;
            }
        /*playMove(testBoard,ix,iy,dx,dy,capPiece);//play the move, later we check if it breaks any other rules
        boolean KingCaptured = false;
        String possibleMoves = genMoves(testBoard,!whiteToMove);
        //System.out.println(possibleMoves);
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
            //System.out.println(maxMoves);
            //System.out.println(KingsSafe);
        }else{
            KingCaptured = true;
            //System.out.println(maxMoves);
            //System.out.println(KingsSafe);
        }
        return !KingCaptured;*/
    }

    public static void playMove(short[] pBoard, short ix, short iy, short dx, short dy, short capPiece){
        if( (pBoard[iy*8+ix]==PawnW || pBoard[iy*8+ix]==PawnB) && (dx-ix!=0 && dy-iy!=0) && (pBoard[dy*8+dx]==0) ){//check for en passant
                pBoard[iy*8+dx] = 0;
        }else if(pBoard[iy*8+ix]==KingW){
            if(pBoard==board){
                KingWMoved = true;
            }
            if(dx-ix==-2 && !RookW00Moved){
                pBoard[(dy)*8+dx+1] = pBoard[(0)*8+0];
                pBoard[(0)*8+0] = 0;
                if(pBoard==board){
                    RookW00Moved = true;
                }
            }else if(dx-ix==2 && !RookW70Moved){
                pBoard[(dy)*8+dx-1] = pBoard[(0)*8+7];
                pBoard[(0)*8+7] = 0;
                if(pBoard==board){
                    RookW70Moved = true;
                }
            }
        }else if(pBoard[iy*8+ix]==KingB){
            if(pBoard==board){
                KingBMoved = true;
            }
            if(dx-ix==-2 && !RookB07Moved){
                pBoard[(dy)*8+dx+1] = pBoard[(7)*8+0];
                pBoard[(7)*8+0] = 0;
                if(pBoard==board){
                    RookB07Moved = true;
                }
            }else if(dx-ix==2 && !RookB77Moved){
                pBoard[(dy)*8+dx-1] = pBoard[(7)*8+7];
                pBoard[(7)*8+7] = 0;
                if(pBoard==board){
                    RookB77Moved = true;
                }
            }
        }else if(iy==0 && ix==0 && pBoard[iy*8+ix]==RookW && pBoard==board){
            RookW00Moved = true;
        }else if(iy==7 && ix==0 && pBoard[iy*8+ix]==RookB && pBoard==board){
            RookB07Moved = true;
        }else if(iy==0 && ix==7 && pBoard[iy*8+ix]==RookW && pBoard==board){
            RookW70Moved = true;
        }else if(iy==7 && ix==7 && pBoard[iy*8+ix]==RookB && pBoard==board){
            RookB77Moved = true;
        }

        if( ( (pBoard[iy*8+ix]==PawnW && dy==7) || (pBoard[iy*8+ix]==PawnB && dy==0) ) && (dx-ix!=0 && dy-iy!=0) && (pBoard[dy*8+dx]!=0) ){
            pBoard[iy*8+ix] = 0;
            pBoard[dy*8+dx] = capPiece;
        }else{
            pBoard[dy*8+dx] = pBoard[iy*8+ix];
            pBoard[iy*8+ix] = 0;
        }

    }

    public static void playMove(short[] pBoard, String playMove){
        short ix = Short.parseShort(playMove.substring(0,1));
        short iy = Short.parseShort(playMove.substring(1,2));
        short dx = Short.parseShort(playMove.substring(2,3));
        short dy = Short.parseShort(playMove.substring(3,4));
        short capPiece = Short.parseShort(playMove.substring(4,6));
        if( (pBoard[iy*8+ix]==PawnW || pBoard[iy*8+ix]==PawnB) && (dx-ix!=0 && dy-iy!=0) && (pBoard[dy*8+dx]==0) ){//check for en passant
            pBoard[iy*8+dx] = 0;
        }else if(pBoard[iy*8+ix]==KingW){
            if(pBoard==board){
                KingWMoved = true;
            }
            if(dx-ix==-2 && !RookW00Moved){
                pBoard[(dy)*8+dx+1] = pBoard[(0)*8+0];
                pBoard[(0)*8+0] = 0;
                if(pBoard==board){
                    RookW00Moved = true;
                }
            }else if(dx-ix==2 && !RookW70Moved){
                pBoard[(dy)*8+dx-1] = pBoard[(0)*8+7];
                pBoard[(0)*8+7] = 0;
                if(pBoard==board){
                    RookW70Moved = true;
                }
            }
        }else if(pBoard[iy*8+ix]==KingB){
            if(pBoard==board){
                KingBMoved = true;
            }
            if(dx-ix==-2 && !RookB07Moved){
                pBoard[(dy)*8+dx+1] = pBoard[(7)*8+0];
                pBoard[(7)*8+0] = 0;
                if(pBoard==board){
                    RookB07Moved = true;
                }
            }else if(dx-ix==2 && !RookB77Moved){
                pBoard[(dy)*8+dx-1] = pBoard[(7)*8+7];
                pBoard[(7)*8+7] = 0;
                if(pBoard==board){
                    RookB77Moved = true;
                }
            }
        }else if(iy==0 && ix==0 && pBoard[iy*8+ix]==RookW && pBoard==board){
            RookW00Moved = true;
        }else if(iy==7 && ix==0 && pBoard[iy*8+ix]==RookB && pBoard==board){
            RookB07Moved = true;
        }else if(iy==0 && ix==7 && pBoard[iy*8+ix]==RookW && pBoard==board){
            RookW70Moved = true;
        }else if(iy==7 && ix==7 && pBoard[iy*8+ix]==RookB && pBoard==board){
            RookB77Moved = true;
        }

        if( ( (pBoard[iy*8+ix]==PawnW && dy==7) || (pBoard[iy*8+ix]==PawnB && dy==0) )){
            pBoard[iy*8+ix] = 0;
            pBoard[dy*8+dx] = capPiece;
        }else{
            pBoard[dy*8+dx] = pBoard[iy*8+ix];
            pBoard[iy*8+ix] = 0;
        }
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
                    g.drawImage(KingWImage,(i%8)*100,(7-(i/8))*100,null);
                    break;
                case QueenW:
                    g.drawImage(QueenWImage,(i%8)*100,(7-(i/8))*100,null);
                    break;
                case BishopW :
                    g.drawImage(BishopWImage,(i%8)*100,(7-(i/8))*100,null);
                    break;
                case RookW:
                    g.drawImage(RookWImage,(i%8)*100,(7-(i/8))*100,null);
                    break;
                case KnightW:
                    g.drawImage(KnightWImage,(i%8)*100,(7-(i/8))*100,null);
                    break;
                case PawnW:
                    g.drawImage(PawnWImage,(i%8)*100,(7-(i/8))*100,null);
                    break;
                case KingB:
                    g.drawImage(KingBImage,(i%8)*100,(7-(i/8))*100,null);
                    break;
                case QueenB:
                    g.drawImage(QueenBImage,(i%8)*100,(7-(i/8))*100,null);
                    break;
                case RookB:
                    g.drawImage(RookBImage,(i%8)*100,(7-(i/8))*100,null);
                    break;
                case BishopB:
                    g.drawImage(BishopBImage,(i%8)*100,(7-(i/8))*100,null);
                    break;
                case KnightB:
                    g.drawImage(KnightBImage,(i%8)*100,(7-(i/8))*100,null);
                    break;
                case PawnB:
                    g.drawImage(PawnBImage,(i%8)*100,(7-(i/8))*100,null);
                    break;
                default:
                    break;
            }
        }

        if(moves.length()/6!=1){
            String lastMove = moves.substring(moves.length()-6);
            short ix = Short.parseShort(lastMove.substring(0,1));
            short iy = Short.parseShort(lastMove.substring(1,2));
            short dx = Short.parseShort(lastMove.substring(2,3));
            short dy = Short.parseShort(lastMove.substring(3,4));
            g.setColor(new Color(255,255,102,85));
            g.fillRect(ix*100,(7-iy)*100,100,100);
            g.fillRect(dx*100,(7-dy)*100,100,100);
            if(movingPiece){
                g.setColor(new Color(106,90,205,125));
                g.fillRect(x1*100,(7-y1)*100,100,100);
            }
        }

    }

    public void hideButtons(){
        queenButton.setVisible(false);
        rookButton.setVisible(false);
        bishopButton.setVisible(false);
        knightButton.setVisible(false);
        frame.revalidate();
    }

    public void showButtons(){
        queenButton.setVisible(true);
        rookButton.setVisible(true);
        bishopButton.setVisible(true);
        knightButton.setVisible(true);
        frame.revalidate();
    }

    public void update(){
        if(isWhiteTurn==AIPlaysWhite){
            String AIMove = chessAI.pickMove(genAllLegalMoves(board,isWhiteTurn, false),board,isWhiteTurn,AIPlaysWhite);
            playMove(board,AIMove);
            moves+=AIMove;
            for (int i = 0; i < board.length; i++) {
                if ((board[i] == PawnW && i / 8 == 7) || (board[i] == PawnB && i / 8 == 0)) {
                        pawnPromoteTo = chessAI.promotePawn(board,isWhiteTurn);
                        board[i] = pawnPromoteTo;
                        pawnPromoteTo = 0;
                }
            }
            isWhiteTurn = !isWhiteTurn;
        }
        if(buttonClicked){
            if(pawnPromoteTo!=0){
                String attemptMove = ("" + x1 + "" + y1 + "" + x2 + "" + y2 +""+pawnPromoteTo);
                if (isLegalMove(isWhiteTurn, attemptMove, board, true)) {
                    playMove(board, attemptMove);
                    isWhiteTurn = !isWhiteTurn;
                    System.out.println("Legal Move");
                    moves += attemptMove;
                } else {
                    System.out.println("Illegal move");
                }
                grabbedPiece = 0;
                movingPiece = false;
                System.out.println("Move made, is it whites turn next:  " + isWhiteTurn);
                pawnPromoteTo = 0;
                buttonClicked = false;
                hideButtons();
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
        if (!buttonClicked){
            if (!movingPiece && (!isWhiteTurn==AIPlaysWhite)) {
                x1 = (short) (e.getX() / 100);
                y1 = (short) (7 - (e.getY() / 100));
                if (board[y1 * 8 + x1] != 0) {
                    if (isWhiteTurn) {
                        if (board[y1 * 8 + x1] / 10 == 2) {
                            movingPiece = true;
                            grabbedPiece = board[y1 * 8 + x1];
                        }
                    } else {
                        if (board[y1 * 8 + x1] / 10 == 1) {
                            movingPiece = true;
                            grabbedPiece = board[y1 * 8 + x1];
                        }
                    }
                }
            } else if(movingPiece && (!isWhiteTurn==AIPlaysWhite)) {
                x2 = (short) (e.getX() / 100);
                y2 = (short) (7 - (e.getY() / 100));
                //check if legal
                String attemptMove = "";
                if(grabbedPiece!=PawnB && grabbedPiece!=PawnW){
                    if (board[y2 * 8 + x2] == 0) {
                        attemptMove = ("" + x1 + "" + y1 + "" + x2 + "" + y2 + "" + "00");
                    } else {
                        attemptMove = ("" + x1 + "" + y1 + "" + x2 + "" + y2 + "" + board[y2 * 8 + x2]);
                    }
                }else if(y2==0 || y2==7){
                    showButtons();
                    buttonClicked = true;
                    attemptMove = ("" + x1 + "" + y1 + "" + x2 + "" + y2 +"22");
                }else{
                    if (board[y2 * 8 + x2] == 0) {
                        attemptMove = ("" + x1 + "" + y1 + "" + x2 + "" + y2 + "" + "00");
                    } else {
                        attemptMove = ("" + x1 + "" + y1 + "" + x2 + "" + y2 + "" + board[y2 * 8 + x2]);
                    }
                }

                if(!buttonClicked){
                    if (isLegalMove(isWhiteTurn, attemptMove, board, true)) {
                        playMove(board, attemptMove);
                        isWhiteTurn = !isWhiteTurn;
                        System.out.println("Legal Move");
                        moves += attemptMove;
                    } else{
                        System.out.println("Illegal move");
                    }
                        grabbedPiece = 0;
                        movingPiece = false;
                        System.out.println("Move made, is it whites turn next:  " + isWhiteTurn);
                    System.out.println(KingInCheck(board,false));
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(queenButton.getActionCommand())){
            if(isWhiteTurn){
                pawnPromoteTo = QueenW;
            }else{
                pawnPromoteTo = QueenB;
            }
        }else if(e.getActionCommand().equals(rookButton.getActionCommand())){
            if(isWhiteTurn){
                pawnPromoteTo = RookW;
            }else{
                pawnPromoteTo = RookB;
            }
        }else if(e.getActionCommand().equals(bishopButton.getActionCommand())){
            if(isWhiteTurn){
                pawnPromoteTo = BishopW;
            }else{
                pawnPromoteTo = BishopB;
            }
        }else if(e.getActionCommand().equals(knightButton.getActionCommand())){
            if(isWhiteTurn){
                pawnPromoteTo = KnightW;
            }else{
                pawnPromoteTo = KnightB;
            }
        }else if(e.getActionCommand().equals(aiWhiteButton.getActionCommand())){
            AIPlaysWhite = true;
            chessAI = new ChessAI(AIPlaysWhite);
            aiWhiteButton.setVisible(false);
            aiBlackButton.setVisible(false);
            frame.revalidate();
        }else if(e.getActionCommand().equals(aiBlackButton.getActionCommand())){
            AIPlaysWhite = false;
            chessAI = new ChessAI(AIPlaysWhite);
            aiWhiteButton.setVisible(false);
            aiBlackButton.setVisible(false);
            frame.revalidate();
        }
    }
}
