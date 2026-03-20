//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Smart_City_Hub_GP.smartcityhub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import smartcityhub.admin.Admin;
import smartcityhub.complaints.RegisterComplaints;
import smartcityhub.db.DBConnection;
import smartcityhub.halls.HallManagement;

public class SmartCityHub {
    static Scanner sc;
    static String f_name;
    static String l_name;
    static String mob;
    static int age;
    static String email;
    static String pass;
    static String confpass;

    public SmartCityHub() {
    }

    public static void main(String[] args) throws Exception {
        SmartCityHub sch = new SmartCityHub();

        int ch;
        do {
            System.out.println("1. Login as User");
            System.out.println("2. Login as Admin");
            System.out.println("3. Sign Up");
            System.out.println("4. Exit");
            ch = sc.nextInt();
            switch (ch) {
                case 1:
                    sch.Login_User();
                    break;
                case 2:
                    sch.Login_Admin();
                    break;
                case 3:
                    sch.sign();
                case 4:
                    break;
                default:
                    System.out.println("Enter valid choice");
            }
        } while(ch != 4);

    }

    public void sign() throws Exception {
        Connection con = DBConnection.getConnection();
        System.out.print("enter first name:");
        f_name = sc.next();
        System.out.println("enter last name");
        l_name = sc.next();
        sc.nextLine();
        boolean m = true;

        while(m) {
            System.out.print("enter mobile number:");
            mob = sc.nextLine();
            int in = mob.charAt(0);
            if (mob.length() != 10 || in != 57 && in != 54 && in != 55 && in != 56) {
                System.out.println("Enter Valid Mobile Number");
            } else {
                m = false;
            }
        }

        boolean a = true;

        while(a) {
            System.out.print("enter age:");
            age = sc.nextInt();
            if (age >= 0 && age <= 110) {
                a = false;
            } else {
                System.out.println("enter valid age");
            }
        }

        sc.nextLine();
        boolean w = true;

        while(w) {
            System.out.print("Enter email: ");
            email = sc.nextLine();
            int index1 = email.indexOf(64);
            int index2 = email.lastIndexOf(46);
            if (index1 != -1 && index2 != -1 && index1 <= index2 && index2 != email.length() - 1) {
                String checkSql = "SELECT * FROM user_info WHERE email = ?";
                PreparedStatement checkPst = con.prepareStatement(checkSql);
                checkPst.setString(1, email);
                ResultSet rs = checkPst.executeQuery();
                if (rs.next()) {
                    System.out.println("Account already exists with this email id");
                    return;
                }

                w = false;
            } else {
                System.out.println("Enter valid email id");
            }
        }

        boolean p = true;

        while(p) {
            System.out.print("enter password:");
            pass = sc.nextLine();
            if (pass.length() <= 7) {
                System.out.println("Password length must be at least 8 character");
            } else {
                p = false;
            }
        }

        boolean s = true;

        while(s) {
            System.out.print("enter confirm password:");
            confpass = sc.nextLine();
            if (pass.equals(confpass)) {
                String sql = "insert into user_info(f_name,l_name,mob,age,email,pass) values(?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, f_name);
                pst.setString(2, l_name);
                pst.setString(3, mob);
                pst.setInt(4, age);
                pst.setString(5, email);
                pst.setString(6, pass);
                int r = pst.executeUpdate();
                if (r > 0) {
                    System.out.println("signed up successfully");
                } else {
                    System.out.println("sign up failed");
                }

                s = false;
            } else {
                System.out.println("enter valid confirmation password");
            }
        }

    }

    public void Login_User() throws Exception {
        Connection con = DBConnection.getConnection();
        RegisterComplaints rc = new RegisterComplaints();
        HallManagement hm = new HallManagement();
        System.out.print("Enter email : ");
        sc.nextLine();
        String email = sc.nextLine();
        String sql = "select pass from user_info where email=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, email);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            String correctPass = rs.getString("pass");
            boolean flag = false;

            while(!flag) {
                System.out.print("Enter password :");
                String pass = sc.nextLine();
                if (pass.equals(correctPass)) {
                    System.out.println("Login successful ");
                    flag = true;

                    while(true) {
                        System.out.println("1. Complaint Management");
                        System.out.println("2. Hall Booking Management");
                        System.out.println("3. Logout");
                        int ch = sc.nextInt();
                        switch (ch) {
                            case 1:
                                boolean check = true;

                                while(check) {
                                    System.out.println("1. Register Complaint");
                                    System.out.println("2. View Complaint Status");
                                    System.out.println("3. Exit");
                                    int choice1 = sc.nextInt();
                                    switch (choice1) {
                                        case 1:
                                            rc.complaints();
                                            break;
                                        case 2:
                                            rc.seeComplaintStatus();
                                            break;
                                        case 3:
                                            check = false;
                                            break;
                                        default:
                                            System.out.println("Invalid choice");
                                    }
                                }
                                break;
                            case 2:
                                boolean test = true;

                                while(test) {
                                    System.out.println("1. Book Hall");
                                    System.out.println("2. Change Booking Date");
                                    System.out.println("3. Cancel Booking");
                                    System.out.println("4. Exit");
                                    int choice2 = sc.nextInt();
                                    switch (choice2) {
                                        case 1:
                                            hm.bookHall();
                                            break;
                                        case 2:
                                            hm.changeBookingDate();
                                            break;
                                        case 3:
                                            hm.cancelBooking();
                                            break;
                                        case 4:
                                            test = false;
                                            break;
                                        default:
                                            System.out.println("Invalid choice");
                                    }
                                }
                            case 3:
                                break;
                            default:
                                System.out.println("Invalid choice");
                        }

                        if (ch == 3) {
                            break;
                        }
                    }
                } else {
                    System.out.println("Incorrect password , try again.");
                }
            }
        }

    }

    public void Login_Admin() throws Exception {
        Connection con = DBConnection.getConnection();
        Admin ad = new Admin();
        System.out.print("enter email:");
        sc.nextLine();
        String email = sc.nextLine();
        System.out.print("enter pass:");
        String pass = sc.nextLine();
        String sql = "select a_pass from admins where a_email=(?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, email);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            if (pass.equals(rs.getString(1))) {
                System.out.println("login successfully");

                int ch;
                do {
                    System.out.println("enter 1 to add Sub-complaint");
                    System.out.println("enter 2 to solve complaint");
                    System.out.println("enter 3 to add Hall");
                    System.out.println("enter 4 to Logout");
                    ch = sc.nextInt();
                    switch (ch) {
                        case 1:
                            ad.add_sub_complaint();
                            break;
                        case 2:
                            ad.solveComplaint();
                            break;
                        case 3:
                            ad.add_hall();
                        case 4:
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                } while(ch != 4);
            } else {
                System.out.println("incorrect password");
            }
        } else {
            System.out.println("Enter valid Admin ID and Password");
        }

    }

    static {
        sc = new Scanner(System.in);
    }
}
