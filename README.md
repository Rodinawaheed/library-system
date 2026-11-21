# Library System & Java Backend Projects

A collection of Java and Spring Boot projects developed as part of my backend development learning journey and Java Spring Boot Bootcamp.

This repository demonstrates the progression from core Java and Object-Oriented Programming (OOP) concepts to building RESTful APIs, database integration, automated testing, authentication, and authorization.

## Repository Projects

| Project | Description |
|---|---|
| [Library System V1](src/) | Core Java Library Management System |
| [Library System V2](library-v2/) | Maven-based Library System project |
| [Session 10](session-10/library-api/) | Spring Boot Library API |
| [Session 11](session-11/library-api/) | Extended Library API project |
| [Session 12](session-12/library-api/) | Library API with Spring Security and JWT |

---

## 1. Library System V1 – Core Java

A Library Management System developed using Java. It models the main entities of a library and demonstrates Object-Oriented Programming concepts, collections, and custom exception handling.

### Project Model

- **Book:** Represents a book in the library.
- **BookStatus:** Defines book statuses: AVAILABLE and BORROWED.
- **Member:** Represents a library member.
- **MemberType:** Defines member types and their borrowing limits.
- **Loan:** Represents a loan associated with a book and a member.
- **Library:** Manages books, members, borrowing, returning, searching, and overdue loans.
- **Lab4LibraryModel:** Contains the model demonstration.
- **Lab6LibraryV1:** Provides the interactive console menu.

### Borrowing Rules

| Member Type | Maximum Books | Loan Duration |
|---|---:|---:|
| STUDENT | 2 | 14 days |
| STAFF | 5 | 30 days |

### Features

- Add and list books.
- Search for books by title or author.
- Manage library members.
- Borrow and return books.
- View overdue loans.
- Prevent duplicate books.
- Handle errors using custom exceptions.

### Custom Exceptions

- LibraryException
- BookNotFoundException
- BookUnavailableException
- DuplicateBookException
- LoanLimitException
- MemberNotFoundException

### Object-Oriented Design

- Classes and Objects
- Enums
- Composition
- Encapsulation
- Exception Handling

### How to Run

1. Open the project in IntelliJ IDEA.
2. Open `src/main/java/library/Lab6LibraryV1.java`.
3. Run the `main()` method.
4. Choose an option from the console menu.

---

## 2. Library System V2

A further development of the Library Management System using Java and Maven.

This project focuses on structured application design, domain modeling, and automated testing.

---

## 3. Session 10 – Library API

A Spring Boot Library API project developed as part of the Java Spring Boot Bootcamp.

Project folder: `session-10/library-api/`

---

## 4. Session 11 – Library API

A continued development of the Library API as part of the bootcamp, building on the previous project.

Project folder: `session-11/library-api/`

---

## 5. Session 12 – Library API with Security

An extension of the Library API introducing authentication and authorization.

### Key Topics

- Spring Security
- JWT Authentication
- Role-Based Authorization
- Member and Librarian permissions
- Protected REST API endpoints
- Database migrations
-Unit Testing with JUnit and Mockito

Project folder: session-12/library-api/

Technologies Used

Java

Object-Oriented Programming (OOP)

Java Collections Framework

Maven

Spring Boot

Spring Data JPA

REST APIs

Spring Security

JSON Web Tokens (JWT)

JUnit

Mockito

Git and GitHub

Project Status

This repository is continuously evolving as I progress through Java backend development and the Spring Boot Bootcamp. More features and projects may be added.
