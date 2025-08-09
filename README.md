# Token Bridge Server

A Spring Boot application for token bridge operations built with Java 17 and Maven.

## Project Structure

This is a Maven-based Spring Boot application with the following key components:
- **Token Bank**: User and game management functionality
- **Token Mint**: Token creation and minting operations  
- **Token Trade**: Trading operations
- **Shared**: Common utilities, configuration, and data access

## CI/CD Pipeline

The project includes a GitHub Actions CI pipeline that automatically:

### Build Pipeline Features
- ✅ **Automated Builds**: Triggers on push to `main`/`develop` branches and pull requests
- ✅ **Multi-step Validation**: Validates, compiles, tests, and packages the application
- ✅ **Dependency Caching**: Caches Maven dependencies for faster builds
- ✅ **Artifact Generation**: Creates and uploads JAR artifacts
- ✅ **Code Quality**: Runs verification checks

### Pipeline Steps
1. **Checkout**: Gets the latest code
2. **Setup Java 17**: Configures the build environment
3. **Cache Dependencies**: Speeds up subsequent builds
4. **Validate**: Validates the Maven project structure
5. **Compile**: Compiles the source code
6. **Test**: Runs unit tests
7. **Package**: Creates the executable JAR file
8. **Verify**: Performs additional quality checks
9. **Upload Artifacts**: Stores build artifacts for 30 days

### Manual Triggering
You can also trigger the pipeline manually using the "workflow_dispatch" option in GitHub Actions.

## Build Commands

To build locally:
```bash
# Compile the project
mvn compile

# Run tests
mvn test

# Package the application
mvn package

# Run the application
java -jar target/monolith-0.0.1-SNAPSHOT.jar
```

## Requirements

- Java 17
- Maven 3.6+
- MySQL database
- Redis (for caching)

## Configuration

The application uses `application.properties` for configuration. Ensure you have the proper database and Redis connections configured for your environment.
