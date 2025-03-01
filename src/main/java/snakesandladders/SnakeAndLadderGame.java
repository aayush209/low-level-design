package snakesandladders;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SnakeAndLadderGame {

    private final List<Player> players;
    private final Board board;
    private final Dice dice;
    private int currentPlayerIndex;

    public SnakeAndLadderGame(List<String> playerNames) {
        board = new Board();
        dice = new Dice();
        players = new ArrayList<>();
        for (String playerName : playerNames){
            players.add(new Player(playerName));
        }
        currentPlayerIndex = 0;
    }

    public void play() {
        while(!isGameOver()){
            Player currPlayer = players.get(currentPlayerIndex);
            int diceRoll = dice.roll();
            int newPosition = currPlayer.getPosition() + diceRoll;

            if(newPosition <= board.getBoardSize()){
                currPlayer.setPosition(board.getNewPositionAfterSnakeOrLadder(newPosition));
                log.info("{} : {} rolled a {} and moved to position {}", Thread.currentThread().getName(), currPlayer.getName(), diceRoll, currPlayer.getPosition());
            }

            if(currPlayer.getPosition() == board.getBoardSize()){
                log.info("{} : {} wins!", Thread.currentThread().getName(), currPlayer.getName());
                break;
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % players.size(); // helps to maintain players' turns in order
        }
    }

    private boolean isGameOver(){
        for(Player player : players){
            if(player.getPosition() == board.getBoardSize()){
                return true;
            }
        }
        return false;
    }
}
