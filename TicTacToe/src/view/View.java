package view;

import game.TicTacToe;

public class View {
    public void printBoard(TicTacToe game) {
        System.out.println("\n    0   1   2 ");
        System.out.println("  -------------");
        for (int i = 0; i < game.getSize(); i++) {
            System.out.print(i + " |");
            for (int j = 0; j < game.getSize(); j++) {
                System.out.print(" " + game.getCellSymbol(i, j) + " |");
            }
            System.out.println("\n  -------------");
        }
    }

    public void showMessage(String message) { System.out.println(message); }
}
