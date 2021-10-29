package datalayer.employee;

import dto.customer.Customer;
import dto.employee.Employee;
import dto.employee.EmployeeCreation;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeStorage {
    public Customer getEmployeeWithId(int customerId) throws SQLException;
    public List<Employee> getEmployee() throws SQLException;
    public int createEmployee(EmployeeCreation employeeToCreate) throws SQLException;
}
