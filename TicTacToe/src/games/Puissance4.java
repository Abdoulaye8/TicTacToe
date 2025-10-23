package games;

import players.Player;
import view.InteractionUtilisateur;
import view.View;

public class Puissance4 extends BoardGame {
    public Puissance4(Player player1, Player player2, View view, InteractionUtilisateur interaction) {
        super(6, 7, player1, player2, view, interaction);
    }
    @Override
    public void play() {

    }

    @Override
    protected void applyMove(int[] move) {

    }

    @Override
    protected boolean hasWinner() {
        return false;
    }

    @Override
    protected boolean isOver() {
        return false;
    }
}
