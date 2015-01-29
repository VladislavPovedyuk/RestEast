package dk.telenor.rest;

/**
 * @author Vladyslav Povediuk.
 */

public class Employee {

    private Integer employeeId;
    private String name;
    private String departmentName;
    private Integer departmentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Employee(){}

    public Employee(Integer employeeId, String name, Integer departmentId) {
        this.employeeId = employeeId;
        this.name = name;
        this.departmentId = departmentId;
    }
}
