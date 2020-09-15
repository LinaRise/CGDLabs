package sample.lab2;

import java.awt.geom.Point2D;
import java.util.List;

public class Movement {

  static List<Point2D> moveFigure(List<Point2D> point2DList, int x, int y) {
    for (int i = 0; i < point2DList.size(); i++) {
      point2DList.set(i, new Point2D.Double(point2DList.get(i).getX() + x, point2DList.get(i).getY() - y));
    }
    return point2DList;
  }


  static List<Point2D> rotateFigure(List<Point2D> point2DList, int angle) {
    for (int i = 0; i < point2DList.size(); i++) {
      double newX = Test2.coordWidth/2 + (point2DList.get(i).getX() - Test2.coordWidth/2) * Math.cos(Math.toRadians(angle)) - (point2DList.get(i).getY() - Test2.coordHeight/2) * Math.sin(Math.toRadians(angle));
//      double newX = point2DList.get(i).getX() * Math.cos(angle) - point2DList.get(i).getY() * Math.sin(angle);
      System.out.println("newX = "+newX);
      double newY = Test2.coordHeight/2 + (point2DList.get(i).getY() - Test2.coordHeight/2) * Math.cos(Math.toRadians(angle)) + (point2DList.get(i).getX() - Test2.coordWidth/2) * Math.sin(Math.toRadians(angle));
//      double newY = point2DList.get(i).getY() * Math.cos(angle) + point2DList.get(i).getX() * Math.sin(angle);
      System.out.println("newY = "+newY);

      point2DList.set(i, new Point2D.Double(newX, newY));
    }
    return point2DList;

  }
}
