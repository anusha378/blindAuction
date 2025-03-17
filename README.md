This project implements a prototype platform for running blind auctions, where buyers can place bids on products with the highest bid winning at the end of the auction. The platform consists of two main services:

User Service: Manages user registration, stores personal information (PII), and validates opaque tokens used to identify users.
Auction Service: Allows sellers to register products for auction, accepts bids from buyers, and determines the winner of the auction based on the highest bid.
Technologies

Java 17: The primary programming language.
Spring Boot: Framework used to build both the User Service and Auction Service.
Spring Data JPA: ORM for database access.
H2 Database: In-memory database for easy testing and development.
JWT/Opaque Tokens: For user identification and validation.
Postman: Tool used for testing the API endpoints.

You can use Postman to test the API endpoints:

Register a User: Call /users/register with the user details.
Validate Token: Call /users/validate-token
Create a Auction: Call /api/create 
Place a Bid: Call /api/{auctionId}/bid 
Successful Bid For the Auction: /{auctionId}/successfulBid

How to Run

Prerequisites
JDK 11 or higher: Make sure you have JDK 11 or a higher version installed.
Maven: Used to build and manage dependencies.

Running the Application

Clone the repository:
git clone https://github.com/your-repository/blind-auction.git
cd blind-auction
Build the project:  mvn clean install
Run the application:  mvn spring-boot:run

Default Database
The application uses an embedded H2 database. You can access the H2 console at:
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: password
