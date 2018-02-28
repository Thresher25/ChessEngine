package Main;

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
    public static final short KingW = 21, QueenW  = 22, RookW = 23, BishopW = 24, KnightW = 25, PawnW = 26, KingB = 11, QueenB  = 12, RookB = 13, BishopB = 14, KnightB = 15, PawnB = 16, Empty = 0;
    public ChessAI(boolean playsWhite) {
        playsAsWhite = playsWhite;
    }

    public String pickMove(String posLegalMoves, short[] pboard){
        String bestMove = posLegalMoves.substring(0,6);
        for (int i = 6; i < posLegalMoves.length(); i+=6) {
            if(playsAsWhite){
                if(evalPosition(posLegalMoves.substring(i,i+6),pboard)>evalPosition(bestMove,pboard)){
                    bestMove = posLegalMoves.substring(i,i+6);
                }
            }else{
                if(evalPosition(posLegalMoves.substring(i,i+6),pboard)<evalPosition(bestMove,pboard)){
                    bestMove = posLegalMoves.substring(i,i+6);
                }
            }

        }
        if(MainClass.moves.length()/6>30){
            inEndGame = true;
        }
        return bestMove;
    }

    public short promotePawn(short[] pboard, boolean isWhiteToMove){
        if(isWhiteToMove){
            return MainClass.QueenW;
        }else{
            return MainClass.QueenB;
        }
    }

    public long evalPosition(String pMove, short[] pboard){
        short[] testBoard = pboard.clone();
        MainClass.playMove(testBoard, pMove);//TODO perhaps this can be moved into this Class, might be more efficient? (look into this)
        long value = 0;
        for (int i = 0; i < testBoard.length; i++) {
            switch(testBoard[i]){
                case PawnW:
                    value+=100;
                    value+=PawnPosVals[i];
                    break;
                case KnightW:
                    value+=300;
                    value+=KnightPosVals[i];
                    break;
                case BishopW:
                    value+=325;
                    value+=BishopPosVals[i];
                    break;
                case RookW:
                    value+=500;
                    value+=RookPosVals[i];
                    break;
                case QueenW:
                    value+=900;
                    value+=QueenPosVals[i];
                    break;
                case KingW:
                    value+=10000;
                    if(inEndGame){
                        value+=KingEndGamePosVals[i];
                    }else{
                        value+=KingMiddleGamePosVals[i];
                    }
                    break;
                case PawnB:
                    value-=100;
                    value-=PawnPosVals[64-i];
                    break;
                case KnightB:
                    value-=300;
                    value-=KnightPosVals[64-i];
                    break;
                case BishopB:
                    value-=325;
                    value-=BishopPosVals[64-i];
                    break;
                case RookB:
                    value-=500;
                    value-=RookPosVals[64-i];
                    break;
                case QueenB:
                    value-=900;
                    value-=QueenPosVals[64-i];
                    break;
                case KingB:
                    value-=10000;
                    if(inEndGame){
                        value-=KingEndGamePosVals[64-i];
                    }else{
                        value-=KingMiddleGamePosVals[64-i];
                    }
                    break;
            }
        }
        return value;
    }

    public long evalPosition(short[] pboard){
        short[] testBoard = pboard.clone();
        long value = 0;
        for (int i = 0; i < testBoard.length; i++) {
            switch(testBoard[i]){
                case PawnW:
                    value+=100;
                    value+=PawnPosVals[i];
                    break;
                case KnightW:
                    value+=300;
                    value+=KnightPosVals[i];
                    break;
                case BishopW:
                    value+=325;
                    value+=BishopPosVals[i];
                    break;
                case RookW:
                    value+=500;
                    value+=RookPosVals[i];
                    break;
                case QueenW:
                    value+=900;
                    value+=QueenPosVals[i];
                    break;
                case KingW:
                    value+=10000;
                    if(inEndGame){
                        value+=KingEndGamePosVals[i];
                    }else{
                        value+=KingMiddleGamePosVals[i];
                    }
                    break;
                case PawnB:
                    value-=100;
                    value-=PawnPosVals[64-i];
                    break;
                case KnightB:
                    value-=300;
                    value-=KnightPosVals[64-i];
                    break;
                case BishopB:
                    value-=325;
                    value-=BishopPosVals[64-i];
                    break;
                case RookB:
                    value-=500;
                    value-=RookPosVals[64-i];
                    break;
                case QueenB:
                    value-=900;
                    value-=QueenPosVals[64-i];
                    break;
                case KingB:
                    value-=10000;
                    if(inEndGame){
                        value-=KingEndGamePosVals[64-i];
                    }else{
                        value-=KingMiddleGamePosVals[64-i];
                    }
                    break;
            }
        }
        return value;
    }

}
