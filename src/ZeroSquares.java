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
        while (!lastState.goalCheck() && !lastState.playerOutCheck()) {
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

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            if (currentState.goalCheck()) {
                ArrayList<State> path = new ArrayList<>();
                path.add(currentState);
                while (currentState.parent != null) {
                    currentState = currentState.parent;
                    path.add(currentState);
                }
                solution.put("visitedSize", visited.size());
                solution.put("path", path);
                return solution;
            }
            for (State nextState : currentState.nextStates()) {
                if (!visited.contains(nextState)) {
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

        while (!stack.isEmpty()) {
            State currentState = stack.pop();
            visited.add(currentState);
            if (currentState.goalCheck()) {
                ArrayList<State> path = new ArrayList<>();
                path.add(currentState);
                while (currentState.parent != null) {
                    currentState = currentState.parent;
                    path.add(currentState);
                }
                solution.put("visitedSize", visited.size());
                solution.put("path", path);
                return solution;
            }
            for (State nextState : currentState.nextStates()) {
                if (!visited.contains(nextState)) {
                    stack.push(nextState);
                }
            }
        }
        return solution;
    }

    public Map<String, Object> solveByRDFS() {
        Map<String, Object> solution = new HashMap<>();
        Set<State> visited = new HashSet<>();
        State startState = states.getFirst();
        return RDFSHelper(startState, solution, visited);
    }

    public Map<String, Object> RDFSHelper(State node, Map<String, Object> solution, Set<State> visited) {
        ArrayList<State> nextStates = node.nextStates();
        visited.add(node);
        if (node.goalCheck()) {
            ArrayList<State> path = new ArrayList<>();
            path.add(node);
            while (node.parent != null) {
                node = node.parent;
                path.add(node);
            }
            solution.put("visitedSize", visited.size());
            solution.put("path", path);
            return solution;
        }
        for (State nextState : nextStates) {
            if (!visited.contains(nextState)) {
                Map<String, Object> result = RDFSHelper(nextState, solution, visited);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public Map<String, Object> solveByUCS() {
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingDouble(State::getCost));
        Set<State> visited = new HashSet<>();
        Map<String, Object> solution = new HashMap<>();
        queue.add(states.getFirst());

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            visited.add(currentState);
            if (currentState.goalCheck()) {
                ArrayList<State> path = new ArrayList<>();
                path.add(currentState);
                while (currentState.parent != null) {
                    currentState = currentState.parent;
                    path.add(currentState);
                }
                solution.put("visitedSize", visited.size());
                solution.put("path", path);
                return solution;
            }
            for (State nextState : currentState.nextStates()) {
                int tempCost = currentState.getCost() + nextState.getCost();
                if (!visited.contains(nextState) || tempCost < nextState.getCost()) {
                    nextState.setCost(tempCost);
                    queue.add(nextState);
                }
            }
        }
        return solution;
    }

    public Map<String, Object> solveBySteepHillClimbing() {
        Set<State> visited = new HashSet<>();
        Map<String, Object> solution = new HashMap<>();
        State currentState = states.getFirst();

        while (true) {
            State tempState = currentState;
            visited.add(tempState);
            int minHeuristic = tempState.getHeuristic();
            List<State> nextStates = tempState.nextStates();
            for (State nextState : nextStates) {
                if (nextState.getHeuristic() < minHeuristic) {
                    minHeuristic = nextState.getHeuristic();
                    tempState = nextState;
                }
            }

            if (currentState.getHeuristic() <= minHeuristic) {
                ArrayList<State> path = new ArrayList<>();
                path.add(tempState);
                while (tempState.parent != null) {
                    tempState = tempState.parent;
                    path.add(tempState);
                }
                solution.put("visitedSize", visited.size());
                solution.put("path", path);
                return solution;
            }
            currentState = tempState;
        }
    }

    public Map<String, Object> solveBySimpleHillClimbing() {
        Set<State> visited = new HashSet<>();
        Map<String, Object> solution = new HashMap<>();
        State currentState = states.getFirst();

        while (true) {
            State tempState = currentState;
            visited.add(tempState);
            int minHeuristic = tempState.getHeuristic();
            List<State> nextStates = tempState.nextStates();
            for (State nextState : nextStates) {
                if (nextState.getHeuristic() < minHeuristic) {
                    minHeuristic = nextState.getHeuristic();
                    tempState = nextState;
                    break;
                }
            }

            if (currentState.getHeuristic() <= minHeuristic) {
                ArrayList<State> path = new ArrayList<>();
                path.add(tempState);
                while (tempState.parent != null) {
                    tempState = tempState.parent;
                    path.add(tempState);
                }
                solution.put("visitedSize", visited.size());
                solution.put("path", path);
                return solution;
            }
            currentState = tempState;
        }
    }

    public Map<String, Object> solveByAStar() {
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingDouble(State::getHeuristicWithCost));
        Set<State> visited = new HashSet<>();
        Map<String, Object> solution = new HashMap<>();
        queue.add(states.getFirst());

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            visited.add(currentState);
            if (currentState.goalCheck()) {
                ArrayList<State> path = new ArrayList<>();
                path.add(currentState);
                while (currentState.parent != null) {
                    currentState = currentState.parent;
                    path.add(currentState);
                }
                solution.put("visitedSize", visited.size());
                solution.put("path", path);
                return solution;
            }
            for (State nextState : currentState.nextStates()) {
                int tempCost = currentState.getCost() + nextState.getCost();
                if (!visited.contains(nextState) || tempCost < nextState.getCost()) {
                    nextState.setCost(tempCost);
                    queue.add(nextState);
                }
            }
        }
        return solution;
    }
}