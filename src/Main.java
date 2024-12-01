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
        System.out.println("Choose a level (1->6) : ");
        int levelNum = scanner.nextInt();
        Map<String, Object> level = LevelLoader.chooseLevel(levelNum);

        ZeroSquares game = new ZeroSquares((GridBlock[][]) level.get("grid"), (Player[]) level.get("players"));

        System.out.print("""
                [1] solve using BFS
                [2] solve using DFS
                [3] solve using RDFS
                [4] solve using UCS
                [5] solve using Hill Climbing
                [6] solve using A*
                Choose a number between 1 and 6 :
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
            case 5:
                solution = game.solveByHillClimbing();
                break;
            case 6:
                solution = game.solveByAStar();
                break;
            default:
                break;
        }
        printPath(solution);
    }
}