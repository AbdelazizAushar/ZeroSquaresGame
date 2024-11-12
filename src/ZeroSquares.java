import java.util.*;

public class ZeroSquares {
    ArrayList<State> states = new ArrayList<>();
    GridBlock[][] grid;

    public ZeroSquares(GridBlock[][] grid, Player[] players) {
        this.grid = grid;
        State firstState = new State(grid, players, null);
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

    public Map<String, Object> solveByBFS() {
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        Map<String, Object> solution = new HashMap<>();
        queue.add(states.getFirst());
        visited.add(states.getFirst());

        while(!queue.isEmpty()) {
            State currentState = queue.poll();
            if(currentState.goalCheck()) {
                ArrayList<State> path = new ArrayList<>();
                path.add(currentState);
                while(currentState.parent != null) {
                    currentState = currentState.parent;
                    path.add(currentState);
                }
                solution.put("visitedSize", visited.size());
                solution.put("path", path);
                return solution;
            }
            for(State nextState : currentState.nextStates()) {
                if(!visited.contains(nextState)) {
                    queue.add(nextState);
                    visited.add(nextState);
                }
            }
        }
        return solution;
    }

    public Map<String, Object> solveByDFS() {
        Stack<State> stack = new Stack<>();
        Set<State> visited = new HashSet<>();
        Map<String, Object> solution = new HashMap<>();
        stack.push(states.getFirst());
        visited.add(states.getFirst());

        while(!stack.isEmpty()) {
            State currentState = stack.pop();
            if(currentState.goalCheck()) {
                ArrayList<State> path = new ArrayList<>();
                path.add(currentState);
                while(currentState.parent != null) {
                    currentState = currentState.parent;
                    path.add(currentState);
                }
                solution.put("visitedSize", visited.size());
                solution.put("path", path);
                return solution;
            }
            for(State nextState : currentState.nextStates()) {
                if(!visited.contains(nextState)) {
                    stack.push(nextState);
                    visited.add(nextState);
                }
            }
        }
        return solution;
    }
}
