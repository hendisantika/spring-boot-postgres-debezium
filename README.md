# Spring Boot PostgreSQL Debezium CDC Demo

A comprehensive demonstration of Change Data Capture (CDC) using Spring Boot, PostgreSQL, and Debezium with Kafka for
real-time data streaming and event-driven architecture.

## 🚀 Overview

This project showcases how to implement Change Data Capture (CDC) using Debezium to capture data changes from PostgreSQL
and stream them to Kafka topics. The Spring Boot application acts as both a data producer (through REST APIs) and
consumer (through Kafka listeners) to demonstrate real-time data synchronization.

## 🛠️ Technologies Used

- **Spring Boot 3.5.5** - Application framework
- **Java 21** - Programming language
- **PostgreSQL 15** - Primary database
- **Apache Kafka** - Message streaming platform
- **Debezium** - Change Data Capture platform
- **Spring Data JPA** - Data access layer
- **Spring Kafka** - Kafka integration
- **Docker Compose** - Container orchestration
- **Maven** - Build tool
- **Lombok** - Code generation

## 📋 Prerequisites

Before running this application, ensure you have:

- Java 21 or higher installed
- Docker and Docker Compose
- Maven 3.6+ (or use the included Maven wrapper)
- Git

## 🏗️ Architecture

```
┌─────────────────┐    ┌──────────────┐    ┌─────────────┐    ┌─────────────────┐
│   Spring Boot   │────│ PostgreSQL   │────│  Debezium   │────│     Kafka       │
│   Application   │    │   Database   │    │  Connector  │    │    Topics       │
└─────────────────┘    └──────────────┘    └─────────────┘    └─────────────────┘
         │                                                              │
         │                                                              │
         └──────────────────── Kafka Consumer ────────────────────────┘
```

The application follows this flow:

1. **Data Changes**: CRUD operations on PostgreSQL database
2. **CDC Capture**: Debezium captures changes from PostgreSQL Write-Ahead Log (WAL)
3. **Kafka Streaming**: Changes are published to Kafka topics
4. **Event Processing**: Spring Boot application consumes and processes change events

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/hendisantika/spring-boot-postgres-debezium.git
cd spring-boot-postgres-debezium
```

### 2. Start Infrastructure Services

```bash
docker-compose up -d
```

This will start:

- **PostgreSQL** on port `5433`
- **Zookeeper** on port `52181`
- **Kafka** on port `29092`
- **Debezium Connect** on port `8083`
- **Kowl** (Kafka UI) on port `8090`

### 3. Verify Services

```bash
docker-compose ps
```

### 4. Build and Run the Application

```bash
# Using Maven wrapper
./mvnw spring-boot:run

# Or using installed Maven
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 🔧 Configuration

### Database Configuration

The application uses PostgreSQL with the following settings:

- **Host**: `localhost:5433`
- **Database**: `debezium`
- **Username**: `postgres`
- **Password**: `postgres`

### Kafka Configuration

- **Bootstrap Servers**: `localhost:29092`
- **Consumer Group**: `policy-group`
- **Auto Offset Reset**: `earliest`

## 📊 API Endpoints

### Policy Management

#### Create a New Policy

```bash
curl -X POST http://localhost:8080/api/policies \
  -H "Content-Type: application/json" \
  -d '{
    "policyNumber": "POL-001",
    "policyHolder": "John Doe",
    "startDate": "2024-01-01T00:00:00",
    "endDate": "2024-12-31T23:59:59"
  }'
```

#### Get All Policies

```bash
curl -X GET http://localhost:8080/api/policies
```

#### Get Policy by ID

```bash
curl -X GET http://localhost:8080/api/policies/1
```

#### Update Policy

```bash
curl -X PUT http://localhost:8080/api/policies/1 \
  -H "Content-Type: application/json" \
  -d '{
    "policyNumber": "POL-001-UPDATED",
    "policyHolder": "John Doe Updated",
    "startDate": "2024-01-01T00:00:00",
    "endDate": "2024-12-31T23:59:59"
  }'
```

#### Delete Policy

```bash
curl -X DELETE http://localhost:8080/api/policies/1
```

### Health Check

#### Application Status

```bash
curl -X GET http://localhost:8080/
```

### HTTP Response Format

All API endpoints return JSON responses in the following format:

#### Success Response (Policy CRUD operations):

