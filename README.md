# Course Service

A Spring Boot microservice for managing educational courses in an EAD (Distance Learning) platform. This service provides comprehensive course management capabilities including course creation, status tracking, instructor management, and complete module management within courses.

## 🚀 Features

- **Complete Course Management**: Full CRUD operations (Create, Read, Update, Delete) for educational courses
- **Complete Module Management**: Full CRUD operations for course modules with nested routing
- **Lesson Management**: Individual lessons within modules with video content (infrastructure ready)
- **Course Levels**: Support for Beginner, Intermediate, and Advanced course levels
- **Course Status Tracking**: Track courses as In Progress or Concluded
- **Instructor Management**: Associate courses with instructors via UUID
- **Image Support**: Course image URL storage for visual content
- **Video Content**: Lesson video URL support for multimedia learning
- **Hierarchical Structure**: Course → Module → Lesson organization
- **JSON Serialization**: Optimized JSON responses with Jackson annotations
- **Database Persistence**: PostgreSQL database with JPA/Hibernate
- **Audit Trail**: Automatic creation and update timestamps
- **Service Layer**: Complete business logic separation with service interfaces and implementations
- **Repository Pattern**: Data access abstraction with Spring Data JPA
- **Lazy Loading**: Optimized entity relationships with lazy fetching
- **Serializable Models**: All entities implement Serializable for caching support
- **UUID Primary Keys**: Secure and globally unique identifiers
- **Validation Ready**: Entity constraints and validation annotations
- **REST API**: Complete RESTful API implementation with controllers
- **DTO Pattern**: Data Transfer Objects for API request/response handling
- **Input Validation**: Bean Validation with @Valid annotations
- **CORS Support**: Cross-Origin Resource Sharing configuration
- **Transactional Operations**: Database transaction management with @Transactional
- **Cascade Deletion**: Automatic cleanup of related entities (Course → Module → Lesson)
- **Duplicate Prevention**: Course name uniqueness validation
- **UTC Timezone**: Consistent timestamp handling across timezones
- **Error Handling**: HTTP status codes and error responses
- **Bean Utils**: Efficient object property copying with BeanUtils.copyProperties
- **Nested Resource Management**: Modules managed within course context
- **Comprehensive Service Implementation**: Full business logic implementation for all entities

## 🛠️ Technology Stack

- **Java 21**: Latest LTS version with modern language features
- **Spring Boot 3.5.0**: Modern Spring framework with enhanced performance
- **Spring Data JPA**: Database abstraction layer with repository pattern
- **Spring Web**: RESTful web services with MVC architecture
- **Spring Validation**: Bean validation framework for input validation
- **PostgreSQL 12+**: Robust relational database with advanced features
- **Lombok**: Reduces boilerplate code and improves readability
- **Maven**: Build and dependency management with lifecycle management
- **Jackson**: Advanced JSON serialization with custom formatting
- **Hibernate**: ORM framework with lazy loading and relationship management
- **Bean Utils**: Efficient object property copying utilities

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
    password: 
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
│   │   ├── configs/
│   │   │   └── ResolverConfig.java        # CORS and web configuration
│   │   ├── controllers/
│   │   │   ├── CourseController.java      # Course REST API controllers
│   │   │   └── ModuleController.java      # Module REST API controllers
│   │   ├── dtos/
│   │   │   ├── CourseRecordDTO.java       # Course Data Transfer Objects
│   │   │   └── ModuleRecordDTO.java       # Module Data Transfer Objects
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

The service includes a complete REST API implementation with comprehensive CRUD operations for both courses and modules:

### Course Management Endpoints

#### Create Course
- `POST /ead-course/courses` - Create new course with validation
  - **Request Body**: CourseRecordDTO with validation
  - **Response**: 201 Created with course details or 409 Conflict for duplicate names
  - **Validation**: All required fields validated with Bean Validation
  - **Features**: Duplicate name prevention, automatic timestamp generation

#### Get All Courses
- `GET /ead-course/courses` - Retrieve all courses
  - **Response**: 200 OK with list of all courses
  - **Features**: Returns complete course information with modules

#### Get Course by ID
- `GET /ead-course/courses/{courseId}` - Get specific course by UUID
  - **Response**: 200 OK with course details or 404 Not Found
  - **Features**: Returns complete course information

#### Update Course
- `PUT /ead-course/courses/{courseId}` - Update existing course
  - **Request Body**: CourseRecordDTO with validation
  - **Response**: 200 OK with updated course details
  - **Features**: Partial updates supported, validation applied

#### Delete Course
- `DELETE /ead-course/courses/{courseId}` - Delete course and all related modules
  - **Response**: 200 OK with success message
  - **Features**: Cascade deletion of related modules and lessons

### Module Management Endpoints

#### Create Module
- `POST /ead-course/courses/{courseId}/modules` - Create new module for specific course
  - **Request Body**: ModuleRecordDTO with validation
  - **Response**: 201 Created with module details
  - **Features**: Automatic course association, validation

