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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import model.bo.Flight;

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
    public void addFlight(int flightId, int airlineId, int duration, int totalSeats, String airlineName, String fromLocation, String toLocation, LocalDateTime departureTime, LocalDateTime arrivalTime) throws IOException, SQLException {
        PreparedStatement insertFlight = null;
        ResultSet rs = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            con.setAutoCommit(false);

            String sqlQuery = "INSERT INTO FLIGHT "
                    + "(flight_id, airline_id, airline_name, from_location, to_location, departure_time, arrival_time, duration, total_seats) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            String tmp = departureTime.toString().replace("T", " ");
            LocalDateTime dT = LocalDateTime.parse(tmp, formatter);
            tmp = arrivalTime.toString().replace("T", " ");
            LocalDateTime aT = LocalDateTime.parse(tmp, formatter);
            
            insertFlight = con.prepareStatement(sqlQuery);
            insertFlight.setInt(1, flightId);
            insertFlight.setInt(2, airlineId);
            insertFlight.setString(3, airlineName);
            insertFlight.setString(4, fromLocation);
            insertFlight.setString(5, toLocation);
            insertFlight.setObject(6, dT);
            insertFlight.setObject(7, aT);
            insertFlight.setInt(8, duration);
            insertFlight.setInt(9, totalSeats);

            insertFlight.execute();
            con.commit();
        } catch (SQLException e) {
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

    @Override
    public List<Flight> getAllFlights() throws SQLException, IOException {
        List<Flight> result = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM FLIGHT";
        PreparedStatement selectAll = null;
        ResultSet rs = null;
        try {
            selectAll = con.prepareStatement(sql);
            rs = selectAll.executeQuery();
            result = ConvertToFlight(rs);
            return result;
        } finally {
            if (selectAll != null) {
                selectAll.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    private List<Flight> ConvertToFlight(ResultSet rs) throws SQLException {
        List<Flight> result = new ArrayList<>();
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS Z");
        while (rs.next()) {

            int flightId = rs.getInt("flight_id");
            int airlineId = rs.getInt("airline_id");
            String airlineName = rs.getString("airline_name");
            String fromLocation = rs.getString("from_location");
            String toLocation = rs.getString("to_location");
            
            LocalDateTime departureTime = rs.getObject("departure_time", LocalDateTime.class);
            LocalDateTime arrivalTime = rs.getObject("arrival_time", LocalDateTime.class);

            int duration = rs.getInt("duration");
            int totalSeats = rs.getInt("total_seats");
            boolean doFlightExist = false;

            if (result.size() > 0) {
                if (result.get(result.size() - 1).getFlightId() == flightId) {
                    doFlightExist = true;
                }
            }

            if (!doFlightExist) {
                result.add(new Flight.FlightBuilder(flightId, airlineId)
                        .airlineName(airlineName)
                        .fromLocation(fromLocation)
                        .toLocation(toLocation)
                        .departureTime(departureTime)
                        .arrivalTime(arrivalTime)
                        .duration(duration)
                        .totalSeats(totalSeats)
                        .build());
            }
        }
        return result;
    }

}
