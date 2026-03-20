//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package smartcityhub.complaints;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import smartcityhub.db.DBConnection;

public class RegisterComplaints {
    static Scanner sc;

    public RegisterComplaints() {
    }

    public void complaints() throws Exception {
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
                email = Arrays.toString(new String[]{"east", "west", "north", "south"});
                String[] complaint = new String[]{"water related problem", "garbage an sanitation problem", "street light problem", "stray animal problem", "road related problem", "drainage problem"};
                System.out.println("regions :");

                for(int ch1 = 0; ch1 < ((Object[])email).length; ++ch1) {
                    System.out.println(ch1 + 1 + ". " + ((Object[])email)[ch1]);
                }

                System.out.println("chose region");
                int ch1 = sc.nextInt();
                String selectedRegion = ((Object[])email)[ch1 - 1];
                System.out.println("complaint category");

                for(int i = 0; i < complaint.length; ++i) {
                    System.out.println(i + 1 + ". " + complaint[i]);
                }

                int ch2 = sc.nextInt();
                String com = complaint[ch2 - 1];
                sql = "select * from subcomplaint_data where category_id=?";
                pst = con.prepareStatement(sql);
                pst.setInt(1, ch2);
                rs = pst.executeQuery();
                int i = 1;
                ArrayList<String> subcomplaints = new ArrayList();
                System.out.println("sub-Complaints:");

                while(rs.next()) {
                    String sub = rs.getString(3);
                    subcomplaints.add(sub);
                    System.out.println(i + ". " + sub);
                    ++i;
                }

                System.out.println("select sub complaint");
                int choice = sc.nextInt();
                String selectedsubcomplaint = (String)subcomplaints.get(choice - 1);
                System.out.println(selectedsubcomplaint);
                System.out.println("enter description ");
                sc.nextLine();
                String description = sc.nextLine();
                System.out.println("enter address");
                String address = sc.nextLine();
                String sql1 = "insert into complaint(user_id,c_region,c_category,c_subcategory,description,address,c_status)values (?,?,?,?,?,?,?)";
                pst = con.prepareStatement(sql1);
                pst.setInt(1, user_id);
                pst.setString(2, selectedRegion);
                pst.setString(3, com);
                pst.setString(4, selectedsubcomplaint);
                pst.setString(5, description);
                pst.setString(6, address);
                pst.setString(7, "Active");
                int r = pst.executeUpdate();
                if (r > 0) {
                    System.out.println("complaint registered");
                } else {
                    System.out.println("something wrong");
                }

                return;
            }

            System.out.println("Invalid email. Please try again.");
        }
    }

    public void seeComplaintStatus() throws Exception {
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
                email = "SELECT complaint_id, c_status, c_subcategory FROM complaint WHERE user_id = ?";
                PreparedStatement pst2 = con.prepareStatement(email);
                pst2.setInt(1, user_id);
                ResultSet var13 = pst2.executeQuery();
                DS_Logic var14 = new DS_Logic();

                boolean found;
                for(found = false; var13.next(); found = true) {
                    int complaintId = var13.getInt("complaint_id");
                    String status = var13.getString("c_status");
                    String subcategory = var13.getString("c_subcategory");
                    ((ComplaintManager)var14).addComplaint(complaintId, status, subcategory);
                }

                if (!found) {
                    System.out.println("No complaints found for this user.");
                } else {
                    System.out.println("\nHow would you like to view your complaints?");
                    System.out.println("1. Ascending order (Oldest first)");
                    System.out.println("2. Descending order (Latest first)");
                    System.out.print("Enter choice: ");
                    int choice = sc.nextInt();
                    sc.nextLine();
                    System.out.println("\n--- Complaint Statuses ---");
                    if (choice == 1) {
                        ((ComplaintManager)var14).displayAscending();
                    } else if (choice == 2) {
                        ((ComplaintManager)var14).displayDescending();
                    } else {
                        System.out.println("Invalid choice. Showing in ascending order by default:");
                        ((ComplaintManager)var14).displayAscending();
                    }
                }

                return;
            }

            System.out.println("Invalid email. Please try again.");
        }
    }

    static {
        sc = new Scanner(System.in);
    }
}
