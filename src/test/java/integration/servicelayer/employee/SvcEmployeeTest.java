package integration.servicelayer.employee;

import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.employee.Employee;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.employee.EmployeeService;
import servicelayer.employee.EmployeeServiceException;
import servicelayer.employee.EmployeeServiceImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
public class SvcEmployeeTest extends ContainerizedDbIntegrationTest {

    private EmployeeService svc;
    private EmployeeStorage storage;

    @BeforeAll
    public void setup() {
        runMigration(3);

        storage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());
        svc = new EmployeeServiceImpl(storage);
    }

    @Test
    public void saveEmployeeWhenCallingCreateEmployee() throws EmployeeServiceException, SQLException {
        // Arrange
        int id = svc.createEmployee("Hans", "Hansen", Date.valueOf("1967-10-10"));
        // Act
        Employee e = svc.getEmployeeById(id);
        // Assert
        assertEquals("Hans", e.getFirstname());
        assertEquals("Hansen", e.getLastname());
    }

    @Test
    public void mustReturnLastId() throws SQLException, EmployeeServiceException {
        //Arrange
        //Act
        int eId1 = svc.createEmployee("Anders", "Andersen", Date.valueOf("1988-09-18"));
        int eId2 = svc.createEmployee("Bent", "Bentsen", Date.valueOf("1990-02-25"));
        //Assert
        assertEquals(1, eId2 - eId1);
    }

    @Test
    public void returnEmployeeWithId() throws SQLException, EmployeeServiceException {
        //Arrange
        int employeeId = svc.createEmployee("Kenneth", "Lauritzen", Date.valueOf("2001-01-01"));
        //Act
        Employee employee = svc.getEmployeeById(employeeId);
        //Assert
        assertTrue(employee.getFirstname().equals("Kenneth") && employee.getLastname().equals("Lauritzen"));
    }

    @Test
    public void returnSizeOfJustCreatedEmployees() throws SQLException, EmployeeServiceException {
        //Arrange
        Collection<Employee> collection = new ArrayList<Employee>();
        //Act
        int eId1 = svc.createEmployee("Anders", "Andersen", Date.valueOf("1960-07-08"));
        int eId2 = svc.createEmployee("Bent", "Bentsen", Date.valueOf("2008-12-12"));
        int eId3 = svc.createEmployee("Kenneth", "Lauritzen", Date.valueOf("1989-04-11"));
        int eId4 = svc.createEmployee("Laura", "Laurasen", Date.valueOf("1994-12-31"));
        int eId5 = svc.createEmployee("Maria", "Mariasen", Date.valueOf("2003-03-13"));
        collection.add(svc.getEmployeeById(eId1));
        collection.add(svc.getEmployeeById(eId2));
        collection.add(svc.getEmployeeById(eId3));
        collection.add(svc.getEmployeeById(eId4));
        collection.add(svc.getEmployeeById(eId5));
        int empCreated = collection.size();
        //Assert
        assertEquals(5, empCreated);
    }

}
