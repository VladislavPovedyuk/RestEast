package dk.telenor.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Vladyslav Povediuk.
 */

@Path("/json")
public class JSONService {

    @GET
    @Path("/employee/{employeeId}")
    @Produces("application/json")
    public Employee getEmployeeById(@PathParam("employeeId") Integer employeeId){
        Employee employee = Company.getEmployees().get(employeeId);
        employee.setDepartmentName(Company.getDepartments().get(employee.getDepartmentId()).getDepartmentName());
        return employee;
    }

    @POST
    @Path("/editEmployee")
    @Produces("application/json")
    public Employee editEmployee(@QueryParam("employeeId") Integer employeeId,
                                 @QueryParam("employeeName") String employeeName,
                                 @QueryParam("employeeDeptId")Integer employeeDeptId) {
        Employee employee = Company.getEmployees().get(employeeId);
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
    @Produces("application/json")
    public Response createEmployee(@QueryParam("employeeName") String employeeName,
                                   @QueryParam("employeeDeptId")Integer employeeDeptId) {

        Employee employee = new Employee();

        if (employeeName != null) {
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

    @POST
    @Path("/deleteEmployee/{employeeId}")
    @Produces("application/json")
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
    @Produces("application/json")
    public List<Employee> getAllEmployees(){
        List<Employee> employees = new ArrayList<Employee>();
        Map<Integer, Employee> map = Company.getEmployees();
        for (Employee emp : map.values()){
            employees.add(emp);
        }

        return employees;
    }

    @POST
    @Path("/register/{token}")
    public Response registerToken(@PathParam("token") String token,
                                  @Context HttpServletRequest request) {

        Company.getTokens().put(token, new Date().getTime());

        return Response.status(200).entity("Security token is set").build();
    }


}
