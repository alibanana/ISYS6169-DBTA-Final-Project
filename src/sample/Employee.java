package sample;

public class Employee {
    private String EmployeeID;
    private String EmployeeName;
    private String Password;
    private String PositionID;
    private String BranchID;

    public Employee(String employeeID, String employeeName, String password, String positionID, String branchID) {
        EmployeeID = employeeID;
        EmployeeName = employeeName;
        Password = password;
        PositionID = positionID;
        BranchID = branchID;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPositionID() {
        return PositionID;
    }

    public void setPositionID(String positionID) {
        PositionID = positionID;
    }

    public String getBranchID() {
        return BranchID;
    }

    public void setBranchID(String branchID) {
        BranchID = branchID;
    }
}
