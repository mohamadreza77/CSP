import java.util.*;

public class BFS {
    Set<String> repeated = new HashSet<>();
    Queue<Node> states = new LinkedList<Node>();

    public Node root;

    public BFS(Node node) {
        root = node;
        root.setMove("root");
        states.add(node);
        addState(node);
    }

    public void Search(){
        //print root
        if(isGoal(root)) return;

        //start search from root
        states.poll();
        successor(root);

        Node finalNode = null;
        while(!states.isEmpty()){
            Node n = states.poll();
            if(isGoal(n)){
                finalNode = n;
                break;
            }
            successor(n);
        }

        if(finalNode != null)
            printMoves(finalNode);
        else System.out.println("Can't find even a way :) ");


    }

    private void printMoves(Node finalNode) {
        Node n = finalNode;
        Stack<String> stack = new Stack();
        while(n != null){
            stack.push(n.move);
            n = n.getParent();
        }
        int co = 0;
        while(!stack.isEmpty()) {
            System.out.print(stack.pop() + ":(depth) " + co + " /");
            co++;
        }
    }

    public void addState(Node node){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < node.tiles.length; i++) {
            stringBuilder.append(node.tiles[0][i]);
        }
        for (int i = 0; i < node.tiles.length; i++) {
            stringBuilder.append(node.tiles[1][i]);
        }
        for (int i = 0; i < node.tiles.length; i++) {
            stringBuilder.append(node.tiles[2][i]);
        }
        repeated.add(stringBuilder.toString());
    }

    private void printFinal(Node node) {
        for (int i = 0; i < node.tiles.length; i++) {
            for (int j = 0; j < node.tiles[i].length; j++) {
                System.out.print(node.tiles[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void successor(Node node){
        int[] position = findZero(node); // where is 0

        if(position[1] != 2){ // zero goes right and tile goes left

            Node leftNode = new Node();
            leftNode.setTiles(node.tiles);

            leftNode.tiles[position[0]][position[1]] = leftNode.tiles[position[0]][position[1] + 1];
            leftNode.tiles[position[0]][position[1] + 1] = 0;

            if(!isRepeated(leftNode)) {
//                node.setLeft(leftNode);
                leftNode.setParent(node);
                leftNode.setMove("left");
                states.add(leftNode);
                addState(leftNode);
            }

        }if(position[0] != 0){ // zero goes up and tile goes down

            Node downNode = new Node();
            downNode.setTiles(node.tiles);

            downNode.tiles[position[0]][position[1]] = downNode.tiles[position[0] - 1][position[1]];
            downNode.tiles[position[0] - 1][position[1]] = 0;

            if(!isRepeated(downNode)) {
//                node.setDown(downNode);
                downNode.setParent(node);
                downNode.setMove("down");
                states.add(downNode);
                addState(downNode);
            }

        }if(position[0] != 2){ // zero goes up and tile goes up

            Node upNode = new Node();
            upNode.setTiles(node.tiles);

            upNode.tiles[position[0]][position[1]] = upNode.tiles[position[0] + 1][position[1]];
            upNode.tiles[position[0] + 1][position[1]] = 0;

            if(!isRepeated(upNode)) {
//                node.setUp(upNode);
                upNode.setParent(node);
                upNode.setMove("up");
                states.add(upNode);
                addState(upNode);
            }

        }if(position[1] != 0){ // zero goes left and tile goes right

            Node rightNode = new Node();
            rightNode.setTiles(node.tiles);

            rightNode.tiles[position[0]][position[1]] = rightNode.tiles[position[0]][position[1] - 1];
            rightNode.tiles[position[0]][position[1] - 1] = 0;


            if(!isRepeated(rightNode)) {
//                node.setRight(rightNode);
                rightNode.setParent(node);
                rightNode.setMove("right");
                states.add(rightNode);
                addState(rightNode);
            }

        }
    }

    private boolean isRepeated(Node node){
        boolean b = false;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < node.tiles.length; i++) {
            stringBuilder.append(node.tiles[0][i]);
        }
        for (int i = 0; i < node.tiles.length; i++) {
            stringBuilder.append(node.tiles[1][i]);
        }
        for (int i = 0; i < node.tiles.length; i++) {
            stringBuilder.append(node.tiles[2][i]);
        }
        if(repeated.contains(stringBuilder.toString()))
            b = true;

        return b;
    }

    private int[] findZero(Node node) {
        int[] position = new int[2];
        for (int i = 0; i < node.tiles.length; i++) {
            for (int j = 0; j < node.tiles[i].length; j++) {
                if(node.tiles[i][j] == 0){
                    position[0] = i;
                    position[1] = j;
                }
            }
        }
        return position;
    }

    public boolean isGoal(Node node){
        boolean f = true;
        if(node.tiles[2][0] != 7) {
            f = false;
            return f;
        }
        if(node.tiles[2][1] != 8) {
            f = false;
            return f;
        }

        for (int i = 0; i < 3; i++) {
            if(node.tiles[0][i] != (i+1)){
                f = false;
                break;
            }
            if(node.tiles[1][i] != (i+4)){
                f = false;
                break;
            }
        }
        return f;

    }
}
