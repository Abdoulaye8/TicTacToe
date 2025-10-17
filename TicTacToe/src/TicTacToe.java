import java.util.Scanner;

public class TicTacToe {
    private Cell[][] board;
    private Player player1;
    private Player player2;
    private int size = 3;

    public TicTacToe() {
        board = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell();
            }
        }
        player1 = new Player('X');
        player2 = new Player('O');
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        Player currentPlayer = player1;
        int moves = 0;

        while (!isOver(moves)) {
            printBoard();
            System.out.println("Joueur " + currentPlayer.getSymbol() + ", entrez ligne et colonne (0-2) : ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (row < 0 || row >= size || col < 0 || col >= size) {
                System.out.println("Position invalide.");
                continue;
            }

            if (!board[row][col].isEmpty()) {
                System.out.println("Case occupée.");
                continue;
            }

            board[row][col].setOwner(currentPlayer);
            moves++;

            if (hasWinner()) {
                printBoard();
                System.out.println("Joueur " + currentPlayer.getSymbol() + " a gagné !");
                return;
            }

            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }

        printBoard();
        System.out.println("Match nul ! Les 9 cases sont remplies.");
    }

    public boolean isOver(int moves) {
        return hasWinner() || moves >= size * size;
    }

    private boolean hasWinner() {
        // Vérifie lignes
        for (int i = 0; i < size; i++) {
            if (!board[i][0].isEmpty() &&
                    board[i][0].getSymbol() == board[i][1].getSymbol() &&
                    board[i][1].getSymbol() == board[i][2].getSymbol()) {
                return true;
            }
        }

        // Vérifie colonnes
        for (int j = 0; j < size; j++) {
            if (!board[0][j].isEmpty() &&
                    board[0][j].getSymbol() == board[1][j].getSymbol() &&
                    board[1][j].getSymbol() == board[2][j].getSymbol()) {
                return true;
            }
        }

        // Diagonale 1
        if (!board[0][0].isEmpty() &&
                board[0][0].getSymbol() == board[1][1].getSymbol() &&
                board[1][1].getSymbol() == board[2][2].getSymbol()) {
            return true;
        }

        // Diagonale 2
        if (!board[0][2].isEmpty() &&
                board[0][2].getSymbol() == board[1][1].getSymbol() &&
                board[1][1].getSymbol() == board[2][0].getSymbol()) {
            return true;
        }

        return false;
    }

    public void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < size; i++) {
            System.out.print("|");
            for (int j = 0; j < size; j++) {
                System.out.print(" " + board[i][j].getSymbol() + " |");
            }
            System.out.println("\n-------------");
        }
    }
}
