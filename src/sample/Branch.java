package sample;

public class Branch {
    private String BranchID;
    private String BranchName;
    private String Address;
    private String Phone;

    public Branch(String branchID, String branchName, String address, String phone){
        BranchID = branchID;
        BranchName = branchName;
        Address = address;
        Phone = phone;
    }

    public String getBranchID() {
        return BranchID;
    }

    public void setBranchID(String branchID) {
        BranchID = branchID;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String branchName) {
        BranchName = branchName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}