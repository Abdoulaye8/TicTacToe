package players;

import games.TicTacToe;

import java.util.Random;

public class ArtificialPlayer extends Player {
    private Random random;

    public ArtificialPlayer(char symbol) {
        super(symbol);
        random = new Random();
    }

    public int[] makeMove(TicTacToe game) {
        int row, col;
        do {
            row = random.nextInt(game.getRows());
            col = random.nextInt(game.getCols());
        } while (!game.isCellEmpty(row, col));

        return new int[]{row, col};
    }
}
