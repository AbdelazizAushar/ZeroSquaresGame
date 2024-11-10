import java.util.*;

public class ZeroSquares {
    ArrayList<State> states = new ArrayList<State>();
    GridBlock[][] grid;

    public ZeroSquares(GridBlock[][] grid, Player[] players) {
        this.grid = grid;
        State firstState = new State(grid, players);
        this.states.add(firstState);
    }

    public void StartGame() {
        State lastState = states.getLast();
        while(!lastState.goalCheck() && !lastState.playerOutCheck()) {
            lastState.printGrid();
            Scanner scanner = new Scanner(System.in);
            String direction = scanner.nextLine();
            State newState = lastState.move(direction);
            states.add(newState);
            lastState = states.getLast();
        }
        lastState.printGrid();
        System.out.println("Game Ended");
    }
}
