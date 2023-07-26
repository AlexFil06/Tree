package org.example;

public class BinTree<T extends Comparable<T>> {
    Node root;
    private int size;

    public boolean add(T value) {
        if (root == null) {
            root = new Node(value);
            root.color = Color.black;
            size = 1;
            return true;
        }
        if (addNode(root, value) != null) {
            size++;
            return true;
        }
        return false;
    }

    private Node addNode(Node node, T value) {
        if (node.value.compareTo(value) == 0)
            return null;
        if (node.value.compareTo(value) > 0) {
            if (node.left == null) {
                node.left = new Node(value);
                return node.left;
            }
            Node result = addNode(node.left, value);
            node.left = rebalance(node.left);
            return result;
        } else { // right
            if (node.right == null) {
                node.right = new Node(value);
                return node.right;
            }
            Node result = addNode(node.right, value);
            node.right = rebalance(node.right);
            return result;
        }
    }


    private Node rebalance(Node node) {
        Node result = node;
        boolean needRebalance = true;
        while (needRebalance) {
            needRebalance = false;
            if (result.right != null && result.right.color == Color.red
                    && (result.left == null || result.left.color == Color.black)) {
                needRebalance = true;
                result = rightSwap(result);
            }
            if (result.left != null && result.left.color == Color.red
                    && result.left.left != null && result.left.left.color == Color.red) {
                needRebalance = true;
                result = leftSwap(result);
            }
            if (result.left != null && result.right != null
                    && result.left.color == Color.red && result.right.color == Color.red) {
                needRebalance = true;
                colorSwap(result);
            }
        }
        return result;
    }

    private void colorSwap(Node node) {
        node.right.color = Color.black;
        node.left.color = Color.black;
        node.color = Color.red;
    }

    private Node leftSwap(Node node) {
        Node left = node.left;
        Node between = left.right;
        left.right = node;
        node.left = between;
        left.color = node.color;
        node.color = Color.red;
        return left;
    }

    private Node rightSwap(Node node) {
        Node right = node.right;
        Node between = right.left;
        right.left = node;
        node.right = between;
        right.color = node.color;
        node.color = Color.red;
        return right;
    }


    private class Node {
        T value;
        Node left;
        Node right;
        Color color;

        Node() {
            color = Color.red;
        }

        Node(T value) {
            this.value = value;
            color = Color.red;
        }
    }
    enum Color {black, red}
}