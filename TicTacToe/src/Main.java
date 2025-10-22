import game.TicTacToe;
import players.ArtificialPlayer;
import players.Player;
import view.InteractionUtilisateur;
import view.View;

public class Main {
    public static void main(String[] args) {
        InteractionUtilisateur ui = new InteractionUtilisateur();
        View view = new View();

        Player player1 = new Player('X');
        Player player2 = new ArtificialPlayer('O');

        TicTacToe game = new TicTacToe(player1, player2, ui, view);
        game.play();

        ui.close();
    }
}
