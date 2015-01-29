package dk.telenor.rest;

import java.sql.Time;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Vladyslav Povediuk.
 */
public class Company {
    private static Map<Integer, Employee> employees = new LinkedHashMap<Integer, Employee>();
    private static Map<Integer, Department> departments = new LinkedHashMap<Integer, Department>();
    private static Map<String, Long> tokens = new LinkedHashMap<String, Long>();

    static {
        employees.put(1, new Employee(1, "Ivan QA 2", 2));
        employees.put(2, new Employee(2, "Ivan Dev", 1));
        employees.put(3, new Employee(3, "Ivan QA", 2));
        employees.put(4, new Employee(4, "Ivan CA", 3));
        employees.put(5, new Employee(5, "Ivan CEO", 4));
        employees.put(6, new Employee(6, "Ivan CTO", 4));
        employees.put(7, new Employee(7, "Ivan CFO", 4));
        employees.put(8, new Employee(8, "Ivan HR", 4));
        employees.put(9, new Employee(9, "Ivan Dev 2", 1));

        departments.put(1, new Department(1, "Dev Dept."));
        departments.put(2, new Department(2, "QA Dept."));
        departments.put(3, new Department(3, "CA Dept."));
        departments.put(4, new Department(4, "Management"));

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        TokenCheckerThread tokenCheckerThread = new TokenCheckerThread();
        scheduledExecutorService.scheduleAtFixedRate(tokenCheckerThread, 0, 10, TimeUnit.SECONDS);
    }

    public static void addEmployee(Employee employee) {
        employees.put(employees.size(), employee);
    }

    public static void addDepartment(Department department) {
        departments.put(departments.size(), department);
    }

    public static Map<Integer, Employee> getEmployees() {
        return employees;
    }

    public static Map<Integer, Department> getDepartments() {
        return departments;
    }

    public static Map<String, Long> getTokens() {
        return tokens;
    }



}
