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
            int[] move = interaction.demanderCoup(currentPlayer);
            int col = move[1];

            int row = getAvailableRow(col);
            if (row != -1) {
                applyMove(new int[]{row, col});
                view.printBoard(board);
                switchPlayer();
            } else {
                interaction.showMessage("colonne pleine, essayez un autre !");
            }
        }
        if (hasWinner()) {
            Player winner = players[(currentPlayerIndex + players.length - 1) % players.length];
            view.showMessage("victoire du jour " + winner + " !");
        } else {
            view.showMessage("match nul !");
        }

    }

    @Override
    protected void applyMove(int[] move) {
        int col = move[0];
        int row = move[1];

        if (isCellEmpty(row, col)) {
            getCell(row, col).setOwner(getCurrentPlayer());
            incrementMoveCount();
        } else {
            view.showMessage("colonne déjà occupé !");
        }

    }

    private int getAvailableRow(int col) {
        for (int i = rows - 1; i >= 0; i--) {
            if (isCellEmpty(i, col))
                return i;
        }
        return -1;
    }

    @Override
    protected boolean hasWinner() {
        char symbol = getCurrentPlayer().getSymbol();
        return checkLigne(symbol, ALIGN_TO_WIN);
    }

    private boolean checkLigne(char symbol, int toAlign) {
        for (int i = 0; i < toAlign; i++) {
            for (int j = 0; j < toAlign; j++) {
                if (board[i][j].getSymbol() == symbol) {
                    if (checkCell(i, j, 1, 0, symbol, toAlign)) {
                        System.out.println("align trouvé");
                        return true;
                    } else if (checkCell(i, j, 0, 1, symbol, toAlign)) {
                        System.out.println("align horizontl trouvé");
                        return true;
                    } else if (checkCell(i, j, 1, 1, symbol, toAlign)) {
                        System.out.println("align diagonal trouvé");
                        return true;
                    } else if (checkCell(i, j, 1, -1, symbol, toAlign)) {
                        System.out.println("alignement diagonal trouvé");
                        return true;
                    }

                }
            }
        }
        return false;
    }


    private boolean checkCell(int row, int col, int dRow, int dCol, char symbol, int toAlign) {
        for (int k = 0; k < toAlign; k++) {
            int r = row + dRow;
            int c = col + dCol;
            if (r < 0 || r >= rows || c < 0 || c >= cols) {
                return false;
            } else if (board[r][c].getSymbol() != symbol) {
                return true;
            }
        }
        return false;

    }

    @Override
    protected boolean isOver() {
        if (hasWinner()) {
            return true;
        } else if (getMovesCount() >= rows * cols) {
            return true;
        } else {
            return false;
        }

    }
}
