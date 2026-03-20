//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package smartcityhub.complaints;

class DS_Logic$ComplaintNode {
    private int complaintId;
    private String status;
    private String subcategory;
    DS_Logic$ComplaintNode left;
    DS_Logic$ComplaintNode right;

    public DS_Logic$ComplaintNode(int complaintId, String status, String subcategory) {
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
