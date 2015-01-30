package dk.telenor.resource;

import dk.telenor.SecurityException;
import dk.telenor.entity.Employee;
import dk.telenor.entity.SecurityToken;
import dk.telenor.storage.Company;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class JSONServiceTest extends JSONService{

    @InjectMocks
    private JSONService jsonService = new JSONService();

    @BeforeMethod
    public void startup() throws SecurityException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllEmployeesNotNull() throws Exception {
        List list = jsonService.getAllEmployees();
        Assert.assertNotNull(list);
    }

    @Test(expectedExceptions = dk.telenor.SecurityException.class)
    public void testRegisterTokenIsNull() throws Exception {
        jsonService.registerToken(new SecurityToken());
    }

    @Test(expectedExceptions = dk.telenor.SecurityException.class)
    public void testRegisterTokenIsEmpty() throws Exception {
        jsonService.registerToken(new SecurityToken(""));
    }

    @Test
    public void testRegisterToken() throws Exception {
        jsonService.registerToken(new SecurityToken("qwe"));
        Assert.assertTrue(Company.getTokens().get("qwe") != null);
    }

    @Test
    public void testRegisterTokenDeleted() throws Exception {
        Thread.sleep(45000);
        Assert.assertNull(Company.getTokens().get("qwe"));
    }

    @Test
    public void testGetAllEmployeeById() throws Exception {
        Employee emp = jsonService.getEmployeeById(1);
        Assert.assertNotNull(emp);
    }

    @Test(expectedExceptions = SecurityException.class)
    public void testGetAllEmployeeByIdNegative() throws Exception {
        jsonService.getEmployeeById(-1);
    }

    @Test(expectedExceptions = SecurityException.class)
    public void testGetAllEmployeeByIdNull() throws Exception {
        jsonService.getEmployeeById(null);
    }

    @Test(expectedExceptions = SecurityException.class)
     public void testEditEmployeeIdIsNull() throws Exception {
        jsonService.editEmployee(null, "", 0);
    }

    @Test(expectedExceptions = SecurityException.class)
    public void testEditEmployeeIdIsNegative() throws Exception {
        jsonService.editEmployee(-1, "", 0);
    }

    @Test
    public void testEditEmployee() throws Exception {
        Employee employee = jsonService.getEmployeeById(1);
        String employeeName = employee.getName();
        Integer employeeDeptId = employee.getDepartmentId();
        jsonService.editEmployee(1, "Changed", 4);
        employee = jsonService.getEmployeeById(1);
        Assert.assertNotEquals(employeeName, employee.getName());
        Assert.assertNotEquals(employeeDeptId, employee.getDepartmentId());
    }

    @Test
    public void testCreateEmployeeWithEmptyName() throws Exception {
        jsonService.createEmployee("", null);
        Assert.assertNull(Company.getEmployees().get(""));
    }

    @Test
    public void testCreateEmployeeWithEmptyDept() throws Exception {
        String name = "Name";
        jsonService.createEmployee(name, null);
        Employee emp = Company.getEmployees().get(Company.getEmployees().size());
        Assert.assertEquals(name, emp.getName());
    }

    @Test
    public void testCreateEmployee() throws Exception {
        String name = "Name";
        Integer deptId = 4;
        jsonService.createEmployee(name, deptId);
        Employee emp = Company.getEmployees().get(Company.getEmployees().size());
        Assert.assertEquals(name, emp.getName());
        Assert.assertEquals(deptId, emp.getDepartmentId());
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        Integer lastEmployeeId = Company.getEmployees().size();
        jsonService.deleteEmployee(lastEmployeeId);
        Assert.assertNull(Company.getEmployees().get(lastEmployeeId));
    }
}