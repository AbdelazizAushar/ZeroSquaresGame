public class Main {
    public static void main(String[] args) {
        String[][] grid = {
                {"x","x","x","x","x","x","x"},
                {"x","o","o","o","o","R","x"},
                {"x","x","x","x","x","x","x"}
        };
        GridBlock[][] gr = new GridHelper().makeGrid(grid);
        Player[] players = {
                new Player(1, 1, "red"),
        };


        ZeroSquares game = new ZeroSquares(gr, players);
        game.StartGame();
    }
}