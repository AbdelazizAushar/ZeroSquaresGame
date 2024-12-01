import java.util.HashMap;
import java.util.Map;

public class LevelLoader {
    static String[][][] grids = {
            // level 1
            {
                    {"x", "x", "x", "x", "x", "x", "x"},
                    {"x", "o", "o", "o", "o", "R", "x"},
                    {"x", "x", "x", "x", "x", "x", "x"}
            },
            // level 2
            {
                    {"x", "x", "x", "x", "x", "x", "x", "", ""},
                    {"x", "R", "o", "o", "o", "x", "x", "", ""},
                    {"x", "o", "o", "B", "o", "o", "x", "x", "x"},
                    {"x", "o", "o", "o", "o", "o", "o", "o", "x"},
                    {"x", "x", "x", "x", "x", "x", "x", "x", "x"},
            },
//            level 3
            {
                    {" ", "x", "x", "x", " ", " ", " "},
                    {"x", "x", "Y", "x", "x", "x", "x"},
                    {"x", "o", "o", "o", "o", "o", "x"},
                    {"x", "B", "x", "R", "x", "x", "x"},
                    {"x", "x", "x", "x", "x", " ", " "},
            },
            //level 4
            {
                    {"x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x"},
                    {"x", "o", "o", "o", "o", "o", "o", "o", "o", "o", "x"},
                    {"x", "o", "o", "o", "o", "o", "Y", "o", "o", "o", "x"},
                    {"x", "x", "o", "o", "o", "G", "o", "o", "o", "o", "x"},
                    {" ", "x", "o", "o", "B", "o", "x", "x", "x", "x", "x"},
                    {" ", "x", "o", "R", "o", "o", "x", " ", " ", " ", " "},
                    {" ", "x", "x", "x", "x", "x", "x", " ", " ", " ", " "},
            },
//            level 5
            {
                    {"x", "x", "x", "x", "x", "x", "x", "x", "x", "x"},
                    {"x", "o", "o", "o", "o", "o", "o", "o", "o", "-"},
                    {"x", "o", "x", "x", "x", "x", "x", "x", "o", "x"},
                    {"x", "o", "x", "x", "x", "x", "x", "x", "o", "x"},
                    {"x", "o", "o", "o", "o", "o", "o", "o", "R", "x"},
                    {"x", "x", "x", "x", "x", "x", "x", "x", "x", "x"},
            },
//            level 6
            {
                    {" ", " ", " ", " ", " ", " ", " ", " ", "x", "x", "x", "x"},
                    {"x", "x", "x", "x", "x", " ", " ", "x", "x", "o", "G", "x"},
                    {"x", "#", "B", "Y", "x", "x", "x", "x", "o", "o", "o", "x"},
                    {"x", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "x"},
                    {"x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x"},
            },
            // level 7
            {
                    {" ", "x", "x", "x", "x", "x", " ", " ", " ", " ", " "},
                    {"x", "x", "o", "o", "o", "x", "x", "x", "x", "x", " "},
                    {"x", "o", "o", "o", "o", "x", "x", "B", "o", "x", " "},
                    {"x", "o", "o", "o", "o", "o", "o", "o", "o", "x", "x"},
                    {"x", "o", "o", "o", "x", "x", "x", "o", "o", "R", "x"},
                    {"x", "o", "o", "o", "o", "o", "o", "o", "o", "x", "x"},
                    {"x", "x", "o", "o", "x", "x", "x", "x", "x", "x", " "},
                    {" ", "x", "x", "x", "x", " ", " ", " ", " ", " ", " "},
            }

    };

    static Player[][] all_level_players = {
//            level 1
            {new Player(1, 1, "red")},
//            level 2
            {
                    new Player(3, 7, "red"),
                    new Player(3, 6, "blue")
            },
//            level 3
            {
                    new Player(2, 1, "yellow"),
                    new Player(2, 2, "blue"),
                    new Player(2, 3, "red")
            },
//            level 4
            {
                    new Player(1, 1, "yellow"),
                    new Player(1, 2, "red"),
                    new Player(1, 3, "blue"),
                    new Player(1, 4, "green")
            },
//            level 5
            {
                    new Player(1, 1, "red")
            },
//            level 6
            {
                    new Player(3, 4, "blue"),
                    new Player(3, 5, "green"),
                    new Player(3, 6, "yellow"),
                    new Player(3, 7, "red")
            },
            // level 7
            {
                    new Player(1, 2, "red"),
                    new Player(6, 2, "blue"),
            }

    };

    public static Map<String, Object> chooseLevel(int level) {
        String[][] gr = grids[level - 1];
        GridBlock[][] grid = new GridHelper().makeGrid(gr);
        Player[] players = all_level_players[level - 1];

        Map<String, Object> init = new HashMap<>();
        init.put("grid", grid);
        init.put("players", players);
        return init;
    }
}
