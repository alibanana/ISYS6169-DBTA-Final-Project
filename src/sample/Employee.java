package sample;

public class Employee {
    private int columnNo;
    private String EmployeeID;
    private String EmployeeName;
    private String Password;
    private String Position;
    private String Branch;

    private String PositionID;
    private String BranchID;

    public Employee(int No, String employeeID, String employeeName, String password, String position, String branch) {
        columnNo = No;
        EmployeeID = employeeID;
        EmployeeName = employeeName;
        Password = password;
        Position = position;
        Branch = branch;
    }

    public Employee(String employeeID, String employeeName, String password, String positionID, String branchID) {
        EmployeeID = employeeID;
        EmployeeName = employeeName;
        Password = password;
        PositionID = positionID;
        BranchID = branchID;
    }


    public int getColumnNo() {
        return columnNo;
    }

    public void setColumnNo(int columnNo) {
        this.columnNo = columnNo;
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

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
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
