package com.math.client;

import java.util.ArrayList;
import java.util.function.Function;

public class Simpson {
    
    private ArrayList<Double[]> points;

    public double main(double start, double finish, Integer numberSplitPoint, Function<Double, Double> funct) {
        double length = finish - start;
        double gap = length / numberSplitPoint;
        double left = start;
        double rigth = start + gap;
        points = new ArrayList<>();
        double result = 0;
        double pointLeft, pointRight, pointCenter;
        pointLeft = funct.apply(left);
        for (int i = 1; i <= numberSplitPoint; i++) {
            pointRight = funct.apply(rigth);
            pointCenter = funct.apply((rigth + left) / 2);
            points.add(new Double[]{pointLeft, pointCenter, pointRight});
            result += (pointLeft + 4 * pointCenter + pointRight) / 6 * gap;
            left = rigth;
            pointLeft = pointRight;
            rigth += gap;
        }
        return result;
    }

    public ArrayList<Double[]> getPoints() {
        return points;
    }
}
