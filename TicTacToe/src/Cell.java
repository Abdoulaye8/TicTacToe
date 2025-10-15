public class Cell {
    private Player owner;

    public String getRepresentation() {
        return owner == null ? "   " : " " + owner.getRepresentation() + " ";
    }

    public void setOwner(Player player) {
        this.owner = player;
    }

    public boolean isEmpty() {
        return owner == null;
    }
}

