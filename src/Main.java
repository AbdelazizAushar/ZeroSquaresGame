import java.io.IOException;
import java.util.*;
import java.util.logging.*;

public class Main {
    static LoggerHelper logger = new LoggerHelper();

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

    private static void comparingAlgorithms() throws IOException {
        int[] levels = {1, 2};
        int[] levelName = {10, 20};
        for (int i = 0; i < levels.length; i++) {
            Map<String, Object> level = LevelLoader.chooseLevel(levels[i]);
            ZeroSquares game = new ZeroSquares((GridBlock[][]) level.get("grid"), (Player[]) level.get("players"));
            long start = System.currentTimeMillis();
            Map<String, Object> solution = game.solveByBFS();
            long end = System.currentTimeMillis();
            long memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            logger.loggerHelper("BFS", levelName[i], solution, (end - start), memory);

            start = System.currentTimeMillis();
            solution = game.solveByDFS();
            end = System.currentTimeMillis();
            memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            logger.loggerHelper("DFS", levelName[i], solution, (end - start), memory);

            start = System.currentTimeMillis();
            solution = game.solveByUCS();
            end = System.currentTimeMillis();
            memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            logger.loggerHelper("UCS", levelName[i], solution, (end - start), memory);

            start = System.currentTimeMillis();
            solution = game.solveByRDFS();
            end = System.currentTimeMillis();
            memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            logger.loggerHelper("RDFS", levelName[i], solution, (end - start), memory);

            start = System.currentTimeMillis();
            solution = game.solveBySimpleHillClimbing();
            end = System.currentTimeMillis();
            memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            logger.loggerHelper("Simple Hill", levelName[i], solution, (end - start), memory);

            start = System.currentTimeMillis();
            solution = game.solveBySteepHillClimbing();
            end = System.currentTimeMillis();
            memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            logger.loggerHelper("Steepest Hill", levelName[i], solution, (end - start), memory);

            start = System.currentTimeMillis();
            solution = game.solveByAStar();
            end = System.currentTimeMillis();
            memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            logger.loggerHelper("A Star", levelName[i], solution, (end - start), memory);
        }
        System.out.println("Check logs folder for the files");
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a level (1->7) : ");
        int levelNum = scanner.nextInt();
        Map<String, Object> level = LevelLoader.chooseLevel(levelNum);

        ZeroSquares game = new ZeroSquares((GridBlock[][]) level.get("grid"), (Player[]) level.get("players"));

        System.out.print("""
                [1] solve using BFS
                [2] solve using DFS
                [3] solve using RDFS
                [4] solve using UCS
                [5] solve using Steep Hill Climbing
                [6] solve using Simple Hill Climbing
                [7] solve using A*
                [8] generate logs for all algorithms (will ignore the level chosen)
                Choose a number between 1 and 8 :
                """);
        int choice = scanner.nextInt();
        Map<String, Object> solution = null;
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
                solution = game.solveBySteepHillClimbing();
                break;
            case 6:
                solution = game.solveBySimpleHillClimbing();
                break;
            case 7:
                solution = game.solveByAStar();
                break;
            case 8:
                comparingAlgorithms();
                break;
            default:
                break;
        }
        if (solution == null) {
            System.out.println("No solution found");
            return;
        }
        printPath(solution);
    }
}