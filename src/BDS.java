import java.util.*;

public class BDS {

    Set<String> rootRepeated = new HashSet<>();
    Set<String> finalRepeated = new HashSet<>();

    Queue<Node> rootStates = new LinkedList<Node>();
    Queue<Node> finalStates = new LinkedList<Node>();

    ArrayList<Node> childrenFromRoot = new ArrayList();
    ArrayList<Node> childrenFromFinal = new ArrayList();


    public Node root;
    Node pointRoot;
    Node pointFinal;
    public Node finalNode;

    public BDS(Node node) {
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        finalNode = new Node(tiles);
        finalStates.add(finalNode);
        addStateFromFinal(finalNode);

        root = node;
        root.setMove("root");
        rootStates.add(root);
        addStateFromRoot(root);
    }

    int co = 0;

    public void Search() {
        //print root
        if (isGoal(root)) return;
        boolean b = false;
        int sw = 0;

        while (true) {
//            int s = rootStates.size();
//            for (int i = 0; i < s; i++) {
                successorFromRoot(rootStates.poll());
//            }
            b = checkRTF();
            if (b) {
                sw = 1;
                break;
            }
            childrenFromRoot.clear();

//            int s2 = finalStates.size();
//            for (int i = 0; i < s2; i++) {
                successorFromFinal(finalStates.poll());
//            }
            b = checkFTR();
            if (b) {
                sw = 2;
                break;
            }
            childrenFromFinal.clear();
        }
        if(sw == 1)
            printMoves(pointRoot);
        else printMoves(pointFinal);
    }

    private boolean checkRTF() {
        boolean b = false;
        Iterator iterator = finalStates.iterator();
        S1:
        for (int i = 0; i < childrenFromRoot.size(); i++) {
            Node n = childrenFromRoot.get(i);
            while (iterator.hasNext()) {
                Node n2 = (Node) iterator.next();
                if (OK(n, n2)) {
                    b = true;
                    pointRoot = n;
                    pointRoot.setRel(n2);
                    break S1;
                }
            }
        }
        return b;
    }

    private boolean OK(Node node, Node next) {
        boolean b = false;
        if (node.tiles[0][0] != next.tiles[0][0]) {
            return b;
        }
        if (node.tiles[0][1] != next.tiles[0][1]) {
            return b;
        }
        if (node.tiles[0][2] != next.tiles[0][2]) {
            return b;
        }
        if (node.tiles[1][0] != next.tiles[1][0]) {
            return b;
        }
        if (node.tiles[1][1] != next.tiles[1][1]) {
            return b;
        }
        if (node.tiles[1][2] != next.tiles[1][2]) {
            return b;
        }
        if (node.tiles[2][0] != next.tiles[2][0]) {
            return b;
        }
        if (node.tiles[2][1] != next.tiles[2][1]) {
            return b;
        }
        if (node.tiles[2][2] != next.tiles[2][2]) {
            return b;
        }

        b = true;
//        pointRoot = node;
//        pointRoot.setRel(next);

        return b;
    }

    private boolean checkFTR() {
        boolean b = false;
        Iterator iterator = rootStates.iterator();
        S1:
        for (int i = 0; i < childrenFromFinal.size(); i++) {
            Node n = childrenFromFinal.get(i);
            while (iterator.hasNext()) {
                Node n2 = (Node) iterator.next();
                if (OK(n, n2)) {
                    b = true;
                    pointFinal = n;
                    pointFinal.setRel(n2);
                    break S1;
                }
            }
        }
        return b;
    }

    private void printMoves(Node finalNode) {
        if(finalNode == pointRoot) {
            Node n = finalNode;
            Stack<String> stack = new Stack();
            while (n != null) {
                stack.push(n.move);
                n = n.getParent();
            }
            int co = 0;
            while (!stack.isEmpty()) {
                System.out.print(stack.pop() + ":(depth) " + co + " /");
                co++;
            }
            System.out.println();
            n = finalNode.getRel();
            while (n != null) {
                System.out.print(n.getMove() + ":(depth) " + co + " /");
                co++;
                n = n.getParent2();
            }
        }else if(finalNode == pointFinal){
            Node n = finalNode.getRel();
            Stack<String> stack = new Stack();
            while (n != null) {
                stack.push(n.move);
                n = n.getParent();
            }
            int co = 0;
            while (!stack.isEmpty()) {
                System.out.print(stack.pop() + ":(depth) " + co + " /");
                co++;
            }
            System.out.println();
            n = finalNode;
            while (n != null) {
                System.out.print(n.getMove() + ":(depth) " + co + " /");
                co++;
                n = n.getParent2();
            }
        }
    }

    public void addStateFromRoot(Node node) {
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
        rootRepeated.add(stringBuilder.toString());
    }

    public void addStateFromFinal(Node node) {
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
        finalRepeated.add(stringBuilder.toString());
    }

