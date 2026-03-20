//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package smartcityhub.halls;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import smartcityhub.db.DBConnection;

public class HallManagement {
    static Scanner sc;

    public HallManagement() {
    }

    public void bookHall() throws Exception {
        Connection con = DBConnection.getConnection();

        while(true) {
            System.out.print("Enter email: ");
            String email = sc.nextLine();
            String sql = "SELECT user_id FROM user_info WHERE email = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int user_id = rs.getInt("user_id");
                email = new String[]{"East", "West", "North", "South"};
                System.out.println("\nAvailable regions:");

                for(int var28 = 0; var28 < ((Object[])email).length; ++var28) {
                    System.out.println(var28 + 1 + ". " + ((Object[])email)[var28]);
                }

                while(true) {
                    System.out.print("Select region (1-" + ((Object[])email).length + "): ");
                    int regionChoice = sc.nextInt();
                    if (regionChoice >= 1 && regionChoice <= ((Object[])email).length) {
                        String region = ((Object[])email)[regionChoice - 1];
                        sc.nextLine();
                        String hallSql = "SELECT hall_id, hall_name, hall_address, hall_capacity FROM hall_details WHERE hall_region = ?";
                        PreparedStatement hallPst = con.prepareStatement(hallSql);
                        hallPst.setString(1, region);
                        ResultSet hallRs = hallPst.executeQuery();
                        List<Integer> hallIds = new ArrayList();
                        List<String> hallNames = new ArrayList();
                        System.out.println("\nAvailable halls in region: " + region);

                        for(int index = 1; hallRs.next(); ++index) {
                            int id = hallRs.getInt("hall_id");
                            String name = hallRs.getString("hall_name");
                            String address = hallRs.getString("hall_address");
                            int capacity = hallRs.getInt("hall_capacity");
                            hallIds.add(id);
                            hallNames.add(name);
                            System.out.println(index + ". " + name + " | Address: " + address + " | Capacity: " + capacity);
                        }

                        if (hallIds.isEmpty()) {
                            System.out.println("No halls found in this region.");
                            return;
                        }

                        while(true) {
                            System.out.print("Select hall (1-" + hallIds.size() + "): ");
                            int hallChoice = sc.nextInt();
                            if (hallChoice >= 1 && hallChoice <= hallIds.size()) {
                                String selectedHall = (String)hallNames.get(hallChoice - 1);
                                int selectedHall_id = (Integer)hallIds.get(hallChoice - 1);
                                sc.nextLine();

                                LocalDate date;
                                while(true) {
                                    System.out.print("Enter booking date (YYYY-MM-DD): ");
                                    String bookingDate = sc.nextLine();

                                    try {
                                        date = LocalDate.parse(bookingDate);
                                        if (!date.isBefore(LocalDate.now())) {
                                            break;
                                        }

                                        System.out.println("Date must be today or in the future. Please try again.");
                                    } catch (DateTimeParseException var26) {
                                        System.out.println("Invalid date...");
                                    }
                                }

                                String checkSql = "SELECT hall_name FROM hall_booking WHERE hall_name = ? AND booking_date = ?";
                                PreparedStatement checkPst = con.prepareStatement(checkSql);
                                checkPst.setString(1, selectedHall);
                                checkPst.setString(2, date.toString());
                                ResultSet checkRs = checkPst.executeQuery();
                                if (!checkRs.next()) {
                                    String insertSql = "INSERT INTO hall_booking (user_id, hall_name, booking_date,hall_id) VALUES (?, ?, ?,?)";
                                    PreparedStatement insertPst = con.prepareStatement(insertSql);
                                    insertPst.setInt(1, user_id);
                                    insertPst.setString(2, selectedHall);
                                    insertPst.setString(3, date.toString());
                                    insertPst.setInt(4, selectedHall_id);
                                    int r = insertPst.executeUpdate();
                                    if (r > 0) {
                                        PrintStream var10000 = System.out;
                                        String var10001 = String.valueOf(date);
                                        var10000.println("Hall booked successfully for " + var10001 + " at " + selectedHall);
                                    } else {
                                        System.out.println("Booking failed. Please try again.");
                                    }

                                    return;
                                }

                                System.out.println("Sorry! " + selectedHall + " is already booked for " + String.valueOf(date));
                                Set<String> bookedHalls = new HashSet();
                                String bookedSql = "SELECT hall_name FROM hall_booking WHERE booking_date = ?";
                                PreparedStatement bookedPst = con.prepareStatement(bookedSql);
                                bookedPst.setString(1, date.toString());
                                ResultSet bookedRs = bookedPst.executeQuery();

                                while(bookedRs.next()) {
                                    bookedHalls.add(bookedRs.getString(1));
                                }

                                System.out.println("\nHalls available in " + region + " on " + String.valueOf(date) + ":");
                                boolean found = false;

                                for(String hall : hallNames) {
                                    if (!bookedHalls.contains(hall)) {
                                        System.out.println("- " + hall);
                                        found = true;
                                    }
                                }

                                if (!found) {
                                    System.out.println("No halls are available for this date in " + region + ".");
                                }

                                return;
                            }

                            System.out.println("Invalid choice. Please select between 1 and " + hallIds.size());
                        }
                    }

                    System.out.println("Invalid choice. Please select between 1 and " + ((Object[])email).length);
                }
            }

            System.out.println("Invalid email. Please try again.");
        }
    }

    public void changeBookingDate() throws Exception {
        Connection con = DBConnection.getConnection();

        while(true) {
            System.out.print("Enter email: ");
            String email = sc.nextLine();
            String sql = "SELECT user_id FROM user_info WHERE email = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int user_id = rs.getInt("user_id");
                email = "SELECT booking_id, hall_name, booking_date FROM hall_booking WHERE user_id = ?";
                PreparedStatement pst = con.prepareStatement(email);
                pst.setInt(1, user_id);
                ResultSet var20 = pst.executeQuery();
                int[] var21 = new int[50];
                int count = 0;
                System.out.println("\nYour bookings:");

                while(var20.next()) {
                    ++count;
                    var21[count - 1] = var20.getInt("booking_id");
                    System.out.println(count + ". Hall: " + var20.getString("hall_name") + ", Date: " + String.valueOf(var20.getDate("booking_date")));
                }

                if (count == 0) {
                    System.out.println("No bookings found for this email.");
                    return;
                } else {
                    while(true) {
                        System.out.print("Select booking to change (1-" + count + "): ");
                        int choice = sc.nextInt();
                        sc.nextLine();
                        if (choice >= 1 && choice <= count) {
                            int selectedBookingId = var21[choice - 1];

                            LocalDate newDate;
                            while(true) {
                                System.out.print("Enter new booking date (YYYY-MM-DD): ");
                                String newDateStr = sc.nextLine();

                                try {
                                    newDate = LocalDate.parse(newDateStr);
                                    if (!newDate.isBefore(LocalDate.now())) {
                                        break;
                                    }

                                    System.out.println("Date must be today or in the future.");
                                } catch (DateTimeParseException var17) {
                                    System.out.println("Invalid date format. Please try again.");
                                }
                            }

                            String checkSql = "SELECT * FROM hall_booking WHERE booking_date = ? AND hall_name = (SELECT hall_name FROM hall_booking WHERE booking_id = ?)";
                            PreparedStatement checkPst = con.prepareStatement(checkSql);
                            checkPst.setString(1, newDate.toString());
                            checkPst.setInt(2, selectedBookingId);
                            ResultSet checkRs = checkPst.executeQuery();
                            if (checkRs.next()) {
                                System.out.println("This hall is already booked for " + String.valueOf(newDate));
                                return;
                            }

                            String updateSql = "UPDATE hall_booking SET booking_date = ? WHERE booking_id = ?";
                            PreparedStatement updatePst = con.prepareStatement(updateSql);
                            updatePst.setString(1, newDate.toString());
                            updatePst.setInt(2, selectedBookingId);
                            int r = updatePst.executeUpdate();
                            if (r > 0) {
                                System.out.println("Booking updated successfully to " + String.valueOf(newDate));
                            } else {
                                System.out.println("Failed to update booking.");
                            }

                            return;
                        }

                        System.out.println("Invalid choice. Try again.");
                    }
                }
            }

            System.out.println("Invalid email. Please try again.");
        }
    }

    public void cancelBooking() throws Exception {
        Connection con = DBConnection.getConnection();

        while(true) {
            System.out.print("Enter email: ");
            String email = sc.nextLine();
            String sql = "SELECT user_id FROM user_info WHERE email = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int user_id = rs.getInt("user_id");
                email = "SELECT booking_id, hall_name, booking_date FROM hall_booking WHERE user_id = ?";
                PreparedStatement pst = con.prepareStatement(email);
                pst.setInt(1, user_id);
                ResultSet var15 = pst.executeQuery();
                int[] var16 = new int[50];
                int count = 0;
                System.out.println("\nYour bookings:");

                while(var15.next()) {
                    ++count;
                    var16[count - 1] = var15.getInt("booking_id");
                    System.out.println(count + ". Hall: " + var15.getString("hall_name") + ", Date: " + String.valueOf(var15.getDate("booking_date")));
                }

                if (count == 0) {
                    System.out.println("No bookings found for this email.");
                    return;
                } else {
                    while(true) {
                        System.out.print("Select booking to cancel (1-" + count + "): ");
                        int choice = sc.nextInt();
                        sc.nextLine();
                        if (choice >= 1 && choice <= count) {
                            int selectedBookingId = var16[choice - 1];
                            String deleteSql = "DELETE FROM hall_booking WHERE booking_id = ?";
                            PreparedStatement deletePst = con.prepareStatement(deleteSql);
                            deletePst.setInt(1, selectedBookingId);
                            int r = deletePst.executeUpdate();
                            if (r > 0) {
                                System.out.println("Booking cancelled successfully.");
                            } else {
                                System.out.println("Failed to cancel booking. Please try again.");
                            }

                            return;
                        }

                        System.out.println("Invalid choice. Try again.");
                    }
                }
            }

            System.out.println("Invalid email. Please try again.");
        }
    }

    static {
        sc = new Scanner(System.in);
    }
}
