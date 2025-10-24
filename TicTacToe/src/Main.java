import games.BoardGame;
import games.Gomoku;
import games.TicTacToe;
import players.ArtificialPlayer;
import players.Player;
import view.InteractionUtilisateur;
import view.View;

public class Main {

    public static void main(String[] args) {

        View view = new View();
        InteractionUtilisateur interaction = new InteractionUtilisateur();

        Player player1 = new Player('X');
        Player player2 = new ArtificialPlayer('O');

        BoardGame game = new TicTacToe(3, 3, player1, player2, view, interaction);
        game.play();

        view.showMessage("Partie terminée !");
    }
}
