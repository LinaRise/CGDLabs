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

    System.out.println(topPane.getHeight());

    Button generateNewPolygonButton = new Button("Сгенерировать");
    generateNewPolygonButton.setLayoutX(400);
    bottomPane.getChildren().add(generateNewPolygonButton);


//    topPane.getChildren().add(imageView);


    generateNewPolygonButton.setOnMouseClicked(event -> {
      topPane.getChildren().clear();
      for (int i = 0; i < writableImage.getWidth() - 1; i++) {
        for (int j = 0; j < writableImage.getHeight() - 1; j++) {
          pixelWriter.setColor(i, j, Color.WHITE);
//              System.out.println("ima"+array[i][j]);
        }
      }


      listOfPoints = PolygonGenerator.generatePolygon();
      Polygon polygon = new Polygon();
      //добавляем точки
      for (Point2D listOfPoint : listOfPoints) {
        polygon.getPoints().addAll(listOfPoint.getX() + Test4.coordWidth / 2, listOfPoint.getY() + Test4.coordHeight / 2);
      }

      for (Point2D listOfPoint : listOfPoints) {
        System.out.println(listOfPoint);
      }
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
//              System.out.println("\nPixel color at coordinates ("
//                      + readX + "," + readY + ") "
//                      + color.toString());
//              System.out.println("R = " + color.getRed());
//              System.out.println("G = " + color.getGreen());
//              System.out.println("B = " + color.getBlue());
//              System.out.println("Opacity = " + color.getOpacity());
//              System.out.println("Saturation = " + color.getSaturation());

      java.awt.Color[][] array = null;
      if (listOfPoints.size() > 0) {
        File file1 = new File("polygon.png");
        try {
          BufferedImage image1 = ImageIO.read(file1);
          array = new java.awt.Color[stageWidth][stageHeight];
          for (int i = 0; i < image1.getWidth() - 1; i++) {
            for (int j = 0; j < image1.getHeight() - 1; j++) {
              array[i][j] = new java.awt.Color(image1.getRGB(i, j));
//              if (array[i][j].getRGB() != -1)
//                System.out.println(array[i][j].getRed() +
//                        " " +
//                        array[i][j].getGreen() +
//                        " " + array[i][j].getBlue() + "\n");
            }
          }


          Stack<Point> stack = new Stack<>();
          Point startPoint = new Point((int) event.getX(), (int) event.getY());
//          System.out.println(startPoint);
          stack.push(startPoint);
          int y = 0;
          while (!stack.isEmpty()) {
            Point p = stack.pop();
//            System.out.println(p);
            array[p.x][p.y] = new java.awt.Color(0, 0, 0);
            pixelWriter.setColor(p.x, p.y, Color.BLACK);
//          System.out.println("RGB = "+array[p.x + 1][p.y].getRGB());
            try {
              //пиксел справа
              if ((array[p.x + 1][p.y].getRed() == 255) && (array[p.x + 1][p.y].getGreen() == 255) && (array[p.x + 1][p.y].getBlue() == 255)) {
                stack.push(new Point(p.x + 1, p.y));
                System.out.println("1");
//                pixelWriter.setColor(p.x, p.y, Color.BLACK);
//                array[p.x + 1][p.y] = new java.awt.Color(0, 0, 0);
              }

              //писксель снизу
              if ((array[p.x][p.y - 1].getRed() == 255) && (array[p.x][p.y - 1].getGreen() == 255) && (array[p.x][p.y - 1].getBlue() == 255)) {
                stack.push(new Point(p.x, p.y - 1));
                System.out.println("2");
//                array[p.x][p.y - 1] = new java.awt.Color(0, 0, 0);
              }

              //пиксель слева
              if ((array[p.x - 1][p.y].getRed() == 255) && (array[p.x - 1][p.y].getGreen() == 255) && (array[p.x - 1][p.y].getBlue() == 255)) {
                stack.push(new Point(p.x - 1, p.y));
                System.out.println("3");
//                array[p.x - 1][p.y] = new java.awt.Color(0, 0, 0);
              }


              //писксель сверху
              if ((array[p.x][p.y + 1].getRed() == 255) && (array[p.x][p.y + 1].getGreen() == 255) && (array[p.x][p.y + 1].getBlue() == 255)) {
                stack.push(new Point(p.x, p.y + 1));
                System.out.println("4");
//                array[p.x][p.y + 1] = new java.awt.Color(0, 0, 0);
              }

//              if ((array[p.x + 1][p.y + 1].getRed() == 255) && (array[p.x + 1][p.y + 1].getGreen() == 255) && (array[p.x + 1][p.y + 1].getBlue() == 255)) {
//                stack.push(new Point(p.x+1, p.y + 1));
//                System.out.println("5");
////                array[p.x][p.y + 1] = new java.awt.Color(0, 0, 0);
//              }
//
//              if ((array[p.x - 1][p.y - 1].getRed() == 255) && (array[p.x - 1][p.y - 1].getGreen() == 255) && (array[p.x - 1][p.y - 1].getBlue() == 255)) {
//                stack.push(new Point(p.x-1, p.y -1));
//                System.out.println("6");
////                array[p.x][p.y + 1] = new java.awt.Color(0, 0, 0);
//              }
//
//              if ((array[p.x - 1][p.y + 1].getRed() == 255) && (array[p.x - 1][p.y + 1].getGreen() == 255) && (array[p.x - 1][p.y + 1].getBlue() == 255)) {
//                stack.push(new Point(p.x-1, p.y + 1));
//                System.out.println("7");
////                array[p.x][p.y + 1] = new java.awt.Color(0, 0, 0);
//              }
//
//              if ((array[p.x + 1][p.y - 1].getRed() == 255) && (array[p.x + 1][p.y - 1].getGreen() == 255) && (array[p.x + 1][p.y - 1].getBlue() == 255)) {
//                stack.push(new Point(p.x+1, p.y - 1));
//                System.out.println("8");
////                array[p.x][p.y + 1] = new java.awt.Color(0, 0, 0);
//              }


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