```json
{
  "id": 1,
  "policyNumber": "POL-001",
  "policyHolder": "John Doe",
  "startDate": "2024-01-01T00:00:00",
  "endDate": "2024-12-31T23:59:59"
}
```

#### Success Response (Get All Policies):

```json
[
  {
    "id": 1,
    "policyNumber": "POL-001",
    "policyHolder": "John Doe",
    "startDate": "2024-01-01T00:00:00",
    "endDate": "2024-12-31T23:59:59"
  },
  {
    "id": 2,
    "policyNumber": "POL-002",
    "policyHolder": "Jane Smith",
    "startDate": "2024-02-01T00:00:00",
    "endDate": "2024-12-31T23:59:59"
  }
]
```

#### Error Response:

```json
{
  "timestamp": "2024-01-01T12:00:00.000Z",
  "status": 404,
  "error": "Not Found",
  "message": "Policy not found with id: 1",
  "path": "/api/policies/1"
}
```

## 🎯 Key Components

### 1. Policy Entity (`Policy.java`)

JPA entity representing insurance policies with CDC-enabled table.

### 2. Kafka Consumer (`KafkaConsumer.java`)

Processes change events from Debezium CDC topics.

### 3. Policy Service (`PolicyService.java`)

Business logic for policy management operations.

### 4. Policy Controller (`PolicyController.java`)

REST endpoints for policy CRUD operations.

### 5. Custom Deserializer (`PolicyDeserializer.java`)

Custom Kafka deserializer for CDC message processing.

## 🔍 Monitoring and Debugging

### Kafka UI (Kowl)

Access the Kafka web UI at `http://localhost:8090` to:

- View Kafka topics and messages
- Monitor consumer groups
- Debug message processing

### Database Access

Connect to PostgreSQL:

```bash
docker exec -it spring-boot-postgres-debezium-db-1 psql -U postgres -d debezium
```

### View CDC Topics

```bash
# List Kafka topics
docker exec -it spring-boot-postgres-debezium-kafka-1 kafka-topics --bootstrap-server localhost:9092 --list

# Consume CDC messages
docker exec -it spring-boot-postgres-debezium-kafka-1 kafka-console-consumer --bootstrap-server localhost:9092 --topic debezium.public.policy --from-beginning
```

## 🧪 Testing the CDC Flow

Follow these steps to test the complete Change Data Capture flow:

### Step 1: Start All Services

```bash
# Start infrastructure
docker-compose up -d

# Start Spring Boot application
./mvnw spring-boot:run
```

### Step 2: Configure Debezium Connector

First, create the Debezium PostgreSQL connector:

```bash
curl -X POST http://localhost:8083/connectors \
  -H "Content-Type: application/json" \
  -d '{
    "name": "postgres-connector",
    "config": {
      "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
      "database.hostname": "db",
      "database.port": "5432",
      "database.user": "postgres",
      "database.password": "postgres",
      "database.dbname": "debezium",
      "database.server.name": "debezium",
      "table.include.list": "public.policy",
      "plugin.name": "pgoutput"
    }
  }'
```

### Step 3: Test CRUD Operations and Monitor CDC Events

#### 3.1 Create a Policy (INSERT Event)

```bash
curl -X POST http://localhost:8080/api/policies \
  -H "Content-Type: application/json" \
  -d '{
    "policyNumber": "POL-001",
    "policyHolder": "John Doe",
    "startDate": "2024-01-01T00:00:00",
    "endDate": "2024-12-31T23:59:59"
  }'
```

#### 3.2 Update the Policy (UPDATE Event)

```bash
curl -X PUT http://localhost:8080/api/policies/1 \
  -H "Content-Type: application/json" \
  -d '{
    "policyNumber": "POL-001-UPDATED",
    "policyHolder": "John Doe Updated",
    "startDate": "2024-01-01T00:00:00",
    "endDate": "2024-12-31T23:59:59"
  }'
```

#### 3.3 Delete the Policy (DELETE Event)

```bash
curl -X DELETE http://localhost:8080/api/policies/1
```

### Step 4: Monitor CDC Events

#### 4.1 Using Kafka Console Consumer

```bash
# Monitor CDC events in real-time
docker exec -it spring-boot-postgres-debezium-kafka-1 kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic debezium.public.policy \
  --from-beginning
```

#### 4.2 Using Kowl UI

