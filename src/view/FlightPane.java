/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.bo.Flight;
import model.bo.Passenger;
import model.bo.Ticket;
import model.db.AirplaneMySQLDb;

/**
 *
 * @author Johan C
 */
public class FlightPane extends VBox {

    private MenuBar menuBar;
    private TableView<Flight> flightTable;
    private ObservableList<Flight> flightsInTable;
    private TextField userTextField;
    private PasswordField pwBox;
    private Label signInAsLabel;
    
    public FlightPane(AirplaneMySQLDb airplaneDb) {
        final Controller controller = new Controller(airplaneDb, this);
        this.init(controller);
    }

    private void init(Controller controller) {
        flightsInTable = FXCollections.observableArrayList();
        userTextField = new TextField();
        pwBox = new PasswordField();
        initMenus(controller);
        initFlightTable(controller);

        signInAsLabel = new Label();
        signInAsLabel.setFont(new Font("Helvetica", 20));
        FlowPane bottomPane = new FlowPane();
        bottomPane.setHgap(10);
        bottomPane.setPadding(new Insets(10, 10, 10, 10));
        bottomPane.getChildren().addAll(signInAsLabel);

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(flightTable);
        mainPane.setBottom(bottomPane);
        mainPane.setPadding(new Insets(10, 10, 10, 10));

        this.getChildren().addAll(menuBar, mainPane);
        VBox.setVgrow(mainPane, Priority.ALWAYS);
    }

    private void initFlightTable(Controller controller) {
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

        ticketSelection(controller);
    }

