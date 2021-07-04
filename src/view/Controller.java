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
import model.bo.Passenger;
import model.bo.Ticket;

/**
 *
 * @author Johan C
 */
public class Controller {

    private final FlightPane flightView; // view
    private final AirplaneDbInterface airplaneDb; // model

    public Controller(AirplaneDbInterface airplaneDb, FlightPane flightView) {
        this.airplaneDb = airplaneDb;
        this.flightView = flightView;
    }

    protected void connect() {
        new Thread() {
            @Override
            public void run() {
                try {
                    airplaneDb.connect();
                    final List<Flight> result = airplaneDb.getAllFlights();
                    javafx.application.Platform.runLater(
                            new Runnable() {
                        public void run() {
                            flightView.displayFlights(result);
                        }
                    });
                } catch (IOException | SQLException | ClassNotFoundException e) {
                    javafx.application.Platform.runLater(
                            new Runnable() {
                        @Override
                        public void run() {
                            flightView.showAlertAndWait("Could not connect to database.", ERROR);
                        }
                    });
                }
            }
        }.start();
    }

    protected boolean isLoginSuccess(String userName, String password) {
        try {
            for (Passenger p : airplaneDb.getAllPassengers()) { // O(n)
                if (userName.equals(p.getUserName()) && password.equals(p.getPassword())) {
                    return true;
                }
            }
        } catch (IOException | SQLException | NullPointerException e) {
            javafx.application.Platform.runLater(
                    new Runnable() {
                @Override
                public void run() {
                    flightView.showAlertAndWait("Could not find any passengers in database.", ERROR);
                }
            });
        }
        return false;
    }
    
    protected int getLoggedInPassengerId(String userName) {
        try {
            return airplaneDb.getLoggedInPassengerId(userName);
        } catch (IOException | SQLException | NullPointerException e) {
            javafx.application.Platform.runLater(
                    new Runnable() {
                @Override
                public void run() {
                    flightView.showAlertAndWait("Could not find any passengers in database.", ERROR);
                }
            });
        }
        return 0;
    }

    protected void registerPassenger(List<String> list) {
        new Thread() {
            @Override
            public void run() {
                try {
                    airplaneDb.addPassenger(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6));
                } catch (IOException | SQLException e) {
                    javafx.application.Platform.runLater(
                            new Runnable() {
                        @Override
                        public void run() {
                            flightView.showAlertAndWait("Could not add passenger.", ERROR);
                        }
                    });
                }
            }
        }.start();
    }
    
    protected void registerTicket(Ticket ticket) {
        new Thread() {
            @Override
            public void run() {
                try {
                    airplaneDb.addTicketInfo(ticket.getProfileId(), ticket.getFlightId(), ticket.getDepartureTime(), ticket.getStatus());
                } catch (IOException | SQLException e) {
                    javafx.application.Platform.runLater(
                            new Runnable() {
                        @Override
                        public void run() {
                            flightView.showAlertAndWait("Could not add ticket.", ERROR);
                        }
                    });
                }
            }
        }.start();
    }

    protected void disconnect() {
        new Thread() {
            @Override
            public void run() {
                try {
                    airplaneDb.disconnect();
                } catch (IOException | SQLException e) {

                }
            }
        }.start();
    }
}
