import java.util.*;

public class State {
    GridBlock[][] grid;
    Player[] players;
    boolean isFinished;
    State parent;
    int cost, transitionCost;

    public State(GridBlock[][] grid, Player[] players, State parent) {
        this.grid = grid;
        this.players = players;
        this.isFinished = false;
        this.parent = parent;
        if(parent == null) initPlayersInGrid();
    }

    private void initPlayersInGrid() {
        for (Player player : players) {
            grid[player.i][player.j].setFree(false);
        }
    }

    // Done
    public State move(String direction) {
        State newState = deepCopy(this);
        switch (direction.toLowerCase()) {
            case "w":
                Arrays.sort(newState.players, (a, b) -> Integer.compare(a.getI(), b.getI()));
                newState.moveHelper(-1, 0);
                break;
            case "a":
                Arrays.sort(newState.players, (a, b) -> Integer.compare(a.getJ(), b.getJ()));
                newState.moveHelper(0, -1);
                break;
            case "s":
                Arrays.sort(newState.players, (a, b) -> Integer.compare(b.getI(), a.getI()));
                newState.moveHelper(+1, 0);
                break;
            case "d":
                Arrays.sort(newState.players, (a, b) -> Integer.compare(b.getJ(), a.getJ()));
                newState.moveHelper(0, +1);
                break;
        }
        return newState;
    }

    // Done
    public void printGrid() {
        System.out.println();
        for (int i = 0; i < grid.length; i++) {
            System.out.print("   ");
            for (int j = 0; j < grid[i].length; j++) {
                boolean hasPlayer = false;
                for (Player player : players) {
                    if (player.i == i && player.j == j && !player.isInGoal && !player.isOut) {
                        hasPlayer = true;
                        System.out.print(getANSIColor(player.color) + player.color.charAt(0) + "   " + "\u001B[0m");
                        break;
                    }
                }
                if (!hasPlayer) {
                    if (grid[i][j].isGoal)
                        System.out.print(getBackgroundColor(grid[i][j].type) + grid[i][j].type + "\u001B[0m" + "   ");
                    else if (grid[i][j].isFree)
                        System.out.print(getBackgroundColor("W") + grid[i][j].type + "\u001B[0m" + "   ");
                    else
                        System.out.print(getBackgroundColor("BL") + grid[i][j].type + "\u001B[0m" + "   ");

                }
            }
            System.out.println();
        }
    }

    private String getBackgroundColor(String color) {
        switch (color) {
            case "R":
                return "\u001B[41m";
            case "G":
                return "\u001B[42m";
            case "B":
                return "\u001B[44m";
            case "Y":
                return "\u001B[43m";
            case "W":
                return "\u001B[47m";
            case "BL":
                return "\u001B[40m";
            case "P":
                return "\u001B[45m";
            case "C":
                return "\u001B[46m";
            default:
                return "\u001B[0m";
        }
    }

    private String getANSIColor(String color) {
        switch (color.toLowerCase()) {
            case "red":
                return "\u001B[31m";
            case "green":
                return "\u001B[32m";
            case "yellow":
                return "\u001B[33m";
            case "blue":
                return "\u001B[34m";
            case "purple":
                return "\u001B[35m";
            case "cyan":
                return "\u001B[36m";
            default:
                return "\u001B[0m";
        }
    }

    // Done
    public boolean goalCheck() {
        for (Player player : this.players) {
            if (!player.isInGoal) return false;
        }
        return true;
    }

    // Done
    public boolean playerOutCheck() {
        for (Player player : this.players) {
            if (player.isOut) return true;
        }
        return false;
    }

    private boolean[] getMovablePlayers(int i, int j) {
        boolean[] playerCanMove = new boolean[players.length];
        Arrays.fill(playerCanMove, true);
        for (int k = 0; k < players.length; k++) {
            int tempI = players[k].i;
            int tempJ = players[k].j;
            for (int l = 0; l < players.length; l++) {
                if (l == k) continue;
                if (players[l].i == tempI + i && players[l].j == tempJ + j) {
                    if (!players[l].isInGoal) {
                        playerCanMove[k] = false;
                        break;
                    }
                }
            }
            if (players[k].isInGoal || players[k].isOut) {
                playerCanMove[k] = false;
            }
        }
        return playerCanMove;
    }

