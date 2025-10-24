package games;

import players.Player;
import view.InteractionUtilisateur;
import view.View;

public class Gomoku extends BoardGame {
    public Gomoku(Player player1, Player player2, View view, InteractionUtilisateur interaction) {
        super(15, 15, player1, player2, view, interaction);

    }

    private static final int align_to_win = 5;

    @Override
    public void play() {
        while (!isOver()) {
            Player currentPlayer = getCurrentPlayer();
            int[] move = interaction.demanderCoup(currentPlayer);
            int row = move[0];
            int col = move[1];

            if (isCellEmpty(row, col)) {
                applyMove(move);
                view.printBoard(board);
                switchPlayer();
            } else {
                view.showMessage("Erreur ! case prise");
            }

        }
        if (hasWinner()) {
            Player winner = players[(currentPlayerIndex + 1) % players.length];
            view.showMessage("vitoire joueur" + winner.getSymbol() + " !");
        } else {
            view.showMessage("mach null");
        }


    }

    @Override
    protected void applyMove(int[] move) {
        int row = move[0];
        int col = move[1];
        getCell(row, col).setOwner((getCurrentPlayer()));
        incrementMoveCount();

    }

    @Override
    protected boolean hasWinner() {
        char symbol = getCurrentPlayer().getSymbol();
        return checkAlignment(symbol, align_to_win);
    }

    public boolean checkAlignment(char symbol, int toAlign) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j].getSymbol() == symbol) {
                    if (checkDirection(i, j, symbol, 1, 0, toAlign) ||
                            checkDirection(i, j, symbol, 0, 1, toAlign) ||
                            checkDirection(i, j, symbol, 1, 1, toAlign) ||
                            checkDirection(i, j, symbol, 1, -1, toAlign))
                        return true;

                }
            }
        }
        return false;


    }

    private boolean checkDirection(int row, int col, char symbol, int dRow, int dCol, int toAlign) {
        for (int k = 1; k < toAlign; k++) {
            int r = row + dRow * k;
            int c = col + dCol * k;
            if (r < 0 || r >= rows || c < 0 || c >= cols || board[r][c].getSymbol() != symbol)
                return false;
            }
        return true;

    }

    @Override
    protected boolean isOver() {
        return hasWinner() || getMovesCount() == rows * cols;
    }
}
