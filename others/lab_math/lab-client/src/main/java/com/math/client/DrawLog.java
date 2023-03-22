package com.math.client;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Color;
import java.util.ArrayList;


public class DrawLog {
    private final int prefW;
    private final int prefH;
    private final int borderGap;
    private final double maxScore;

    public DrawLog(int prefW, int prefH, int borderGap, double maxScore) {
        this.prefW = prefW;
        this.prefH = prefH;
        this.borderGap = borderGap;
        this.maxScore = maxScore;
    }

    public void drawRect(Graphics2D gr, ArrayList<Double[]> map, Double start, Double finish, Double gap) {
        int counter = 0;
        double xScale = ((double) prefW - 2 * borderGap) / (map.size());
        double yScale = ((double) prefH - 2 * borderGap) / maxScore;
        int x1, x2, x3, x4;
        int y1, y2, y3, y4;
        for (int i = 0; i < map.size(); i++) {
            Double[] coord = map.get(i);
            x1 = (int) (xScale * (counter) + borderGap);
            y1 = (int) ((maxScore - coord[0]) * yScale + borderGap);
            x2 = (int) (xScale * (counter++) + borderGap);
            y2 = (int) (prefH - borderGap);
            x3 = (int) (xScale * (counter) + borderGap);
            y3 = y2;
            x4 = (int) (xScale * (counter) + borderGap);
            y4 = y1;
            Polygon p = new Polygon();
            p.addPoint(x1, y1);
            p.addPoint(x2, y2);
            p.addPoint(x3, y3);
            p.addPoint(x4, y4);
            gr.setColor(Color.BLUE);
            gr.fillPolygon(p);
            }
        }

    public void drawTrap(Graphics2D gr, ArrayList<Double[]> map, Double start, Double finish, Double gap) {
        int counter = 0;
        double xScale = ((double) prefW - 2 * borderGap) / (map.size());
        double yScale = ((double) prefH - 2 * borderGap) / maxScore;
        int x1, x2, x3, x4;
        int y1, y2, y3, y4;
        for (int i = 0; i < map.size(); i++) {
            Double[] coord = map.get(i);
            x1 = (int) (xScale * (counter) + borderGap);
            y1 = (int) ((maxScore - coord[0]) * yScale + borderGap);
            x2 = (int) (xScale * (counter++) + borderGap);
            y2 = (int) (prefH - borderGap);
            x3 = (int) (xScale * (counter) + borderGap);
            y3 = y2;
            x4 = (int) (xScale * (counter) + borderGap);
            y4 = (int) ((maxScore - coord[1]) * yScale + borderGap);
            Polygon p = new Polygon();
            p.addPoint(x1, y1);
            p.addPoint(x2, y2);
            p.addPoint(x3, y3);
            p.addPoint(x4, y4);
            gr.setColor(Color.BLUE);
            gr.fillPolygon(p);
        }
    }

    public void drawSimp(Graphics2D gr, ArrayList<Double[]> map, Double start, Double finish, Double gap) {
        int counter = 0;
        double xScale = ((double) prefW - 2 * borderGap) / (map.size()) / 2;
        double yScale = ((double) prefH - 2 * borderGap) / maxScore;
        int x1, x2, x3, x4, x5;
        int y1, y2, y3, y4, y5;
        for (int i = 0; i < map.size(); i++) {
            Double[] coord = map.get(i);
            x1 = (int) (xScale * (counter) + borderGap);
            y1 = (int) ((maxScore - coord[0]) * yScale + borderGap);
            x2 = (int) (xScale * (counter) + borderGap);
            y2 = (int) (prefH - borderGap);
            counter += 2;
            x3 = (int) (xScale * (counter) + borderGap);
            y3 = y2;
            x4 = (int) (xScale * (counter) + borderGap);
            y4 = (int) ((maxScore - coord[2]) * yScale + borderGap);
            counter -= 1;
            x5 = (int) (xScale * (counter) + borderGap);
            y5 = (int) ((maxScore - coord[1]) * yScale + borderGap);
            counter += 1;
            Polygon p = new Polygon();
            p.addPoint(x1, y1);
            p.addPoint(x2, y2);
            p.addPoint(x3, y3);
            p.addPoint(x4, y4);
            p.addPoint(x5, y5);
            gr.setColor(Color.BLUE);
            gr.fillPolygon(p);
        }
    }
}
