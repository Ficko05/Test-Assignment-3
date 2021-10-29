package servicelayer.employee;


import dto.customer.Customer;
import dto.employee.Employee;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

public interface EmployeeService {
    int createEmplyee(String firstName, String lastName, Date birthdate) throws EmployeeServiceException;
    Customer getEmployeeById(int id) throws SQLException;
    Collection<Employee> getEmplpyeesByFirstName(String firstName);

}
