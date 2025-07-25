# Course Service

A Spring Boot microservice for managing educational courses in an EAD (Distance Learning) platform. This service provides comprehensive course management capabilities including course creation, status tracking, and instructor management.

## 🚀 Features

- **Course Management**: Create, update, and manage educational courses
- **Module Management**: Organize courses into structured modules
- **Lesson Management**: Individual lessons within modules with video content
- **Course Levels**: Support for Beginner, Intermediate, and Advanced course levels
- **Course Status Tracking**: Track courses as In Progress or Concluded
- **Instructor Management**: Associate courses with instructors via UUID
- **Image Support**: Course image URL storage for visual content
- **Video Content**: Lesson video URL support for multimedia learning
- **Hierarchical Structure**: Course → Module → Lesson organization
- **JSON Serialization**: Optimized JSON responses with Jackson annotations
- **Database Persistence**: PostgreSQL database with JPA/Hibernate
- **Audit Trail**: Automatic creation and update timestamps
- **Service Layer**: Business logic separation with service interfaces
- **Repository Pattern**: Data access abstraction with Spring Data JPA
- **Lazy Loading**: Optimized entity relationships with lazy fetching
- **Serializable Models**: All entities implement Serializable for caching support
- **UUID Primary Keys**: Secure and globally unique identifiers
- **Validation Ready**: Entity constraints and validation annotations

## 🛠️ Technology Stack

- **Java 21**: Latest LTS version with modern language features
- **Spring Boot 3.5.0**: Modern Spring framework with enhanced performance
- **Spring Data JPA**: Database abstraction layer with repository pattern
- **PostgreSQL 12+**: Robust relational database with advanced features
- **Lombok**: Reduces boilerplate code and improves readability
- **Maven**: Build and dependency management with lifecycle management
- **Jackson**: Advanced JSON serialization with custom formatting
- **Hibernate**: ORM framework with lazy loading and relationship management

## 📋 Prerequisites

Before running this service, ensure you have the following installed:

- **Java 21** or higher (OpenJDK or Oracle JDK)
- **Maven 3.6+** for build management
- **PostgreSQL 12+** for database storage
- **Git** (for cloning the repository)
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code recommended)

## 🗄️ Database Setup

1. **Install PostgreSQL** if not already installed
   - Download from: https://www.postgresql.org/download/
   - Or use Docker: `docker run --name postgres -e POSTGRES_PASSWORD=banco123 -p 5432:5432 -d postgres`

2. **Create Database**:
   ```sql
   CREATE DATABASE "ead-course";
   ```

3. **Create User** (optional, if using default postgres user):
   ```sql
   CREATE USER postgres WITH PASSWORD 'banco123';
   GRANT ALL PRIVILEGES ON DATABASE "ead-course" TO postgres;
   ```

4. **Verify Connection**:
   ```bash
   psql -h localhost -U postgres -d ead-course
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
        jdbc:
          lob:
            non-contextual-creation: true
```

### Environment Variables

You can override the default configuration using environment variables:

- `DB_URL`: Database connection URL
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password
- `SERVER_PORT`: Application port (default: 8082)
- `SPRING_PROFILES_ACTIVE`: Active Spring profile (dev, prod, test)

### Docker Configuration

For containerized deployment, you can use the following environment variables:

```bash
export DB_URL=jdbc:postgresql://postgres:5432/ead-course
export DB_USERNAME=postgres
export DB_PASSWORD=banco123
export SERVER_PORT=8082
```

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd course
```

### 2. Build the Project
```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package the application
mvn clean package
```

### 3. Run the Application
```bash
# Using Maven
mvn spring-boot:run

# Using JAR file
java -jar target/course-0.0.1-SNAPSHOT.jar

# Using Docker (if Dockerfile is available)
docker build -t course-service .
docker run -p 8082:8082 course-service
```

### 4. Verify Installation
The service will be available at: `http://localhost:8082/ead-course/`

### 5. Health Check
Check if the application is running:
```bash
curl http://localhost:8082/ead-course/actuator/health
```

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
| `modules` | Set<ModuleModel> | Collection of course modules | One-to-Many relationship |

**Key Features:**
- JSON serialization with custom date formatting
- Null value exclusion in responses
- Write-only access for sensitive fields
- Automatic timestamp management

### Module Model

The `ModuleModel` represents a course module with the following attributes:

| Field | Type | Description | Constraints |
|-------|------|-------------|-------------|
| `moduleId` | UUID | Unique module identifier | Auto-generated |
| `title` | String | Module title | Required, max 150 chars |
| `description` | String | Module description | Required, max 255 chars |
| `creationDate` | LocalDateTime | Module creation timestamp | Required, auto-set |
| `course` | CourseModel | Parent course | Required, Many-to-One relationship |
| `lessons` | Set<LessonModel> | Collection of module lessons | One-to-Many relationship |

**Key Features:**
- Lazy loading for lessons collection
- Write-only access for parent course reference
- Optimized JSON serialization

### Lesson Model

The `LessonModel` represents an individual lesson with the following attributes:

