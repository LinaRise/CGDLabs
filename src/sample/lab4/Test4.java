package sample.lab4;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import sample.lab2.Test2;

import javax.imageio.ImageIO;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Test4 extends Application {
  static final int stageWidth = 900;
  static final int stageHeight = 600;
  static int coordWidth = 900;
  static int coordHeight = 570;
  List<Point2D> listOfPoints = new ArrayList<>();
//  PixelWriter pixelWriter = null;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    //создаем сцену и нужные элементы
    AnchorPane anchorPane = new AnchorPane();

    WritableImage writableImage = new WritableImage(stageWidth, stageHeight);
    ImageView imageView = new ImageView(writableImage);
    imageView.setFitHeight(stageHeight);
    imageView.setFitWidth(stageWidth);
    PixelWriter pixelWriter = writableImage.getPixelWriter();

    SplitPane splitPane = new SplitPane();
    splitPane.setOrientation(Orientation.VERTICAL);
    AnchorPane.setTopAnchor(splitPane, 0.0);
    AnchorPane.setRightAnchor(splitPane, 0.0);
    AnchorPane.setBottomAnchor(splitPane, 0.0);
    AnchorPane.setLeftAnchor(splitPane, 0.0);

    Pane topPane = new Pane();
    Pane bottomPane = new Pane();
    splitPane.getItems().addAll(topPane, bottomPane);
    splitPane.setDividerPositions(0.95);
    SplitPane.Divider divider = splitPane.getDividers().get(0);
    divider.positionProperty().addListener((observable, oldValue, newValue) -> divider.setPosition(0.95));
    anchorPane.getChildren().add(splitPane);

// создаем кнопку генерации
    Button generateNewPolygonButton = new Button("Сгенерировать");
    generateNewPolygonButton.setLayoutX(400);
    bottomPane.getChildren().add(generateNewPolygonButton);

// устанавливаем действия н клик по кнопке
    generateNewPolygonButton.setOnMouseClicked(event -> {
      topPane.getChildren().clear();
      for (int i = 0; i < writableImage.getWidth() - 1; i++) {
        for (int j = 0; j < writableImage.getHeight() - 1; j++) {
          pixelWriter.setColor(i, j, Color.WHITE);
        }
      }
      //генерация многоугольника
      listOfPoints = PolygonGenerator.generatePolygon();
      Polygon polygon = new Polygon();
      //добавляем точки
      for (Point2D listOfPoint : listOfPoints) {
        polygon.getPoints().addAll(listOfPoint.getX() + Test4.coordWidth / 2, listOfPoint.getY() + Test4.coordHeight / 2);
      }

//      for (Point2D listOfPoint : listOfPoints) {
//        System.out.println(listOfPoint);
//      }
      polygon.setStroke(Color.BLACK);
      polygon.setStrokeWidth(3);
      polygon.setFill(Color.TRANSPARENT);


      topPane.getChildren().addAll(imageView, polygon);

      WritableImage image = topPane.snapshot(new SnapshotParameters(), null);
      File file = new File("polygon.png");
      try {
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
      } catch (IOException e) {
        e.printStackTrace();
      }

    });


    topPane.setOnMouseClicked(event -> {
  //массив для хранения цветов пикселей
      java.awt.Color[][] colorsArray = null;
      if (listOfPoints.size() > 0) {
        File file1 = new File("polygon.png");
        try {
          BufferedImage image1 = ImageIO.read(file1);
          colorsArray = new java.awt.Color[stageWidth][stageHeight];
          for (int i = 0; i < image1.getWidth() - 1; i++) {
            for (int j = 0; j < image1.getHeight() - 1; j++) {
              colorsArray[i][j] = new java.awt.Color(image1.getRGB(i, j));
//              if ((array[i][j].getRed() != 255) && (array[i][j].getGreen() != 255) && (array[i][j].getBlue() != 255))
//                System.out.println(array[i][j].getRed() +
//                        " " +
//                        array[i][j].getGreen() +
//                        " " + array[i][j].getBlue() + "\n");
            }
          }

            //создаем стек
          Stack<Point> stack = new Stack<>();
          // берем затравочный пискель и кладем его в стек
          Point startPoint = new Point((int) event.getX(), (int) event.getY());
          stack.push(startPoint);
          // проверка стека на пустоту
          while (!stack.empty()) {
            //вытаскиваем верхний элемент
            Point currentPoint = stack.pop();
            //присваиваем ему черный цвет
            colorsArray[currentPoint.x][currentPoint.y] = new java.awt.Color(0, 0, 0);
            //закрашиваем
            pixelWriter.setColor(currentPoint.x, currentPoint.y, Color.BLACK);
            try {
              //проверки являются ли пиксели справа, снизу, слева, сверху уже обработавнными(закрашенными) или нет
              //пиксел справа
              if ((colorsArray[currentPoint.x + 1][currentPoint.y].getRed() != 0) && (colorsArray[currentPoint.x + 1][currentPoint.y].getGreen() != 0) && (colorsArray[currentPoint.x + 1][currentPoint.y].getBlue() != 0)) {
                stack.push(new Point(currentPoint.x + 1, currentPoint.y));
//                System.out.println("справа");
              }

              //писксель снизу
              if ((colorsArray[currentPoint.x][currentPoint.y - 1].getRed() != 0) && (colorsArray[currentPoint.x][currentPoint.y - 1].getGreen() != 0) && (colorsArray[currentPoint.x][currentPoint.y - 1].getBlue() != 0)) {
                stack.push(new Point(currentPoint.x, currentPoint.y - 1));
//                System.out.println("снизу");
              }

              //пиксель слева
              if ((colorsArray[currentPoint.x - 1][currentPoint.y].getRed() != 0) && (colorsArray[currentPoint.x - 1][currentPoint.y].getGreen() != 0) && (colorsArray[currentPoint.x - 1][currentPoint.y].getBlue() != 0)) {
                stack.push(new Point(currentPoint.x - 1, currentPoint.y));
//                System.out.println("слева");
              }


              //писксель сверху
              if ((colorsArray[currentPoint.x][currentPoint.y + 1].getRed() != 0) && (colorsArray[currentPoint.x][currentPoint.y + 1].getGreen() != 0) && (colorsArray[currentPoint.x][currentPoint.y + 1].getBlue() != 0)) {
                stack.push(new Point(currentPoint.x, currentPoint.y + 1));
//                System.out.println("сверху");
              }

            } catch (Exception e) {

            }
          }
        } catch (Exception e) {

        }
      }


    });


    Scene scene = new Scene(anchorPane, stageWidth, stageHeight);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
