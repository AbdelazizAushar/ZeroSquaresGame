import java.util.*;

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
        Scanner scanner = new Scanner(System.in);
        String[][] grid = {
                {"x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x"},
                {"x", "o", "o", "o", "o", "o", "o", "o", "o", "o", "x"},
                {"x", "o", "o", "o", "o", "o", "Y", "o", "o", "o", "x"},
                {"x", "x", "o", "o", "o", "G", "o", "o", "o", "o", "x"},
                {" ", "x", "o", "o", "B", "o", "x", "x", "x", "x", "x"},
                {" ", "x", "o", "R", "o", "o", "x", " ", " ", " ", " "},
                {" ", "x", "x", "x", "x", "x", "x", " ", " ", " ", " "},
        };
        Player[] players = {
                new Player(1, 1, "yellow"),
                new Player(1, 2, "red"),
                new Player(1, 3, "blue"),
                new Player(1, 4, "green")
        };

        GridBlock[][] gr = new GridHelper().makeGrid(grid);
        ZeroSquares game = new ZeroSquares(gr, players);

        System.out.print("""
                [1] solve using BFS
                [2] solve using DFS
                [3] solve using RDFS
                [4] solve using UCS
                Choose a number between 1 and 4 :
                """);
        int choice = scanner.nextInt();
        Map<String, Object> solution = new HashMap<>();
        switch (choice) {
            case 1:
                solution = game.solveByBFS();
                break;
            case 2:
                solution = game.solveByDFS();
                break;
            case 3:
                solution = game.solveByRDFS();
                break;
            case 4:
                solution = game.solveByUCS();
                break;
            default:
                break;
        }
        printPath(solution);
    }
}