public class GridHelper {
    public GridBlock[][] makeGrid(String[][] grid) {
        GridBlock[][] newGrid = new GridBlock[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                newGrid[i][j] = new GridBlock(grid[i][j]);
            }
        }
        return newGrid;
    }
}
