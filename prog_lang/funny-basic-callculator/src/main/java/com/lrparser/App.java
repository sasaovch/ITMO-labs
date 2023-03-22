package com.lrparser;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Deque;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            new App().parse();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    public Deque<Node> deque = new LinkedList<>();
    public void parse() {
        try (Scanner sc = new Scanner(System.in)){
            String line = sc.nextLine();
            if (!checkBrackets(line)) {
                throw new IllegalArgumentException("Brackets are not balanced");
            }
            for (int i = 0; i < line.length(); i++) {
                int c = line.charAt(i);
                if (Character.isDigit(c)) {
                    int s = Character.digit(c, 10);
                    i++;
                    while (i < line.length()) {
                        c = line.charAt(i);
                        if (Character.isDigit(c)) {
                            s = s * 10 + Character.digit(c, 10);
                            i++;
                        } else {
                            i--;
                            break;
                        }
                    }
                    Node node = new Node();
                    node.setType(TokeEnum.NUMBER);
                    node.setValue(String.valueOf(s));
                    deque.offerFirst(createAnotherTree(node));
                } else if (c == 10 || c == 61) {
                    Node node = new Node();
                    node.setType(TokeEnum.EOF);
                    node.setValue("\n");
                    System.out.println(createAnotherTree(node).print(new StringBuilder(), "", ""));
                    deque.clear();
                } else if (c == 32) {
                } else if (c == 40) {
                    Node node = new Node();
                    node.setType(TokeEnum.LPAREN);
                    node.setValue("(");
                    deque.offerFirst(node);
                } else if (c == 41) {
                    Node node = new Node();
                    node.setType(TokeEnum.RPAREN);
                    node.setValue(")");
                    deque.offerFirst(createAnotherTree(node));
                } else if (c == 42) {
                    Node node = new Node();
                    node.setType(TokeEnum.TIMES);
                    node.setValue("*");
                    deque.offerFirst(createAnotherTree(node));
                    deque.offerFirst(node);
                } else if (c == 43) {
                    Node node = new Node();
                    node.setType(TokeEnum.PLUS);
                    node.setValue("+");
                    deque.offerFirst(createAnotherTree(node));
                    deque.offerFirst(node);
                } else if (c == 45) {
                    Node node = new Node();
                    node.setType(TokeEnum.MINUS);
                    node.setValue("-");
                    deque.offerFirst(createAnotherTree(node));
                    deque.offerFirst(node);
                } else if (c == 47) {
                    Node node = new Node();
                    node.setType(TokeEnum.DIV);
                    node.setValue("/");
                    deque.offerFirst(createAnotherTree(node));
                    deque.offerFirst(node);
                } else {
                    throw new IllegalArgumentException("Illegal character: " + (char) c);
                }
            }
            if (!deque.isEmpty()) {
                throw new IllegalArgumentException("Expression is not complete. Use '=' to finish it.");
            }
        }
    }

    public Node createAnotherTree(Node node) {
        if (node.getType() == TokeEnum.NUMBER) {
            Node parent = new Node();
            parent.setType(TokeEnum.ATOM);
            parent.setValue(node.getValue());
            parent.setLeft(node);
            node.setParent(parent);
            return parent;
        } else if (node.getType() == TokeEnum.PLUS || node.getType() == TokeEnum.MINUS) {
            while (true) {
                Node previous = deque.pollFirst();
                if (previous.getType() == TokeEnum.EXPRESSION) {
                    return previous;
                } else if (previous.getType() == TokeEnum.ATOM) {
                    Node parent = new Node();
                    if (deque.isEmpty() || 
                            deque.peekFirst().getType() == TokeEnum.LPAREN ||
                            deque.peekFirst().getType() == TokeEnum.PLUS || deque.peekFirst().getType() == TokeEnum.MINUS) {
                        parent.setType(TokeEnum.MULTIPLYEXP);
                        parent.setValue(previous.getValue());
                        parent.setLeft(previous);
                        previous.setParent(parent);
                        deque.offerFirst(parent);
                    } else if (deque.peekFirst().getType() == TokeEnum.TIMES || deque.peekFirst().getType() == TokeEnum.DIV) {
                        Node center = deque.pollFirst();
                        Node left = deque.pollFirst();
                        parent.setType(TokeEnum.MULTIPLYEXP);
                        parent.setValue(countExpression(left.getValue(), center.getType().getValue(), previous.getValue()));
                        parent.setRight(previous);
                        parent.setLeft(left);
                        parent.setCenter(center);
                        previous.setParent(parent);
                        left.setParent(parent);
                        center.setParent(parent);
                        deque.offerFirst(parent);
                    } else {
                        throw new IllegalArgumentException("Unexpected token: " + previous.getType() + " " + previous.getValue());
                    }
                } else if (previous.getType() == TokeEnum.MULTIPLYEXP) {
                    Node parent = new Node();
                    if (deque.isEmpty() || deque.peekFirst().getType() == TokeEnum.LPAREN) {
                        parent.setType(TokeEnum.EXPRESSION);
                        parent.setValue(previous.getValue());
                        parent.setLeft(previous);
                        previous.setParent(parent);
                        deque.offerFirst(parent);  
                    } else if (deque.peekFirst().getType() == TokeEnum.PLUS || deque.peekFirst().getType() == TokeEnum.MINUS) {
                        Node center = deque.pollFirst();
                        Node left = deque.pollFirst();
                        parent.setType(TokeEnum.EXPRESSION);
                        parent.setValue(countExpression(left.getValue(), center.getType().getValue(), previous.getValue()));
                        parent.setRight(previous);
                        parent.setLeft(left);
                        parent.setCenter(center);
                        previous.setParent(parent);
                        left.setParent(parent);
                        center.setParent(parent);
                        deque.offerFirst(parent);
                    } else if (deque.peekFirst().getType() == TokeEnum.TIMES || deque.peekFirst().getType() == TokeEnum.DIV) {
                        Node center = deque.pollFirst();
                        Node left = deque.pollFirst();
                        parent.setType(TokeEnum.MULTIPLYEXP);
                        parent.setValue(countExpression(left.getValue(), center.getType().getValue(), previous.getValue()));
                        parent.setRight(previous);
                        parent.setLeft(left);
                        parent.setCenter(center);
                        previous.setParent(parent);
                        left.setParent(parent);
                        center.setParent(parent);
                        deque.offerFirst(parent);
                    } else {
                        throw new IllegalArgumentException("Unexpected token: " + previous.getType() + " " + previous.getValue());
                    }
                } else {
                    throw new IllegalArgumentException("Unexpected token: " + previous.getType() + " " + previous.getValue());
                }
            }
        } else if (node.getType() == TokeEnum.TIMES || node.getType() == TokeEnum.DIV) {
            while (true) {
                Node previous = deque.pollFirst();
                if (previous.getType() == TokeEnum.MULTIPLYEXP) {
                    return previous;
                } else if (deque.isEmpty() || deque.peekFirst().getType() == TokeEnum.LPAREN || (previous.getType() == TokeEnum.ATOM && (deque.peekFirst().getType() == TokeEnum.PLUS || deque.peekFirst().getType() == TokeEnum.MINUS))) {
                    Node parent = new Node();
                    parent.setType(TokeEnum.MULTIPLYEXP);
                    parent.setValue(previous.getValue());
                    parent.setLeft(previous);
                    previous.setParent(parent);
                    deque.offerFirst(parent);
                } else if (previous.getType() == TokeEnum.ATOM && (deque.peekFirst().getType() == TokeEnum.TIMES || deque.peekFirst().getType() == TokeEnum.DIV)) {
                    Node parent = new Node();
                    Node center = deque.pollFirst();
                    Node left = deque.pollFirst();
                    parent.setType(TokeEnum.MULTIPLYEXP);
                    parent.setValue(countExpression(left.getValue(), center.getType().getValue(), previous.getValue()));
                    parent.setRight(previous);
                    parent.setLeft(left);
                    parent.setCenter(center);
                    previous.setParent(parent);
                    left.setParent(parent);
                    center.setParent(parent);
                    deque.offerFirst(parent);
                } else {
                    throw new IllegalArgumentException("Unexpected token: " + previous.getType() + " " + previous.getValue());
                }
            }
        } else if (node.getType() == TokeEnum.EOF) {
            while (true) {
                Node previous = deque.pollFirst();
                if (previous.getType() == TokeEnum.EXPRESSION || deque.isEmpty()) {
                    if (previous.getType() == TokeEnum.MULTIPLYEXP) {
                        Node parent = new Node();
                        parent.setType(TokeEnum.EXPRESSION);
                        parent.setValue(previous.getValue());
                        parent.setLeft(previous);
                        parent.setRight(node);
                        previous.setParent(parent);
                        return parent;
                    }
                    return previous;
                } else if (deque.isEmpty() || deque.peekFirst().getType() == TokeEnum.LPAREN || (previous.getType() == TokeEnum.ATOM && (deque.peekFirst().getType() == TokeEnum.PLUS || deque.peekFirst().getType() == TokeEnum.MINUS))) {
                    Node parent = new Node();
                    parent.setType(TokeEnum.MULTIPLYEXP);
                    parent.setValue(previous.getValue());
                    parent.setLeft(previous);
                    previous.setParent(parent);
                    deque.offerFirst(parent);
                } else if (previous.getType() == TokeEnum.ATOM && (deque.peekFirst().getType() == TokeEnum.TIMES || deque.peekFirst().getType() == TokeEnum.DIV)) {
                    Node parent = new Node();
                    Node center = deque.pollFirst();
                    Node left = deque.pollFirst();
                    parent.setType(TokeEnum.MULTIPLYEXP);
                    parent.setValue(countExpression(left.getValue(), center.getType().getValue(), previous.getValue()));
                    parent.setRight(previous);
                    parent.setLeft(left);
                    parent.setCenter(center);
                    previous.setParent(parent);
                    left.setParent(parent);
                    center.setParent(parent);
                    deque.offerFirst(parent);
                } else if (previous.getType() == TokeEnum.MULTIPLYEXP) {
                    if (deque.isEmpty() || deque.peekFirst().getType() == TokeEnum.LPAREN) {
                        Node parent = new Node();
                        parent.setType(TokeEnum.EXPRESSION);
                        parent.setValue(previous.getValue());
                        parent.setLeft(previous);
                        previous.setParent(parent);
                        deque.offerFirst(parent);
                    } else {
                        Node parent = new Node();
                        Node center = deque.pollFirst();
                        Node left = deque.pollFirst();
                        parent.setType(TokeEnum.EXPRESSION);
                        parent.setValue(countExpression(left.getValue(), center.getType().getValue(), previous.getValue()));
                        parent.setRight(previous);
                        parent.setLeft(left);
                        parent.setCenter(center);
                        previous.setParent(parent);
                        left.setParent(parent);
                        center.setParent(parent);
                        deque.offerFirst(parent);
                    }
                } else {
                    throw new IllegalArgumentException("Unexpected token: " + previous.getType() + " " + previous.getValue());
                }
            }
        } else if (node.getType() == TokeEnum.RPAREN) {
            while (true) {
                Node previous = deque.pollFirst();
                if (previous.getType() == TokeEnum.EXPRESSION) {
                    deque.pollFirst();
                    Node parent = new Node();
                    parent.setType(TokeEnum.ATOM);
                    parent.setValue(previous.getValue());
                    parent.setLeft(previous);
                    previous.setParent(parent);
                    return parent;
                } else if (previous.getType() == TokeEnum.MULTIPLYEXP) {
                    if (deque.peekFirst().getType() == TokeEnum.LPAREN) {
                        Node parent = new Node();
                        parent.setType(TokeEnum.EXPRESSION);
                        parent.setValue(previous.getValue());
                        parent.setLeft(previous);
                        previous.setParent(parent);
                        deque.offerFirst(parent);
                    } else {
                        Node parent = new Node();
                        Node center = deque.pollFirst();
                        Node left = deque.pollFirst();
                        parent.setType(TokeEnum.EXPRESSION);
                        parent.setValue(countExpression(left.getValue(), center.getType().getValue(), previous.getValue()));
                        parent.setRight(previous);
                        parent.setLeft(left);
                        parent.setCenter(center);
                        previous.setParent(parent);
                        left.setParent(parent);
                        center.setParent(parent);
                        deque.offerFirst(parent);
                    }
                } else if (deque.peekFirst().getType() == TokeEnum.LPAREN || (previous.getType() == TokeEnum.ATOM && (deque.peekFirst().getType() == TokeEnum.PLUS || deque.peekFirst().getType() == TokeEnum.MINUS))) {
                    Node parent = new Node();
                    parent.setType(TokeEnum.MULTIPLYEXP);
                    parent.setValue(previous.getValue());
                    parent.setLeft(previous);
                    previous.setParent(parent);
                    deque.offerFirst(parent);
                } else if (previous.getType() == TokeEnum.ATOM && (deque.peekFirst().getType() == TokeEnum.TIMES || deque.peekFirst().getType() == TokeEnum.DIV)) {
                    Node parent = new Node();
                    Node center = deque.pollFirst();
                    Node left = deque.pollFirst();
                    parent.setType(TokeEnum.MULTIPLYEXP);
                    parent.setValue(countExpression(left.getValue(), center.getType().getValue(), previous.getValue()));
                    parent.setRight(previous);
                    parent.setLeft(left);
                    parent.setCenter(center);
                    previous.setParent(parent);
                    left.setParent(parent);
                    center.setParent(parent);
                    deque.offerFirst(parent);
                } else {
                    throw new IllegalArgumentException("Unexpected token: " + previous.getType() + " " + previous.getValue());
                }
            }
        } else {
            throw new IllegalArgumentException("Unexpected token: " + node.getType() + " " + node.getValue());
        }
    }

    public String countExpression(String left, String operation, String right) {
        int leftInt = Integer.parseInt(left);
        int rightInt = Integer.parseInt(right);
        if (operation.equals("+")) {
            return leftInt + rightInt + "";
        } else if (operation.equals("-")) {
            return leftInt - rightInt + "";
        } else if (operation.equals("*")) {
            return leftInt * rightInt + "";
        } else if (operation.equals("/")) {
            return ((int) leftInt / rightInt) + "";
        } else {
            throw new IllegalArgumentException("Illegal operation");
        }
    }

    // checking the string for the correct bracket sequence
    public boolean checkBrackets(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                count++;
            } else if (str.charAt(i) == ')') {
                count--;
            }
            if (count < 0) {
                return false;
            }
        }
        return count == 0;
    }
}
