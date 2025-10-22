public class TicTacToe {
    private Cell[][] board;
    private Player player1;
    private Player player2;
    private int size = 3;

    private InteractionUtilisateur ui;
    private View view;

    public TicTacToe(Player p1, Player p2, InteractionUtilisateur ui, View view) {
        this.size = 3;
        board = new Cell[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                board[i][j] = new Cell();

        this.player1 = p1;
        this.player2 = p2;
        this.ui = ui;
        this.view = view;
    }

    public void play() {
        Player currentPlayer = player1;
        int moves = 0;

        while (!isOver(moves)) {
            view.printBoard(this);

            int row, col;
            if (currentPlayer instanceof ArtificialPlayer) {
                int[] move = ((ArtificialPlayer) currentPlayer).makeMove(this);
                row = move[0];
                col = move[1];
                view.showMessage("Joueur " + currentPlayer.getSymbol() + " joue en " + row + " " + col);
            } else {
                int[] move = ui.getMove(this, currentPlayer);
                row = move[0];
                col = move[1];
            }

            board[row][col].setOwner(currentPlayer);
            moves++;

            if (hasWinner()) {
                view.printBoard(this);
                view.showMessage("Joueur " + currentPlayer.getSymbol() + " a gagné !");
                return;
            }

            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }

        view.printBoard(this);
        view.showMessage("Match nul ! Les 9 cases sont remplies.");
    }

    public boolean isOver(int moves) {
        return hasWinner() || moves >= size * size;
    }

    private boolean hasWinner() {
        // lignes
        for (int i = 0; i < size; i++)
            if (!board[i][0].isEmpty() &&
                    board[i][0].getSymbol() == board[i][1].getSymbol() &&
                    board[i][1].getSymbol() == board[i][2].getSymbol())
                return true;

        // colonnes
        for (int j = 0; j < size; j++)
            if (!board[0][j].isEmpty() &&
                    board[0][j].getSymbol() == board[1][j].getSymbol() &&
                    board[1][j].getSymbol() == board[2][j].getSymbol())
                return true;

        // diagonales
        if (!board[0][0].isEmpty() &&
                board[0][0].getSymbol() == board[1][1].getSymbol() &&
                board[1][1].getSymbol() == board[2][2].getSymbol())
            return true;

        if (!board[0][2].isEmpty() &&
                board[0][2].getSymbol() == board[1][1].getSymbol() &&
                board[1][1].getSymbol() == board[2][0].getSymbol())
            return true;

        return false;
    }

    public int getSize() { return size; }
    public boolean isCellEmpty(int row, int col) { return board[row][col].isEmpty(); }
    public char getCellSymbol(int row, int col) { return board[row][col].getSymbol(); }
}
