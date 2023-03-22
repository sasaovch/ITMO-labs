package com.math.client;

import java.util.ArrayList;
import java.util.function.Function;

public class Rectangle {

    private ArrayList<Double[]> points;

    public double main(double start, double finish, Integer numberSplitPoint, WayPoint wayforSplit, Function<Double, Double> funct) {
        double length = finish - start;
        double gap = length / numberSplitPoint;
        double left = start;
        double rigth = start + gap;
        points = new ArrayList<>();
        double result = 0;
        double point;
        for (int i = 1; i <= numberSplitPoint; i++) {
            switch (wayforSplit.getName()) {
                case "left" :
                    point = funct.apply(left);
                    points.add(new Double[]{point});
                    left = rigth;
                    rigth += gap;
                    result += point * gap;
                    break;
                case "right" :
                    point = funct.apply(rigth);
                    points.add(new Double[]{point});
                    left = rigth;
                    rigth += gap;
                    result += point * gap;
                    break;
                case "center" :
                    point = funct.apply(((left + rigth) / 2));
                    points.add(new Double[]{point});
                    left = rigth;
                    rigth += gap;
                    result += point * gap;
                    break;
                case "random" :
                    point = funct.apply(Math.random() * gap + left);
                    points.add(new Double[]{point});
                    left = rigth;
                    rigth += gap;
                    result += point * gap;
                    break;
            }
        }
        return result;
    }

    public ArrayList<Double[]> getPoints() {
        return points;
    }
}
