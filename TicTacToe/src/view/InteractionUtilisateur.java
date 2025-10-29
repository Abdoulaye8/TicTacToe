package view;

import controller.BoardGame;
import model.players.Player;

import java.util.Scanner;

public class InteractionUtilisateur {

    private Scanner scanner;

    public InteractionUtilisateur() {
        scanner = new Scanner(System.in);
    }

    /**
     * Demande un coup à un joueur pour n'importe quel BoardGame
     * @param player le joueur actif
     * @param game le plateau de jeu (TicTacToe, Gomoku, Puissance4)
     * @return tableau [ligne, colonne] (pour Puissance4 : ligne sera déterminée automatiquement)
     */
    public int[] demanderCoup(Player player, BoardGame game) {
        int ligne = -1;
        int colonne = -1;
        boolean valid = false;

        while (!valid) {
            try {
                if (game.getRows() > 1) {
                    System.out.println("joueur " + player.getSymbol() + ", entre la ligne (" + (game.getRows() - 1) + "): ");
                    String ligneStr = scanner.nextLine();
                    ligne = Integer.parseInt(ligneStr);
                    if (ligne < 0 || ligne >= game.getRows()) {
                        throw new IllegalArgumentException(" ton coup est hors plateau");
                    }
                }
                System.out.println("entre la colonne (" + (game.getCols() - 1) + "): ");
                String colonneStr = scanner.nextLine();
                colonne = Integer.parseInt(colonneStr);
                if (colonne < 0 || colonne >= game.getCols()) {
                    throw new IllegalArgumentException("ton coup hors du plateau ");
                }

                valid = true;

            } catch (NumberFormatException e) {
                System.out.println(" oups il faut un nombre entier");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        return new int[]{ligne, colonne};
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
