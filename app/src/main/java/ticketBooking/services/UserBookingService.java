package ticketBooking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticketBooking.entities.Ticket;
import ticketBooking.entities.Train;
import ticketBooking.entities.User;
import ticketBooking.utils.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserBookingService {

    private ObjectMapper objectMapper = new ObjectMapper();
    private List<User> userList;
    private User user;

    private final String USER_FILE_PATH = "app/src/main/java/ticketBooking/localDb/users.json";

    public UserBookingService(User user) throws IOException {
        this.user = user;
        loadUserListFromFile();
    }

    public UserBookingService() throws IOException {
        loadUserListFromFile();
    }

    private void loadUserListFromFile() throws IOException {
        File userFile = new File(USER_FILE_PATH);
        if (!userFile.exists()) {
            userFile.getParentFile().mkdirs();
            objectMapper.writeValue(userFile, new ArrayList<User>());
            userList = new ArrayList<>();
        } else {
            userList = objectMapper.readValue(userFile, new TypeReference<List<User>>() {});
        }
    }

    public Boolean loginUser() {
        for (User u : userList) {
            if (u.getName().equals(user.getName()) &&
                    UserServiceUtil.checkPassword(user.getPassword(), u.getHashedPassword())) {
                this.user = u; // set the logged-in user
                return true;
            }
        }
        return false;
    }

    public Boolean signUp(User newUser) {
        try {
            userList.add(newUser);
            saveUserListToFile();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void fetchBookings() {
        if (user.getTicketsBooked() == null || user.getTicketsBooked().isEmpty()) {
            System.out.println("No tickets booked.");
        } else {
            System.out.println("Your booked tickets:");
            user.printTickets();
        }
    }

    public Boolean cancelBooking(String ticketId) throws IOException {
        if (ticketId == null || ticketId.isEmpty()) {
            System.out.println("Ticket ID cannot be empty.");
            return false;
        }
        boolean removed = user.getTicketsBooked().removeIf(t -> t.getTicketId().equals(ticketId));
        if (removed) {
            updateUser(user);
            System.out.println("Ticket cancelled successfully!");
        } else {
            System.out.println("No ticket found with this ID.");
        }
        return removed;
    }

    public void updateUser(User updatedUser) throws IOException {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserId().equals(updatedUser.getUserId())) {
                userList.set(i, updatedUser);
                saveUserListToFile();
                return;
            }
        }
    }

    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USER_FILE_PATH);
        usersFile.getParentFile().mkdirs();
        objectMapper.writeValue(usersFile, userList);
    }

    public User getUser() {
        return user;
    }

}
