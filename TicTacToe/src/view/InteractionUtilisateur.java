package view;

import players.Player;

import java.util.Scanner;

public class InteractionUtilisateur {

    private Scanner scanner;

    public InteractionUtilisateur() {
        scanner = new Scanner(System.in);
    }


    public int[] demanderCoup(Player player) {
        System.out.println("Joueur " + player.getSymbol() + ", entrez votre coup (ligne colonne) :");
        int ligne = scanner.nextInt();
        int colonne = scanner.nextInt();
        return new int[]{ligne, colonne};
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
