




<img width="1179" height="893" alt="Ekran Resmi 2026-02-28 15 47 05" src="https://github.com/user-attachments/assets/4c37bf42-8ee1-4cf1-9a1d-23001862d1c2" />













# 🍔 Food Ordering System API

## 📝 Project Overview

A Spring Boot RESTful backend application for managing restaurants, products, categories, carts, orders, ratings, and owner approval workflows.

This project was developed as a backend learning & portfolio project, focusing on clean architecture, secure authentication, and real-world business logic.

The goal is to simulate a structured food ordering system while applying backend best practices.

---

## 🚀 Features
### 🏪 Restaurant Management
-Create, update, delete restaurants.

-Toggle open/close status.

-Search & filter restaurants.

-Pagination & sorting support.

-Retrieve restaurants owned by current user.

### 🗂 Category & Product Management
-Create categories per restaurant.

-Add products under categories.

-Update product stock.

-Toggle product availability.

-Retrieve products by category.

### 🛒 Cart System
-Add product to cart.

-Remove product from cart.

-Clear cart.

-View current user cart.

-Checkout process (convert cart to order).

### 📦 Order Management
-Create order from cart.

-Cancel order.

-Update order status.

-Retrieve user orders.

-Retrieve orders by restaurant.

-Order status tracking.

### ⭐ Rating System
-Add rating to restaurant.

-Retrieve restaurant ratings.

-Delete rating.

-Pagination-ready responses.

### 👑 Owner Request Workflow
-Submit owner request.

-Approve / Reject owner requests.

-Role-based authorization.

### 🔐 Authentication & Security
-JWT Authentication.

-Refresh Token support.

-Role-based authorization.

-Custom security handlers.

-Spring Security integration.

### 📄 API Documentation
-Swagger UI.

-OpenAPI 3 documentation.

-JWT secured API testing.

### 🧱 Database & Migration
-PostgreSQL database.

-Flyway migrations.

-Constraint-based schema design.

---

## 🏗 Architecture
The project follows a layered architecture: controller → service → repository → database.

### 📂 Package Structure
config: Application configurations.

controller: REST controllers.

dto: Data Transfer Objects (request / response).

exception: Centralized exception handling.

jwt: Token management.

mapper: Object mapping via MapStruct.

model: Core entities & enums.

pagination: Pageable response utilities.

repository: Data access layer.

security: Authentication logic & filters.

service: Business logic implementations.

db.migration: SQL versioning with Flyway.


### 🧠 Architectural Focus
This project emphasizes:

DTO-based API responses: Decoupling database from the client.

MapStruct: Clean and fast mapping layer.

Global Exception Handling: Standardized error management.

Custom Pageable: Consistent response structure across the app.

Domain Modeling: Using enums for order status and roles.

Data Integrity: Enforcing constraints at the database level.

Persistence: Refresh token storage and lifecycle management.

## 🗄 Entity Model
### Core Entities
-User, RefreshToken, Restaurant, Category, Product, Cart, CartItem, Order, OrderItem, Rating, OwnerRequest.

### Key Relationships
-Restaurant → Category: OneToMany.

-Category → Product: OneToMany.

-User → Cart: OneToOne.

-Cart → CartItem: OneToMany.

-Order → OrderItem: OneToMany.

-User → Order: OneToMany.


## 🛠 Tech Stack

| Technology        | Usage                 |
| ----------------- | --------------------- |
| Java 17           | Backend language      |
| Spring Boot 3     | Framework             |
| Spring Security   | Authentication        |
| JWT               | Token-based auth      |
| Spring Data JPA   | ORM                   |
| PostgreSQL        | Database              |
| Flyway            | DB Migration          |
| Lombok            | Boilerplate reduction |
| MapStruct         | DTO mapping           |
| Swagger/OpenAPI   | API documentation     |
| Log4j2            | Logging               |
| Maven             | Build tool            |


---

## ▶️ Running the Project
### 1️⃣ Clone Repository
-git clone https://github.com/yusufguc/SpringFoodOrderingApi

-cd SpringFoodOrderingApi
### 2️⃣ Configure Database
-Update:
   application.properties or
   application-local.properties with your PostgreSQL credentials.

### 3️⃣ Run Flyway Migration
-Runs automatically when the application starts.

### 4️⃣ Start Project
mvn spring-boot:run

---

## 📘 API Documentation
Swagger UI: 
http://localhost:8080/swagger-ui/index.html

Token Endpoint: /auth/authenticate

Authorize: 
Use the JWT token in Swagger's "Authorize" button.

## 🔐 Authentication Flow
Register: /auth/register

Login: /auth/authenticate

Tokens: Receive access token + refresh token.

Security: Use access token for secured endpoints.

Renewal: Refresh token when access token expires.


## 📌 Example Endpoints
### 🔐 Authentication
- POST /auth/register

- POST /auth/authenticate

- POST /auth/refresh-token

### 🏪 Restaurant
- POST /api/v1/restaurants

- PUT /api/v1/restaurants/id/{restaurantId}

- PUT /api/v1/restaurants/{restaurantId}/toggle-status

- GET /api/v1/restaurants/search

- GET /api/v1/restaurants

- GET /api/v1/restaurants/my-restaurants

- GET /api/v1/restaurants/filter

- DELETE /api/v1/restaurants/id/{restaurantId}

### 🗂 Product

- GET /api/v1/products/{productId}

- PUT /api/v1/products/{productId}

- DELETE /api/v1/products/{productId}

- PUT /api/v1/products/{productId}/update-stock

- PUT /api/v1/products/{productId}/toggle-status

- GET /api/v1/category/{categoryId}/products

- POST /api/v1/categories/{categoryId}/products

### 👑 owner-request
- PUT /api/v1/owner-requests/reject/{requestId}

- PUT /api/v1/owner-requests/approve/{requestId}

- POST /api/v1/owner-requests

### 📦 Order

- PUT /api/v1/orders/{orderId}/status

- PUT /api/v1/orders/{orderId}/cancel

- POST /api/v1/orders/create

- GET /api/v1/orders

- GET /api/v1/orders/{orderId}

- GET /api/v1/orders/restaurant/{restaurantId}

###  Category

- GET /api/v1/categories/{categoryId}

- PUT /api/v1/categories/{categoryId}

- DELETE /api/v1/categories/{categoryId}

- GET /api/v1/restaurants/{restaurantId}/categories

- POST /api/v1/restaurants/{restaurantId}/categories

- GET /api/v1/restaurants/{restaurantId}/my-categories

- GET /api/v1/restaurants/{restaurantId}/categories/search

### Rating

- POST /api/v1/ratings

- GET /api/v1/ratings/restaurant/{restaurantId}

- DELETE /api/v1/ratings/{ratingId}

### Cart

- POST /api/v1/carts/checkout

- POST /api/v1/carts/add

- GET /api/v1/carts/my-cart

- DELETE /api/v1/carts/remove/{productId}

- DELETE /api/v1/carts/clear


## 🎯 Purpose of This Project
Practice clean backend architecture.

Implement secure authentication systems.

Model real-world ordering logic.

Apply database migration strategies.

Build a portfolio-ready backend project.


👨‍💻 Author
Yusuf Guc -Backend Developer (Java & Spring Boot)

