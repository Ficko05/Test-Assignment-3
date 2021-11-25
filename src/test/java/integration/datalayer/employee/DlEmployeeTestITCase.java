package integration.datalayer.employee;

import com.github.javafaker.Faker;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.employee.Employee;
import dto.employee.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("IntegrationTest")
public class DlEmployeeTestITCase extends ContainerizedDbIntegrationTest {
    private EmployeeStorage employeeStorage;

    @BeforeAll
    private void setup() throws SQLException {
        runMigration(2);
        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());
        int numEmployees = employeeStorage.getEmployees().size();
        if (numEmployees < 50) {
            addFakeEmployees(50 - numEmployees);
        }
    }

    private void addFakeEmployees(int numEmployees) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numEmployees; i++) {
            EmployeeCreation e = new EmployeeCreation(faker.name().firstName(), faker.name().lastName(), faker.date().birthday());
            employeeStorage.createEmployee(e);
        }
    }

    @Test
    public void mustSaveEmployeeInDatabaseWhenCallingCreateEmployee() throws SQLException {
        //Arrange
        //Act
        employeeStorage.createEmployee(new EmployeeCreation("Hannah", "Hansen", new Date()));
        //Assert
        Collection<Employee> employees = employeeStorage.getEmployees();
        assertTrue(
                employees.stream().anyMatch(x ->
                        x.getFirstname().equals("Hannah") &&
                                x.getLastname().equals("Hansen")));

    }

    @Test
    public void mustReturnLastId() throws SQLException {
        //Arrange
        //Act
        int eId1 = employeeStorage.createEmployee(new EmployeeCreation("Anders", "Andersen", new Date()));
        int eId2 = employeeStorage.createEmployee(new EmployeeCreation("Bent", "Bentsen", new Date()));
        //Assert
        assertEquals(1, eId2 - eId1);
    }

    @Test
    public void mustReturnEmployeeWithId() throws SQLException {
        //Arrange
        //Act
        int employeeId = employeeStorage.createEmployee(new EmployeeCreation("Kenneth", "Lauritzen", new Date()));
        Employee employee = employeeStorage.getEmployeeWithId(employeeId);
        //Assert
        assertTrue(employee.getFirstname().equals("Kenneth") && employee.getLastname().equals("Lauritzen"));
    }

    @Test
    public void mustReturnSizeOfJustCreatedEmployees() throws SQLException {
        //Arrange
        //Act
        employeeStorage.createEmployee(new EmployeeCreation("Anders", "Andersen", new Date()));
        employeeStorage.createEmployee(new EmployeeCreation("Bent", "Bentsen", new Date()));
        employeeStorage.createEmployee(new EmployeeCreation("Kenneth", "Lauritzen", new Date()));
        employeeStorage.createEmployee(new EmployeeCreation("Laura", "Laurasen", new Date()));
        employeeStorage.createEmployee(new EmployeeCreation("Maria", "Mariasen", new Date()));

        int empCreated = employeeStorage.getEmployees().size();
        //Assert
        assertEquals(55, empCreated);
    }
}