    private void successorFromRoot(Node node) {
        int[] position = findZero(node); // where is 0

        if (position[1] != 2) { // zero goes right and tile goes left

            Node leftNode = new Node();
            leftNode.setTiles(node.tiles);

            leftNode.tiles[position[0]][position[1]] = leftNode.tiles[position[0]][position[1] + 1];
            leftNode.tiles[position[0]][position[1] + 1] = 0;

            if (!isRepeatedFromRoot(leftNode)) {
//                node.setLeft(leftNode);
                leftNode.setParent(node);
                leftNode.setMove("left");
                childrenFromRoot.add(leftNode);
                rootStates.add(leftNode);
                addStateFromRoot(leftNode);
            }

        }
        if (position[0] != 0) { // zero goes up and tile goes down

            Node downNode = new Node();
            downNode.setTiles(node.tiles);

            downNode.tiles[position[0]][position[1]] = downNode.tiles[position[0] - 1][position[1]];
            downNode.tiles[position[0] - 1][position[1]] = 0;

            if (!isRepeatedFromRoot(downNode)) {
//                node.setDown(downNode);
                downNode.setParent(node);
                downNode.setMove("down");
                childrenFromRoot.add(downNode);
                rootStates.add(downNode);
                addStateFromRoot(downNode);
            }

        }
        if (position[0] != 2) { // zero goes up and tile goes up

            Node upNode = new Node();
            upNode.setTiles(node.tiles);

            upNode.tiles[position[0]][position[1]] = upNode.tiles[position[0] + 1][position[1]];
            upNode.tiles[position[0] + 1][position[1]] = 0;

            if (!isRepeatedFromRoot(upNode)) {
//                node.setUp(upNode);
                upNode.setParent(node);
                upNode.setMove("up");
                childrenFromRoot.add(upNode);
                rootStates.add(upNode);
                addStateFromRoot(upNode);
            }

        }
        if (position[1] != 0) { // zero goes left and tile goes right

            Node rightNode = new Node();
            rightNode.setTiles(node.tiles);

            rightNode.tiles[position[0]][position[1]] = rightNode.tiles[position[0]][position[1] - 1];
            rightNode.tiles[position[0]][position[1] - 1] = 0;


            if (!isRepeatedFromRoot(rightNode)) {
//                node.setRight(rightNode);
                rightNode.setParent(node);
                rightNode.setMove("right");
                childrenFromRoot.add(rightNode);
                rootStates.add(rightNode);
                addStateFromRoot(rightNode);
            }
        }
    }

    private void successorFromFinal(Node node) {
        int[] position = findZero(node); // where is 0

        if (position[1] != 2) { // zero goes right and tile goes left

            Node leftNode = new Node();
            leftNode.setTiles(node.tiles);

            leftNode.tiles[position[0]][position[1]] = leftNode.tiles[position[0]][position[1] + 1];
            leftNode.tiles[position[0]][position[1] + 1] = 0;

            if (!isRepeatedFromFinal(leftNode)) {
//                node.setLeft(leftNode);
                leftNode.setParent2(node);
                leftNode.setMove("right");
                childrenFromFinal.add(leftNode);
                finalStates.add(leftNode);
                addStateFromFinal(leftNode);
            }

        }
        if (position[0] != 0) { // zero goes up and tile goes down

            Node downNode = new Node();
            downNode.setTiles(node.tiles);

            downNode.tiles[position[0]][position[1]] = downNode.tiles[position[0] - 1][position[1]];
            downNode.tiles[position[0] - 1][position[1]] = 0;

            if (!isRepeatedFromFinal(downNode)) {
//                node.setDown(downNode);
                downNode.setParent2(node);
                downNode.setMove("up");
                childrenFromFinal.add(downNode);
                finalStates.add(downNode);
                addStateFromFinal(downNode);
            }

        }
        if (position[0] != 2) { // zero goes up and tile goes up

            Node upNode = new Node();
            upNode.setTiles(node.tiles);

            upNode.tiles[position[0]][position[1]] = upNode.tiles[position[0] + 1][position[1]];
            upNode.tiles[position[0] + 1][position[1]] = 0;

            if (!isRepeatedFromFinal(upNode)) {
//                node.setUp(upNode);
                upNode.setParent2(node);
                upNode.setMove("down");
                childrenFromFinal.add(upNode);
                finalStates.add(upNode);
                addStateFromFinal(upNode);
            }

        }
        if (position[1] != 0) { // zero goes left and tile goes right

            Node rightNode = new Node();
            rightNode.setTiles(node.tiles);

            rightNode.tiles[position[0]][position[1]] = rightNode.tiles[position[0]][position[1] - 1];
            rightNode.tiles[position[0]][position[1] - 1] = 0;


            if (!isRepeatedFromFinal(rightNode)) {
//                node.setRight(rightNode);
                rightNode.setParent2(node);
                rightNode.setMove("left");
                childrenFromFinal.add(rightNode);
                finalStates.add(rightNode);
                addStateFromFinal(rightNode);
            }

        }
    }

    private boolean isRepeatedFromRoot(Node node) {
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
        if (rootRepeated.contains(stringBuilder.toString()))
            b = true;

        return b;
    }

    private boolean isRepeatedFromFinal(Node node) {
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
        if (rootRepeated.contains(stringBuilder.toString()))
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
