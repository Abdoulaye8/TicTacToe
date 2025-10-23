package games;

import players.Player;
import view.InteractionUtilisateur;
import view.View;

public class Gomoku extends BoardGame {
    public Gomoku(Player player1, Player player2, View view, InteractionUtilisateur interaction) {
        super(15, 15, player1, player2, view, interaction);

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
