package sample.lab2;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Controller extends Application {

  @FXML
  private AnchorPane anchorPane;

  @FXML
  private Line yAxis;

  @FXML
  private Line xAxis;

  @FXML
  private TextField angleTF;

  @FXML
  private TextField xTF;

  @FXML
  private TextField yTF;

  @FXML
  private Button rotateBtn;

  @FXML
  private Button moveBtn;

  @FXML
  private AnchorPane coordinatesPane;

  @FXML
  private Button generateBtn;

  @FXML
  private SplitPane splitPane;

  List<Point2D> listOfPoints = new ArrayList<>();


  //проверка на ввод в поле для смещения по X
  public void xTFTyped(javafx.scene.input.KeyEvent keyEvent) {
    final int maxCharacter = 4;
    String key = keyEvent.getCharacter();
    if ((!key.isEmpty() && (!Character.isDigit(key.charAt(0)) && !key.equals("-")) || (xTF.getText().length() >= maxCharacter))) {
      Toolkit tk = Toolkit.getDefaultToolkit();
      tk.beep();
      keyEvent.consume();
    }
  }
  //проверка на ввод в поле для смещения по Y
  public void yTFTyped(javafx.scene.input.KeyEvent keyEvent) {
    final int maxCharacter = 4;
    String key = keyEvent.getCharacter();
    if ((!key.isEmpty() && (!Character.isDigit(key.charAt(0)) && !key.equals("-")) || (yTF.getText().length() >= maxCharacter))) {
      Toolkit tk = Toolkit.getDefaultToolkit();
      tk.beep();
      keyEvent.consume();
    }
  }


  //отрисовка полигона
  Polygon drawPolygon() {

    coordinatesPane.getChildren().clear();
    Polygon polygon = new Polygon();
    polygon.setStroke(Color.BLACK);
    polygon.setFill(Color.TRANSPARENT);
    for (Point2D listOfPoint : listOfPoints) {
      //прибавим значения, чтобы корректо отобразтить мнооугольгик на холсте
      polygon.getPoints().addAll(listOfPoint.getX()+Test2.coordWidth/2, listOfPoint.getY()+Test2.coordHeight/2);
    }
    coordinatesPane.getChildren().addAll(xAxis, yAxis, polygon);
    return polygon;

  }

//метод, отвечающий за нажатие на конопку "Сгенерировать"
  void generateBtnOnClik() {
    listOfPoints = PolygonGenerator.generatePolygon();
    drawPolygon();
  }

  //метод, отвечающий за нажатие на конопку "Повернуть"
  void rotateBtnOnClick() {
    if (!angleTF.getText().isEmpty() && angleTF.getText() != null &&  listOfPoints != null && listOfPoints.size()>0) {
      listOfPoints = Movement.rotateFigure(listOfPoints, Integer.parseInt(angleTF.getText()));
      drawPolygon();
    } else {
      String errorMessage = "Пожалуйста, введите корректные значения угла и сгенерируйте фигуру";
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Ошибка ");
      alert.setHeaderText("Неправильный ввод");
      alert.setContentText(errorMessage);

      alert.showAndWait();

    }

  }

  //метод, отвечающий за нажатие на конопку "Переместить"
  void moveBtnOnClick() {
    if (!xTF.getText().isEmpty() && !yTF.getText().isEmpty() &&
            xTF.getText() != null && yTF.getText() != null && listOfPoints != null && listOfPoints.size()>0) {
      listOfPoints = Movement.moveFigure(listOfPoints, Integer.parseInt(xTF.getText()), Integer.parseInt(yTF.getText()));
      drawPolygon();
    } else {
      String errorMessage = "Пожалуйста, введите корректные значения для перемещения и сгенерируйте фигуру";
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Ошибка ");
      alert.setHeaderText("Неправильный ввод");
      alert.setContentText(errorMessage);

      alert.showAndWait();

    }
  }


  @FXML
  void initialize() {

    splitPane.setDividerPositions(0.75);
    SplitPane.Divider divider = splitPane.getDividers().get(0);
    divider.positionProperty().addListener((observable, oldValue, newValue) -> divider.setPosition(0.75));

    generateBtn.setOnAction(event -> {
      generateBtnOnClik();
    });

    moveBtn.setOnAction(event -> moveBtnOnClick());

    rotateBtn.setOnAction(event -> rotateBtnOnClick());


  }


  @Override
  public void start(Stage primaryStage) {

  }
}
