package tictactoe;

import java.util.Scanner;

public class TicTacToeGame {

    private Player[] players;
    private GameBoard gameBoard;
    private int [] trackBoardRows;
    private int [] trackBoardCols;
    private int diagonal;
    private int antiDiagonal;
    private int currPlayerIndex;

    public TicTacToeGame(int size) {
        initializeGame(size);
    }

    private void initializeGame(int size) {
        players = new Player[2];
        players[0] = new Player("Aayush", new PieceX());
        players[1] = new Player("Ankita", new PieceO());

        trackBoardRows = new int[size];
        trackBoardCols = new int[size];
        gameBoard = new GameBoard(size);
        currPlayerIndex = 0; // as game starts with X
    }

    public String startGame() {
        while(true){
            Player currPlayer = players[currPlayerIndex];
            gameBoard.printBoard();

            int freeCells = gameBoard.freeCells();

            if(freeCells == 0){
                break;
            }

            boolean isInputValid = false;

            while(!isInputValid){
                System.out.println("Player : " + currPlayer.getName() + "'s turn. Enter row and col (1-based index) : ");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                while (input.isEmpty() || input.isBlank() || !input.contains(",")){
                    System.out.print("Please enter row and col correctly : ");
                    input = scanner.nextLine();
                }

                String [] values = input.split(",");
                int row = Integer.parseInt(values[0]) - 1;
                int col = Integer.parseInt(values[1]) - 1;

                if(row < 0 || row >= gameBoard.getSize() || col < 0 || col >= gameBoard.getSize()){
                    System.out.println("Add correct row and col. e.g. 2,3");
                    continue;
                }

                boolean placedPieceSuccessfully = gameBoard.addPieceOnBoard(row, col, currPlayer.getPiece());
                if(!placedPieceSuccessfully){
                    System.out.println("Position already occupied, try again");
                    continue;
                }
                isInputValid = true;

                // boolean winner = isThereAWinner(row, col, currPlayer.getPiece().getPieceType());
                boolean winner = isThereAWinnerOptimized(row, col, currPlayerIndex);
                if(winner) {
                    gameBoard.printBoard();
                    return currPlayer.getName();
                }
            }

            currPlayerIndex = (currPlayerIndex + 1) % 2;
        }

        return "Game Tied";
    }

    public boolean isThereAWinner(int lastFilledRow, int lastFilledCol, PieceType pieceType) {
        boolean rowMatch = true, colMatch = true, diagonalMatch = true, antidiagonalMatch = true;

        for(int col = 0; col < gameBoard.getSize(); col++) {
            if(gameBoard.isCellFree(lastFilledRow, col) || gameBoard.getCellValue(lastFilledRow, col).getPieceType() != pieceType)
                rowMatch = false;
        }

        for(int row = 0; row < gameBoard.getSize(); row++) {
            if(gameBoard.isCellFree(row, lastFilledCol) || gameBoard.getCellValue(row, lastFilledCol).getPieceType() != pieceType)
                colMatch = false;
        }

        for(int row = 0,col = 0; row < gameBoard.getSize(); row++, col++) {
            if(gameBoard.isCellFree(row, col) || gameBoard.getCellValue(row, col).getPieceType() != pieceType)
                diagonalMatch = false;
        }

        for(int row = 0,col = gameBoard.getSize() - 1; row < gameBoard.getSize(); row++, col--) {
            if(gameBoard.isCellFree(row, col) || gameBoard.getCellValue(row, col).getPieceType() != pieceType)
                antidiagonalMatch = false;
        }

        return rowMatch || colMatch || diagonalMatch || antidiagonalMatch;
    }

    public boolean isThereAWinnerOptimized(int row, int col, int currPlayerIndex) {
        int toAdd = (currPlayerIndex == 0) ? 1 : -1;
        trackBoardRows[row] += toAdd;
        trackBoardCols[col] += toAdd;

        if(row == col){
            diagonal += toAdd;
        }

        if(row + col == gameBoard.getSize() - 1){
            antiDiagonal += toAdd;
        }

        return Math.abs(trackBoardRows[row]) == gameBoard.getSize() || Math.abs(trackBoardCols[col]) == gameBoard.getSize()
                || Math.abs(diagonal) == gameBoard.getSize() || Math.abs(antiDiagonal) == gameBoard.getSize();
    }
}
