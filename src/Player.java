import java.util.Objects;

public class Player {
    int i, j;
    String color;
    boolean isInGoal;
    boolean isOut;

    public Player(int i, int j, String color) {
        this.i = i;
        this.j = j;
        this.isInGoal = false;
        this.color = color;
        this.isOut = false;
    }

    public void setIJ(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public void setInGoal(boolean inGoal) {
        isInGoal = inGoal;
    }

    public void setOut(boolean out) {
        isOut = out;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public Player(Player player) {
        this.i = player.i;
        this.j = player.j;
        this.isInGoal = player.isInGoal;
        this.color = player.color;
        this.isOut = player.isOut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return i == player.i && j == player.j
                && Objects.equals(color, player.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j, color);
    }

    public int getManhattanDistance(GridBlock[][] grid) {
        if (isInGoal) return 0;
        int manI = -1;
        int manJ = -1;
        for (int k = 0; k < grid.length; k++) {
            for (int l = 0; l < grid[k].length; l++) {
                if (grid[k][l].type.toLowerCase().equals(String.valueOf(color.charAt(0)).toLowerCase())) {
                    manI = k;
                    manJ = l;
                }
            }
        }
        if(manJ == -1 && manI == -1) {
            for (int k = 0; k < grid.length; k++) {
                for (int l = 0; l < grid[k].length; l++) {
                    if (grid[k][l].isGlobalGoal()) {
                        manI = k;
                        manJ = l;
                    }
                }
            }
        }
        return Math.abs(i - manI) + Math.abs(j - manJ);
    }
}
