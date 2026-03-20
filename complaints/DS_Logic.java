//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Smart_City_Hub_GP.smartcityhub.complaints;

import java.io.PrintStream;

public class DS_Logic extends smartcityhub.complaints.ComplaintManager {
    private ComplaintNode root;

    public DS_Logic() {
    }

    public void addComplaint(int id, String status, String subcategory) {
        this.root = this.insert(this.root, id, status, subcategory);
    }

    private ComplaintNode insert(ComplaintNode root, int complaintId, String status, String subcategory) {
        if (root == null) {
            return new ComplaintNode(complaintId, status, subcategory);
        } else {
            if (complaintId < root.getComplaintId()) {
                root.left = this.insert(root.left, complaintId, status, subcategory);
            } else if (complaintId > root.getComplaintId()) {
                root.right = this.insert(root.right, complaintId, status, subcategory);
            }

            return root;
        }
    }

    public void displayAscending() {
        this.inorder(this.root);
    }

    public void displayDescending() {
        this.reverseInorder(this.root);
    }

    private void inorder(ComplaintNode root) {
        if (root != null) {
            this.inorder(root.left);
            PrintStream var10000 = System.out;
            int var10001 = root.getComplaintId();
            var10000.println("Complaint ID: " + var10001 + " | Complaint: " + root.getSubcategory() + " | Status: " + root.getStatus());
            this.inorder(root.right);
        }

    }

    private void reverseInorder(ComplaintNode root) {
        if (root != null) {
            this.reverseInorder(root.right);
            PrintStream var10000 = System.out;
            int var10001 = root.getComplaintId();
            var10000.println("Complaint ID: " + var10001 + " | Complaint: " + root.getSubcategory() + " | Status: " + root.getStatus());
            this.reverseInorder(root.left);
        }

    }

    static class ComplaintNode {
        private int complaintId;
        private String status;
        private String subcategory;
        ComplaintNode left;
        ComplaintNode right;

        public ComplaintNode(int complaintId, String status, String subcategory) {
            this.complaintId = complaintId;
            this.status = status;
            this.subcategory = subcategory;
            this.left = this.right = null;
        }

        public int getComplaintId() {
            return this.complaintId;
        }

        public String getStatus() {
            return this.status;
        }

        public String getSubcategory() {
            return this.subcategory;
        }
    }
}
