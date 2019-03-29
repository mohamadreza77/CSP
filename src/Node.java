public class Node{
    public int [][] tiles = new int[3][3];
    public Node parent;
    public Node parent2;
    public Node left;
    public Node down;
    public Node up;
    public Node right;
    public int cost;
    public int g;
    public int h;
    public Node rel;
    public String move = "";
    public Node secondBest;

    public Node getSecondBest() {
        return secondBest;
    }

    public void setSecondBest(Node secondBest) {
        this.secondBest = secondBest;
    }

    public Node getRel() {
        return rel;
    }

    public void setRel(Node rel) {
        this.rel = rel;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Node getParent2() {
        return parent2;
    }

    public void setParent2(Node parent2) {
        this.parent2 = parent2;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public Object clone() throws
            CloneNotSupportedException
    {
        Node n = (Node) super.clone();
        return n;
    }

    public int[][] getTiles() {
        return tiles;
    }

    //    public void setTiles(int[][] tiles) {
//        this.tiles = tiles;
//    }
    public void setTiles(int[][] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, 3);
        }
    }


    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getDown() {
        return down;
    }

    public void setDown(Node down) {
        this.down = down;
    }

    public Node getUp() {
        return up;
    }

    public void setUp(Node up) {
        this.up = up;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node(int[][] tiles) {
        this.tiles = tiles;
    }
    public Node(){

    }
}
