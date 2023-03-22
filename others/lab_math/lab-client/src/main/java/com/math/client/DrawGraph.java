package com.math.client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Font;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.geom.AffineTransform;


public class DrawGraph extends JPanel {
   private final int prefW = 1024;
   private final int prefH = 720;
   private final int borderGap = 30;
   private final Color graphColor = Color.green;
   private final Stroke graphStroke = new BasicStroke(3f);
   private final int resolution = 100;
   private Double maxScore;
   private Way way;
   private Double start, finish, gap;
   private ArrayList<Double[]> map;
   private String[] mess;
   private Function<Double, Double> funct;
   private List<Double> scores;

   public DrawGraph(Double start, Double finish, double gap, ArrayList<Double[]> map, Function<Double, Double> funct, String[] mess, Way way) {
      this.start = start;
      this.finish = finish;
      this.gap = gap;
      this.map = map;
      this.mess = mess;
      this.way = way;
      this.funct = funct;
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      // рисование столбцов
      DrawLog log = new DrawLog(prefW, prefH, borderGap, maxScore);
      if (way == Way.RECTANGLE) {
         log.drawRect(g2, map, start, finish, gap);
      } else if (way == Way.TRAPEZOID) {
         log.drawTrap(g2, map, start, finish, gap);
      } else {
         log.drawSimp(g2, map, start, finish, gap);
      }
      g2.setColor(Color.GRAY);
      // определение шага для оси x и y
      double xScale = ((double) prefW - 2 * borderGap) / (resolution);
      double yScale = ((double) prefH - 2 * borderGap) / maxScore;
      List<Point> graphPoints = new ArrayList<Point>();
      // рисование точек графика функции
      for (int i = 0; i <= resolution; i++) {
         int x1 = (int) (i * xScale + borderGap);
         int y1 = (int) ((maxScore - scores.get(i)) * yScale + borderGap);
         graphPoints.add(new Point(x1, y1));
      }
      // рисование оси x и y
      g2.drawLine(borderGap, getHeight() - borderGap, borderGap, borderGap);
      g2.drawLine(borderGap, getHeight() - borderGap, getWidth() - borderGap, getHeight() - borderGap);
      // рисование линии графика функции, соединение точек
      g2.setColor(graphColor);
      g2.setStroke(graphStroke);
      for (int i = 0; i < graphPoints.size() - 1; i++) {
         int x1 = graphPoints.get(i).x;
         int y1 = graphPoints.get(i).y;
         int x2 = graphPoints.get(i + 1).x;
         int y2 = graphPoints.get(i + 1).y;
         g2.drawLine(x1, y1, x2, y2);         
      }
      // рисование надписи с сообщением
      FontRenderContext frc = g2.getFontRenderContext();
      int textSize = 30;
      Font f = new Font("Arial Bold", Font.ITALIC, textSize);
      AffineTransform at = new AffineTransform();
      int count = 1;
      g2.setColor(Color.RED);
      g2.setFont(f);
      int textH = 50;
      for (String me : mess) {
         TextLayout tl = new TextLayout(me, f, frc);
         at.setToTranslation(prefW / 2 - (tl.getBounds().getMaxX()) / 2, textH * count++);
         Shape sh = tl.getOutline(at);
         g2.draw(sh);
      }
   }

   @Override
   public Dimension getPreferredSize() {
      return new Dimension(prefW, prefH);
   }

   public void startDraw() {
      maxScore = Double.MIN_VALUE;
      scores = new ArrayList<>();
      // определение значения точек графика функции
      for (int i = 0; i <= resolution ; i++) {
         scores.add(funct.apply(start + i * (finish - start) / resolution));
         if (maxScore < funct.apply(start + i * (finish - start) / resolution)) {
            maxScore = funct.apply(start + i * (finish - start) / resolution);
         }
      }
      JFrame frame = new JFrame("DrawGraph");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(this);
      frame.pack();
      frame.setLocationByPlatform(true);
      frame.setVisible(true);
   }
}