1. Open `http://localhost:8090` in your browser
2. Navigate to Topics → `debezium.public.policy`
3. Observe INSERT, UPDATE, and DELETE events

#### 4.3 Check Application Logs

```bash
# View Spring Boot application logs
docker-compose logs -f
```

### Step 5: Verify Connector Status

```bash
# Check connector status
curl -X GET http://localhost:8083/connectors/postgres-connector/status

# List all connectors
curl -X GET http://localhost:8083/connectors
```

### Expected CDC Event Format

#### INSERT Event:

```json
{
  "before": null,
  "after": {
    "id": 1,
    "policy_number": "POL-001",
    "policy_holder": "John Doe",
    "start_date": 1704067200000000,
    "end_date": 1735689599000000
  },
  "source": {
    "version": "2.7.0.Final",
    "connector": "postgresql",
    "name": "debezium",
    "ts_ms": 1704067200000,
    "snapshot": "false",
    "db": "debezium",
    "schema": "public",
    "table": "policy",
    "txId": 123,
    "lsn": 456
  },
  "op": "c",
  "ts_ms": 1704067200000
}
```

#### UPDATE Event:

```json
{
  "before": {
    "id": 1,
    "policy_number": "POL-001",
    "policy_holder": "John Doe",
    "start_date": 1704067200000000,
    "end_date": 1735689599000000
  },
  "after": {
    "id": 1,
    "policy_number": "POL-001-UPDATED",
    "policy_holder": "John Doe Updated",
    "start_date": 1704067200000000,
    "end_date": 1735689599000000
  },
  "source": {
    ...
  },
  "op": "u",
  "ts_ms": 1704067200000
}
```

#### DELETE Event:

```json
{
  "before": {
    "id": 1,
    "policy_number": "POL-001-UPDATED",
    "policy_holder": "John Doe Updated",
    "start_date": 1704067200000000,
    "end_date": 1735689599000000
  },
  "after": null,
  "source": {
    ...
  },
  "op": "d",
  "ts_ms": 1704067200000
}
```

### Operation Types

- `c` = CREATE (INSERT)
- `u` = UPDATE
- `d` = DELETE
- `r` = READ (snapshot)

## 📂 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── id/my/hendisantika/springbootpostgresdebezium/
│   │       ├── controller/          # REST controllers
│   │       ├── model/              # JPA entities and DTOs
│   │       ├── repository/         # JPA repositories
│   │       ├── service/            # Business logic
│   │       ├── kafka/              # Kafka configuration
│   │       ├── deserializer/       # Custom deserializers
│   │       └── SpringBootPostgresDebeziumApplication.java
│   └── resources/
│       └── application.properties
├── test/                          # Unit and integration tests
└── docker-compose.yml            # Infrastructure services
```

## 🐳 Docker Services

| Service          | Port  | Purpose                                   |
|------------------|-------|-------------------------------------------|
| PostgreSQL       | 5433  | Primary database with logical replication |
| Zookeeper        | 52181 | Kafka coordination service                |
| Kafka            | 29092 | Message streaming platform                |
| Debezium Connect | 8083  | CDC connector platform                    |
| Kowl             | 8090  | Kafka web UI                              |

## 🔧 Troubleshooting

### Common Issues

1. **Kafka Connection Failed**
    - Ensure Docker containers are running
    - Verify port `29092` is accessible

2. **Database Connection Issues**
    - Check PostgreSQL container status
    - Verify port `5433` is not in use

3. **Debezium Connector Not Working**
    - Ensure PostgreSQL has logical replication enabled
    - Check Debezium Connect logs: `docker-compose logs debezium`

### Useful Commands

```bash
# View application logs
docker-compose logs -f

# Restart services
docker-compose restart

# Stop all services
docker-compose down

# Remove volumes (clean start)
docker-compose down -v
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Hendi Santika**

- Email: hendisantika@gmail.com
- Telegram: [@hendisantika34](https://t.me/hendisantika34)
- GitHub: [@hendisantika](https://github.com/hendisantika)

## 🌟 Acknowledgments

- [Debezium](https://debezium.io/) for excellent CDC capabilities
- [Spring Boot](https://spring.io/projects/spring-boot) for the amazing framework
- [Apache Kafka](https://kafka.apache.org/) for reliable message streaming
- The open-source community for continuous inspiration