    private void ticketSelection(Controller controller){
        flightTable.setRowFactory(tv -> {
            TableRow<Flight> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) && event.getButton() == MouseButton.PRIMARY) {
                    if (controller.isLoginSuccess(userTextField.getText(), pwBox.getText())) {
                        Optional<ButtonType> result = showAlertAndConf("Do you want to book this?", Alert.AlertType.CONFIRMATION);
                        if (!result.isPresent() || result.get() == ButtonType.OK) {
                            Flight rowData = row.getItem();
                            Ticket ticket = new Ticket.TicketBuilder(rowData.getFlightId(),  controller.getLoggedInPassengerId(userTextField.getText()))
                                    .departureTime(rowData.getDepartureTime().toLocalDate())
                                    .status("Purchased")
                                    .build();
                            controller.registerTicket(ticket);
                            showAlertAndWait("Reservation successful", Alert.AlertType.INFORMATION);
                        }
                    } else {
                        showAlertAndWait("You need to login!", Alert.AlertType.ERROR);
                    }
                }
            });
            return row;
        });
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
        MenuItem signUpItem = new MenuItem("Sign up");
        MenuItem reservationItem = new MenuItem("Reservations");
        MenuItem signOutItem = new MenuItem("Sign out");
        reservationItem.setVisible(false);
        signOutItem.setVisible(false);

        accountMenu.getItems().addAll(loginItem, signUpItem, reservationItem, signOutItem);

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

        signUpItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                initSignInWindow(controller);
            }
        });

        loginItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                initLoginWindow(controller);
            }
        });

        signOutItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuBar.getMenus().get(3).getItems().get(0).setVisible(true); //Login unseen
                menuBar.getMenus().get(3).getItems().get(1).setVisible(true); //Sign up unseen
                menuBar.getMenus().get(3).getItems().get(2).setVisible(false); //Reservation show
                menuBar.getMenus().get(3).getItems().get(3).setVisible(false); //Sign out show
                signInAsLabel.setText("");
                userTextField.clear();
                pwBox.clear();
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

    protected Optional<ButtonType> showAlertAndConf(String msg, Alert.AlertType type) {
        // types: INFORMATION, WARNING et c.
        Alert alert = new Alert(type, msg);
        return alert.showAndWait();
    }

    private void initLoginWindow(Controller controller) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Login");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        grid.add(pwBox, 1, 2);

        Button btn = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        Scene scene = new Scene(grid, 300, 275);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                if (controller.isLoginSuccess(userTextField.getText(), pwBox.getText())) {
                    showAlertAndWait("Welcome " + userTextField.getText(), Alert.AlertType.INFORMATION);
                    signInAsLabel.setText("Signed in as: " + userTextField.getText());
                    menuBar.getMenus().get(3).getItems().get(0).setVisible(false); //Login unseen
                    menuBar.getMenus().get(3).getItems().get(1).setVisible(false); //Sign up unseen
                    menuBar.getMenus().get(3).getItems().get(2).setVisible(true); //Reservation show
                    menuBar.getMenus().get(3).getItems().get(3).setVisible(true); //Sign out show 
                    stage.close();
                } else {
                    userTextField.clear();
                    pwBox.clear();
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("User name or password is incorrect");
                }
            }
        });
    }

    private void initSignInWindow(Controller controller) {
        userTextField.clear();
        pwBox.clear();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Sign up");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User name (20 characters):");
        grid.add(userName, 0, 1);

        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password (20 characters):");
        grid.add(pw, 0, 2);

        grid.add(pwBox, 1, 2);

        Label firstName = new Label("First name (20 characters):");
        grid.add(firstName, 0, 3);

        TextField firstNameTextField = new TextField();
        grid.add(firstNameTextField, 1, 3);

        Label lastName = new Label("Last name (20 characters):");
        grid.add(lastName, 0, 4);

        TextField lastNameTextField = new TextField();
        grid.add(lastNameTextField, 1, 4);

        Label address = new Label("Address (20 characters):");
        grid.add(address, 0, 5);

        TextField addressTextField = new TextField();
        grid.add(addressTextField, 1, 5);

        Label telNo = new Label("Telephone number (20 characters):");
        grid.add(telNo, 0, 6);

        TextField telNoTextField = new TextField();
        grid.add(telNoTextField, 1, 6);

        Label email = new Label("Email (20 characters):");
        grid.add(email, 0, 7);

        TextField emailTextField = new TextField();
        grid.add(emailTextField, 1, 7);

        Button btn = new Button("Sign up");
        HBox hbBtn = new HBox(10);

        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 8);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 9);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                //Fix later
                if (hasMoreThan20Chars(userTextField, pwBox, firstNameTextField, lastNameTextField, addressTextField, telNoTextField, emailTextField)) {

                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("You can't add more than 20 characters!");
                } else {
                    String userName = userTextField.getText();
                    String password = pwBox.getText();
                    String firstName = firstNameTextField.getText();
                    String lastName = lastNameTextField.getText();
                    String address = addressTextField.getText();
                    String telNo = telNoTextField.getText();
                    String email = emailTextField.getText();

                    List<String> result = new ArrayList<>();
                    result.add(userName);
                    result.add(password);
                    result.add(firstName);
                    result.add(lastName);
                    result.add(address);
                    result.add(telNo);
                    result.add(email);
                    controller.registerPassenger(result);
                    userTextField.clear();
                    pwBox.clear();
                    firstNameTextField.clear();
                    lastNameTextField.clear();
                    addressTextField.clear();
                    telNoTextField.clear();
                    emailTextField.clear();
                    showAlertAndWait("Success", Alert.AlertType.INFORMATION);
                }
            }
        });

        Scene scene = new Scene(grid, 500, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    private boolean hasMoreThan20Chars(TextField userTextField,
            PasswordField pwBox, TextField firstNameTextField,
            TextField lastNameTextField, TextField addressTextField,
            TextField telNoTextField, TextField emailTextField) {

        return userTextField.getText().length() > 20
                || pwBox.getText().length() > 20
                || firstNameTextField.getText().length() > 20
                || lastNameTextField.getText().length() > 20
                || addressTextField.getText().length() > 20
                || telNoTextField.getText().length() > 20
                || emailTextField.getText().length() > 40;
    }
}
