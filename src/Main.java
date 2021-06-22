
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.bo.Flight;
import model.bo.Passenger;
import model.db.AirplaneMySQLDb;

/**
 *
 * @author Johan C
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        AirplaneMySQLDb db = new AirplaneMySQLDb();
        
        
        if(db.connect()){
            
            String str = "2008-11-11 13:23:44";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime departureTime = LocalDateTime.parse(str, formatter);
            
            str = "2008-11-11 16:23:44";
            LocalDateTime arrivalTime = LocalDateTime.parse(str, formatter);
            
            Flight flight = new Flight.FlightBuilder(2, 234)
            .airlineName("Sas")
            .duration(23)
            .totalSeats(100)
            .fromLocation("Sweden")
            .toLocation("England")
            .departureTime(departureTime)
            .arrivalTime(arrivalTime)
            .build();
            
            System.out.println(flight);
            
            Passenger passenger = new Passenger.PassengerBuilder("johan", "abc123")
                    .firstName("Johan")
                    .lastName("Bravo")
                    .address("Rymdgatan 40")
                    .telNo("0765596196")
                    .email("axel1234516@hotmail.com")
                    .build();
            
            System.out.println(passenger);
                    
            
            /*db.addFlight(flight.getFlightId(), flight.getAirlineId(), flight.getDuration(), flight.getTotalSeats(), flight.getAirlineName(), 
                    flight.getFromLocation(), flight.getToLocation(), flight.getDepartureTime(), flight.getArrivalTime());*/
            db.disconnect();
        }
    }
    
}
