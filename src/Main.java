import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Main {
    private static void printPath(Map<String, Object> solution) {
        int visitedSize = (int) solution.get("visitedSize");
        ArrayList<State> path = (ArrayList<State>) solution.get("path");
        System.out.println("Visited States: " + visitedSize);
        System.out.println("Path States: " + path.size());
        Collections.reverse(path);
        for (State s : path) {
            s.printGrid();
        }
    }

    public static void main(String[] args) {
        String[][] grid = {
                {" "," "," "," "," "," "," "," ","x","x","x","x"},
                {"x","x","x","x","x"," "," ","x","x","o","G","x"},
                {"x","#","B","Y","x","x","x","x","o","o","o","x"},
                {"x","o","o","o","o","o","o","o","o","o","o","x"},
                {"x","x","x","x","x","x","x","x","x","x","x","x"},
        };

        Player[] players = {
                new Player(3, 4, "blue"),
                new Player(3, 5, "green"),
                new Player(3, 6, "yellow"),
                new Player(3, 7, "red")
        };
        GridBlock[][] gr = new GridHelper().makeGrid(grid);
        ZeroSquares game = new ZeroSquares(gr, players);

        // BFS Solution
//        Map<String, Object> solution = game.solveByDFS();
//        Map<String, Object> solution = game.solveByBFS();
        Map<String, Object> solution = game.solveByRDFS();
        printPath(solution);
//        System.out.println(solution);
    }

}