package com.math.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Function;

public final class App {

    private static final BufferedReader IN = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter OUT = new PrintWriter(System.out, true);
    private static final Function<Double, Double> FUNCTION = s -> Math.pow(s, 2);

    private App() {
    }

    public static void main(String[] args) throws Exception {
        //ввод способа интегрирования, точки начала и конца, способа разбиения, количества интервалов
        Way way = write("Enter the integration method: rectangle, trapezoid, simpson", s -> Way.valueOf(s.toUpperCase()));
        double startPoint = write("Enter start point", Double::parseDouble);
        double finishPoint = write("Enter finish point", Double::parseDouble);
        Integer numberSplitPoint = write("Enter number split points", Integer::parseInt);
        double length = finishPoint - startPoint;
        double gap = length / numberSplitPoint;
        ArrayList<Double[]> points = new ArrayList<>();
        double result = 0;
        String[] mess = null;
        if (way == Way.RECTANGLE) {
            WayPoint wayforSplit = write("Enter way for split: left, right, center, random", s -> WayPoint.valueOf(s.toUpperCase()));
            Rectangle r = new Rectangle();
            result = r.main(startPoint, finishPoint, numberSplitPoint, wayforSplit, FUNCTION);
            points = r.getPoints();
            mess = new String[]{"Integral = " + String.format("%.10f", result), "The integration method - " + Way.RECTANGLE.getName(), "Way split - " + wayforSplit, "Number split points = " + numberSplitPoint};
        } else if (way == Way.TRAPEZOID) {
            Trapezoid t = new Trapezoid();
            result = t.main(startPoint, finishPoint, numberSplitPoint, FUNCTION);
            points = t.getPoints();
            mess = new String[]{"Integral = " + String.format("%.10f", result), "The integration method - " + Way.TRAPEZOID.getName(), "Number split points = " + numberSplitPoint};
        } else if (way == Way.SIMPSON) {
            Simpson s = new Simpson();
            result = s.main(startPoint, finishPoint, numberSplitPoint, FUNCTION);
            points = s.getPoints();
            mess = new String[]{"Integral = " + String.format("%.10f", result), "The integration method - " + Way.SIMPSON.getName(), "Number split points = " + numberSplitPoint};
        }
        OUT.printf("Result = %.10f", result);
        //начало рисования графика
        DrawGraph gr = new DrawGraph(startPoint, finishPoint, gap, points, FUNCTION, mess, way);
        gr.startDraw();
    }

    public static <T> T write(String message, Function<String, T> converter) throws IOException {
        while (true) {
            OUT.println(message);
            OUT.printf("%s", ">>>");
            String line = IN.readLine().trim();
            T result;
            try {
                result = converter.apply(line);
            } catch (IllegalArgumentException e) {
                OUT.println("It isn't correct");
                continue;
            }
            return result;
        }
    }
}
