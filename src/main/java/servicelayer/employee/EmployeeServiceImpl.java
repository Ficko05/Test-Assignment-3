package servicelayer.employee;


import datalayer.employee.EmployeeStorage;
import dto.customer.Customer;
import dto.employee.Employee;
import dto.employee.EmployeeCreation;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeStorage employeeStorage;

    public EmployeeServiceImpl(EmployeeStorage employeeStorage) {
        this.employeeStorage = employeeStorage;
    }


    @Override
    public int createEmplyee(String firstName, String lastName, Date birthdate) throws EmployeeServiceException {
        try {
            return employeeStorage.createEmployee(new EmployeeCreation(firstName, lastName, birthdate));
        } catch (SQLException throwables) {
            throw new EmployeeServiceException(throwables.getMessage());
        }
    }

    @Override
    public Customer getEmployeeById(int id) throws SQLException {
        return employeeStorage.getEmployeeWithId(id);
    }

    @Override
    public Collection<Employee> getEmplpyeesByFirstName(String firstName) {
        return null;
    }
}
