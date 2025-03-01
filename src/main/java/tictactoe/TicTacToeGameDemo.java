package tictactoe;

import java.util.Scanner;

public class TicTacToeGameDemo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter size of the board : ");
        String input = scanner.nextLine();
        while (input.isEmpty() || input.isBlank()){
            System.out.print("Please enter size of the board again : ");
            input = scanner.nextLine();
        }
        int size = Integer.parseInt(input);
        if(size < 3 || size > 10){
            System.out.println("Board size must be between 3 to 10");
        }

        TicTacToeGame game = new TicTacToeGame(size);
        System.out.println("Game winner is : " + game.startGame());
    }
}
