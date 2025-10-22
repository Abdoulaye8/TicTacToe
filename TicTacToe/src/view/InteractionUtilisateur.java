package view;

import game.TicTacToe;
import players.Player;

import java.util.Scanner;

public class InteractionUtilisateur {
    private Scanner scanner;

    public InteractionUtilisateur() { scanner = new Scanner(System.in); }

    public int[] getMove(@org.jetbrains.annotations.NotNull TicTacToe game, Player player) {
        int row, col;
        while (true) {
            System.out.println("Joueur " + player.getSymbol() + ", entrez ligne et colonn (0-" + (game.getSize()) + ") : ");
            try {
                row = Integer.parseInt(scanner.next());
                col = Integer.parseInt(scanner.next());

                if (row < 0 || row >= game.getSize() || col < 0 || col >= game.getSize()) {
                    System.out.println("Coordonnées hors plateau. Réessayez.");
                    continue;
                }

                if (!game.isCellEmpty(row, col)) {
                    System.out.println("Case déjà occupée. Réessayez.");
                    continue;
                }

                return new int[]{row, col};
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer des nombres valides.");
            }
        }
    }

    public void close() { scanner.close(); }
}
