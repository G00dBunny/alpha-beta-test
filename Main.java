import java.util.ArrayList;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // Create the root node of the tree
        Node root = new Node(0); // Root node

        // Build a full binary tree of depth 3
        buildTree(root, 3, true); // Build up to level 2 (nodes at level 3 will be filled in by user input)

        // Input values for the leaf nodes at level 3 (8 nodes)
        inputLeafNodes(root);

        // Perform alpha-beta pruning and print the result
        int result = minimax(root, 3, true);
        int result2 = minimax2(root, 3, true);
        System.out.println("Best score: " + result);
        System.out.println("Best score autre methode: " + result2);
    }

    // Method to build a full binary tree of depth 3, except for the leaf nodes (level 3)
    private static void buildTree(Node node, int depth, boolean isMaximizingPlayer) {
        if (depth == 0) return;

        // Add two children to the current node
        Node leftChild = new Node(0);  // Placeholder for future values
        Node rightChild = new Node(0);  // Placeholder for future values

        node.addChild(leftChild);
        node.addChild(rightChild);

        // Recurse into both children, alternating between maximizing and minimizing
        buildTree(leftChild, depth - 1, !isMaximizingPlayer);  // Left child
        buildTree(rightChild, depth - 1, !isMaximizingPlayer); // Right child
    }

    // Method to input the values for the leaf nodes (level 3)
    private static void inputLeafNodes(Node root) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Node> leafNodes = new ArrayList<>();
        
        // Traverse the tree to find the leaf nodes (level 3)
        collectLeafNodes(root, leafNodes);

        System.out.println("Please enter 8 values for the leaf nodes at level 3:");

        // Take user input for the leaf nodes (values at level 3)
        for (int i = 0; i < leafNodes.size(); i++) {
            System.out.print("Enter value for leaf node " + (i + 1) + ": ");
            int value = scanner.nextInt();
            leafNodes.get(i).setValue(value);  // Set user input as the value of the leaf node
        }
    }

    // Helper method to collect all leaf nodes (nodes at level 3)
    private static void collectLeafNodes(Node node, ArrayList<Node> leafNodes) {
        if (node.getChildren().isEmpty()) {
            leafNodes.add(node);  // If the node has no children, it's a leaf node
            return;
        }

        // Otherwise, recurse into child nodes
        for (Node child : node.getChildren()) {
            collectLeafNodes(child, leafNodes);
        }
    }

    // Simplified Node class to represent a state in the tree
    static class Node {
        int value;  // The evaluation value of the node
        ArrayList<Node> children;  // List of child nodes (possible moves)

        Node(int value) {
            this.value = value;
            this.children = new ArrayList<>();
        }

        void addChild(Node child) {
            children.add(child);
        }

        ArrayList<Node> getChildren() {
            return children;
        }

        int getValue() {
            return value;
        }

        void setValue(int value) {
            this.value = value;
        }
    }


    //Right minimax
    private static int minimax(Node node, int depth, boolean isMaximizingPlayer) {
        if (depth == 0 || node.getChildren().isEmpty()) {
            return node.getValue();
        }

        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (Node child : node.getChildren()) {
                int eval = minimax(child, depth - 1, false);
                maxEval = Math.max(maxEval, eval);
               
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Node child : node.getChildren()) {
                int eval = minimax(child, depth - 1,  true);
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }

    //Our minimax 
    private static int minimax2(Node node, int depth, boolean isMaximizingPlayer) {
        if (depth == 0 || node.getChildren().isEmpty()) {
            return node.getValue();
        }

        int meilleurScore = isMaximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;


        for (Node child : node.getChildren()) {

            if (isMaximizingPlayer) {
    
                int score = minimax2(child, depth - 1, !isMaximizingPlayer);
                meilleurScore = Math.max(meilleurScore, score);
                   
            }else {
                int score = minimax2(child, depth - 1, isMaximizingPlayer);
                meilleurScore = Math.min(meilleurScore, score);
                   
            }


        }
         return meilleurScore;
    }
}
