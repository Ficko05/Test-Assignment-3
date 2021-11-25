package as.servicelayer.employee;


import dto.employee.Employee;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

public interface EmployeeService {
    int createEmployee(String firstName, String lastName, Date birthdate) throws EmployeeServiceException;
    Employee getEmployeeById(int employeeId) throws SQLException;
    Collection<Employee> getEmployees();
}
