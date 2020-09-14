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
}
