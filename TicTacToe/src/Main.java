import controller.BoardGame;
import model.players.ArtificialPlayer;
import model.players.Player;
import view.InteractionUtilisateur;
import view.View;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        View view = new View();
        InteractionUtilisateur interaction = new InteractionUtilisateur();

        Scanner input = new Scanner(System.in);

        System.out.println("==== menu des jeux ====");
        System.out.println("1 Tictactoe");
        System.out.println("2 Gomoku");
        System.out.println("3 puissance4");
        int choice = input.nextInt();

        Player player1 = new Player('X');
        Player player2 = new ArtificialPlayer('O');

        BoardGame game = null;

        switch (choice) {
            case 1:
                game = new BoardGame.TicTacToe(3, 3, player1, player2, view, interaction);
                break;
            case 2:
                game = new BoardGame.Gomoku(player1, player2, view, interaction);
                break;
            case 3:
                game = new BoardGame.Puissance4(player1, player2, view, interaction);
                break;
            default:
                System.out.println("Choix invalide !");
                System.exit(0);
        }
        game.play();
        view.showMessage("Partie terminée !");
    }
}
