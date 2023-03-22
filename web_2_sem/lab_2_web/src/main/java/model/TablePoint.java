package model;

import java.util.ArrayList;
import java.util.List;

public class TablePoint {
    private List<Point> points = new ArrayList<Point>();
    private Integer attampt = 0;

    public TablePoint() {
        points = new ArrayList<Point>();
    }
    public List<Point> getPoints() {
        return points;
    }
    public void setPoints(List<Point> points) {
        attampt = 0;
        for (Point point : points) {
            if (attampt < point.getAttampt()) {
                attampt = point.getAttampt();
            }
        }
        this.points = points;
    }
    public void addPoint(Point point) {
        point.setAttampt(attampt++);
        points.add(point);
    }
    public void clearPoints() {
        points.clear();
    }

}
