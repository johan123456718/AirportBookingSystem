/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author Johan C
 */
public class FlightPane extends VBox{
    
    private MenuBar menuBar;
    
    public FlightPane(){
        this.init();
    }
    
    
    private void init(){
        initMenus();
        BorderPane mainPane = new BorderPane();
        //mainPane.setCenter(booksTable);
        //mainPane.setBottom(bottomPane);
        mainPane.setPadding(new Insets(10, 10, 10, 10));
        this.getChildren().addAll(menuBar, mainPane);
        VBox.setVgrow(mainPane, Priority.ALWAYS);
    }
    
    
    private void initMenus() {

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
        
        
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, searchMenu, manageMenu);
    }
}
