import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int[][] tiles = new int[3][3];
        Scanner s = new Scanner(System.in);
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = s.nextInt();
            }
        }
        Node node = new Node(tiles);

//        DFS dfs = new DFS(node);
//        dfs.Search();
//
//        System.out.println();
//        System.out.println();
//
//        BFS bfs = new BFS(node);
//        bfs.Search();
//
//        System.out.println();
//        System.out.println();
//
//        UCS ucs = new UCS(node);
//        ucs.Search();
//
//        System.out.println();
//        System.out.println();
//
//        BDS bds = new BDS(node);
//        bds.Search();
//
//        System.out.println();
//        System.out.println();
//
//        AStar aStar = new AStar(node);
//        aStar.Search();
//
//        System.out.println();
//        System.out.println();
//
        RBFS rbfs = new RBFS(node);
        rbfs.Search();
//
//        System.out.println();
//        System.out.println();


    }

}
