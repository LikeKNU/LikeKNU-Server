# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

LikeKNU is a comprehensive Spring Boot application serving Kongju National University students with announcements, bus schedules, meal menus, and academic calendars. The system uses a multi-module architecture with event-driven processing and external data collection.

## Build System and Commands

This project uses Gradle with a multi-module setup. Key commands:

```bash
# Build the project
./gradlew build

# Run tests
./gradlew test

# Run specific module tests
./gradlew :like-knu-api:test

# Build and run the application (main module)
./gradlew :like-knu-api:bootRun

# Create executable JAR
./gradlew :like-knu-api:bootJar
```

The main application JAR is built as `like-knu.jar` in the `like-knu-api` module.

## Module Architecture

The project is organized into 7 modules with clear separation of concerns:

- **like-knu-api**: Main REST API module, depends on all other modules
- **like-knu-job**: Message processing and AI integration (OpenAI) for announcement tagging
- **like-knu-collector**: External data collection from university websites and bus APIs
- **like-knu-data**: JPA entities, repositories, and data layer
- **like-knu-common**: Shared utilities and exception handling
- **like-knu-external**: External service integrations (Slack)
- **univ-club**: University club management functionality

## Configuration Management

Environment-specific configuration is managed through profile-based property files in `src/main/resources/secrets/`:
- `application-local.properties`
- `application-local-dev.properties` 
- `application-dev.properties`
- `application-prod.properties`
- `application-common.properties`

Default profile is `local`. Test configuration uses H2 database with SQL logging enabled.

## Key Architectural Patterns

### Event-Driven Processing
The collector modules use Spring Events for decoupled data processing:
- Collectors gather data from external sources
- Events are published to trigger downstream processing
- Job module consumes events for data transformation and storage

### Data Collection Strategy
- **Schedulers**: Automated collection of announcements, menus, bus times, and academic calendars
- **External APIs**: Integration with Kakao/Naver maps for real-time bus information
- **Web Scraping**: University website parsing for announcements and menus using JSoup

### Caching and Performance
- Redis integration for caching frequently accessed data
- Menu and announcement caching to reduce external API calls

## Database Strategy

- **Primary Database**: MySQL for main application data
- **Test Database**: H2 in-memory database
- **Multiple DataSources**: Separate configurations for LikeKNU and UnivClub modules
- **Flyway Migrations**: Database versioning in `src/main/resources/db/`

## Monitoring and Observability

Comprehensive monitoring setup included:
- **OpenTelemetry**: Distributed tracing (currently disabled)
- **Prometheus**: Metrics collection
- **Grafana**: Dashboards and visualization
- **Loki**: Log aggregation
- **Logstash**: Log processing

Configuration files are in `src/main/resources/monitoring/` and `src/main/resources/logging/`.

## Deployment

Production deployment uses:
- Profile: `prod`
- JAR execution with environment-specific properties
- Deployment scripts in `scripts/` directory
- AWS infrastructure with S3 for static resources