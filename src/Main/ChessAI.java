package Main;

public class ChessAI {

    public boolean playsAsWhite = true;
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
        return bestMove;
    }

    public short promotePawn(short[] pboard, boolean isWhiteToMove){
        if(isWhiteToMove){
            return MainClass.QueenW;
        }else{
            return MainClass.QueenB;
        }
    }

    public double evalPosition(String pMove, short[] pboard){
        short[] testBoard = pboard.clone();
        MainClass.playMove(testBoard, pMove);//TODO perhaps this can be moved into this Class, might be more efficient? (look into this)
        double value = 0;
        for (int i = 0; i < testBoard.length; i++) {
            switch(testBoard[i]){
                case PawnW:
                    value+=1.0;
                    break;
                case KnightW:
                    value+=3.0;
                    break;
                case BishopW:
                    value+=3.25;
                    break;
                case RookW:
                    value+=5.0;
                    break;
                case QueenW:
                    value+=9.0;
                    break;
                case KingW:
                    value+=10000.0;
                    break;
                case PawnB:
                    value-=1.0;
                    break;
                case KnightB:
                    value-=3.0;
                    break;
                case BishopB:
                    value-=3.25;
                    break;
                case RookB:
                    value-=5.0;
                    break;
                case QueenB:
                    value-=9.0;
                    break;
                case KingB:
                    value-=10000.0;
                    break;
            }
        }
        return value;
    }

}
