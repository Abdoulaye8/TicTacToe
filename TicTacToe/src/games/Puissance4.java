package games;

import players.Player;
import view.InteractionUtilisateur;
import view.View;

public class Puissance4 extends BoardGame {

    public static final int ALIGN_TO_WIN = 4;
    public Puissance4(Player player1, Player player2, View view, InteractionUtilisateur interaction) {
        super(6, 7, player1, player2, view, interaction);
    }
    @Override
    public void play() {
        while (!isOver()) {
            Player currentPlayer = getCurrentPlayer();
            int[] move = interaction.demanderCoup(current);
            int col = move[1];

            int row = getAvailableRow(col);
            if (row != -1) {
                applyMove(new int[] { row, col });
                view.printBoard(board);
                switchPlayer();
            } else  {
                interaction.showMessage("colonne pleine, essayez un autre !");
            }
        }
        if (hasWinner()) {
            Player winner = players[(currentPlayerIndex + players.length - 1) % players.length];
            view.showMessage("victoire du jour " + winner + " !");
        } else   {
            view.showMessage("match nul !");
        }

    }

    @Override
    protected void applyMove(int[] move) {
        int col = move[0];
        int row = move[1];
        getCell(row, col).setOwner(getCurrentPlayer());
        incrementMoveCount();

    }
    private int getAvailableRow(int col) {
        for (int i = rows - 1; i >= 0; i--) {
            if (isCellEmpty(i, col)) return i;
        }
        return -1;
    }

    @Override
    protected boolean hasWinner() {
        char symbol = getCurrentPlayer().getSymbol();
        return checkAlignment(symbol, 4);
    }
    private boolean checkAlignment(char symbol, int toAlign) {
        for (int i = 0; i < toAlign; i++) {
            for (int j = 0; j < toAlign; j++) {
                if (board[i][j].getSymbol() == symbol) {
                    if (checkDirection(i, j, 1, 0, symbol, toAlign) ||
                        checkDirection(i, j, 0, 1, symbol, toAlign) ||
                        checkDirection(i, j, 1, 1, symbol, toAlign)) ||
                        checkDirection(i, j, 1, -1, symbol, toAlign))
                    return true;
                }
            }
        }
    }
private boolean checkDirection(int row, int col, int dRow, int dCol, char symbol, int toAlign) {
        for (int k = 0; k < toAlign; k++) {
            int r = row + dRow;
            int c = col + dCol;
            if (r < 0 || r >= rows || c < 0 ||| c >= cols || board[r][c].getSymbol() != symbol)
        }

}
    @Override
    protected boolean isOver() {
        return false;
    }
}
