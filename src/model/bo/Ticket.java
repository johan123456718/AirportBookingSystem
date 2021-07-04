/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bo;

import java.time.LocalDate;

/**
 *
 * @author Johan C
 */
public class Ticket {
    private final int flightId; // required
    private final int profileId; // required;
    private final LocalDate departureTime;
    private final String status;
    
    public Ticket(TicketBuilder builder){
        this.flightId = builder.flightId;
        this.profileId = builder.profileId;
        this.departureTime = builder.departureTime;
        this.status = builder.status;
    }
    
    
    
    /**
     * @return the flightId
     */
    public int getFlightId() {
        return flightId;
    }

    /**
     * @return the profileId
     */
    public int getProfileId() {
        return profileId;
    }

    /**
     * @return the departureTime
     */
    public LocalDate getDepartureTime() {
        return departureTime;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    
     @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ticket: ").append(this.flightId).append(", ").append(this.profileId).append(", ").append(this.departureTime).append(", ").append(this.status);
        return sb.toString();
    }
    
    public static class TicketBuilder {
        private final int flightId; // required
        private final int profileId; // required;
        private LocalDate departureTime;
        private String status;
        
        public TicketBuilder(int flightId, int profileId){
            this.flightId = flightId;
            this.profileId = profileId;
        }
    
        
        public TicketBuilder departureTime(LocalDate departureTime){
            this.departureTime = departureTime;
            return this;
        }
        
        public TicketBuilder status(String status){
            this.status = status;
            return this;
        }
        
        public Ticket build(){
            Ticket ticket = new Ticket(this);
            return ticket;
        }
    }
}
