package Main;

import com.sun.tools.javac.Main;

public class ChessAI {

    public final short[] PawnPosVals = {    0,  0,  0,  0,  0,  0,  0,  0,
                                            50, 50, 50, 50, 50, 50, 50, 50,
                                            10, 10, 20, 30, 30, 20, 10, 10,
                                            5,  5, 10, 25, 25, 10,  5,  5,
                                            0,  0,  0, 20, 20,  0,  0,  0,
                                            5, -5,-10,  0,  0,-10, -5,  5,
                                            5, 10, 10,-20,-20, 10, 10,  5,
                                            0,  0,  0,  0,  0,  0,  0,  0};

    public final short[] KnightPosVals = {  -50,-40,-30,-30,-30,-30,-40,-50,
                                            -40,-20,  0,  0,  0,  0,-20,-40,
                                            -30,  0, 10, 15, 15, 10,  0,-30,
                                            -30,  5, 15, 20, 20, 15,  5,-30,
                                            -30,  0, 15, 20, 20, 15,  0,-30,
                                            -30,  5, 10, 15, 15, 10,  5,-30,
                                            -40,-20,  0,  5,  5,  0,-20,-40,
                                            -50,-40,-30,-30,-30,-30,-40,-50};

    public final short[] BishopPosVals = {  -20,-10,-10,-10,-10,-10,-10,-20,
                                            -10,  0,  0,  0,  0,  0,  0,-10,
                                            -10,  0,  5, 10, 10,  5,  0,-10,
                                            -10,  5,  5, 10, 10,  5,  5,-10,
                                            -10,  0, 10, 10, 10, 10,  0,-10,
                                            -10, 10, 10, 10, 10, 10, 10,-10,
                                            -10,  5,  0,  0,  0,  0,  5,-10,
                                            -20,-10,-10,-10,-10,-10,-10,-20};

    public final short[] RookPosVals = {  0,  0,  0,  0,  0,  0,  0,  0,
                                          5, 10, 10, 10, 10, 10, 10,  5,
                                          -5,  0,  0,  0,  0,  0,  0, -5,
                                          -5,  0,  0,  0,  0,  0,  0, -5,
                                          -5,  0,  0,  0,  0,  0,  0, -5,
                                          -5,  0,  0,  0,  0,  0,  0, -5,
                                          -5,  0,  0,  0,  0,  0,  0, -5,
                                          0,  0,  0,  5,  5,  0,  0,  0};

    public final short[] QueenPosVals = {-20,-10,-10, -5, -5,-10,-10,-20,
                                         -10,  0,  0,  0,  0,  0,  0,-10,
                                         -10,  0,  5,  5,  5,  5,  0,-10,
                                         -5,  0,  5,  5,  5,  5,  0, -5,
                                         0,  0,  5,  5,  5,  5,  0, -5,
                                         -10,  5,  5,  5,  5,  5,  0,-10,
                                         -10,  0,  5,  0,  0,  0,  0,-10,
                                         -20,-10,-10, -5, -5,-10,-10,-20};

    public final short[] KingMiddleGamePosVals = {-30,-40,-40,-50,-50,-40,-40,-30,
                                                  -30,-40,-40,-50,-50,-40,-40,-30,
                                                  -30,-40,-40,-50,-50,-40,-40,-30,
                                                  -30,-40,-40,-50,-50,-40,-40,-30,
                                                  -20,-30,-30,-40,-40,-30,-30,-20,
                                                  -10,-20,-20,-20,-20,-20,-20,-10,
                                                  20, 20,  0,  0,  0,  0, 20, 20,
                                                  20, 30, 10,  0,  0, 10, 30, 20};

