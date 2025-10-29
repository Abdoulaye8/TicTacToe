package controller;

import model.games.Cell;
import model.players.ArtificialPlayer;
import model.players.Player;
import view.InteractionUtilisateur;
import view.View;

public abstract class BoardGame {
    protected Player[] players;
    protected int currentPlayerIndex;
    protected View view;
    protected InteractionUtilisateur interaction;
    protected Cell[][] board;
    public int rows;
    public int cols;
    private int movesCount;

    public BoardGame(int rows, int cols, Player player1, Player player2,
                     View view, InteractionUtilisateur interaction) {
        /**
         * 
         */
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

    protected Player getCurrentPlayer() {
        return players[currentPlayerIndex]; }
    protected void switchPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length; }
    public boolean isCellEmpty(int row, int col) {
        return board[row][col].isEmpty(); }
    protected Cell getCell(int row, int col) {
        return board[row][col]; }
    protected void incrementMoveCount() { movesCount++; }
    protected int getMovesCount() { return movesCount; }

    public int getRows() { return rows; }
    public int getCols() { return cols; }

    public abstract void play();
    protected abstract void applyMove(int[] move);
    protected abstract boolean hasWinner();
    protected abstract boolean isOver();

    public static class Gomoku extends BoardGame {

        private static final int ALIGN_TO_WIN = 5;

        public Gomoku(Player player1, Player player2, View view, InteractionUtilisateur interaction) {
            super(15, 15, player1, player2, view, interaction);
        }

