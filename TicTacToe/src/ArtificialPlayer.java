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
            row = random.nextInt(game.getSize());
            col = random.nextInt(game.getSize());
        } while (!game.isCellEmpty(row, col));

        return new int[]{row, col};
    }
}
