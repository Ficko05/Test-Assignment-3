# Test-Assignment-3
Test Assignment #3

Filip Filipovic. cph-ff37

Allan Simonsen. cph-

Nina Lisakowski cph-
____
# Setup

1. Docker to set up a virtual mysql database.
2. MySQL Workbench or any mysql client.
3. IDE, we used IntelliJ

______
#How to run the project

1. Setup docker's virtual mysql server with this command: docker run -d --rm --name mysql-test-db -e MYSQL_ROOT_PASSWORD=testuser123 -p 3307:3306 mysql
2. Connect to the docker database through your client with the username and password we setup earlier: uname: root, pw: testuser123. look in main if i doubt
3. Run flyway migrate in IntelliJ IDE under maven plugins. or if you have maven installed localy run this command  mvn flyway:migrate


