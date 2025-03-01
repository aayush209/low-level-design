package tictactoe;

public class GameBoard {

    private final Piece [][] board;
    private final int size;

    public GameBoard(int size){
         this.board = new Piece[size][size];
         this.size = size;
    }

    public int getSize() {
        return size;
    }

    public boolean isCellFree(int row, int col){
        return board[row][col] == null;
    }

    public Piece getCellValue(int row, int col){
        return board[row][col];
    }

    // add piece to a location
    public boolean addPieceOnBoard(int row, int col, Piece piece){
        if(board[row][col] != null){
            System.out.println("Can't add piece here");
            return false;
        }
        board[row][col] = piece;
        return true;
    }

    // check number of free cells
    public int freeCells(){
        int freeCells = 0;
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if(board[row][col] == null)
                    freeCells++;
            }
        }

        return freeCells;
    }

    // print the board
    public void printBoard(){
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                System.out.print("|");

                if(board[row][col] == null){
                    System.out.print("  ");
                }else{
                    System.out.print(board[row][col].getPieceType().name() + " ");
                }
            }
            System.out.println("|");
        }
    }
}