    // Done
    private void moveHelper(int i, int j) {
        boolean[] playerCanMove = getMovablePlayers(i, j);

        for (int k = 0; k < players.length; k++) {
            grid[players[k].i][players[k].j].setFree(false);
            if (players[k].isInGoal || players[k].isOut) {
                grid[players[k].i][players[k].j].setFree(true);
            }
            if (!playerCanMove[k]) {
                continue;
            }
            int newI = players[k].i;
            int newJ = players[k].j;
            while (isValidMove(newI + i, newJ + j) && !players[k].isInGoal && !players[k].isOut) {
                grid[newI][newJ].setFree(true);
                if (grid[newI + i][newJ + j].type.equals("-")) players[k].setOut(true);
                if (checkSquareFinished(players[k], newI + i, newJ + j)) {
                    players[k].setInGoal(true);
                    grid[newI + i][newJ + j].setGoal(false);
                    grid[newI][newJ].setFree(true);
                    continue;
                }
                if (grid[newI + i][newJ + j].isGlobalGoal()) {
                    grid[newI + i][newJ + j].setGoalColor(players[k].color);
                }
                players[k].setIJ(newI + i, newJ + j);
                newI = players[k].i;
                newJ = players[k].j;
                grid[newI][newJ].setFree(false);
            }
        }
    }

    // Done
    private boolean checkSquareFinished(Player player, int i, int j) {
        return player.color.charAt(0) == grid[i][j].type.toLowerCase().charAt(0);
    }

    // Done
    private boolean isValidMove(int i, int j) {
        return ((i >= 0 && i < grid.length && j >= 0 && j < grid[0].length) && (grid[i][j].isFree || grid[i][j].isWeakBlock));
    }

    // Done
    private State deepCopy(State state) {
        GridBlock[][] newGridBlock = deepCopyGridHelper(state);
        Player[] newPlayers = deepCopyPlayerHelper(state);
        return new State(newGridBlock, newPlayers, state);
    }

    private Player[] deepCopyPlayerHelper(State state) {
        Player[] newPlayers = new Player[state.players.length];
        for (int i = 0; i < state.players.length; i++) {
            newPlayers[i] = new Player(state.players[i]);
        }
        return newPlayers;
    }

    private GridBlock[][] deepCopyGridHelper(State state) {
        GridBlock[][] newGridBlock = new GridBlock[state.grid.length][state.grid[0].length];
        for (int i = 0; i < state.grid.length; i++) {
            for (int j = 0; j < state.grid[i].length; j++) {
                newGridBlock[i][j] = new GridBlock(state.grid[i][j]);
            }
        }
        return newGridBlock;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof State)) return false;
        State otherState = (State) obj;

        if (this.players.length != otherState.players.length) return false;

        Arrays.sort(this.players, Comparator.comparing(player -> player.color));
        Arrays.sort(otherState.players, Comparator.comparing(player -> player.color));

        for (int i = 0; i < players.length; i++) {
            if (!this.players[i].equals(otherState.players[i])) return false;
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (!this.grid[i][j].equals(otherState.grid[i][j])) return false;
            }
        }
        return this.isFinished == otherState.isFinished;
    }

    @Override
    public int hashCode() {
        Arrays.sort(this.players, Comparator.comparing(player -> player.color));
        return Objects.hash(Arrays.deepHashCode(grid), Arrays.hashCode(players), isFinished);
    }

    public ArrayList<State> nextStates() {
        ArrayList<State> nextStates = new ArrayList<>();
        String[] directions = {"w", "a", "s", "d"};
        int[][] directionIndexes = {
                {-1,0}, //w
                {0,-1}, //a
                {1,0},  //s
                {0,1},  //d
        };
        for (int i = 0; i < directions.length; i++) {
            State newState = move(directions[i]);
            if (!this.equals(newState)) {
                nextStates.add(newState);
                newState.setTransitionCost(calculateCost(directionIndexes[i]));
            }
        }
        return nextStates;
    }

    private int calculateCost(int[] direction) {
        int i = direction[0];
        int j = direction[1];
        int cost = 0;
        for(Player player : players) {
            int newI = player.i;
            int newJ = player.j;
            while (isValidMove(newI + i, newJ + j) && !player.isInGoal && !player.isOut) {
                cost++;
                newI += i;
                newJ += j;
            }
        }
        return cost;
    }

    public void setTransitionCost(int transitionCost) {
        this.transitionCost = transitionCost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        if (this.parent == null) return 0;
        return cost;
    }

    public int getTransitionCost() {
        return transitionCost;
    }

    public int getHeuristic() {
        int heuristic = 0;
        for (Player player : players) {
            heuristic += player.getManhattanDistance(grid);
        }
        return heuristic;
    }

    public int getHeuristicWithCost() {
        return getHeuristic() + getCost();
    }

    public boolean isSolvable() {
        Map<String, Integer> colorDuplicates = new HashMap<String, Integer>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j].isGoal) {
                    if(colorDuplicates.get(grid[i][j].type) == null) {
                        colorDuplicates.put(grid[i][j].type, 1);
                    } else {
                        return false;
                    }
                }
            }
        }
        return !playerOutCheck();
    }
}
