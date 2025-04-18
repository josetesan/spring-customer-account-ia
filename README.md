# Spring Customer Account Management System

A Spring Boot application for customer account management with AI-powered conversational capabilities using Spring AI and Ollama.

## Features

- Customer management
- Account management
- Product catalog
- Purchase tracking
- AI-powered chat interface with memory
- Vector-based search using Qdrant

## Tech Stack

- Java 24
- Spring Boot 3.4.4
- Spring Data JPA
- Spring AI 1.0.0-M7
- Ollama for LLM integration
- Qdrant for vector storage
- PostgreSQL for data persistence
- Docker and Docker Compose for containerization

## Requirements

- Java 24 JDK
- Maven
- Docker and Docker Compose
- Ollama with llama3.2 and bge-m3 models installed

## Running Locally

### Using Docker Compose

The easiest way to run the application is using Docker Compose, which will set up all required services:

1. Build the application:
   ```bash
   mvn clean package
   ```

2. Start all services:
   ```bash
   docker compose up
   ```

This will start:
- PostgreSQL database
- Qdrant vector store
- The Spring Boot application

The application will be available at http://localhost:8080

### Manual Setup

If you prefer to run components separately:

1. Start PostgreSQL and Qdrant:
   ```bash
   docker compose up postgres qdrant
   ```

2. Configure the application:
   - Update `src/main/resources/application.yml` with your database and Qdrant connection details

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

### Customers
- `GET /customers` - List all customers
- `GET /customers/{id}` - Get customer by ID
- `POST /customers` - Create a new customer

### Accounts
- `GET /accounts` - List all accounts
- `GET /accounts/{id}` - Get account by ID

### Products
- `GET /products` - List all products
- `POST /products` - Create a new product

### Purchases
- `GET /purchases` - List all purchases
- `POST /purchases` - Create a new purchase

### AI Chat
- `POST /chat` - Interact with the AI assistant

## AI Chat Interface

The application includes an AI-powered chat interface built with Spring AI and Ollama. The assistant can:

- Answer questions about customers, products, and purchases
- Perform actions like creating products and recording purchases
- Maintain conversation context using chat memory

Example query:
```
POST /chat
Content-Type: text/plain

Show me all customers who purchased product X
```

## Performance Testing

The repository includes a K6 script (`k6-script.js`) for load testing the application.

Run the test with:
```bash
k6 run k6-script.js
```

## License

This project is open source and available under the [MIT License](LICENSE).