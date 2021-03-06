package datalayer.employee;

import dto.employee.Employee;
import dto.employee.EmployeeCreation;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class EmployeeStorageImpl implements datalayer.employee.EmployeeStorage {

    private String connectionString;
    private String username, password;

    public EmployeeStorageImpl(String connectionString, String username, String password) {
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }


    @Override
    public Employee getEmployeeWithId(int employeeId) throws SQLException {
        var sql = "select ID, firstname, lastname, birthdate from Employee where id = ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);

            try (var resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    var id = resultSet.getInt("ID");
                    var firstname = resultSet.getString("firstname");
                    var lastname = resultSet.getString("lastname");
                    var birthdate = resultSet.getDate("birthdate");
                    return new Employee(id, firstname, lastname, birthdate);
                }
                return null;
            }
        }
    }

    @Override
    public Collection<Employee> getEmployees() throws SQLException {
        try (var con = getConnection();
             var stmt = con.createStatement()) {
            var result = new ArrayList<Employee>();

            try (ResultSet resultSet = stmt.executeQuery("select ID, firstname, lastname, birthdate from Employee")) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String firstname = resultSet.getString("firstname");
                    String lastname = resultSet.getString("lastname");
                    Date birthdate = resultSet.getDate("birthdate");

                    Employee e = new Employee(id, firstname, lastname, birthdate);
                    result.add(e);
                }
            }
            return result;
        }
    }

    @Override
    public int createEmployee(EmployeeCreation employeeToCreate) throws SQLException {
        var sql = "insert into Employee(firstname, lastname, birthdate) values (?, ?,?)";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, employeeToCreate.getFirstname());
            stmt.setString(2, employeeToCreate.getLastname());
            // passes it from util.Date to sql.Date for the sql string above
            java.util.Date utilDate = employeeToCreate.getBirthdate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            stmt.setDate(3, sqlDate);

            stmt.executeUpdate();

            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                int newId = resultSet.getInt(1);
                return newId;
            }
        }

    }
}

