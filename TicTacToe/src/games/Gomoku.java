package games;

import players.Player;
import view.InteractionUtilisateur;
import view.View;

public class Gomoku extends BoardGame {

    private static final int ALIGN_TO_WIN = 5;

    public Gomoku(Player player1, Player player2, View view, InteractionUtilisateur interaction) {
        super(15, 15, player1, player2, view, interaction);
    }

    @Override
    public void play() {
        Player winner = null;

        while (!isOver()) {
            Player currentPlayer = getCurrentPlayer();
            int[] move = interaction.demanderCoup(currentPlayer);

            int row = move[0];
            int col = move[1];

            if (isCellEmpty(row, col)) {
                applyMove(move);
                view.printBoard(board); // affichage unique
                if (hasWinner()) {
                    winner = currentPlayer;
                    break;
                } else {
                    switchPlayer();
                }
            } else {
                view.showMessage("Erreur : case déjà occupée !");
            }
        }

        if (winner != null) {
            view.showMessage("Victoire du joueur " + winner.getSymbol() + " !");
        } else {
            view.showMessage("Match nul !");
        }
    }

    @Override
    protected void applyMove(int[] move) {
        int row = move[0];
        int col = move[1];

        if (isCellEmpty(row, col)) {
            getCell(row, col).setOwner(getCurrentPlayer());
            incrementMoveCount();
        } else {
            view.showMessage("Erreur : case déjà occupée !");
        }
    }

    @Override
    protected boolean hasWinner() {
        char symbol = getCurrentPlayer().getSymbol();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j].getSymbol() == symbol) {
                    if (checkCell(i, j, 1, 0, symbol, ALIGN_TO_WIN)) {
                        return true;
                    } else if (checkCell(i, j, 0, 1, symbol, ALIGN_TO_WIN)) {
                        return true;
                    } else if (checkCell(i, j, 1, 1, symbol, ALIGN_TO_WIN)) {
                        return true;
                    } else if (checkCell(i, j, 1, -1, symbol, ALIGN_TO_WIN)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkCell(int row, int col, int dRow, int dCol, char symbol, int toAlign) {
        for (int k = 0; k < toAlign; k++) {
            int r = row + dRow * k;
            int c = col + dCol * k;

            if (r < 0 || r >= rows || c < 0 || c >= cols) {
                return false;
            } else {
                if (board[r][c].getSymbol() != symbol) {
                    return false;
                }
            }
        }
        return true;
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
