# **Spring Boot and Angular Application: Real-Time Ticket Management System**

This project is a full-stack application designed using **Spring Boot** for the backend and **Angular** for the frontend. It demonstrates a real-time ticket management system, including configuration settings, customer and vendor interactions, and live log monitoring using WebSockets.

---

## **Features**

### Backend (Spring Boot)
- Implements the **MVC (Model-View-Controller)** architecture.
- **Service and Controller interfaces** are used to separate business logic and API handling.
- Real-time **WebSocket** integration for broadcasting backend logs to the frontend.
- RESTful APIs for handling configurations, ticket management, and task execution.
- Custom `WebSocketAppender` for redirecting SLF4J logs to WebSocket clients.

### Frontend (Angular)
- Implements a modular **Component-Service** architecture.
- **Form-based configuration** submission with validation.
- Real-time **log display** using WebSocket integration.
- Responsive UI designed with Angular Material.

---

## **Getting Started**

### Prerequisites
- Java 17+
- Node.js and npm
- Angular CLI
- Maven

### Backend Setup
1. Clone the repository.
2. Navigate to the backend directory:
   ```bash
   cd backend
   ```
3. Build and run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
4. Access the backend at `http://localhost:8080`.

### Frontend Setup
1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the Angular application:
   ```bash
   ng serve
   ```
4. Access the frontend at `http://localhost:4200`.

---

## **Key Components**

### **Backend**
- **`ConfigurationController`**: Handles configuration-related API calls.
- **`LogService` and `LogServiceImpl`**: Sends backend logs to WebSocket clients.
- **`WebSocketAppender`**: Custom Logback appender for broadcasting SLF4J logs via WebSocket.
- **`CustomerTask` and `VendorTask`**: Runnable tasks for customer and vendor operations.
- **`WebSocketConfig`**: Configures WebSocket endpoints for real-time log broadcasting.

### **Frontend**
- **Configuration Component**: A form for submitting system configuration settings.
- **Log Display Component**: Displays real-time backend logs using WebSocket integration.
- **Services**: Handles API communication and manages WebSocket subscriptions.

---

## **Endpoints**

### Backend
| Endpoint                     | Method | Description                          |
|------------------------------|--------|--------------------------------------|
| `/api/configuration`         | POST   | Save system configuration settings. |
| `/logs/websocket`            | WS     | WebSocket endpoint for logs.        |

### Frontend Routes
| Route          | Description                           |
|-----------------|---------------------------------------|
| `/configuration`| Form to configure system settings.   |
| `/logs`         | Displays real-time backend logs.     |

---

## **License**
This project is licensed under the MIT License. See the `LICENSE` file for details.

---
