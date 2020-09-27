package sample.lab2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Test2 extends Application {
  static final int coordWidth = 600;
  static final int coordHeight = 600;
  static final int stageWidth = 800;
  static final int stageHeight = 600;

  @Override
  public void start(Stage primaryStage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

    Scene scene = new Scene(root, stageWidth, stageHeight);
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.show();

  }
}
