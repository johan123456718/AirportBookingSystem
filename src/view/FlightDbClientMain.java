/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.db.AirplaneMySQLDb;

/**
 *
 * @author Johan C
 */
public class FlightDbClientMain  extends Application {

    @Override
    public void start(Stage primaryStage){

        AirplaneMySQLDb booksDb = new AirplaneMySQLDb();

        FlightPane root = new FlightPane();

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Books Database Client");

        EventHandler closeHandler = new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to close application?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    try {
                        booksDb.disconnect();
                        primaryStage.close();
                    } catch (Exception e) {}
                }
            }
        };
        primaryStage.setOnCloseRequest(closeHandler);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}