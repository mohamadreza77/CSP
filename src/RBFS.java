import java.util.*;

public class RBFS {
    Set<String> repeated = new HashSet<>();
    List<Node> states = new ArrayList<>();

    List<Node> children = new ArrayList<>();

    public Node root;

    public RBFS(Node node) {
        root = node;
        root.setMove("root");
        states.add(node);
        addState(node);
    }

    public void Search(){
        if(isGoal(root)) return;

        states.remove(0);
        successorRoot(root);
        sortStatesByCostMaxToMin();
        states.get(0).setSecondBest(states.get(1));

//        System.out.println("*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#");
//        for (int i = 0; i < states.size(); i++) {
//            printFinal(states.get(i));
//            //System.out.println("g : " + states.get(i).g + " h : " + states.get(i).h + " cost : " + states.get(i).cost);
//
//        }
//        System.out.println("*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#");

        Node finalNode = null;
        while(!states.isEmpty()){
            Node n = states.remove(0);
            if(isGoal(n)){
                finalNode = n;
                break;
            }
            successor(n);
            sortStatesByCostMaxToMin();
            states.get(0).setSecondBest(states.get(1));


//            for (int i = 0; i < states.size(); i++) {
//
//                printFinal(states.get(i));
//                //System.out.println("g : " + states.get(i).g + " h : " + states.get(i).h + " cost : " + states.get(i).cost);
//
//            }
//
//            System.out.println("*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#");

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

    private void successor(Node node){
        int[] position = findZero(node); // where is 0

        if(position[1] != 2){ // zero goes right and tile goes left

            Node leftNode = new Node();
            leftNode.setTiles(node.tiles);

            int where = findWhere(position[0], position[1]);
            leftNode.g = node.g + Math.abs(leftNode.tiles[position[0]][position[1] + 1] - where); // calculate g

            leftNode.tiles[position[0]][position[1]] = leftNode.tiles[position[0]][position[1] + 1];
            leftNode.tiles[position[0]][position[1] + 1] = 0;

            leftNode.h = h(leftNode.tiles); //manhattan dist
            leftNode.cost = leftNode.g + leftNode.h;

            //System.out.println("g : " + leftNode.g + " h : " + leftNode.h + " cost : " + leftNode.cost);

            if(!isRepeated(leftNode)) {
                leftNode.setParent(node);
                leftNode.setMove("left");
                children.add(leftNode);
            }

        }if(position[0] != 0){ // zero goes up and tile goes down

            Node downNode = new Node();
            downNode.setTiles(node.tiles);

            int where = findWhere(position[0], position[1]);
            downNode.g = node.g + Math.abs(downNode.tiles[position[0] - 1][position[1]] - where); // calculate g

            downNode.tiles[position[0]][position[1]] = downNode.tiles[position[0] - 1][position[1]];
            downNode.tiles[position[0] - 1][position[1]] = 0;

            downNode.h = h(downNode.tiles);
            downNode.cost = downNode.g + downNode.h;


            //System.out.println("g : " + downNode.g + " h : " + downNode.h + " cost : " + downNode.cost);

            if(!isRepeated(downNode)) {
                downNode.setParent(node);
                downNode.setMove("down");
                children.add(downNode);
            }

        }if(position[0] != 2){ // zero goes up and tile goes up

            Node upNode = new Node();
            upNode.setTiles(node.tiles);

            int where = findWhere(position[0], position[1]);
            upNode.g = node.g + Math.abs(upNode.tiles[position[0] + 1][position[1]] - where); // calculate g

            upNode.tiles[position[0]][position[1]] = upNode.tiles[position[0] + 1][position[1]];
            upNode.tiles[position[0] + 1][position[1]] = 0;

            upNode.h = h(upNode.tiles);
            upNode.cost = upNode.g + upNode.h;

            //System.out.println("g : " + upNode.g + " h : " + upNode.h + " cost : " + upNode.cost);

            if(!isRepeated(upNode)) {
                upNode.setParent(node);
                upNode.setMove("up");
                children.add(upNode);
            }

        }if(position[1] != 0){ // zero goes left and tile goes right

            Node rightNode = new Node();
            rightNode.setTiles(node.tiles);

            int where = findWhere(position[0], position[1]);
            rightNode.g = node.g + Math.abs(rightNode.tiles[position[0]][position[1] - 1] - where); // calculate g

            rightNode.tiles[position[0]][position[1]] = rightNode.tiles[position[0]][position[1] - 1];
            rightNode.tiles[position[0]][position[1] - 1] = 0;

            rightNode.h = h(rightNode.tiles);
            rightNode.cost = rightNode.g + rightNode.h;

            //System.out.println("g : " + rightNode.g + " h : " + rightNode.h + " cost : " + rightNode.cost);

            if(!isRepeated(rightNode)) {
                rightNode.setParent(node);
                rightNode.setMove("right");
                children.add(rightNode);
            }

        }

        if(children.size() != 0) {
            int min = children.get(0).cost;
            for (int i = 1; i < children.size(); i++) {
                if (children.get(i).cost < min) {
                    min = children.get(i).cost;
                }
            }
            if (min <= node.getSecondBest().getCost()) {
                node.setSecondBest(null);
                for (int i = 0; i < children.size(); i++) {
                    states.add(children.get(i));
                    addState(children.get(i));
                }

            } else {
                states.add(node);
                node.setSecondBest(null);
                node.setCost(min);
            }
            children.clear();
        }
    }

    private void successorRoot(Node node){
        int[] position = findZero(node); // where is 0

        if(position[1] != 2){ // zero goes right and tile goes left

            Node leftNode = new Node();
            leftNode.setTiles(node.tiles);

            int where = findWhere(position[0], position[1]);
            leftNode.g = node.g + Math.abs(leftNode.tiles[position[0]][position[1] + 1] - where); // calculate g

            leftNode.tiles[position[0]][position[1]] = leftNode.tiles[position[0]][position[1] + 1];
            leftNode.tiles[position[0]][position[1] + 1] = 0;

            leftNode.h = h(leftNode.tiles); //manhattan dist
            leftNode.cost = leftNode.g + leftNode.h;

            if(!isRepeated(leftNode)) {
                leftNode.setParent(node);
                leftNode.setMove("left");
                states.add(leftNode);
                addState(leftNode);
            }

        }if(position[0] != 0){ // zero goes up and tile goes down

            Node downNode = new Node();
            downNode.setTiles(node.tiles);

            int where = findWhere(position[0], position[1]);
            downNode.g = node.g + Math.abs(downNode.tiles[position[0] - 1][position[1]] - where); // calculate g

            downNode.tiles[position[0]][position[1]] = downNode.tiles[position[0] - 1][position[1]];
            downNode.tiles[position[0] - 1][position[1]] = 0;

            downNode.h = h(downNode.tiles);
            downNode.cost = downNode.g + downNode.h;

            if(!isRepeated(downNode)) {
                downNode.setParent(node);
                downNode.setMove("down");
                states.add(downNode);
                addState(downNode);
            }

        }if(position[0] != 2){ // zero goes up and tile goes up

            Node upNode = new Node();
            upNode.setTiles(node.tiles);

            int where = findWhere(position[0], position[1]);
            upNode.g = node.g + Math.abs(upNode.tiles[position[0] + 1][position[1]] - where); // calculate g

            upNode.tiles[position[0]][position[1]] = upNode.tiles[position[0] + 1][position[1]];
            upNode.tiles[position[0] + 1][position[1]] = 0;

            upNode.h = h(upNode.tiles);
            upNode.cost = upNode.g + upNode.h;

            if(!isRepeated(upNode)) {
                upNode.setParent(node);
                upNode.setMove("up");
                states.add(upNode);
                addState(upNode);
            }

        }if(position[1] != 0){ // zero goes left and tile goes right

            Node rightNode = new Node();
            rightNode.setTiles(node.tiles);

            int where = findWhere(position[0], position[1]);
            rightNode.g = node.g + Math.abs(rightNode.tiles[position[0]][position[1] - 1] - where); // calculate g

            rightNode.tiles[position[0]][position[1]] = rightNode.tiles[position[0]][position[1] - 1];
            rightNode.tiles[position[0]][position[1] - 1] = 0;

            rightNode.h = h(rightNode.tiles);
            rightNode.cost = rightNode.g + rightNode.h;

            if(!isRepeated(rightNode)) {
                rightNode.setParent(node);
                rightNode.setMove("right");
                states.add(rightNode);
                addState(rightNode);
            }

        }
    }

    private int h(int[][] tiles) {
        int manhattanDistanceSum = 0;
        for (int x = 0; x < tiles.length; x++)
            for (int y = 0; y < tiles[x].length; y++) {
                int value = tiles[x][y];
                if (value != 0) {
                    int targetX = (value - 1) / 3;
                    int targetY = (value - 1) % 3;
                    int dx = x - targetX;
                    int dy = y - targetY;
                    manhattanDistanceSum += Math.abs(dx) + Math.abs(dy);
                }
            }
        return manhattanDistanceSum;
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

    private void sortStatesByCostMaxToMin() {
        Collections.sort(states, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.cost - o2.cost;
            }
        });
    }

    private void printFinal(Node node) {
        if(node.getSecondBest() != null)
            System.out.println(node.cost + " sec : " + node.getSecondBest().cost);
        else
            System.out.println(node.cost);
        for (int i = 0; i < node.tiles.length; i++) {
            for (int j = 0; j < node.tiles[i].length; j++) {
                System.out.print(node.tiles[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
