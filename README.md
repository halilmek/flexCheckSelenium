# Selenium Test Project with Cucumber and Page Object Model

## Project Structure
```
src/
├── test/
│   ├── java/
│   │   └── com/
│   │       └── test/
│   │           ├── pages/         # Page Object classes
│   │           ├── runners/       # Cucumber runners
│   │           ├── stepdefinitions/ # Step definitions
│   │           └── utilities/     # Utility classes
│   └── resources/
│       └── features/             # Cucumber feature files
```

## Getting Started

1. Clone the repository
2. Run `mvn clean install` to download dependencies
3. Run tests using `mvn test` or run the `CukesRunner` class directly

## Project Components

### Pages
- `BasePage`: Abstract base class for all page objects
- `LoginPage`: Sample page object for login functionality
- Additional page objects will be added as needed

### Step Definitions
- `LoginStepDefs`: Contains step definitions for login functionality
- Additional step definition classes will be added as needed

### Features
- `login.feature`: Sample feature file for login functionality
- Additional feature files will be added as needed

### Utilities
- `Driver`: WebDriver management utility class
- `BrowserUtil`: Common browser utility methods
- `ConfigurationReader`: Configuration management utility
- Additional utility classes will be added as needed

## Configuration

The project uses `configuration.properties` file in the root directory for managing:
- Browser settings
- Test environment URLs
- Timeouts
- Credentials
- Parallel execution settings

## Documentation

For detailed project requirements, progress tracking, and future enhancements, please refer to:
- `PRD.txt`: Project Requirements Document

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request 