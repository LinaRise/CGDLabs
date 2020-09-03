package sample.lab1;

import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;


import java.awt.geom.Point2D;
import java.util.List;

public class Main extends Application {
  final int stageWidth = 600;
  final int stageHeight = 600;

  @Override
  public void start(Stage primaryStage) throws Exception {

    List<Point2D> listOfPoints = RandomPolygon.generate_polygon();
    Polygon polygon = new Polygon();
    for (int i = 0; i < listOfPoints.size(); i++) {
      polygon.getPoints().addAll(listOfPoints.get(i).getX(), listOfPoints.get(i).getY());
    }

    polygon.setStroke(Color.BLACK);
    polygon.setFill(Color.TRANSPARENT);

    Group root = new Group();

    WritableImage writableImage = new WritableImage(stageWidth, stageHeight);
    ImageView imageView = new ImageView(writableImage);

    imageView.setFitHeight(stageHeight);
    imageView.setFitWidth(stageWidth);

    PixelWriter pixelWriter = writableImage.getPixelWriter();

    PixelReader pixelReader = writableImage.getPixelReader();
    System.out.println(pixelReader.getColor(20, 20));
    pixelWriter.setColor(20, 20, Color.BLACK);
    System.out.println(pixelReader.getColor(20, 20));


    root.getChildren().addAll(imageView, polygon);

    Scene scene = new Scene(root, stageWidth, stageHeight);

    boolean[][] pixelMatrix = new boolean[600][600];

    scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        for (int pointNumber = 0; pointNumber < listOfPoints.size(); pointNumber++) {
          if (pointNumber != listOfPoints.size() - 1) {
            double minY = listOfPoints.get(pointNumber + 1).getY();
            double maxY = listOfPoints.get(pointNumber).getY();
            if (listOfPoints.get(pointNumber).getY() < listOfPoints.get(pointNumber + 1).getY()) {
              minY = listOfPoints.get(pointNumber).getY();
              maxY = listOfPoints.get(pointNumber + 1).getY();
            }

            for (int y = (int) minY; y < maxY; y++) {
//              double currentStartX = (listOfPoints.get(pointNumber).getX() < listOfPoints.get(pointNumber + 1).getX())
//                      ? listOfPoints.get(pointNumber).getX()
//                      : listOfPoints.get(pointNumber + 1).getX();

              double k = (listOfPoints.get(pointNumber).getY() - listOfPoints.get(pointNumber + 1).getY()) /
                      (listOfPoints.get(pointNumber).getX() - listOfPoints.get(pointNumber + 1).getX());
              double b = listOfPoints.get(pointNumber + 1).getY() -
                      (listOfPoints.get(pointNumber).getY() - listOfPoints.get(pointNumber + 1).getY()) /
                              (listOfPoints.get(pointNumber).getX() - listOfPoints.get(pointNumber + 1).getX()) *
                              listOfPoints.get(pointNumber + 1).getX();

              double currentX = (y - b) / k;
//              double divider1 = listOfPoints.get(pointNumber).getX() - listOfPoints.get(pointNumber + 1).getX();
//              double divider2 = listOfPoints.get(pointNumber).getY() - listOfPoints.get(pointNumber + 1).getY();
//              double divided2 = y - listOfPoints.get(pointNumber).getY();
//             double currentStartX = listOfPoints.get(pointNumber).getX()

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
          } else {

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
      }
    });


    primaryStage.setScene(scene);
    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }


  private static class SleepService extends Service {
    private static final int SLEEP_TIME = 2000;

    private SleepService() {
      setOnSucceeded(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent event) {
          System.out.println("Freeze");
        }
      });
    }

    @Override
    protected Task createTask() {
      return new Task() {
        @Override
        protected String call() throws Exception {
          Thread.sleep(SLEEP_TIME);
          return "Freeze";
        }
      };
    }
  }
}
