package as.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@ComponentScan
@RestController
@RequestMapping("/hello")
public class Controller {

    private static final String conStr = "jdbc:mysql://localhost:3307/TestASS";
    private static final String user = "root";
    private static final String pass = "testuser123";


    @GetMapping("/hello")
    public ResponseEntity retrieveAllAssignments() throws SQLException {
        datalayer.customer.CustomerStorageImpl customerStorage = new datalayer.customer.CustomerStorageImpl(conStr, user, pass);
        List<as.dto.customer.Customer> listcustomer = customerStorage.getCustomers();

        return new ResponseEntity(listcustomer.toString(), HttpStatus.ACCEPTED);
    }
}