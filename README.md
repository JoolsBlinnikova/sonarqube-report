# sonarqube-report
The application allows to generate a report in HTML format using data that was obtained using the SonarQube API.

#What you’ll need
 
 -	JDK 1.8 or later
 -	Maven 3.2+ 
 
 # How to build
 
 -	go to ./src/main/resources/application.properties and add properties for connection to SonarQube server:
  ```sh
 token=
 sonarqube.url=
 ``` 
 -	go to an application folder and execute the following command using a terminal:
 ```sh
 mvn clean install
 ```
 -	go to ./target and execute the following command using a terminal, the project key must be specified as a command line argument: 
 ```sh
 java -jar sonar-1.0-SNAPSHOT.jar <project key>
 ```
