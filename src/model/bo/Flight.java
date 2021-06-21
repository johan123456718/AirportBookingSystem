/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bo;

import java.time.LocalDateTime; // import the LocalDateTime class

/**
 *
 * @author Johan C
 */
public class Flight {

    public static void FlightBuilder(int i, double d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private final int flightId; // required
    private final int airlineId; // required
    private final int duration; //optional
    private final int totalSeats; //optional
    private final String airlineName; //optional
    private final LocalDateTime departureTime; //optional
    private final LocalDateTime arrivalTime; //optional
    private final String fromLocation; //optional
    private final String toLocation; //optional

    public Flight(FlightBuilder builder) {
        this.flightId = builder.flightId;
        this.airlineId = builder.airlineId;
        this.duration = builder.duration;
        this.totalSeats = builder.totalSeats;
        this.airlineName = builder.airlineName;
        this.fromLocation = builder.fromLocation;
        this.toLocation = builder.toLocation;
        this.departureTime = builder.departureTime;
        this.arrivalTime = builder.arrivalTime;
    }

    /**
     * @return the flightId
     */
    public int getFlightId() {
        return flightId;
    }

    /**
     * @return the airlineId
     */
    public int getAirlineId() {
        return airlineId;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @return the totalSeats
     */
    public int getTotalSeats() {
        return totalSeats;
    }

    /**
     * @return the airlineName
     */
    public String getAirlineName() {
        return airlineName;
    }

    /**
     * @return the fromLocation
     */
    public String getFromLocation() {
        return fromLocation;
    }

    /**
     * @return the toLocation
     */
    public String getToLocation() {
        return toLocation;
    }
    
    /**
     * @return the departureTime
     */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    /**
     * @return the arrivalTime
     */
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Flight: ").append(this.flightId).append(", ").append(this.airlineId).append(", ").append(this.duration).append(", ").append(this.totalSeats).append(", ")
                .append(this.airlineName).append(", ").append(this.fromLocation).append(", ").append(this.toLocation).append(", ").append(this.departureTime).append(", ").append(this.arrivalTime);
        return sb.toString();
    }

    public static class FlightBuilder {

        private final int flightId;
        private final int airlineId;
        private int duration;
        private int totalSeats;
        private String airlineName;
        private String fromLocation;
        private String toLocation;

        private LocalDateTime departureTime; //optional
        private LocalDateTime arrivalTime; //optional

        public FlightBuilder(int flightId, int airlineId) {
            this.flightId = flightId;
            this.airlineId = airlineId;
        }

        public FlightBuilder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public FlightBuilder totalSeats(int totalSeats) {
            this.totalSeats = totalSeats;
            return this;
        }

        public FlightBuilder airlineName(String airlineName) {
            this.airlineName = airlineName;
            return this;
        }

        public FlightBuilder fromLocation(String fromLocation) {
            this.fromLocation = fromLocation;
            return this;
        }

        public FlightBuilder toLocation(String toLocation) {
            this.toLocation = toLocation;
            return this;
        }

        public FlightBuilder departureTime(LocalDateTime departureTime) {
            this.departureTime = departureTime;
            return this;
        }

        public FlightBuilder arrivalTime(LocalDateTime arrivalTime) {
            this.arrivalTime = arrivalTime;
            return this;
        }

        public Flight build() {
            Flight flight = new Flight(this);
            return flight;
        }

    }
}
