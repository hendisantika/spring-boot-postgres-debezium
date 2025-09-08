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

```http
# Create a new policy
POST /api/policies
Content-Type: application/json
{
  "policyNumber": "POL-001",
  "policyHolder": "John Doe",
  "startDate": "2024-01-01T00:00:00",
  "endDate": "2024-12-31T23:59:59"
}

# Get all policies
GET /api/policies

# Get policy by ID
GET /api/policies/{id}

# Update policy
PUT /api/policies/{id}

# Delete policy
DELETE /api/policies/{id}
```

### Health Check

```http
GET /
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

1. **Create a Policy**: Use the REST API to create a new policy
2. **Monitor Kafka**: Check Kowl UI for CDC messages in `debezium.public.policy` topic
3. **Verify Processing**: Check application logs for consumed CDC events
4. **Update/Delete**: Perform updates/deletes and observe CDC events

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