    public final short[] KingEndGamePosVals = { -50,-40,-30,-20,-20,-30,-40,-50,
                                                -30,-20,-10,  0,  0,-10,-20,-30,
                                                -30,-10, 20, 30, 30, 20,-10,-30,
                                                -30,-10, 30, 40, 40, 30,-10,-30,
                                                -30,-10, 30, 40, 40, 30,-10,-30,
                                                -30,-10, 20, 30, 30, 20,-10,-30,
                                                -30,-30,  0,  0,  0,  0,-30,-30,
                                                -50,-30,-30,-30,-30,-30,-30,-50};
    public boolean playsAsWhite = true;
    public boolean inEndGame = false;
    int numcalcs = 0;
    public static final short KingW = 21, QueenW  = 22, RookW = 23, BishopW = 24, KnightW = 25, PawnW = 26, KingB = 11, QueenB  = 12, RookB = 13, BishopB = 14, KnightB = 15, PawnB = 16, Empty = 0;
    public ChessAI(boolean playsWhite) {
        playsAsWhite = playsWhite;
    }

    public String pickMove(String posLegalMoves, short[] pboard, boolean whiteTurn, boolean maxingWhite){
            int depth;
        int numPieces = 0;
        for(int i=0;i<pboard.length;i++){
            if(pboard[i]!=0){
                numPieces++;
            }
        }
            short[] testBoard = pboard.clone();
            String posMoves = posLegalMoves;
            int branchingFactor = posLegalMoves.length()/6;
            if(branchingFactor<10){
                depth = 6;
            }else if(branchingFactor<15 || numPieces<5){
                depth = 4;
            }else{
                depth=3;
            }
        System.out.println(depth);
            String candidateMove = "";
            long pval;
            if(whiteTurn){
                pval = -1000000;
            }else{
                pval = 1000000;
            }

            for (int i = 0; i < posMoves.length(); i+=6) {
                long r = getBestMove(testBoard,!whiteTurn,depth-1,posMoves.substring(i,i+6));//value of a node
                if(maxingWhite){
                    if(r>pval){
                        pval = r;
                        candidateMove = posMoves.substring(i,i+6);
                    }
                }else{
                    if(r<pval){
                        pval = r;
                        candidateMove = posMoves.substring(i,i+6);
                    }
                }
            }
        if(MainClass.moves.length()/6>30 || numPieces<=10){
            inEndGame = true;
        }
        System.out.println(candidateMove);
        System.out.println("Number of positions evaluated: "+numcalcs);
        numcalcs = 0;
        if(candidateMove.equals("")){
            System.out.println();
            System.out.println("AI Doesn't want to make a move!");
            System.out.println();
            return posLegalMoves.substring(0,6);
        }
        return candidateMove;
    }

    public short promotePawn(short[] pboard, boolean isWhiteToMove){
        if(isWhiteToMove){
            return MainClass.QueenW;
        }else{
            return MainClass.QueenB;
        }
    }

    public long getBestMove(short[] pboard, boolean whiteTurn, int depth, String moveToPlay){
        short[] testBoard = pboard.clone();
        if(MainClass.moves.length()/6>10){
            String last10moves = MainClass.moves.substring(MainClass.moves.length()-(10*6));
            int numreps = 0;
            for(int k=0;k<last10moves.length();k+=6){
                if(moveToPlay.equals(last10moves.substring(k,k+6))){
                    numreps++;
                }
            }
            if(numreps>=3){
                return 0;
            }
        }
        MainClass.playMove(testBoard,moveToPlay);
        if(depth<=0){
            numcalcs++;
            return evalPosition(testBoard);
        }

        String posMoves = MainClass.genAllLegalMoves(testBoard, whiteTurn);
        long pval;
        if(whiteTurn){
            pval = -1000000;
        }else{
            pval = 1000000;
        }

        //only do the below if this position doesnt result in 3fold repitition (This is a very rough solution) TODO Fix this

        for (int i = 0; i < posMoves.length(); i+=6) {
            long r = getBestMove(testBoard,!whiteTurn,depth-1,posMoves.substring(i,i+6));//value of a node
            if(whiteTurn){
                if(r>pval){
                    pval = r;
                }
            }else{
                if(r<pval){
                    pval = r;
                }
            }
        }
        return pval;
    }

