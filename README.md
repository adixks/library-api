# Bookworm Library 📚✨

Welcome to the Bookworm Library application! My platform has been designed to handle a massive number of clients and an extensive collection of books. My aim not only to provide efficient organization but also to deliver a unique experience for our readers through subscribing to favorite authors and categories.

## Table of Contents
- Introduction
- Features
- Installation
- Usage
- Tests
- Examples
- Authors

## Introduction
Bookworm Library is a system for managing clients, authors, books, and subscriptions. Each client can register, add favorite authors, and subscribe to interesting categories to receive notifications about new books.

## Features
- Registration of clients with email confirmation.
- Adding new books with information about the author, title, and category.
- Creating and canceling book subscriptions based on category and/or author.

## Installation
To install and run the application, follow these steps:

1. Clone the repository to your local machine.
```bash
git clone https://github.com/adixks/library-api.git
cd library-api
```

2. Run the application using a dependency management tool such as Maven.
```bash
mvn clean install
```

3. Start the application.
```bash
java -jar target/bookworm-library.jar
```

## Usage
To use the API, refer to the documentation available at: API Documentation.

## Tests
The project includes unit tests using JUnit 5, Mockito 5, and Faker for services and mappers. Additionally, an integration test is available.

To run the tests, use the following command:
```bash
mvn test
```

# Examples
## Client Registration
```bash
curl -X POST -H "Content-Type: application/json" -d '{"firstName":"John","lastName":"Doe","email":"john.doe@email.com"}' http://localhost:8080/api/register
```

## Adding a New Book
```bash
curl -X POST -H "Content-Type: application/json" -d '{"author":"J.K. Rowling","title":"Harry Potter and the Sorcerer\'s Stone","category":"Fantasy"}' http://localhost:8080/api/addBook
```

## Category Subscription
```bash
curl -X POST -H "Content-Type: application/json" -d '{"clientId":1,"category":"Fantasy"}' http://localhost:8080/api/subscribe
```

## Author
Adrian Księżak (@adixks) - https://github.com/adixks

Thank you for your interest in my application! I am ready to deliver unforgettable reading experiences on a scale of millions of books and clients. We look forward to your feedback and any questions! 📖✨
