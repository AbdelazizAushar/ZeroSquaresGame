import java.util.*;

public class State {
    GridBlock[][] grid;
    Player[] players;
    boolean isFinished;

    public State(GridBlock[][] grid, Player[] players) {
        this.grid = grid;
        this.players = players;
        this.isFinished = false;
        initPlayersInGrid();
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
        GridBlock[][] newGridBlock = deepCopyGridHelper();
        Player[] newPlayers = deepCopyPlayerHelper();
        return new State(newGridBlock, newPlayers);
    }

    private Player[] deepCopyPlayerHelper() {
        Player[] newPlayers = new Player[players.length];
        for (int i = 0; i < players.length; i++) {
            newPlayers[i] = new Player(players[i]);
        }
        return newPlayers;
    }

    private GridBlock[][] deepCopyGridHelper() {
        GridBlock[][] newGridBlock = new GridBlock[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                newGridBlock[i][j] = new GridBlock(grid[i][j]);
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

    public List<State> nextStates() {
        ArrayList<State> nextStates = new ArrayList<>();
        String[] directions = {"w", "a", "s", "d"};
        for (String direction : directions) {
            State newState = move(direction);
            if(!this.equals(newState)) nextStates.add(newState);
        }
        return nextStates;
    }
}
