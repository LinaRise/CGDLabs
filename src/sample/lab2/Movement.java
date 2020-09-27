package sample.lab2;

import java.awt.geom.Point2D;
import java.util.List;

class Movement {

  //перемещение фигуры
  static List<Point2D> moveFigure(List<Point2D> point2DList, int x, int y) {
    for (int i = 0; i < point2DList.size(); i++) {
//      - y тк в программе на JavaFX начало координат отсчет идет от левого верхнего угла
      point2DList.set(i, new Point2D.Double(point2DList.get(i).getX() + x, point2DList.get(i).getY() - y));
    }
    return point2DList;
  }

//метод поворота фигуры, который принимает на вход координаты точек многоугольника и уголв градусах
  static List<Point2D> rotateFigure(List<Point2D> point2DList, int angle) {
    for (int i = 0; i < point2DList.size(); i++) {
      //x*=x*cos(θ)-y*sin(θ)
      double newX = point2DList.get(i).getX() * Math.cos(Math.toRadians(angle)) -
              point2DList.get(i).getY() * Math.sin(Math.toRadians(angle));
      System.out.println("newX = "+newX);
//      y*=x*sin(θ)+y*cos(θ)
      double newY = point2DList.get(i).getY() * Math.cos(Math.toRadians(angle)) +
              point2DList.get(i).getX() * Math.sin(Math.toRadians(angle));
      System.out.println("newY = "+newY);
      point2DList.set(i, new Point2D.Double(newX, newY));
    }
    return point2DList;

  }
}
