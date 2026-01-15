package ticketBooking;

import ticketBooking.entities.Ticket;
import ticketBooking.entities.Train;
import ticketBooking.entities.User;
import ticketBooking.services.TrainService;
import ticketBooking.services.UserBookingService;
import ticketBooking.utils.UserServiceUtil;

import java.io.IOException;
import java.util.*;

public class App {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Running Train Booking System");

        UserBookingService userBookingService = new UserBookingService();
        User loggedInUser = null;
        Train selectedTrain = null;
        String source = "", destination = "", dateOfTravel = "";

        while (true) {
            System.out.println("\nChoose option");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel Booking");
            System.out.println("7. Exit");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    System.out.println("Enter username to signup:");
                    String signupName = scanner.nextLine();
                    System.out.println("Enter password to signup:");
                    String signupPassword = scanner.nextLine();
                    User newUser = new User(signupName, signupPassword, UserServiceUtil.hashPassword(signupPassword),
                            new ArrayList<>(), UUID.randomUUID().toString());
                    if (userBookingService.signUp(newUser)) {
                        System.out.println("Signup successful!");
                    } else {
                        System.out.println("Signup failed!");
                    }
                    break;

                case 2:
                    System.out.println("Enter username to login:");
                    String loginName = scanner.nextLine();
                    System.out.println("Enter password to login:");
                    String loginPassword = scanner.nextLine();
                    loggedInUser = new User(loginName, loginPassword, "", new ArrayList<>(), UUID.randomUUID().toString());
                    userBookingService = new UserBookingService(loggedInUser);
                    if (userBookingService.loginUser()) {
                        loggedInUser = userBookingService.getUser();
                        System.out.println("Login successful!");
                    } else {
                        loggedInUser = null;
                        System.out.println("Login failed! Check username/password.");
                    }
                    break;

                case 3:
                    if (loggedInUser != null) {
                        userBookingService.fetchBookings();
                    } else {
                        System.out.println("Login first!");
                    }
                    break;

                case 4:
                    if (loggedInUser == null) {
                        System.out.println("Login first!");
                        break;
                    }
                    System.out.println("Enter source station:");
                    source = scanner.nextLine();
                    System.out.println("Enter destination station:");
                    destination = scanner.nextLine();

                    List<Train> trains = new TrainService().searchTrains(source, destination);
                    if (trains.isEmpty()) {
                        System.out.println("No trains found!");
                        break;
                    }
                    for (int i = 0; i < trains.size(); i++) {
                        Train t = trains.get(i);
                        System.out.println((i + 1) + " Train ID: " + t.getTrainId());
                        t.getStationTimes().forEach((station, time) ->
                                System.out.println("Station " + station + " Time: " + time));
                    }
                    System.out.println("Select a train by typing index:");
                    int trainIndex = scanner.nextInt() - 1;
                    scanner.nextLine();
                    if (trainIndex >= 0 && trainIndex < trains.size()) {
                        selectedTrain = trains.get(trainIndex);
                        System.out.println("Train selected: " + selectedTrain.getTrainId());
                    } else {
                        System.out.println("Invalid train selection!");
                        selectedTrain = null;
                    }
                    break;

                case 5:
                    if (loggedInUser == null) {
                        System.out.println("Login first!");
                        break;
                    }
                    if (selectedTrain == null) {
                        System.out.println("Select a train first!");
                        break;
                    }
                    System.out.println("Enter date of travel (YYYY-MM-DD):");
                    dateOfTravel = scanner.nextLine();

                    List<List<Integer>> seats = selectedTrain.getSeats();
                    System.out.println("Available seats (0 = free, 1 = booked):");
                    for (int i = 0; i < seats.size(); i++) {
                        System.out.print("Row " + i + ": ");
                        for (int j = 0; j < seats.get(i).size(); j++) {
                            System.out.print(seats.get(i).get(j) + " ");
                        }
                        System.out.println();
                    }

                    System.out.println("Select seat row:");
                    int row = scanner.nextInt();
                    System.out.println("Select seat column:");
                    int col = scanner.nextInt();
                    scanner.nextLine();

                    if (row >= 0 && row < seats.size() && col >= 0 && col < seats.get(0).size()) {
                        if (seats.get(row).get(col) == 0) {
                            seats.get(row).set(col, 1); // mark booked
                            selectedTrain.setSeats(seats);

                            // Create ticket
                            Ticket ticket = new Ticket(UUID.randomUUID().toString(),
                                    loggedInUser.getUserId(), source, destination, dateOfTravel, selectedTrain);
                            loggedInUser.getTicketsBooked().add(ticket);
                            userBookingService.updateUser(loggedInUser);

                            System.out.println("Seat booked successfully! Enjoy your journey.");
                        } else {
                            System.out.println("Seat already booked!");
                        }
                    } else {
                        System.out.println("Invalid seat selection!");
                    }
                    break;

                case 6:
                    if (loggedInUser == null) {
                        System.out.println("Login first!");
                        break;
                    }
                    System.out.println("Enter Ticket ID to cancel:");
                    String ticketId = scanner.nextLine();
                    userBookingService.cancelBooking(ticketId);
                    break;

                case 7:
                    System.out.println("Exiting App. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }
}
