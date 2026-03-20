//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package smartcityhub.admin;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import smartcityhub.db.DBConnection;

public class Admin {
    static Scanner sc;

    public Admin() {
    }

    public void add_sub_complaint() throws Exception {
        Connection con = DBConnection.getConnection();
        String[] complaint = new String[]{"water related problem", "garbage an sanitation problem", "street light problem", "stray animal problem", "road related problem", "drainage problem"};

        for(int i = 0; i < complaint.length; ++i) {
            System.out.println(i + 1 + ". " + complaint[i]);
        }

        System.out.println("enter for which category sub-complaint you want to add");
        int ch = sc.nextInt();
        System.out.println("enter sub-complaint name");
        sc.nextLine();
        String com = sc.nextLine();
        String sql = "insert into subcomplaint_data(category_id,complaint) values(?,?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, ch);
        pst.setString(2, com);
        int r = pst.executeUpdate();
        if (r > 0) {
            System.out.println("insert successfully");
        } else {
            System.out.println("insert fail");
        }

    }

    public void solveComplaint() throws Exception {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT complaint_id, user_id, c_region, c_category, c_subcategory, description, address, c_status FROM complaint WHERE c_status = 'Active'";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        ArrayList<Integer> complaintIds = new ArrayList();
        ArrayList<String> regions = new ArrayList();
        int count = 0;
        System.out.println("\nPending Complaints:");

        while(rs.next()) {
            ++count;
            int id = rs.getInt("complaint_id");
            complaintIds.add(id);
            regions.add(rs.getString("c_region"));
            System.out.println(count + ". Complaint ID: " + id);
            System.out.println("User ID: " + rs.getInt("user_id"));
            System.out.println("Region: " + rs.getString("c_region"));
            System.out.println("Category: " + rs.getString("c_category"));
            System.out.println("Subcategory: " + rs.getString("c_subcategory"));
            System.out.println("Desc: " + rs.getString("description"));
            System.out.println("Address: " + rs.getString("address"));
            System.out.println("Status: " + rs.getString("c_status"));
            System.out.println("-----------------------------------");
        }

        if (count == 0) {
            System.out.println("No pending complaints.");
        } else {
            System.out.print("\nEnter complaint number to resolve: ");
            int choice = sc.nextInt();
            if (choice >= 1 && choice <= count) {
                int selectedComplaintId = (Integer)complaintIds.get(choice - 1);
                String selectedRegion = (String)regions.get(choice - 1);
                String updateSql = "UPDATE complaint SET c_status = 'Resolved' WHERE complaint_id = ?";
                PreparedStatement updatePst = con.prepareStatement(updateSql);
                updatePst.setInt(1, selectedComplaintId);
                int r = updatePst.executeUpdate();
                if (r > 0) {
                    System.out.println("Complaint ID " + selectedComplaintId + " marked as Resolved.");
                    this.saveResolvedComplaintToFile(selectedComplaintId, selectedRegion, con);
                } else {
                    System.out.println("Failed to update complaint.");
                }

            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private void saveResolvedComplaintToFile(int complaintId, String region, Connection con) {
        try {
            String sql = "SELECT * FROM complaint WHERE complaint_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, complaintId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String fileName = region + "_resolved.txt";
                FileWriter fw = new FileWriter(fileName, true);
                fw.write("Complaint ID: " + rs.getInt("complaint_id") + "\n");
                fw.write("User ID: " + rs.getInt("user_id") + "\n");
                fw.write("Region: " + rs.getString("c_region") + "\n");
                fw.write("Category: " + rs.getString("c_category") + "\n");
                fw.write("Subcategory: " + rs.getString("c_subcategory") + "\n");
                fw.write("Description: " + rs.getString("description") + "\n");
                fw.write("Address: " + rs.getString("address") + "\n");
                fw.write("Status: " + rs.getString("c_status") + "\n");
                fw.write("---------------------------------------------------\n");
                fw.close();
                System.out.println(" Complaint saved in file: " + fileName);
            }
        } catch (IOException | SQLException e) {
            System.out.println(" Error saving complaint to file: " + ((Exception)e).getMessage());
        }

    }

    public void add_hall() throws Exception {
        Connection con = DBConnection.getConnection();
        sc.nextLine();
        System.out.print("Enter Hall Name: ");
        String hallName = sc.nextLine();
        String[] regions = new String[]{"east", "west", "north", "south"};
        System.out.println("Select Hall Region:");

        for(int i = 0; i < regions.length; ++i) {
            System.out.println(i + 1 + ". " + regions[i]);
        }

        while(true) {
            System.out.print("Enter choice (1-4): ");
            int regionChoice = sc.nextInt();
            if (regionChoice >= 1 && regionChoice <= 4) {
                String hallRegion = regions[regionChoice - 1];
                sc.nextLine();
                System.out.print("Enter Hall Address: ");
                String hallAddress = sc.nextLine();

                while(true) {
                    System.out.print("Enter Hall Capacity: ");
                    int hallCapacity = sc.nextInt();
                    if (hallCapacity > 0) {
                        String sql = "INSERT INTO hall_details (hall_name, hall_region, hall_address, hall_capacity) VALUES (?, ?, ?, ?)";
                        PreparedStatement pst = con.prepareStatement(sql);
                        pst.setString(1, hallName);
                        pst.setString(2, hallRegion);
                        pst.setString(3, hallAddress);
                        pst.setInt(4, hallCapacity);
                        int r = pst.executeUpdate();
                        if (r > 0) {
                            System.out.println("Hall added successfully.");
                        } else {
                            System.out.println("Failed to add hall.");
                        }

                        return;
                    }

                    System.out.println("Capacity must be positive.");
                }
            }

            System.out.println("Invalid choice, try again.");
        }
    }

    static {
        sc = new Scanner(System.in);
    }
}
