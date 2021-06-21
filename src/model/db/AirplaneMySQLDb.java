/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 *
 * @author Johan C
 */
public class AirplaneMySQLDb implements AirplaneDbInterface {

    private Connection con;

    public AirplaneMySQLDb() {
        con = null;
    }

    @Override
    public boolean connect() throws IOException, SQLException, ClassNotFoundException {
        String database = "BookingSystem";
        String user = "admin"; // user name
        String pwd = "admin123"; // password 
        String server
                = "jdbc:mysql://localhost:3306/" + database
                + "?UseClientEnc=UTF8" + "?useTimezone=true&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(server, user, pwd);
        return true;
    }

    @Override
    public void disconnect() throws IOException, SQLException {
        if (con != null) {
            con.close();
        }
    }

    @Override
    public void addFlight(int flightId, int airlineId, int duration, int totalSeats, String airlineName,String fromLocation, String toLocation, LocalDateTime departureTime, LocalDateTime arrivalTime) throws IOException, SQLException {
        PreparedStatement insertFlight = null;
        ResultSet rs = null;

        try {
            con.setAutoCommit(false);
            
            String sqlQuery = "INSERT INTO FLIGHT "
                    + "(flight_id, airline_id, airline_name, from_location, to_location, departure_time, arrival_time, duration, total_seats) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
            
            insertFlight = con.prepareStatement(sqlQuery);
            insertFlight.setInt(1, flightId);
            insertFlight.setInt(2, airlineId);
            insertFlight.setString(3, airlineName);
            insertFlight.setString(4, fromLocation);
            insertFlight.setString(5, toLocation);
            insertFlight.setObject(6, departureTime);
            insertFlight.setObject(7, arrivalTime);
            insertFlight.setInt(8, duration);
            insertFlight.setInt(9, totalSeats);

            insertFlight.execute();
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            if (insertFlight != null) {
                insertFlight.close();
            }
            if (rs != null) {
                rs.close();
            }
            con.setAutoCommit(true);
        }
    }

}
