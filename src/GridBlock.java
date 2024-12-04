import java.util.Objects;

public class GridBlock {
    boolean isFree, isGoal, isWeakBlock;
    String type;

    // o == isFree, !isGoal, !isWeakBlock
    // x == !isFree, !isGoal, !isWeakBlock
    // - == isFree, !isGoal, isWeakBlock
    // # == isFree, isGoal, !isWeakBlock
    // goal == isFree, isGoal, !isWeakBlock
    public GridBlock(String type) {
        this.type = type;
        switch (type) {
            case "o":
                isFree = true;
                isGoal = false;
                isWeakBlock = false;
                break;
            case "x":
                isFree = false;
                isGoal = false;
                isWeakBlock = false;
                break;
            case "-":
                isFree = true;
                isGoal = false;
                isWeakBlock = true;
                break;
            case "#":
                isFree = true;
                isGoal = true;
                isWeakBlock = false;
                break;
            case " ":
                break;
            default: // goal (uppercase letter)
                isFree = true;
                isGoal = true;
                isWeakBlock = false;
                break;
        }
    }

    public void setGoal(boolean goal) {
        isGoal = goal;
        this.type = "o";
    }

    public void setFree(boolean free) {
        this.isFree = free;
        if (this.isGoal) return;
        if (this.isWeakBlock) this.type = "-";
        else if (!isFree) this.type = "x";
        else this.type = "o";
    }

    public GridBlock(GridBlock gridBlock) {
        this.isFree = gridBlock.isFree;
        this.isGoal = gridBlock.isGoal;
        this.type = gridBlock.type;
        this.isWeakBlock = gridBlock.isWeakBlock;
    }

    public boolean isGlobalGoal() {
        return isGoal && Objects.equals(type, "#");
    }

    public void setGoalColor(String goalColor) {
        this.type = String.valueOf(goalColor.toUpperCase().charAt(0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridBlock gridBlock = (GridBlock) o;
        return isFree == gridBlock.isFree && isGoal == gridBlock.isGoal
                && isWeakBlock == gridBlock.isWeakBlock &&
                Objects.equals(type, gridBlock.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isFree, isGoal, isWeakBlock, type);
    }
}
