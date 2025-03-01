package snakesandladders;

import lombok.Getter;

@Getter
public class Player {

    private final String name;
    private int position;

    public Player(String name){
        this.name = name;
        this.position = 0;
    }

    public void setPosition(int newPosition){
        this.position = newPosition;
    }
}
