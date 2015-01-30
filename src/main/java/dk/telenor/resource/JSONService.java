package dk.telenor.resource;

import dk.telenor.SecurityException;
import dk.telenor.entity.Employee;
import dk.telenor.entity.SecurityToken;
import dk.telenor.storage.Company;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Vladyslav Povediuk.
 */

@Path("/json")
@Produces("application/json")
public class JSONService {

    @GET
    @Path("/employee/{employeeId}")
    public Employee getEmployeeById(@PathParam("employeeId") Integer employeeId) throws SecurityException{
        Employee employee;
        if (employeeId != null && employeeId > 0) {
            employee = Company.getEmployees().get(employeeId);
            employee.setDepartmentName(Company.getDepartments().get(employee.getDepartmentId()).getDepartmentName());
        } else {
            throw new SecurityException("Please, provide employeeId as parameter");
        }
        return employee;
    }

    @PUT
    @Path("/editEmployee")
    public Employee editEmployee(@QueryParam("employeeId") Integer employeeId,
                                 @QueryParam("employeeName") String employeeName,
                                 @QueryParam("employeeDeptId")Integer employeeDeptId) throws SecurityException{
        Employee employee;
        if (employeeId != null && employeeId > 0) {
            employee = Company.getEmployees().get(employeeId);
            employee.setDepartmentName(Company.getDepartments().get(employee.getDepartmentId()).getDepartmentName());
        } else {
            throw new SecurityException("Please, provide employeeId as parameter");
        }

        if (employeeName != null) {
            employee.setName(employeeName);
        }

        if (employeeDeptId != null) {
            employee.setDepartmentId(employeeDeptId);
            employee.setDepartmentName(Company.getDepartments().get(employeeDeptId).getDepartmentName());
        }

        return employee;
    }

    @POST
    @Path("/createEmployee")
    @Produces("text/plain")
    public Response createEmployee(@QueryParam("employeeName") String employeeName,
                                   @QueryParam("employeeDeptId")Integer employeeDeptId) {

        Employee employee = new Employee();

        if (employeeName != null && !employeeName.isEmpty()) {
            employee.setName(employeeName);
        } else {
            return Response.status(204).build();
        }

        if (employeeDeptId != null) {
            employee.setDepartmentId(employeeDeptId);
            employee.setDepartmentName(Company.getDepartments().get(employeeDeptId).getDepartmentName());
        }

        Company.getEmployees().put(Company.getEmployees().size(), employee);

        return Response.status(200).entity("Employee with name : " + employee.getName() + " is created.").build();
    }

    @DELETE
    @Path("/deleteEmployee/{employeeId}")
    @Produces("text/plain")
    public Response deleteEmployee(@PathParam("employeeId") Integer employeeId ) {
        Employee employee = null;

        if (employeeId != null && employeeId > 0) {
            employee = Company.getEmployees().get(employeeId);
            Company.getEmployees().remove(employeeId);
        } else {
            return Response.status(204).build();
        }

        return Response.status(200).entity("Employee with name: " + employee.getName() + " deleted").build();
    }

    @GET
    @Path("/employees")
    public List<Employee> getAllEmployees(){
        List<Employee> employees = new ArrayList<Employee>();
        Map<Integer, Employee> map = Company.getEmployees();
        for (Employee emp : map.values()){
            employees.add(emp);
        }

        return employees;
    }

    @POST
    @Path("/register")
    @Produces("text/plain")
    @Consumes("application/json")
    public Response registerToken(SecurityToken token) throws dk.telenor.SecurityException {
        if (token.getToken() != null && !token.getToken().isEmpty()) {
            Company.getTokens().put(token.getToken(), new Date().getTime());
            System.out.println("Token " + token.getToken() + " registered");
        } else {
            throw new SecurityException("Provide security token");
        }

        return Response.status(200).entity("Security token is set").build();
    }


}
