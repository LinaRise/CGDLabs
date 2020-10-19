package sample.lab4;

import sample.lab2.Test2;

import java.awt.geom.Point2D;
import java.util.*;

//моя реализация
class PolygonGenerator {

  //генерируем колиечство вершин
  static private int getRandomPeekAmount() {
    Random r = new Random();
    int minPeekAmount = 3;
    int maxPeekAmount = 11;

    //[3;10]
    return r.nextInt(maxPeekAmount - minPeekAmount) + minPeekAmount;
  }


  //генерация полигона
  static List<Point2D> generatePolygon() {
    List<Point2D> points = new ArrayList<>();
    int peeksAmount = getRandomPeekAmount();
    System.out.println("peeksAmount = " + peeksAmount);
    double[] angles = new double[peeksAmount];
    for (int i = 0; i < peeksAmount; i++) {
      angles[i] = Math.random() * 2 * Math.PI;
    }
    Arrays.sort(angles);
    System.out.println(Arrays.toString(angles));


    for (double angle : angles) {
      while (true) {
        double vx = Math.random() * Math.sqrt(Math.pow(Test4.coordWidth / 2, 2) + Math.pow(Test4.coordWidth / 2, 2));
        double vy = Math.random() * Math.sqrt(Math.pow(Test4.coordHeight / 2, 2) + Math.pow(Test4.coordHeight / 2, 2));
//        Point2D point2D = new Point2D.Double(300 + (int) (Math.cos(angle) * vx), 300 + (int) (Math.sin(angle) * vy));
        Point2D point2D = new Point2D.Double((int) (Math.cos(angle) * vx), (int) (Math.sin(angle) * vy));
        //выполянется проверка на то, что многоугольник будет виден целиком на холсте при первичной генерации центр (300;300)
        if (!points.contains(point2D) && (point2D.getY() + Test4.coordHeight / 2) > 0 && (point2D.getY() + Test4.coordHeight / 2) < Test4.coordHeight && (point2D.getX() + Test4.coordWidth / 2) > 0 && (point2D.getX() + Test4.coordWidth / 2) < Test4.coordWidth) {
          points.add(point2D);
          System.out.println(point2D.getX() + ";" + point2D.getY());
          break;
        }
      }
    }


    double y = points.stream().mapToDouble(v -> v.getY()).max().orElseThrow(IllegalStateException::new);
    double x = points.stream().filter(v -> v.getY() == y).mapToDouble(v -> v.getX()).min().orElseThrow(IllegalStateException::new);
    Point2D lowest = new Point2D.Double(x, y);
    System.out.println("Самая нижняя = " + lowest);

//сортируем точки
    points.sort((byAngleComparator(lowest)));
    System.out.println("Отсортированное");
    for (int i = 0; i < points.size(); i++) {
      System.out.println(points.get(i).getX() + ";" + points.get(i).getY());
    }

    return points;
  }

  //для сортировки точек
  private static Comparator<Point2D> byAngleComparator(
          Point2D lowest) {

    return (p0, p1) -> (int) angleCompare(lowest, p0, p1);
  }

  //сравнение углов
  private static double angleCompare(Point2D lowestRightPoint, Point2D point2, Point2D point3) {
    double left = isLeft(lowestRightPoint, point2, point3);
    if (left == 0) return distCompare(lowestRightPoint, point2, point3);
    return left;
  }


  private static double isLeft(Point2D lowestRightPoint, Point2D point1, Point2D point2) {
    return (point1.getX() - lowestRightPoint.getX()) * (point2.getY() - lowestRightPoint.getY()) -
            (point2.getX() - lowestRightPoint.getX()) * (point1.getY() - lowestRightPoint.getY());
  }

  //сравнение расстояний
  private static double distCompare(Point2D lowestRightPoint, Point2D point1, Point2D point2) {
    double distA = Math.pow(lowestRightPoint.getX() - point1.getX(), 2) + Math.pow(lowestRightPoint.getY() - point1.getY(), 2);
    double distB = Math.pow(lowestRightPoint.getX() - point2.getX(), 2) + Math.pow(lowestRightPoint.getY() - point2.getY(), 2);
    return distA - distB;
  }


}



