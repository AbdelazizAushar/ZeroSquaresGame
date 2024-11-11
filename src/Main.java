public class Main {
    public static void main(String[] args) {
        String[][] grid = {
                {" "," "," "," "," "," "," "," ","x","x","x","x"},
                {"x","x","x","x","x"," "," ","x","x","o","G","x"},
                {"x","#","B","Y","x","x","x","x","o","o","o","x"},
                {"x","o","o","o","o","o","o","o","o","o","o","x"},
                {"x","x","x","x","x","x","x","x","x","x","x","x"},
        };
        GridBlock[][] gr = new GridHelper().makeGrid(grid);
        Player[] players = {
                new Player(3, 4, "blue"),
                new Player(3, 5, "green"),
                new Player(3, 6, "yellow"),
                new Player(3, 7, "red")
        };

        ZeroSquares game = new ZeroSquares(gr, players);
        game.StartGame();
    }
}