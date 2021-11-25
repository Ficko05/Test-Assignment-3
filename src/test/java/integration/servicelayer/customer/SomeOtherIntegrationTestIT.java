package integration.servicelayer.customer;

import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import as.servicelayer.customer.CustomerService;
import as.servicelayer.customer.CustomerServiceException;
import as.servicelayer.customer.CustomerServiceImpl;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("IntegrationTest")
public class SomeOtherIntegrationTestIT extends ContainerizedDbIntegrationTest {

    private CustomerService svc;
    private CustomerStorage storage;

    @BeforeAll
    public void setup() {
        runMigration(3);

        storage = new CustomerStorageImpl(getConnectionString(), "root", getDbPassword());
        svc = new CustomerServiceImpl(storage);
    }

    @Test
    public void Stuff() throws CustomerServiceException, SQLException {
        // Arrange
        var id = svc.createCustomer("schmeep", "schmoop", Date.valueOf("1987-10-07"));

        // Act
        var c = svc.getCustomerById(id);

        // Assert
        assertEquals("schmeep", c.getFirstname());
        assertEquals("schmoop", c.getLastname());
    }

}
