package sample;

public class Employee {
    private int columnNo;
    private String EmployeeID;
    private String EmployeeName;
    private String Password;
    private String Position;
    private String Branch;

    public Employee(int No, String employeeID, String employeeName, String password, String position, String branch) {
        columnNo = No;
        EmployeeID = employeeID;
        EmployeeName = employeeName;
        Password = password;
        Position = position;
        Branch = branch;
    }

//    public Employee(int No, String employeeID, String employeeName, String password, String position) {
//        columnNo = No;
//        EmployeeID = employeeID;
//        EmployeeName = employeeName;
//        Password = password;
//        Position = position;
//    }

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
}
