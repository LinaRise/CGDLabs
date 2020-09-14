package sample.lab2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Test2  extends Application {
  static final int coordWidth = 600;
  static final int coordHeight = 600;
  static final int stageWidth = 800;
  static final int stageHeight = 600;

  @Override
  public void start(Stage primaryStage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

    Group group = new Group();
    Scene scene = new Scene(root, stageWidth, stageHeight);

     
//    Line xAxis = new Line(0, coordHeight/2,   coordHeight,   coordHeight/2);
//    Line yAxis = new Line(coordWidth/2, 0,   coordWidth/2,   coordHeight);
//    group.getChildren().addAll(xAxis,yAxis);

    primaryStage.setScene(scene);
    primaryStage.show();

  }
}
