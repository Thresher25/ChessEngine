# ChessEngine
An experiment which could honestly go either way

Current ELO estimate: ~900 (4-6 move depth, minimax+alphabeta) !Can get to checkmate/draw, but crashes right after

If you wish to run this engine and play against it you must have the following:
- Java 9

-A decent processor (the engine will take much longer on slower hardware as the depth is hardcoded)

Once the above requirements are met, you can download the zip of this repo and exctract it somewhere. Navigate to the ChessEngine\out\artifacts\ChessEngine_jar directory. You will see a ChessEngine.jar - this is what you want
You can move the jar wherever as the assets are already inside the jar. Just double click to run and the engine should start.

If the engine doesnt run after double clicking (And you made sure you have Java 9), then try to open cmd or terminal and navigate to the directory where the chess engine jar is, or on windows you should be able to shift+rightclick and open a powershell window in the directory. Once that is done then try to run the command "java -jar ./ChessEngine.jar"
if that doesnt work then please post an issue and maybe I can try to solve it
