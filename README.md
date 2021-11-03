# Test-Assignment-3
Test Assignment #3

Filip Filipovic, cph-ff37

Allan Simonsen, cph-as484

Nina Lisakowski, cph-nl163
____
## Setup

1. Docker to set up a virtual mysql database.
2. MySQL Workbench or any mysql client.
3. IDE, we used IntelliJ.

______
## How to run the project

1. Setup docker's virtual mysql server with this command:  
   docker run -d --rm --name mysql-test-db -e MYSQL_ROOT_PASSWORD=testuser123 -p 3307:3306 mysql 
2. Connect to the docker database through your client with the username and password we setup earlier:  
    uname: root,  
    pw: testuser123   
    Look in the main if in doubt.
    Create a schema called TestASS
3. Run flyway migrate in the IntelliJ IDE under Maven plugins. Or if you have Maven installed locally run this command:  
  mvn flyway:migrate
______
## Features in the system
- Running the main method will use most of the required functionality and print them to the console
- It is possible to create customers, employees and bookings.
- A customer may have a phone number, which can be added with the script to migrate the database with Flyway.
- An SMS is sent when an appointment with a customer is booked.
- We used a containerized test solution that creates a new mysql database specifally for testing and runs the flyway migration between each test.
