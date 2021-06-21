/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.db;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 *
 * @author Johan C
 */
public interface AirplaneDbInterface {

    /**
     * Connect to the database.
     *
     * @return true on successful connection.
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public boolean connect() throws IOException, SQLException, ClassNotFoundException;

    /**
     * Disconnects from database if connection is active
     *
     * @throws IOException
     * @throws SQLException
     */
    public void disconnect() throws IOException, SQLException;

    public void addFlight(int flightId, int airlineId, int duration, int totalSeats, String airlineName,String fromLocation, String toLocation, LocalDateTime departureTime, LocalDateTime arrivalTime) throws IOException, SQLException;
}
