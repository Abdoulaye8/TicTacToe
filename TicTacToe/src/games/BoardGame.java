package games;

import players.Player;
import view.InteractionUtilisateur;
import view.View;

public abstract class BoardGame {
    protected Player[] players;
    protected int currentPlayerIndex;
    protected View view;
    protected InteractionUtilisateur interaction;
    protected Cell[][] board;
    protected int rows;
    protected int cols;
    private int movesCount;

    public BoardGame(int rows, int cols, Player player1, Player player2,
                     View view, InteractionUtilisateur interaction) {
        this.players = new Player[]{player1, player2};
        this.view = view;
        this.interaction = interaction;
        this.rows = rows;
        this.cols = cols;
        this.currentPlayerIndex = 0;
        this.movesCount = 0;
        initializeBoard();
    }

    public void initializeBoard() {
        board = new Cell[rows][cols];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                board[i][j] = new Cell();
            }
        }
        movesCount = 0;
    }

    protected Player getCurrentPlayer() { return players[currentPlayerIndex]; }
    protected void switchPlayer() { currentPlayerIndex = (currentPlayerIndex + 1) % players.length; }
    public boolean isCellEmpty(int row, int col) { return board[row][col].isEmpty(); }
    protected Cell getCell(int row, int col) { return board[row][col]; }
    protected void incrementMoveCount() { movesCount++; }
    protected int getMovesCount() { return movesCount; }

    public int getRows() { return rows; }
    public int getCols() { return cols; }

    public abstract void play();
    protected abstract void applyMove(int[] move);
    protected abstract boolean hasWinner();
    protected abstract boolean isOver();
}
