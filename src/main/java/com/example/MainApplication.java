package com.example;

import com.example.watchmanagement.DBconnection;
import com.example.watchmanagement.models.Watch;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class MainApplication extends Application {
  private static VBox watchBlock;
  private static VBox formBlock;
  private static Stage primaryStage;
  private static final DBconnection connection = new DBconnection();
  @Override
  public void start(Stage stage) {
    primaryStage = stage;
    formBlock = drawForm(null);
    watchBlock = getThenDisplayWatches();
    drawUI();
  }




  static VBox getThenDisplayWatches() {
    ArrayList<Watch> wList = connection.getWatches();
    GridPane grid = new GridPane();
    grid.setHgap(50);
    grid.setVgap(20);
    for (int i = 0; i<wList.size();i++){

      Label lbId = new Label("" + wList.get(i).id);
      Label lbName = new Label(wList.get(i).name);
      //display image
      Image image = new Image(wList.get(i).image);
      ImageView imageView = new ImageView();
      imageView.setImage(image);
      imageView.setFitWidth(90);
      imageView.setFitHeight(90);

      Label lbPrice = new Label(String.valueOf(wList.get(i).price));
      Label lbDescription = new Label(String.valueOf(wList.get(i).description));
      Button btnDelete = new Button("delete");

      int index = i;
      btnDelete.setOnAction(actionEvent -> {
        connection.deleteWatch(wList.get(index).id);
        watchBlock = getThenDisplayWatches();
        drawUI();
      });
      Button btnUpdate = new Button("Update");
      btnUpdate.setOnAction(e -> {
        formBlock = drawForm(wList.get(index));
        drawUI();
      });
//      hBoxWatch.getChildren().addAll(lbId, lbName, imageView, lbPrice, lbDescription, btnDelete, btnUpdate );
//      vBox.getChildren().add(hBoxWatch);
      grid.add(lbId, 0,i);
      grid.add(lbName, 1,i);
      grid.add(imageView, 2,i);
      grid.add(lbPrice, 3,i);
      grid.add(lbDescription, 4,i);
      grid.add(btnDelete, 5,i);
      grid.add(btnUpdate, 6,i);
    }
    VBox root = new VBox(grid);
    root.setPadding(new Insets(50));
    return root;
  }

  static VBox drawForm(Watch watch ) {
    VBox vBox = new VBox();
    vBox.setPadding(new Insets(50));
    HBox hbName = new HBox();
    HBox hbImage = new HBox();
    HBox hbPrice = new HBox();
    HBox hbDescription = new HBox();
    Label lbName = new Label();
    TextField textName = new TextField();
    textName.setText(watch == null ? "" : watch.name);
    textName.setPrefWidth(110);
    textName.setPromptText("Nhap ten dong ho...");
    TextField textImage = new TextField();
    textImage.setText(watch == null ? "" : watch.image);
    textName.setPrefWidth(110);
    textImage.setPromptText("Nhap Link anh");
    TextField textDescription = new TextField();
    textDescription.setText(watch == null ? "" : watch.description);
    textName.setPrefWidth(110);
    textDescription.setPromptText("Nhap mo ta");
    TextField textPrice = new TextField();
    textPrice.setText(watch == null ? "" : String.valueOf(watch.price));
    textName.setPrefWidth(110);
    textPrice.setPromptText("Nhap gia");

    Button btnSave = new Button("Save");
    btnSave.setOnAction(e -> {
          try {
            String name = textName.getText();
            String image = textImage.getText();
            int price = 0;
            try {
              price = Integer.parseInt(textPrice.getText());
            } catch(Exception ex) {
              System.out.println(ex.getMessage());
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setContentText("Nhap gia loi, vui long nhap lai!");
              alert.showAndWait();
            }
            String description = textDescription.getText();
            if (watch == null) {
              connection.insertWatch(new Watch(name, image, price,description));
            }
            else {
              watch.name = name;
              watch.image = image;
              watch.price = price;
              watch.description = description;
              connection.updateWatch(watch);
            }
          } catch (Exception ex) {
            System.out.println(ex.getMessage());
          }
      formBlock = drawForm(null);
      watchBlock = getThenDisplayWatches();
      drawUI();
    });

    hbName.getChildren().addAll(lbName,textName);
    hbImage.getChildren().addAll(textImage);
    hbPrice.getChildren().addAll(textPrice);
    hbDescription.getChildren().addAll(textDescription);
    vBox.getChildren().addAll(hbName,hbImage,hbPrice,hbDescription, btnSave);

    return vBox;
  }
  private static void drawUI() {
    Scene scene = new Scene(new ScrollPane(new VBox(formBlock, watchBlock)), 1200, 600);
//    primaryStage.setFullScreen(true);
    primaryStage.setTitle("Hello!");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  public static void main(String[] args) {
    launch();
  }

}