package snakesandladders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GameManager {

    private static volatile GameManager instance;
    private final AtomicInteger counter;
    private final List<SnakeAndLadderGame> games; //as multiple games are going on in parallel

    private GameManager(){
        games = new ArrayList<>();
        counter = new AtomicInteger(0);
    }

    public static GameManager getInstance(){
        if(instance == null){
            synchronized (GameManager.class){
                if(instance == null){
                    instance = new GameManager();
                }
            }
        }
        return instance;
    }

    public void startNewGame(List<String> playerNames){
        SnakeAndLadderGame newGame = new SnakeAndLadderGame(playerNames);
        games.add(newGame);

        new Thread(newGame::play, "GameNumber-" + counter.incrementAndGet()).start(); // one game per thread
    }
}
