package datalayer.employee;


import dto.employee.Employee;
import dto.employee.EmployeeCreation;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface EmployeeStorage {
    public Employee getEmployeeWithId(int employeeId) throws SQLException;
    public Collection<Employee> getEmployees() throws SQLException;
    public int createEmployee(EmployeeCreation employeeToCreate) throws SQLException;
}
