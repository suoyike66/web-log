# API Generator Templates

This directory contains templates for the REST API interface generator for the weblog-springboot project.

These templates follow the project's architecture and coding standards:
- Uses VO objects for data transfer
- Implements MapStruct converters for DO/VO transformations
- Follows the layered architecture (Controller -> Service -> Mapper)
- Integrates with JWT authentication and security
- Uses MyBatis Plus for database operations
- Includes Swagger annotations for API documentation