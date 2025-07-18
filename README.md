# Course Service

A Spring Boot microservice for managing educational courses in an EAD (Distance Learning) platform. This service provides comprehensive course management capabilities including course creation, status tracking, and instructor management.

## 🚀 Features

- **Course Management**: Create, update, and manage educational courses
- **Course Levels**: Support for Beginner, Intermediate, and Advanced course levels
- **Course Status Tracking**: Track courses as In Progress or Concluded
- **Instructor Management**: Associate courses with instructors via UUID
- **Image Support**: Course image URL storage for visual content
- **RESTful API**: Clean REST endpoints for course operations
- **Database Persistence**: PostgreSQL database with JPA/Hibernate
- **Audit Trail**: Automatic creation and update timestamps

## 🛠️ Technology Stack

- **Java 21**: Latest LTS version
- **Spring Boot 3.5.0**: Modern Spring framework
- **Spring Data JPA**: Database abstraction layer
- **PostgreSQL**: Primary database
- **Lombok**: Reduces boilerplate code
- **Maven**: Build and dependency management

## 📋 Prerequisites

Before running this service, ensure you have the following installed:

- **Java 21** or higher
- **Maven 3.6+**
- **PostgreSQL 12+**
- **Git** (for cloning the repository)

## 🗄️ Database Setup

1. **Install PostgreSQL** if not already installed
2. **Create Database**:
   ```sql
   CREATE DATABASE "ead-course";
   ```
3. **Create User** (optional, if using default postgres user):
   ```sql
   CREATE USER postgres WITH PASSWORD 'banco123';
   GRANT ALL PRIVILEGES ON DATABASE "ead-course" TO postgres;
   ```

## ⚙️ Configuration

The application is configured via `application.yaml`:

```yaml
server:
  port: 8082
  servlet:
    context-path: '/ead-course/'

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/ead-course
    username: postgres
    password: banco123
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        show-sql: true
```

### Environment Variables

You can override the default configuration using environment variables:

- `DB_URL`: Database connection URL
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password
- `SERVER_PORT`: Application port (default: 8082)

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd course
```

### 2. Build the Project
```bash
mvn clean install
```

### 3. Run the Application
```bash
# Using Maven
mvn spring-boot:run

# Using JAR file
java -jar target/course-0.0.1-SNAPSHOT.jar
```

### 4. Verify Installation
The service will be available at: `http://localhost:8082/ead-course/`

## 📊 Data Model

### Course Model

The `CourseModel` represents an educational course with the following attributes:

| Field | Type | Description | Constraints |
|-------|------|-------------|-------------|
| `courseId` | UUID | Unique course identifier | Auto-generated |
| `name` | String | Course name | Required, unique, max 150 chars |
| `description` | String | Course description | Required, max 255 chars |
| `creationDate` | LocalDateTime | Course creation timestamp | Required, auto-set |
| `lastUpdateDate` | LocalDateTime | Last update timestamp | Required, auto-updated |
| `courseStatus` | CourseStatus | Current course status | Required (IN_PROGRESS/CONCLUDED) |
| `courseLevel` | CourseLevel | Course difficulty level | Required (BEGINNER/INTERMEDIATE/ADVANCED) |
| `userInstructor` | UUID | Instructor identifier | Required |
| `imageUrl` | String | Course image URL | Optional, max 255 chars |

### Enums

#### CourseLevel
- `BEGINNER`: Entry-level courses
- `INTERMEDIATE`: Intermediate difficulty courses
- `ADVANCED`: Advanced-level courses

#### CourseStatus
- `IN_PROGRESS`: Course is currently active
- `CONCLUDED`: Course has been completed

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/ead/course/
│   │   ├── CourseApplication.java          # Main application class
│   │   ├── enums/
│   │   │   ├── CourseLevel.java           # Course difficulty levels
│   │   │   └── CourseStatus.java          # Course status states
│   │   └── models/
│   │       └── CourseModel.java           # Course entity model
│   └── resources/
│       ├── application.yaml               # Application configuration
│       ├── static/                        # Static resources
│       └── templates/                     # Template files
└── test/
    └── java/com/ead/course/
        └── CourseApplicationTests.java     # Application tests
```

## 🧪 Testing

Run the test suite:

```bash
mvn test
```

## 📝 API Documentation

*Note: API endpoints are not yet implemented in the current version. The service is set up with the data model and ready for REST controller implementation.*

### Planned Endpoints

The service is designed to support the following REST endpoints:

- `GET /courses` - List all courses
- `GET /courses/{id}` - Get course by ID
- `POST /courses` - Create new course
- `PUT /courses/{id}` - Update course
- `DELETE /courses/{id}` - Delete course
- `GET /courses/instructor/{instructorId}` - Get courses by instructor
- `GET /courses/status/{status}` - Get courses by status
- `GET /courses/level/{level}` - Get courses by level

## 🔧 Development

### Adding New Features

1. Create new models in the `models` package
2. Add corresponding enums if needed
3. Implement controllers for REST endpoints
4. Add service layer for business logic
5. Create repository interfaces for data access
6. Add comprehensive tests

### Code Style

- Follow Java naming conventions
- Use Lombok annotations to reduce boilerplate
- Implement proper JPA annotations
- Add comprehensive JavaDoc comments

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the project documentation

## 🔄 Version History

- **v0.0.1-SNAPSHOT**: Initial release with basic course model and database setup

---

**Note**: This is a microservice component of a larger EAD platform. It's designed to work in conjunction with other services like user management, authentication, and content delivery systems.
