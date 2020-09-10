package sample.lab1;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.awt.geom.Point2D;
import java.util.List;

public class Main extends Application {
  static final int stageWidth = 600;
  static final int stageHeight = 600;

  @Override
  public void start(Stage primaryStage) {

    //кнопка для генерации новой фигуры
    Button generateNewPolygonButton = new Button("Сгенерировать");
    Group root = new Group();
    Scene scene = new Scene(root, stageWidth, stageHeight);
    root.getChildren().add(generateNewPolygonButton);

    generateNewPolygonButton.setOnMouseClicked(event -> {
              //очистка сцены
              root.getChildren().clear();
              root.getChildren().add(generateNewPolygonButton);
              List<Point2D> listOfPoints = PolygonRandom.generatePolygon();
              Polygon polygon = new Polygon();
              //добавляем точки
              for (Point2D listOfPoint : listOfPoints) {
                polygon.getPoints().addAll(listOfPoint.getX(), listOfPoint.getY());
              }

              for (Point2D listOfPoint : listOfPoints) {
                System.out.println(listOfPoint);
              }
              polygon.setStroke(Color.BLACK);
              polygon.setFill(Color.TRANSPARENT);


              WritableImage writableImage = new WritableImage(stageWidth, stageHeight);
              ImageView imageView = new ImageView(writableImage);
              imageView.setFitHeight(stageHeight);
              imageView.setFitWidth(stageWidth);
              PixelWriter pixelWriter = writableImage.getPixelWriter();


              root.getChildren().addAll(imageView, polygon);


              boolean[][] pixelMatrix = new boolean[600][600];

//              scene.setOnMousePressed(event -> System.out.println(event.getX() + ";" + event.getY() + "!!!!"));


              //при клике начнется выполнятся алгоритм, который при клике начнет закращивать с одной из сторон
              scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
                int pointNumber = -1;

                @Override
                public void handle(MouseEvent event) {

                  //проход по всем сторонам и заливка
                  pointNumber++;
                  if (pointNumber < listOfPoints.size() - 1) {
                    double minY = listOfPoints.get(pointNumber + 1).getY();
                    double maxY = listOfPoints.get(pointNumber).getY();
                    if (listOfPoints.get(pointNumber).getY() < listOfPoints.get(pointNumber + 1).getY()) {
                      minY = listOfPoints.get(pointNumber).getY();
                      maxY = listOfPoints.get(pointNumber + 1).getY();
                    }

                    for (int y = (int) minY; y < maxY; y++) {
//

                      double k = (listOfPoints.get(pointNumber).getY() - listOfPoints.get(pointNumber + 1).getY()) /
                              (listOfPoints.get(pointNumber).getX() - listOfPoints.get(pointNumber + 1).getX());

//              System.out.println(listOfPoints.get(pointNumber).getX() + ";" + listOfPoints.get(pointNumber).getY());
                      System.out.println(listOfPoints.get(pointNumber + 1).getX() + ";" + listOfPoints.get(pointNumber + 1).getY());
//              Circle circle1 = new Circle(listOfPoints.get(pointNumber).getX(), listOfPoints.get(pointNumber).getY(), 5);
//              Circle circle2 = new Circle(listOfPoints.get(pointNumber).getX(), listOfPoints.get(pointNumber).getY(), 5);
//              root.getChildren().addAll(circle1, circle2);


                      double b = listOfPoints.get(pointNumber + 1).getY() -
                              ((listOfPoints.get(pointNumber).getY() - listOfPoints.get(pointNumber + 1).getY()) /
                                      (listOfPoints.get(pointNumber).getX() - listOfPoints.get(pointNumber + 1).getX())) *
                                      listOfPoints.get(pointNumber + 1).getX();
                      if (Double.isInfinite(k)) k = 0;
//              System.out.println("k = " + k);
                      if (Double.isInfinite(b)) b = 0;
//              System.out.println("b = " + b);
                      double currentX = (y - b) / k;
                      if (Double.isInfinite(currentX)) currentX = listOfPoints.get(pointNumber).getX();
                      System.out.println(currentX);

                      for (int x = (int) currentX; x < 600; x++) {
                        if (!pixelMatrix[x][y]) {
                          pixelWriter.setColor(x, y, Color.GREY);
                          pixelMatrix[x][y] = true;
                        } else {
                          pixelWriter.setColor(x, y, Color.WHITE);
                          pixelMatrix[x][y] = false;
                        }
                      }
                    }
                  } else if (pointNumber == listOfPoints.size() - 1) {

                    double minY = listOfPoints.get(0).getY();
                    double maxY = listOfPoints.get(pointNumber).getY();
                    if (listOfPoints.get(pointNumber).getY() < listOfPoints.get(0).getY()) {
                      minY = listOfPoints.get(pointNumber).getY();
                      maxY = listOfPoints.get(0).getY();
                    }

                    for (int y = (int) minY; y < maxY; y++) {
                      double k = (listOfPoints.get(pointNumber).getY() - listOfPoints.get(0).getY()) /
                              (listOfPoints.get(pointNumber).getX() - listOfPoints.get(0).getX());
                      double b = listOfPoints.get(0).getY() -
                              (listOfPoints.get(pointNumber).getY() - listOfPoints.get(0).getY()) /
                                      (listOfPoints.get(pointNumber).getX() - listOfPoints.get(0).getX()) *
                                      listOfPoints.get(0).getX();

                      double currentX = (y - b) / k;
                      for (int x = (int) currentX; x < 600; x++) {
                        if (!pixelMatrix[x][y]) {
                          pixelWriter.setColor(x, y, Color.GREY);
                          pixelMatrix[x][y] = true;
                        } else {
                          pixelWriter.setColor(x, y, Color.WHITE);
                          pixelMatrix[x][y] = false;
                        }
                      }
                    }
                  }
                }

              });

            }

    );


    primaryStage.setScene(scene);
    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }


}
