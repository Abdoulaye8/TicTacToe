package games;

import players.Player;
import players.ArtificialPlayer;
import view.InteractionUtilisateur;
import view.View;

public class TicTacToe extends BoardGame {

    public TicTacToe(int rows, int cols, Player player1, Player player2,
                     View view, InteractionUtilisateur interaction) {
        super(rows, cols, player1, player2, view, interaction);
    }

    @Override
    public void play() {
        Player winner = null;

        while (!isOver()) {
            Player currentPlayer = getCurrentPlayer();
            int[] move;

            if (currentPlayer instanceof ArtificialPlayer) {
                System.out.println("tour de l'ArtificialPlayer");
                move = ((ArtificialPlayer) currentPlayer).makeMove(this);
            } else {
                move = interaction.demanderCoup(currentPlayer);
            }

            int row = move[0];
            int col = move[1];

            if (isCellEmpty(row, col)) {
                applyMove(move);
                view.printBoard(board);
                if (hasWinner()) {
                    winner = currentPlayer;
                    break;
                } else {
                    switchPlayer();
                }
            } else {
                view.showMessage("Case déjà occupée, essayez un autre coup !");
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
            view.showMessage("Case déjà occupée !");
        }
    }

    @Override
    protected boolean hasWinner() {
        for (int i = 0; i < rows; i++) {
            if (!isCellEmpty(i, 0) &&
                    board[i][0].getSymbol() == board[i][1].getSymbol() &&
                    board[i][1].getSymbol() == board[i][2].getSymbol()) {
                return true;
            }
        }
        for (int j = 0; j < cols; j++) {
            if (!isCellEmpty(0, j) &&
                    board[0][j].getSymbol() == board[1][j].getSymbol() &&
                    board[1][j].getSymbol() == board[2][j].getSymbol()) {
                return true;
            }
        }
        if (!isCellEmpty(0,0) &&
                board[0][0].getSymbol() == board[1][1].getSymbol() &&
                board[1][1].getSymbol() == board[2][2].getSymbol()) {
            return true;
        }
        if (!isCellEmpty(0,2) &&
                board[0][2].getSymbol() == board[1][1].getSymbol() &&
                board[1][1].getSymbol() == board[2][0].getSymbol()) {
            return true;
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
