package Main;

public class ChessAI {

    public ChessAI() {

    }

    public String pickMove(String posLegalMoves){
        int numMovesRange = posLegalMoves.length()/6-1;
        int pick = (int)Math.round(Math.random()*numMovesRange);
        return posLegalMoves.substring(pick*6,pick*6+6);
    }

    public short promotePawn(short[] board, boolean isWhiteToMove){
        if(isWhiteToMove){
            return MainClass.QueenW;
        }else{
            return MainClass.QueenB;
        }
    }

}
