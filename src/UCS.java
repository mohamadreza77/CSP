import java.util.*;

public class UCS {

    List<Node> states = new ArrayList<>();
    Set<String> repeated = new HashSet<>();

    int c;

    public Node root;

    public UCS(Node node) {
        root = node;
        root.setMove("root");
        states.add(node);
        addState(node);
    }

    public void Search() {
        if (isGoal(root)) return;
        states.remove(0);
        successor(root);
        sortStatesByCostMaxToMin();

        Node finalNode = null;
        while(!states.isEmpty()){
            Node n = states.remove(0);
            if(isGoal(n)){
                finalNode = n;
                break;
            }
            successor(n);

            sortStatesByCostMaxToMin();
        }

        if(finalNode != null)
            printMoves(finalNode);
        else System.out.println("Can't find even a way :) ");
    }

    private void sortStatesByCostMaxToMin() {
        Collections.sort(states, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.cost - o2.cost;
            }
        });
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

    public void addState(Node node) {
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

    private void successor(Node node) {
        int[] position = findZero(node); // where is 0

        if (position[1] != 2) { // zero goes right and tile goes left

            Node leftNode = new Node();
            leftNode.setTiles(node.tiles);

            int where = findWhere(position[0], position[1]);
            leftNode.cost = node.cost + Math.abs(leftNode.tiles[position[0]][position[1] + 1] - where);

            leftNode.tiles[position[0]][position[1]] = leftNode.tiles[position[0]][position[1] + 1];
            leftNode.tiles[position[0]][position[1] + 1] = 0;

            if (!isRepeated(leftNode)) {
//                node.setLeft(leftNode);
                leftNode.setParent(node);
                leftNode.setMove("left");
                states.add(leftNode);
                addState(leftNode);
            }

        }
        if (position[0] != 0) { // zero goes up and tile goes down

            Node downNode = new Node();
            downNode.setTiles(node.tiles);

            int where = findWhere(position[0], position[1]);
            downNode.cost = node.cost + Math.abs(downNode.tiles[position[0] - 1][position[1]] - where);

            downNode.tiles[position[0]][position[1]] = downNode.tiles[position[0] - 1][position[1]];
            downNode.tiles[position[0] - 1][position[1]] = 0;

            if (!isRepeated(downNode)) {
//                node.setDown(downNode);
                downNode.setParent(node);
                downNode.setMove("down");
                states.add(downNode);
                addState(downNode);
            }

        }
        if (position[0] != 2) { // zero goes up and tile goes up

            Node upNode = new Node();
            upNode.setTiles(node.tiles);

            int where = findWhere(position[0], position[1]);
            upNode.cost = node.cost + Math.abs(upNode.tiles[position[0] + 1][position[1]] - where);

            upNode.tiles[position[0]][position[1]] = upNode.tiles[position[0] + 1][position[1]];
            upNode.tiles[position[0] + 1][position[1]] = 0;

            if (!isRepeated(upNode)) {
//                node.setUp(upNode);
                upNode.setParent(node);
                upNode.setMove("up");
                states.add(upNode);
                addState(upNode);
            }

        }
        if (position[1] != 0) { // zero goes left and tile goes right

            Node rightNode = new Node();
            rightNode.setTiles(node.tiles);

            int where = findWhere(position[0], position[1]);
            rightNode.cost = node.cost + Math.abs(rightNode.tiles[position[0]][position[1] - 1] - where);

            rightNode.tiles[position[0]][position[1]] = rightNode.tiles[position[0]][position[1] - 1];
            rightNode.tiles[position[0]][position[1] - 1] = 0;


            if (!isRepeated(rightNode)) {
//                node.setRight(rightNode);
                rightNode.setParent(node);
                rightNode.setMove("right");
                states.add(rightNode);
                addState(rightNode);
            }

        }
    }

    private boolean isRepeated(Node node) {
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
        if (repeated.contains(stringBuilder.toString()))
            b = true;

        return b;
    }

    private int[] findZero(Node node) {
        int[] position = new int[2];
        for (int i = 0; i < node.tiles.length; i++) {
            for (int j = 0; j < node.tiles[i].length; j++) {
                if (node.tiles[i][j] == 0) {
                    position[0] = i;
                    position[1] = j;
                }
            }
        }
        return position;
    }

    public boolean isGoal(Node node) {
        boolean f = true;
        if (node.tiles[2][0] != 7) {
            f = false;
            return f;
        }
        if (node.tiles[2][1] != 8) {
            f = false;
            return f;
        }

        for (int i = 0; i < 3; i++) {
            if (node.tiles[0][i] != (i + 1)) {
                f = false;
                break;
            }
            if (node.tiles[1][i] != (i + 4)) {
                f = false;
                break;
            }
        }
        return f;

    }

    private int findWhere(int i, int i0) {
        int where;
        if (i == 0 && i0 == 0) {
            where = 1;
        } else if (i == 0 && i0 == 1) {
            where = 2;
        } else if (i == 0 && i0 == 2) {
            where = 3;
        } else if (i == 1 && i0 == 0) {
            where = 4;
        } else if (i == 1 && i0 == 1) {
            where = 5;
        } else if (i == 1 && i0 == 2) {
            where = 6;
        } else if (i == 2 && i0 == 0) {
            where = 7;
        } else if (i == 2 && i0 == 1) {
            where = 8;
        } else {
            where = 9;
        }
        return where;
    }

}
