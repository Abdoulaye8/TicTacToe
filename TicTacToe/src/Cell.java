public class Cell {
    private Player owner;

    public boolean isEmpty() {
        return owner == null;
    }

    public void setOwner(Player player) {
        this.owner = player;
    }

    public char getSymbol() {
        return (owner == null) ? ' ' : owner.getSymbol();
    }
}
