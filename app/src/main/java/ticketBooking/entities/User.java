package ticketBooking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User {

    private String name;
    private String password;
    private String hashedPassword;
    private List<Ticket> ticketsBooked;
    private String userId;

    public User() {}

    public User(String name, String password, String hashedPassword, List<Ticket> ticketsBooked, String userId) {
        this.name = name;
        this.password = password;
        this.hashedPassword = hashedPassword;
        this.ticketsBooked = ticketsBooked;
        this.userId = userId;
    }

    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getHashedPassword() { return hashedPassword; }
    public List<Ticket> getTicketsBooked() { return ticketsBooked; }
    public String getUserId() { return userId; }

    public void setName(String name) { this.name = name; }
    public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }
    public void setTicketsBooked(List<Ticket> ticketsBooked) { this.ticketsBooked = ticketsBooked; }
    public void setUserId(String userId) { this.userId = userId; }

    public void printTickets() {
        if (ticketsBooked != null && !ticketsBooked.isEmpty()) {
            ticketsBooked.forEach(ticket -> System.out.println(ticket.getTicketInfo()));
        } else {
            System.out.println("No tickets booked.");
        }
    }
}