| Field | Type | Description | Constraints |
|-------|------|-------------|-------------|
| `lessonId` | UUID | Unique lesson identifier | Auto-generated |
| `title` | String | Lesson title | Required, max 150 chars |
| `description` | String | Lesson description | Required, max 255 chars |
| `videoUrl` | String | Lesson video URL | Required |
| `creationDate` | LocalDateTime | Lesson creation timestamp | Required, auto-set |
| `module` | ModuleModel | Parent module | Required, Many-to-One relationship |

**Key Features:**
- Lazy loading for parent module reference
- Video URL support for multimedia content
- Write-only access for parent module reference

### Enums

#### CourseLevel
- `BEGINNER`: Entry-level courses suitable for new learners
- `INTERMEDIATE`: Intermediate difficulty courses for experienced learners
- `ADVANCED`: Advanced-level courses for expert learners

#### CourseStatus
- `IN_PROGRESS`: Course is currently active and accepting enrollments
- `CONCLUDED`: Course has been completed and is no longer accepting enrollments

### Entity Relationships

```
Course (1) ←→ (N) Module (1) ←→ (N) Lesson
```

- **One-to-Many**: One course can have multiple modules
- **Many-to-One**: One module belongs to one course
- **One-to-Many**: One module can have multiple lessons
- **Many-to-One**: One lesson belongs to one module

**Relationship Features:**
- Lazy loading for performance optimization
- Cascade operations for data integrity
- Bidirectional relationships with proper mapping

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/ead/course/
│   │   ├── CourseApplication.java          # Main application class
│   │   ├── enums/
│   │   │   ├── CourseLevel.java           # Course difficulty levels
│   │   │   └── CourseStatus.java          # Course status states
│   │   ├── models/
│   │   │   ├── CourseModel.java           # Course entity model
│   │   │   ├── ModuleModel.java           # Module entity model
│   │   │   └── LessonModel.java           # Lesson entity model
│   │   ├── repositories/
│   │   │   ├── CourseRepository.java      # Course data access
│   │   │   ├── ModuleRepository.java      # Module data access
│   │   │   └── LessonRepository.java      # Lesson data access
│   │   └── services/
│   │       ├── CourseService.java         # Course business logic interface
│   │       ├── ModuleService.java         # Module business logic interface
│   │       ├── LessonService.java         # Lesson business logic interface
│   │       └── impl/
│   │           ├── CourseServiceImpl.java # Course service implementation
│   │           ├── ModuleServiceImpl.java # Module service implementation
│   │           └── LessonServiceImpl.java # Lesson service implementation
│   └── resources/
│       ├── application.yaml               # Application configuration
│       ├── static/                        # Static resources
│       └── templates/                     # Template files
└── test/
    └── java/com/ead/course/
        └── CourseApplicationTests.java     # Application tests
```

## 🧪 Testing

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=CourseApplicationTests

# Run tests with coverage
mvn test jacoco:report
```

### Test Structure
- **Unit Tests**: Individual component testing
- **Integration Tests**: Database and service layer testing
- **Application Tests**: Full application context testing

## 📝 API Documentation

*Note: API endpoints are not yet implemented in the current version. The service is set up with the data model, repositories, and service layer, ready for REST controller implementation.*

### Planned Endpoints

The service is designed to support the following REST endpoints:

#### Course Management
- `GET /courses` - List all courses with pagination
- `GET /courses/{id}` - Get course by ID with full details
- `POST /courses` - Create new course with validation
- `PUT /courses/{id}` - Update course with partial updates
- `DELETE /courses/{id}` - Delete course with cascade
- `GET /courses/instructor/{instructorId}` - Get courses by instructor
- `GET /courses/status/{status}` - Get courses by status
- `GET /courses/level/{level}` - Get courses by level
- `GET /courses/search` - Search courses by name or description

#### Module Management
- `GET /courses/{courseId}/modules` - List all modules for a course
- `GET /modules/{id}` - Get module by ID with lessons
- `POST /courses/{courseId}/modules` - Create new module for a course
- `PUT /modules/{id}` - Update module details
- `DELETE /modules/{id}` - Delete module with cascade
- `GET /modules/search` - Search modules by title

#### Lesson Management
- `GET /modules/{moduleId}/lessons` - List all lessons for a module
- `GET /lessons/{id}` - Get lesson by ID with video details
- `POST /modules/{moduleId}/lessons` - Create new lesson for a module
- `PUT /lessons/{id}` - Update lesson details
- `DELETE /lessons/{id}` - Delete lesson
- `GET /lessons/video/{id}` - Get lesson video URL
- `GET /lessons/search` - Search lessons by title

### JSON Response Format

The API uses Jackson annotations for optimized JSON serialization:

- `@JsonInclude(JsonInclude.Include.NON_NULL)` - Excludes null values from responses
- `@JsonFormat` - Custom date formatting (dd-MM-yyyy HH:mm:ss)
- `@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)` - Hides sensitive fields in responses

### Example JSON Responses

