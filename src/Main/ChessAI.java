package Main;

import com.sun.tools.javac.Main;

public class ChessAI {

    public final short[] PawnPosVals = {    0,  0,  0,  0,  0,  0,  0,  0,
                                            50, 50, 50, 50, 50, 50, 50, 50,
                                            10, 10, 20, 30, 30, 20, 10, 10,
                                            5,  5, 10, 25, 25, 10,  5,  5,
                                            0,  0,  0, 20, 20,  0,  0,  0,
                                            6, -5,-10,  0,  0,-10, -5,  6,
                                            5, 10, 10,-20,-20, 10, 10,  5,
                                            0,  0,  0,  0,  0,  0,  0,  0};

    public final short[] PawnEndGamePosVals = {    0,   0,  0,  0,  0,  0,  0,  0,
                                                   70, 70, 70, 70, 70, 70, 70, 70,
                                                   50, 50, 50, 50, 50, 50, 50, 50,
                                                   30, 30, 30, 30, 30, 30, 30, 30,
                                                   10, 10, 10, 10, 10, 10, 10, 10,
                                                    0,  0,  0,  0,  0,  0,  0,  0,
                                                   -5, -5, -5, -5, -5, -5, -5, -5,
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
     int numPieces = 0;
     int numcalcs = 0;
    int mdepth = 0;
    public static final short KingW = 21, QueenW  = 22, RookW = 23, BishopW = 24, KnightW = 25, PawnW = 26, KingB = 11, QueenB  = 12, RookB = 13, BishopB = 14, KnightB = 15, PawnB = 16, Empty = 0;
    public ChessAI(boolean playsWhite) {
        playsAsWhite = playsWhite;
    }

    public int pickMove(int[] posLegalMoves, short[] pboard, boolean whiteTurn, boolean maxingWhite){//TODO Checkmate is broken, AI doesnt play it
        long time = System.currentTimeMillis();//TODO add 3move rep draw, and checkmate detection between parent and child nodes
            numPieces = 0;
        for(int i=0;i<pboard.length;i++){
            if(pboard[i]!=0){
                numPieces++;
            }
        }
            short[] testBoard = pboard.clone();
            int[] posMoves = posLegalMoves;
            int branchingFactor = posLegalMoves.length;
            if(numPieces<9){
                mdepth = 6;
            }else if(numPieces<12){
                mdepth = 5;
            }else{
                mdepth=4;
            }
            int candidateMove = 0;
            long pval;
            long alpha = -1000000000;
            long beta = 1000000000;
            if(whiteTurn){
                pval = -1000000;
            }else{
                pval = 1000000;
            }
                for (int i = 0; i < posMoves.length; i++){
                    if(posMoves[i]==0){
                        break;
                    }
                    long score = alphaBetaMin(testBoard,!whiteTurn,mdepth-1,posMoves[i],alpha,beta, maxingWhite);
                    if(score>beta){//Assuming maxing white
                        break;
                    }
                    if(score>alpha){
                        alpha = score;
                        candidateMove = posMoves[i];
                    }
                }
        if(numPieces<15){
            inEndGame = true;
            System.out.println("--------ENDGAME--------");
        }
        //System.out.println(candidateMove);
        System.out.println("Number of positions evaluated: "+numcalcs+"  and took "+(System.currentTimeMillis()-time)+" milliseconds");
        numcalcs = 0;
        if(candidateMove==0){
            System.out.println();
            System.out.println("AI Doesn't want to make a move!");
            System.out.println();
            return posLegalMoves[0];
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

    public long alphaBetaMax(short[] pboard, boolean whiteTurn, int depth, int moveToPlay, long alpha, long beta, boolean maxWhite){
        short[] testBoard = pboard.clone();//TODO Engine can be speed up about 2x by not cloning the board, instead play an undo move
        MainClass.playMove(testBoard,moveToPlay);
        if(depth<=0){
            numcalcs++;
            if(maxWhite){
                return evalPosition(testBoard, whiteTurn);
            }else{
                return -evalPosition(testBoard, whiteTurn);
            }
        }
        int[] posMoves = MainClass.genAllLegalMoves(testBoard, whiteTurn, false);
        for (int i = 0; i < posMoves.length; i++) {
            if(posMoves[i]==0){
                break;
            }
            long score = alphaBetaMin(testBoard,!whiteTurn,depth-1,posMoves[i],alpha,beta, maxWhite);
                if(score>beta){//Assuming maxing white
                    return score;
                }
                if(score>alpha){
                    alpha = score;
                }
        }
        if(posMoves[0]==0){
            if(MainClass.KingInCheck(testBoard,whiteTurn)){
                return -30000000*depth;
            }else{
                return 0;
            }
        }
        /*String mainCMoves = MainClass.moves;
        if(mainCMoves.length()/6>10){
            String last10moves = mainCMoves.substring(mainCMoves.length()-(10*6));
            int numreps = 0;
            for(int k=0;k<last10moves.length();k+=6){
                if(moveToPlay.equals(last10moves.substring(k,k+6))){
                    numreps++;
                }
            }
            if(numreps>=3){
                return 0;
            }
        }*/
        return alpha;
    }

    public long alphaBetaMin(short[] pboard, boolean whiteTurn, int depth, int moveToPlay, long alpha, long beta, boolean maxWhite){
        short[] testBoard = pboard.clone();
        MainClass.playMove(testBoard,moveToPlay);
        if(depth<=0){
            numcalcs++;
            if(maxWhite){
                return evalPosition(testBoard, whiteTurn);
            }else{
                return -evalPosition(testBoard, whiteTurn);
            }
        }
        int[] posMoves = MainClass.genAllLegalMoves(testBoard, whiteTurn, false);
        for (int i = 0; i < posMoves.length; i++) {
            if(posMoves[i]==0){
                break;
            }
            long score = alphaBetaMax(testBoard,!whiteTurn,depth-1,posMoves[i],alpha,beta, maxWhite);
            if(score<alpha){//Assuming maxing white
                return score;
            }
            if(score<beta){
                beta = score;
            }
        }
        if(posMoves[0]==0){
            if(MainClass.KingInCheck(testBoard,whiteTurn)){
                return 30000000*depth;
            }else{
                return 0;
            }
        }
        /*String mainCMoves = MainClass.moves;
          if(mainCMoves.length()/6>10){
            String last10moves = mainCMoves.substring(mainCMoves.length()-(10*6));
            int numreps = 0;
            for(int k=0;k<last10moves.length();k+=6){
                if(moveToPlay.equals(last10moves.substring(k,k+6))){
                    numreps++;
                }
            }
            if(numreps>=3){
                return 0;
            }
        }*/
        return beta;
    }

    public long evalPosition(short[] pboard, boolean whitesMove){//TODO rate counter-play and trade down pieces when engine is ahead, and penalize blocked pieces
        long value = 0;//TODO consider king safety/escape squares, and tempos/attacks, interpolate positional bonuses from mid-end game
        int numWBishop = 0;
        int numBBishop = 0;
        if(mdepth<5){
            int[] allWMoves = MainClass.genAllLegalMoves(pboard,true, false);
            int[] allBMoves = MainClass.genAllLegalMoves(pboard,false, false);
            int wMovs = 0;
            int bMovs = 0;
            for(int i=0;i<allWMoves.length;i++){
                if(allWMoves[i]!=0){
                    wMovs++;
                }else{
                    break;
                }
            }
            for(int i=0;i<allBMoves.length;i++){
                if(allBMoves[i]!=0){
                    bMovs++;
                }else{
                    break;
                }
            }
            value += 0.1*(wMovs-bMovs);
            if(whitesMove){
                if(allWMoves[0]==0){
                    if(!MainClass.KingInCheck(pboard,whitesMove)){
                        return 0;
                    }else{
                        return -200000;
                    }
                }
            }else{
                if(allBMoves[0]==0){
                    if(!MainClass.KingInCheck(pboard,whitesMove)){
                        return 0;
                    }else{
                        return 200000;
                    }
                }
            }
        }else{
            if(numPieces<16){
                boolean inChk = MainClass.KingInCheck(pboard,whitesMove);
                if(MainClass.genAllLegalMoves(pboard,whitesMove,false)[0]==0 ){
                    if(inChk){
                        if(whitesMove){
                            return -200000;
                        }else{
                            return 200000;
                        }
                    }else{
                        return 0;
                    }
                }
            }
        }
        for (int i = 0; i < pboard.length; i++) {
            int x = i%8;
            int y = i/8;
            switch(pboard[i]){
                case PawnW:
                    value+=100;
                    if(numPieces<10){
                       value+=PawnEndGamePosVals[(7-y)*8+x];
                    }else{
                       value+=PawnPosVals[(7-y)*8+x];
                    }
                    if(pboard[(y-1)*8+x]==PawnW){//penalty for doubled pawns
                        value-=40;
                    }
                    break;
                case KnightW:
                    value+=300;
                    value+=KnightPosVals[(7-y)*8+x];
                    break;
                case BishopW:
                    numWBishop++;
                    value+=275;
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
                    value+=100000;
                    if(inEndGame){
                        value+=KingEndGamePosVals[(7-y)*8+x];
                    }else{
                        value+=KingMiddleGamePosVals[(7-y)*8+x];
                    }
                    break;
                case PawnB:
                    value-=100;
                    if(numPieces<10){
                        value-=PawnEndGamePosVals[i];
                    }else{
                      value-=PawnPosVals[i];
                    }
                    if(pboard[(y+1)*8+x]==PawnB){//penalty for doubled pawns
                        value+=40;
                    }
                    break;
                case KnightB:
                    value-=300;
                    value-=KnightPosVals[i];
                    break;
                case BishopB:
                    numBBishop++;
                    value-=275;
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
                    value-=100000;
                    if(inEndGame){
                        value-=KingEndGamePosVals[i];
                    }else{
                        value-=KingMiddleGamePosVals[i];
                    }
                    break;
            }
        }
        if(numBBishop>=2){
            value-=150;
        }
        if(numWBishop>=2){
            value+=150;
        }
        return value;
    }

}