        @Override
        public void play() {
            Player winner = null;

            while (!isOver()) {
                Player currentPlayer = getCurrentPlayer();
                int[] move;

                move = interaction.demanderCoup(currentPlayer, this);

                try {
                    applyMove(move);
                    view.printBoard(board);

                    if (hasWinner()) {
                        winner = currentPlayer;
                        break;
                    } else {
                        switchPlayer();
                    }
                } catch (IllegalArgumentException | IllegalStateException e) {
                    view.showMessage(e.getMessage());
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

            if (row < 0 || row >= rows || col < 0 || col >= cols) {
                throw new IllegalArgumentException("Coup hors du plateau !");
            }

            if (!isCellEmpty(row, col)) {
                throw new IllegalStateException("Erreur : case déjà occupée !");
            }

            getCell(row, col).setOwner(getCurrentPlayer());
            incrementMoveCount();
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

    public static class Puissance4 extends BoardGame {

        public static final int ALIGN_TO_WIN = 4;

        public Puissance4(Player player1, Player player2, View view, InteractionUtilisateur interaction) {
            super(6, 7, player1, player2, view, interaction);
        }

        @Override
        public void play() {
            Player winner = null;

            while (!isOver()) {
                Player currentPlayer = getCurrentPlayer();
                int[] move = interaction.demanderCoup(currentPlayer, this);
                int col = move[1];

                try {
                    int row = getAvailableRow(col);

                    if (row != -1) {
                        applyMove(new int[]{row, col});
                        view.printBoard(board);

                        if (hasWinner()) {
                            winner = currentPlayer;
                            break;
                        } else {
                            switchPlayer();
                        }
                    } else {
                        throw new IllegalStateException("Colonne pleine, essayez un autre !");
                    }

                } catch (IllegalArgumentException | IllegalStateException e) {
                    view.showMessage(e.getMessage());
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

            if (col < 0 || col >= cols)
                throw new IllegalArgumentException("Colonne hors plateau !");

            if (!isCellEmpty(row, col))
                throw new IllegalStateException("Colonne déjà occupée !");

            getCell(row, col).setOwner(getCurrentPlayer());
            incrementMoveCount();
        }

        private int getAvailableRow(int col) {
            for (int i = rows - 1; i >= 0; i--)
                if (isCellEmpty(i, col))
                    return i;
            return -1;
        }

        @Override
        protected boolean hasWinner() {
            char symbol = getCurrentPlayer().getSymbol();
            return checkLigne(symbol, ALIGN_TO_WIN);
        }

        private boolean checkLigne(char symbol, int ALIGN_TO_WIN) {
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                    if (board[i][j].getSymbol() == symbol)
                        if (checkCell(i, j, 1, 0, symbol, ALIGN_TO_WIN))
                            return true;
                        else if (checkCell(i, j, 0, 1, symbol, ALIGN_TO_WIN))
                            return true;
                        else if (checkCell(i, j, 1, 1, symbol, ALIGN_TO_WIN))
                            return true;
                        else if (checkCell(i, j, 1, -1, symbol, ALIGN_TO_WIN))
                            return true;
            return false;
        }

        private boolean checkCell(int row, int col, int dRow, int dCol, char symbol, int toAlign) {
            for (int k = 0; k < toAlign; k++) {
                int r = row + dRow * k;
                int c = col + dCol * k;

                if (r < 0 || r >= rows || c < 0 || c >= cols)
                    return false;

                if (board[r][c].getSymbol() != symbol)
                    return false;
            }
            return true;
        }

        @Override
        protected boolean isOver() {
            if (hasWinner())
                return true;
            if (getMovesCount() >= rows * cols)
                return true;
            return false;
        }
    }

    /**
     * class qui represente le jeu TicTacToe
     * herite de BoardGame et lui implemente les règles qui lui sont spécifiques.
     */
    public static class TicTacToe extends BoardGame {
        /**
         * constructeur de TicTacToe
         * @param rows le nombre de rows
         * @param cols nombre de colonnes
         * @param player1 joueur 1
         * @param player2 joueur 2
         * @param view interface
         * @param interaction interface utilisateur
         */

        public TicTacToe(int rows, int cols, Player player1, Player player2,
                         View view, InteractionUtilisateur interaction) {
            super(rows, cols, player1, player2, view, interaction);
        }

        /**
         * lance le déroulement de TicTacToe
         * demande les coups aux joueurs ou l'IA et affiche le plateau.
         * termine en cas gagnant ou si le plateau est vide
         */
        @Override
        public void play() {
            Player winner = null;


            while (!isOver()) {
                Player currentPlayer = getCurrentPlayer();
                int[] move;

                if (currentPlayer instanceof ArtificialPlayer) {
                    System.out.println("Tour de l'ArtificialPlayer");
                    move = ((ArtificialPlayer) currentPlayer).makeMove(this);
                } else {
                    move = interaction.demanderCoup(currentPlayer, this);
                }

                try {
                    applyMove(move);
                    view.printBoard(board);

                    if (hasWinner()) {
                        winner = currentPlayer;
                        break;
                    } else {
                        switchPlayer();
                    }
                } catch (IllegalArgumentException | IllegalStateException e) {
                    view.showMessage(e.getMessage());
                }
            }

            if (winner != null) {
                view.showMessage("victoire du joueur " + winner.getSymbol() + " !");
            } else {
                view.showMessage("le match est nul ");
            }
        }

        /**
         * pour appliquer le coup
         * @param move le tableau ligne et col
         */
        @Override
        protected void applyMove(int[] move) {
            int row = move[0];
            int col = move[1];

            if (row < 0 || row >= rows || col < 0 || col >= cols) {
                throw new IllegalArgumentException("c oup hors du plateau ");
            }

            if (!isCellEmpty(row, col)) {
                throw new IllegalStateException("case déjà occupé ");
            }

            getCell(row, col).setOwner(getCurrentPlayer());
            incrementMoveCount();
        }

        /**
         * vérifie si un joueur a gagné
         * parcout les ligne, colonnes et diagonales
         * @return le true si les symboles sont allignés.
         */

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

        /**
         * pour vérifier si le jeu est terminé.
         * @return true en de gagnant ou si les case sont remplies.
         */

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
}