#### Course Response
```json
{
  "courseId": "123e4567-e89b-12d3-a456-426614174000",
  "name": "Spring Boot Fundamentals",
  "description": "Learn Spring Boot from scratch",
  "creationDate": "15-07-2025 10:30:00",
  "lastUpdateDate": "15-07-2025 10:30:00",
  "courseStatus": "IN_PROGRESS",
  "courseLevel": "BEGINNER",
  "userInstructor": "456e7890-e89b-12d3-a456-426614174001",
  "imageUrl": "https://example.com/course-image.jpg"
}
```

#### Module Response
```json
{
  "moduleId": "789e0123-e89b-12d3-a456-426614174002",
  "title": "Introduction to Spring Boot",
  "description": "Basic concepts and setup",
  "creationDate": "15-07-2025 10:30:00"
}
```

## 🔧 Development

### Adding New Features

1. **Create Models**: Add new entity models in the `models` package
2. **Add Enums**: Create corresponding enums if needed
3. **Create Repositories**: Add repository interfaces in the `repositories` package
4. **Implement Services**: Create service interfaces and implementations
5. **Add Controllers**: Implement REST controllers for API endpoints
6. **Add Tests**: Create comprehensive unit and integration tests
7. **Update Documentation**: Update README and API documentation

### Architecture Patterns

The service follows these architectural patterns:

- **Repository Pattern**: Data access abstraction with Spring Data JPA
- **Service Layer Pattern**: Business logic separation with service interfaces
- **Entity-Relationship Model**: Hierarchical course → module → lesson structure
- **JSON Serialization**: Optimized API responses with Jackson annotations
- **Lazy Loading**: Performance optimization for entity relationships
- **Audit Trail**: Automatic timestamp management

### Code Style Guidelines

- Follow Java naming conventions (camelCase for variables, PascalCase for classes)
- Use Lombok annotations to reduce boilerplate code
- Implement proper JPA annotations with constraints
- Add comprehensive JavaDoc comments
- Use meaningful variable and method names
- Follow SOLID principles in service design

### Performance Considerations

- **Lazy Loading**: Used for entity relationships to improve performance
- **Indexing**: Database indexes on frequently queried fields
- **Pagination**: Support for large dataset handling
- **Caching**: Serializable models ready for caching implementation
- **Connection Pooling**: Optimized database connection management

## 🚀 Deployment

### Production Deployment

1. **Build the Application**:
   ```bash
   mvn clean package -DskipTests
   ```

2. **Configure Environment**:
   ```bash
   export SPRING_PROFILES_ACTIVE=prod
   export DB_URL=jdbc:postgresql://prod-db:5432/ead-course
   export DB_USERNAME=prod_user
   export DB_PASSWORD=secure_password
   ```

3. **Run the Application**:
   ```bash
   java -jar target/course-0.0.1-SNAPSHOT.jar
   ```

### Docker Deployment

1. **Create Dockerfile** (if not exists):
   ```dockerfile
   FROM openjdk:21-jdk-slim
   COPY target/course-0.0.1-SNAPSHOT.jar app.jar
   EXPOSE 8082
   ENTRYPOINT ["java", "-jar", "/app.jar"]
   ```

2. **Build and Run**:
   ```bash
   docker build -t course-service .
   docker run -p 8082:8082 course-service
   ```

### Kubernetes Deployment

1. **Create Deployment YAML**:
   ```yaml
   apiVersion: apps/v1
   kind: Deployment
   metadata:
     name: course-service
   spec:
     replicas: 3
     selector:
       matchLabels:
         app: course-service
     template:
       metadata:
         labels:
           app: course-service
       spec:
         containers:
         - name: course-service
           image: course-service:latest
           ports:
           - containerPort: 8082
   ```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Development Workflow

1. **Create Issue**: Document the feature or bug
2. **Create Branch**: Use feature/issue-number naming convention
3. **Implement**: Follow coding standards and add tests
4. **Test**: Run all tests and verify functionality
5. **Document**: Update README and API documentation
6. **Submit PR**: Create pull request with detailed description

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the project documentation
- Review the troubleshooting guide

## 🔄 Version History

- **v0.0.1-SNAPSHOT**: Initial release with basic course model and database setup
- **Current**: Enhanced data model with module and lesson management, service layer implementation, and repository pattern
- **Next**: REST API implementation with comprehensive endpoints and validation

## 🎯 Roadmap

### Short Term (Next Release)
- [ ] Implement REST controllers for all entities
- [ ] Add comprehensive validation and error handling
- [ ] Implement pagination and sorting
- [ ] Add search functionality
- [ ] Create comprehensive test suite

### Medium Term
- [ ] Add authentication and authorization
- [ ] Implement caching with Redis
- [ ] Add monitoring and metrics
- [ ] Create API documentation with Swagger
- [ ] Add event-driven architecture

### Long Term
- [ ] Microservice communication
- [ ] Advanced analytics and reporting
- [ ] Content management system integration
- [ ] Mobile API optimization
- [ ] Internationalization support

---

**Note**: This is a microservice component of a larger EAD platform. It's designed to work in conjunction with other services like user management, authentication, and content delivery systems. The current implementation provides a solid foundation for the course management functionality with a well-structured data model and service layer ready for REST API implementation.