    public long evalPosition(String pMove, short[] pboard){
        short[] testBoard = pboard.clone();
        MainClass.playMove(testBoard, pMove);
        long value = 0;
        if( (MainClass.genAllLegalMoves(testBoard,true).length()==0 || MainClass.genAllLegalMoves(testBoard,false).length()==0) && !MainClass.KingInCheck(pboard)){//check for a Stalemate
            return 0;
        }
        for (int i = 0; i < testBoard.length; i++) {
            int x = i%8;
            int y = i/8;
            switch(testBoard[i]){
                case PawnW:
                    value+=100;
                    value+=PawnPosVals[(7-y)*8+x];
                    break;
                case KnightW:
                    value+=300;
                    value+=KnightPosVals[(7-y)*8+x];
                    break;
                case BishopW:
                    value+=325;
                    value+=BishopPosVals[(7-y)*8+x];
                    break;
                case RookW:
                    value+=500;
                    value+=RookPosVals[(7-y)*8+x];
                    break;
                case QueenW:
                    value+=900;
                    value+=QueenPosVals[(7-y)*8+x];
                    break;
                case KingW:
                    value+=10000;
                    if(inEndGame){
                        value+=KingEndGamePosVals[(7-y)*8+x];
                    }else{
                        value+=KingMiddleGamePosVals[(7-y)*8+x];
                    }
                    break;
                case PawnB:
                    value-=100;
                    value-=PawnPosVals[i];
                    break;
                case KnightB:
                    value-=300;
                    value-=KnightPosVals[i];
                    break;
                case BishopB:
                    value-=325;
                    value-=BishopPosVals[i];
                    break;
                case RookB:
                    value-=500;
                    value-=RookPosVals[i];
                    break;
                case QueenB:
                    value-=900;
                    value-=QueenPosVals[i];
                    break;
                case KingB:
                    value-=10000;
                    if(inEndGame){
                        value-=KingEndGamePosVals[i];
                    }else{
                        value-=KingMiddleGamePosVals[i];
                    }
                    break;
            }
        }
        return value;
    }

    public long evalPosition(short[] pboard){
        long value = 0;
        if( (MainClass.genAllLegalMoves(pboard,true).length()==0 || MainClass.genAllLegalMoves(pboard,false).length()==0) && !MainClass.KingInCheck(pboard)){//check for a Stalemate
            return 0;
        }
        for (int i = 0; i < pboard.length; i++) {
            int x = i%8;
            int y = i/8;
            switch(pboard[i]){
                case PawnW:
                    value+=100;
                    value+=PawnPosVals[(7-y)*8+x];
                    break;
                case KnightW:
                    value+=300;
                    value+=KnightPosVals[(7-y)*8+x];
                    break;
                case BishopW:
                    value+=325;
                    value+=BishopPosVals[(7-y)*8+x];
                    break;
                case RookW:
                    value+=500;
                    value+=RookPosVals[(7-y)*8+x];
                    break;
                case QueenW:
                    value+=900;
                    value+=QueenPosVals[(7-y)*8+x];
                    break;
                case KingW:
                    value+=10000;
                    if(inEndGame){
                        value+=KingEndGamePosVals[(7-y)*8+x];
                    }else{
                        value+=KingMiddleGamePosVals[(7-y)*8+x];
                    }
                    break;
                case PawnB:
                    value-=100;
                    value-=PawnPosVals[i];
                    break;
                case KnightB:
                    value-=300;
                    value-=KnightPosVals[i];
                    break;
                case BishopB:
                    value-=325;
                    value-=BishopPosVals[i];
                    break;
                case RookB:
                    value-=500;
                    value-=RookPosVals[i];
                    break;
                case QueenB:
                    value-=900;
                    value-=QueenPosVals[i];
                    break;
                case KingB:
                    value-=10000;
                    if(inEndGame){
                        value-=KingEndGamePosVals[i];
                    }else{
                        value-=KingMiddleGamePosVals[i];
                    }
                    break;
            }
        }
        return value;
    }

}
