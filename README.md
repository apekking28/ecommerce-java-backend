# Documentation For E - commerce Project

## Project Descriptions
The project is an efficient and user-friendly E-Commerce platform, which provides an optimized online shopping experience. Its main features include: an authentication feature with user registration and login, a shopping cart feature to add and manage products in the cart, a product feature that allows users to browse products through categories, search and use filters, and provide accurate and detailed product information. The project also has an ordering feature that eases the ordering process with shipping and payment details, as well as the ability to track order status. The goal of this project is to provide a convenient and secure online shopping experience, make it easier for users to find and purchase products, and increase customer convenience and satisfaction.

## Features
### Authentication Feature 
  - User registration and login

### Shopping Cart Feature
  - Add and manage products in the cart.

### Product Features
  - Browse products through categories, search and filters.<br>
  - Accurate and detailed product information.

### Ordering Features
  - Easy ordering process with shipping and payment details.
  - Track order status

The project is designed to provide a convenient and secure online shopping experience, make it easier for users to find and purchase products, and increase customer convenience and satisfaction.


### Library or dependency
- spring-boot-starter-data-jpa: <br>
This dependency is used to integrate Spring Data JPA in your project. Spring Data JPA is a part of the Spring Framework that simplifies the implementation of object-relational data access (ORM).

- spring-boot-starter-web <br>
This dependency is used to develop web applications using Spring MVC (Model-View-Controller). It provides basic features for web development.

- spring-boot-devtools<br>
This dependency provides additional development tools to speed up the Spring Boot development cycle. It includes automatic restart of the application when there are changes in the code.

- mysql-connector-java<br>
This dependency is used to connect the Spring Boot application with a MySQL database using JDBC (Java Database Connectivity).

- spring-boot-configuration-processor<br>
This dependency helps in processing Spring Boot configuration metadata and generating useful metadata files.

- spring-boot-configuration-processor<br>
This dependency allows you to reduce writing boilerplate code in your project by automatically generating getter/setter methods, constructors, and more.

- spring-boot-starter-security<br>
This dependency provides security and authentication features for Spring Boot applications. It lets you secure endpoints and configure access rules.

- spring-boot-starter-test<br>
This dependency is used to write and run unit tests in Spring Boot projects using testing frameworks like JUnit.

- jjwt<br>
This dependency is the JSON Web Token (JWT) library for Java. JWT is used to secure and authenticate API requests.


## design database
<img width="250" alt="Screen Shot 2023-07-12 at 18 13 11" src="https://github.com/apekking28/ecommerce-java-backend/assets/106460262/5588e7b0-4e29-4923-900e-c90f9801a09b">

## API EndPoint

### Auth
- POST `http://localhost:8080/auth/signin` -> Login user or admin
- POST `http://localhost:8080/auth/signup` -> Register user

### Product
- GET `http://localhost:8080/api/produks` -> List all products
- GET `http://localhost:8080/api/produks/{id}` -> detail product
- POST `http://localhost:8080/api/produks` -> add new product
- PUT `http://localhost:8080/api/produks/{id}` -> Edit product
- DELETE `http://localhost:8080/api/produks/{id}` -> Delete product

### Category
- GET `http://localhost:8080/api/kategoris` -> List all Categories
- GET `http://localhost:8080/api/kategoris/{id}` -> Detail Category
- POST `http://localhost:8080/api/kategoris` -> Add new category
- PUT `http://localhost:8080/api/kategoris/{id}` -> Edit category
- DELETE `http://localhost:8080/api/kategoris/{id}` -> DELETE category


### shopping carts
- GET `http://localhost:8080/api/keranjangs` -> List all shopping carts
- POST `http://localhost:8080/api/keranjangs` -> Add new shopping cart
- PATCH `http://localhost:8080/api/keranjangs/{productId}` -> Edit shopping cart
- DELETE `http://localhost:8080/api/keranjangs/{productId}` -> Delete shopping cart


### Odering
#### For USER
- GET `http://localhost:8080/api/pesanans` -> List Orders
- POST `http://localhost:8080/api/pesanans` -> Create new order
- PATCH  `http://localhost:8080/api/pesanans/{pesananId}/cancel` -> Cancel order
- PATCH  `http://localhost:8080/api/pesanans/{pesananId}/terima` -> receive order
- PATCH  `http://localhost:8080/api/pesanans/{pesananId}/konfirmasi` -> confirm the order

#### For ADMIN 
- GET `http://localhost:8080/api/pesanans/admin` -> Search orders
- PATCH  `http://localhost:8080/api/pesanans/{pesananId}/kirim` -> send order
- PATCH `http://localhost:8080/api/pesanans/{pesananId}/packing` -> message packaging


## Build In With
- Spring boot
- Spring security
- Spring web
- JJWT
- Lombok
- maven


