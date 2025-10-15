import java.util.Scanner;
// class principale qui gère le jeux
public class TicTacToe {
    private Cell[][] board; // ma grille
    private int size; // stocker ou si besoin de modifier
    private Player player;

    public TicTacToe(int size) { // contructeur
        this.size = size;
        this.player = new Player("campus");
// instantialisation de chaque case et parcourrir mes cellules
        board = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell();
            }
        }
    }

    // affichage du pltau ensuite parcourrir ligne et colonne
    public void display() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j].getRepresentation());
                if (j < size - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < size - 1) {
                System.out.println("---+---+---");
            }
        }
    }
// demader la saisie du player
    public int[] getMoveFromPlayer() {
        Scanner scanner = new Scanner(System.in);
        int row, col;

        while (true) {
            System.out.print("Entrez la ligne (0-2) : ");
            if (!scanner.hasNextInt()) {
                System.out.println(" Entrez un nombre !");
                scanner.next();
                continue;
            }
            row = scanner.nextInt();

            System.out.print("Entrez la colonne (0-2) : ");
            if (!scanner.hasNextInt()) {
                System.out.println(" Entrez un nombre !");
                scanner.next();
                continue;
            }
            col = scanner.nextInt();

            if (row < 0 || row >= size || col < 0 || col >= size) {
                System.out.println("Coordonnées hors du plateau !");
                continue;
            }

            if (!board[row][col].isEmpty()) {
                System.out.println("Cette case est déjà occupée !");
                continue;
            }

            break;
        }

        return new int[]{row, col};
    }
    // affecte campus à cellule choisie
    public void setOwner(int row, int col, Player player) {
        board[row][col].setOwner(player);
    }
// afficher le plteau initial
    public void play() {
        display();
        int[] move = getMoveFromPlayer();
        setOwner(move[0], move[1], player);
        System.out.println("\n mon plateau après l coup :\n");
        display();
    }
}
