import java.util.Scanner;
// créer le plateau et les 2 joueurs ainsi que affichage,les coups et changement de joueur
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

        while (!isGameOver(moves)) {
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

            // Changement de joueur
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }

        printBoard();
        System.out.println("Partie terminée !");
    }
    private boolean isGameOver(int moves) {
        return moves == 9;
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
