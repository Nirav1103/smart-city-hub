# Smart City Hub

Smart City Hub is a Java-based console application developed as a 2nd semester academic project.  
It provides a centralized system for managing city complaints, hall bookings, and administrative services using Java, JDBC, MySQL, and Data Structures.

---

## Features

### Citizen Features
- Register complaints based on region and category
- View complaint status in ascending or descending order
- Book community halls
- Change or cancel hall bookings

### Admin Features
- Add complaint sub-categories
- View and resolve complaints
- Store resolved complaints region-wise in files
- Add new halls with region and capacity

---

## Technologies Used

- Java (Core Java, OOP)
- JDBC
- MySQL Database
- Data Structures (Binary Search Tree for complaint sorting)
- File Handling

---

## Project Structure

```
smartcityhub/
│
├── admin/
│   └── Admin.java
│
├── complaints/
│   ├── ComplaintManager.java
│   ├── DS_Logic.java
│   └── RegisterComplaints.java
│
├── halls/
│   └── HallManagement.java
│
└── db/
    └── DBConnection.java
```

---

## Database Tables

This project uses MySQL database **test1** with the following tables:

- user_info
- complaint
- subcomplaint_data
- hall_details
- hall_booking

---

## Key Concepts Implemented

### Object-Oriented Programming
- Abstraction via abstract class `ComplaintManager`
- Encapsulation in complaint node structure
- Modular package-based architecture

### Data Structures
A Binary Search Tree is used to:
- store complaint records
- display them in ascending and descending order

### JDBC Integration
The system connects to MySQL using:
```java
DriverManager.getConnection("jdbc:mysql://localhost:3306/test1", "root", "");
```

---

## How to Run the Project

### 1. Clone the repository
```
git clone https://github.com/your-username/smart-city-hub.git
```

### 2. Import into IDE
You can use:
- IntelliJ IDEA
- Eclipse
- NetBeans

### 3. Setup Database
Create database:
```
CREATE DATABASE test1;
```

Create required tables before running the project.

### 4. Run Main Class
Run your main menu class to start the application.

---

## Sample Functional Modules

### Complaint Management
- Register complaints by region and category
- View complaint status sorted by ID

### Hall Booking
- Book halls by region
- Prevent duplicate booking on same date
- Modify and cancel bookings

### Admin Panel
- Resolve complaints
- Save resolved complaint history to files

---

## Academic Information

- Project Type: Semester Project
- Course: Java Programming + Data Structures + DBMS
- Developed by: Nirav Mistry

---

## Future Improvements

- GUI using Java Swing or JavaFX
- Role-based login system
- Email or SMS notifications
- Web or Android version

---

## License

This project is created for educational purposes.
