
Library System
Overview
This project is a Library Management System developed using Java. It models the main entities of a library and demonstrates Object-Oriented Programming (OOP) concepts, collections, and custom exception handling.
Project Model
The project includes the following classes and enums:
Book: Represents a book in the library.
BookStatus: Defines book statuses: AVAILABLE and BORROWED.
Member: Represents a library member.
MemberType: Defines member types and their borrowing limits.
Loan: Represents a loan associated with a book and a member.
Library: Manages books, members, borrowing, returning, searching, and overdue loans.
Lab4LibraryModel: Contains the model demonstration.
Lab6LibraryV1: Provides the interactive console menu.
Borrowing Rules
Member Type
Maximum Books
Loan Duration
STUDENT
2
14 days
STAFF
5
30 days
Features
Add and list books.
Search for books by title or author.
Manage library members.
Borrow and return books.
View overdue loans.
Prevent duplicate books.
Handle errors using custom exceptions.
Custom Exceptions
LibraryException
BookNotFoundException
BookUnavailableException
DuplicateBookException
LoanLimitException
MemberNotFoundException
Object-Oriented Design
Classes and Objects: Represent library entities.
Enums: Define fixed values for book status and member type.
Composition: A Loan is associated with a book and a member.
Encapsulation: Organizes data and behavior within classes.
Exception Handling: Handles library-related errors using custom exceptions.
Sample Output
===== Library System =====
1) List books
2) Search
3) Add book
4) Borrow
5) Return
6) Overdue
0) Exit
Choice: 1
How to Run
Open the project in IntelliJ IDEA.
Open src/main/java/library/Lab6LibraryV1.java.
Run the main() method.
Choose an option from the console menu.
Technologies
Java
Java Collections Framework
Git and GitHub
Project Status
The project is under development, and additional features may be added.
