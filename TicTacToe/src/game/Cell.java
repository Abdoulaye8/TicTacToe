package game;

import players.Player;

public class Cell {
    private Player owner;

    public boolean isEmpty() { return owner == null; }
    public void setOwner(Player player) { owner = player; }
    public char getSymbol() { return (owner == null) ? ' ' : owner.getSymbol(); }
}
