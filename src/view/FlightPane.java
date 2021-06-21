/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.time.LocalDateTime;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.bo.Flight;
import model.db.AirplaneMySQLDb;

/**
 *
 * @author Johan C
 */
public class FlightPane extends VBox{
    
    private MenuBar menuBar;
    private TableView<Flight> flightTable;
    private ObservableList<Flight> flightsInTable;
    
    public FlightPane(AirplaneMySQLDb airplaneDb){
        final Controller controller = new Controller(airplaneDb, this);
        this.init(controller);
    }
    
    
    private void init(Controller controller){
        flightsInTable = FXCollections.observableArrayList();
        initFlightTable();
        initMenus(controller);
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(flightTable);
        //mainPane.setBottom(bottomPane);
        mainPane.setPadding(new Insets(10, 10, 10, 10));
        this.getChildren().addAll(menuBar, mainPane);
        VBox.setVgrow(mainPane, Priority.ALWAYS);
    }
    
    private void initFlightTable(){
        flightTable = new TableView<>();
        flightTable.setEditable(false);
        
        TableColumn<Flight, Integer> flightIdCol = new TableColumn<>("Flight_id");
        TableColumn<Flight, Integer> airlineIdCol = new TableColumn<>("Airline_id");
        TableColumn<Flight, String> airlineNameCol = new TableColumn<>("Airline name");
        TableColumn<Flight, String> fromlocationCol = new TableColumn<>("From location");
        TableColumn<Flight, String> tolocationCol = new TableColumn<>("To location");
        TableColumn<Flight, LocalDateTime> depatureTimeCol = new TableColumn<>("Departure time");
        TableColumn<Flight, LocalDateTime> arrivalTimeCol = new TableColumn<>("Arrival time");
        TableColumn<Flight, Integer> durationCol = new TableColumn<>("Duration");
        TableColumn<Flight, Integer> totalSeatsCol = new TableColumn<>("Total seats");
        
        flightTable.getColumns().addAll(flightIdCol, airlineIdCol, airlineNameCol, fromlocationCol, tolocationCol, depatureTimeCol, arrivalTimeCol,
        durationCol, totalSeatsCol);
        
        airlineNameCol.prefWidthProperty().bind(flightTable.widthProperty().multiply(0.13));
        fromlocationCol.prefWidthProperty().bind(flightTable.widthProperty().multiply(0.15));
        tolocationCol.prefWidthProperty().bind(flightTable.widthProperty().multiply(0.15));
        depatureTimeCol.prefWidthProperty().bind(flightTable.widthProperty().multiply(0.15));
        arrivalTimeCol.prefWidthProperty().bind(flightTable.widthProperty().multiply(0.15));
        
        flightIdCol.setCellValueFactory(new PropertyValueFactory<>("flightId"));
        airlineIdCol.setCellValueFactory(new PropertyValueFactory<>("airlineId"));
        airlineNameCol.setCellValueFactory(new PropertyValueFactory<>("airlineName"));
        fromlocationCol.setCellValueFactory(new PropertyValueFactory<>("fromLocation"));
        tolocationCol.setCellValueFactory(new PropertyValueFactory<>("toLocation"));
        depatureTimeCol.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        arrivalTimeCol.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        totalSeatsCol.setCellValueFactory(new PropertyValueFactory<>("totalSeats"));
        flightTable.setItems(flightsInTable);
    }
    
    
    private void initMenus(Controller controller) {

        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        MenuItem connectItem = new MenuItem("Connect to Db");
        MenuItem disconnectItem = new MenuItem("Disconnect");
        fileMenu.getItems().addAll(exitItem, connectItem, disconnectItem);
        
        
        Menu searchMenu = new Menu("Search");
        MenuItem searchGenreItem = new MenuItem("Search Flight");
        MenuItem searchRatingItem = new MenuItem("Search Rating");
        MenuItem allItems = new MenuItem("Get all books");
        searchMenu.getItems().addAll(searchGenreItem, searchRatingItem, allItems);
        
        Menu manageMenu = new Menu("Manage");
        MenuItem addItem = new MenuItem("Add");
        MenuItem addRatingItem = new MenuItem("Add rating");
        MenuItem addAuthorItem = new MenuItem("Add author");
        manageMenu.getItems().addAll(addItem, addRatingItem, addAuthorItem);
        
        
        Menu accountMenu = new Menu("Account");
        MenuItem loginItem = new MenuItem("Login");
        MenuItem signInItem = new MenuItem("Sign in");
        MenuItem reservationItem = new MenuItem("Reservations");
        reservationItem.setVisible(false);
        accountMenu.getItems().addAll(loginItem, signInItem, reservationItem);
        
        connectItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.connect();
            }
        });
        
        disconnectItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.disconnect();
                flightsInTable.clear();
            }
        });
        
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, searchMenu, manageMenu, accountMenu);
    }
    
    public void displayFlights(List<Flight> flights) {
        flightsInTable.clear();
        flightsInTable.addAll(flights);
    }
    
    
    protected void showAlertAndWait(String msg, Alert.AlertType type) {
        // types: INFORMATION, WARNING et c.
        Alert alert = new Alert(type, msg);
        alert.showAndWait();
    }

}
