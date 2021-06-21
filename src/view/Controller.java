/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.db.AirplaneDbInterface;
import static javafx.scene.control.Alert.AlertType.*;
import model.bo.Flight;
/**
 *
 * @author Johan C
 */
public class Controller {
    private final FlightPane flightView; // view
    private final AirplaneDbInterface airplaneDb; // model
    
    public Controller(AirplaneDbInterface airplaneDb,FlightPane flightView){
        this.airplaneDb = airplaneDb;
        this.flightView = flightView;
    }
    
    
    protected void connect(){
         new Thread(){
            @Override
            public void run(){
                try{
                    airplaneDb.connect();
                    final List<Flight> result = airplaneDb.getAllFlights();
                    javafx.application.Platform.runLater(
                        new Runnable() {
                        public void run(){
                            flightView.displayFlights(result);
                        }
                    });
                }
                catch (IOException | SQLException | ClassNotFoundException e) {
                    javafx.application.Platform.runLater(
                        new Runnable() {
                        @Override
                        public void run(){
                            flightView.showAlertAndWait("Could not connect to database.", ERROR);
                        }
                    }); 
                } 
            }
        }.start();
    }
    
    
    protected void disconnect(){
        new Thread(){
            @Override
            public void run(){
                try{
                    airplaneDb.disconnect();
                }catch (IOException | SQLException e) {
            
                }
            }
        }.start();
    }
}
