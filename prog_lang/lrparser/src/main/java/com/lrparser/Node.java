package com.lrparser;

import lombok.Data;

@Data
public class Node {
    public Node parent;
    public Node left;
    public Node right;
    public Node center;
    public TokeEnum type;
    public String value; 

    public String print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(type + " " + value);
        buffer.append('\n');
        if (left != null && (right != null || center != null)) {
            left.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
        } else if (left != null) {
            left.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
        }
        if (center != null && right != null) {
            center.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            right.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
        } else if (center != null) {
            center.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
        } else if (right != null) {
            right.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
        }
        return buffer.toString();
    }
}