#### Get All Modules for Course
- `GET /ead-course/courses/{courseId}/modules` - Retrieve all modules for a course
  - **Response**: 200 OK with list of modules
  - **Features**: Returns modules with lessons information

#### Get Module by ID
- `GET /ead-course/courses/{courseId}/modules/{moduleId}` - Get specific module
  - **Response**: 200 OK with module details or 404 Not Found
  - **Features**: Validates module belongs to specified course

#### Update Module
- `PUT /ead-course/courses/{courseId}/modules/{moduleId}` - Update existing module
  - **Request Body**: ModuleRecordDTO with validation
  - **Response**: 200 OK with updated module details
  - **Features**: Course context validation, partial updates

#### Delete Module
- `DELETE /ead-course/courses/{courseId}/modules/{moduleId}` - Delete module
  - **Response**: 200 OK with success message
  - **Features**: Cascade deletion of related lessons

### API Request/Response Examples

#### Create Course Request
```json
POST /ead-course/courses
Content-Type: application/json

{
  "name": "Spring Boot Fundamentals",
  "description": "Learn Spring Boot from scratch with hands-on projects",
  "courseStatus": "IN_PROGRESS",
  "courseLevel": "BEGINNER",
  "userInstructor": "123e4567-e89b-12d3-a456-426614174000",
  "imageUrl": "https://example.com/course-image.jpg"
}
```

#### Create Module Request
```json
POST /ead-course/courses/{courseId}/modules
Content-Type: application/json

{
  "title": "Introduction to Spring Boot",
  "description": "Basic concepts and setup of Spring Boot framework"
}
```

#### Success Response (201 Created)
```json
{
  "moduleId": "456e7890-e89b-12d3-a456-426614174001",
  "title": "Introduction to Spring Boot",
  "description": "Basic concepts and setup of Spring Boot framework",
  "creationDate": "15-07-2025 10:30:00"
}
```

#### Error Response (409 Conflict)
```json
{
  "message": "course name already exists"
}
```

### DTO Pattern and Validation

The API uses Data Transfer Objects (DTOs) for request/response handling:

#### CourseRecordDTO
- **Purpose**: Handles course creation and update requests with validation
- **Validation**: Bean Validation annotations for input validation
- **Fields**: name, description, courseStatus, courseLevel, userInstructor, imageUrl
- **Features**: Required field validation, enum validation, UUID validation

#### ModuleRecordDTO
- **Purpose**: Handles module creation and update requests with validation
- **Validation**: Bean Validation annotations for input validation
- **Fields**: title, description
- **Features**: Required field validation, course context validation

#### Validation Annotations
- `@NotBlank` - Ensures string fields are not null or empty
- `@NotNull` - Ensures object fields are not null
- `@Valid` - Triggers validation on request body

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
- **DTO Pattern**: Data Transfer Objects for API request/response handling
- **MVC Pattern**: Model-View-Controller architecture with REST controllers
- **Entity-Relationship Model**: Hierarchical course → module → lesson structure
- **JSON Serialization**: Optimized API responses with Jackson annotations
- **Lazy Loading**: Performance optimization for entity relationships
- **Audit Trail**: Automatic timestamp management
- **Transaction Management**: Database transaction handling with @Transactional
- **Validation Pattern**: Input validation with Bean Validation framework

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
- **CORS Configuration**: Optimized cross-origin resource sharing
- **Transaction Management**: Efficient database transaction handling
- **Bean Utils**: Fast object property copying for DTO conversion

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
- **v0.1.0**: Enhanced data model with module and lesson management, service layer implementation, and repository pattern
- **v0.2.0**: REST API implementation with CourseController, DTO pattern, and validation
- **Current**: Complete course creation API with validation, CORS support, and transaction management
- **Next**: Additional CRUD operations, pagination, and search functionality

## 🎯 Roadmap

### Short Term (Next Release)
- [x] Implement REST controllers for course entity
- [x] Add comprehensive validation and error handling
- [x] Implement DTO pattern for API requests
- [x] Add CORS configuration
- [x] Implement complete CRUD operations for courses (GET, POST, PUT, DELETE)
- [x] Implement complete CRUD operations for modules (GET, POST, PUT, DELETE)
- [x] Add nested resource management (modules within courses)
- [x] Implement comprehensive service layer with full implementations
- [ ] Implement pagination and sorting
- [ ] Add search functionality
- [ ] Create comprehensive test suite
- [ ] Add lesson controllers and management

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

**Note**: This is a microservice component of a larger EAD platform. It's designed to work in conjunction with other services like user management, authentication, and content delivery systems. The current implementation provides a complete course and module management API with full CRUD operations, validation, transaction management, and nested resource handling. The service is production-ready with proper error handling, CORS support, comprehensive service layer implementation, and follows Spring Boot best practices.
