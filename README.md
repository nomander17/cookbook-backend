# Cookbook Backend

This is the backend repository for the Cookbook application, an online forum for sharing recipes. It is built using Spring Boot 3 and Java 8 (1.8).

## Prerequisites

- Java Development Kit (JDK) 8 (1.8)
- Visual Studio Code (VSCode) or Spring Tool Suite (STS)

## Getting Started

1. Clone the repository:

   ```
   git clone https://github.com/nomander17/cookbook-backend.git
   ```

2. Navigate to the project directory:

   ```
   cd cookbook-backend
   ```

3. Create the `application.properties` file:

   - Locate the `application.properties.template` file in the `src/main/resources` directory.
   - Create a copy of the template file and rename it to `application.properties`.
   - Open the `application.properties` file and fill in the required database and email configuration details.

4. Running with Visual Studio Code (VSCode):

   - Open the project in VSCode.
   - Use the Spring Boot dashboard from the Spring Boot Extension pack to run the project.

5. Running with Spring Tool Suite (STS):

   - Open STS and import the project as a Maven project.
   - Right-click on the project in the STS Package Explorer and select "Run As" -> "Spring Boot App".

   This will start the backend server on `http://localhost:8090`.

## Frontend Repository

The frontend for the Cookbook application is developed using ReactJS and is located in a separate repository. You can find the frontend repository at:

[https://github.com/nomander17/cookbook-frontend](https://github.com/nomander17/cookbook-frontend)

Make sure to clone and run the frontend repository as well to have a fully functional Cookbook application.

## License

This project is licensed under the [GNU GPLv3](LICENSE).