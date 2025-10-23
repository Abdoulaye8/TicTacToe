package view;

import games.Cell;

public class View {

    public void printBoard(Cell[][] board) {
        int rows = board.length;
        int cols = board[0].length;


        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("[" + board[i][j].getSymbol() + "]");
            }
            System.out.println();
        }
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
