# Java Ticket Booking System

A standalone Java console application for booking train tickets. Users can create accounts, login, search trains, book seats, cancel bookings, and fetch their bookings, all through a simple menu-driven interface.

---

## 🔹 Features

- **Sign Up:** Register a new user account  
- **Login:** Secure login for existing users  
- **Fetch Bookings:** View all current bookings for a user  
- **Search Trains:** Check available trains between two stations  
- **Book a Seat:** Reserve a seat on a chosen train  
- **Cancel Booking:** Cancel a previously booked ticket  
- **Exit:** Close the application  

---

## 🔹 Technology Stack

- **Language:** Java (JDK 8+)  
- **Data Storage:** File-based storage or in-memory lists (can be enhanced with a database)  
- **Interface:** Console-based, menu-driven application  
- **IDE:** IntelliJ IDEA / Eclipse / NetBeans  

---

## 🔹 Project Structure

Java-Ticket-Booking/
│
├── src/ # Source code files
│ ├── TicketBookingSystem.java
│ ├── User.java
│ ├── Train.java
│ └── Booking.java
├── README.md
└── .gitignore


---

## 🔹 Menu Example
1.Sign Up

2.Login

3.Fetch Bookings

4.Search Trains

5.Book a Seat

6.Cancel Booking

7.Exit


---

## 🔹 Run & Setup Instructions

### 1️⃣ Install Java JDK
- Ensure **Java JDK 8+** is installed.  
- Verify installation:


3️⃣ Compile the Java files
Navigate to the src folder and run:
javac TicketBookingSystem.java User.java Train.java Booking.java

4️⃣ Run the application
java TicketBookingSystem

5️⃣ Using the Application

Sign Up: Enter a new username and password to register
Login: Enter your registered credentials
Search Trains: Enter source and destination stations
Book a Seat: Select a train and seat to reserve
Fetch Bookings: View all your current bookings
Cancel Booking: Select a booking to cancel
Exit: Close the application


🔹 Future Enhancements

Integrate database (MySQL or MongoDB) for persistent storage
Add seat selection UI
Implement admin panel to manage trains and bookings
Integrate email notifications for booking confirmations and cancellations


```bash
java -version
javac -version
git clone https://github.com/AyushAshutoshPanda/Ticket-Booking.git
cd <Ticket-Booking>
