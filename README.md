# **TixSimulate: Real-Time Ticket Simulation System**

TixSimulate is a full-stack application built with **Spring Boot** (backend) and **Angular** (frontend) to simulate a real-time ticketing system. It allows configuration of ticket pools, vendors, and customers, while utilizing **WebSockets** to stream real-time logs.

---

## **Features**

### Backend (Spring Boot)
- **MVC Architecture**: Separates concerns between controllers, services, and models.
- **Service and Controller Interfaces**: Ensures modularity and scalability.
- **WebSocket-Based Real-Time Logs**: Uses a custom `WebSocketAppender` to stream logs.
- **RESTful APIs**: Supports ticket pool management, customer/vendor operations, and task execution.
- **Exception Handling**: Error management across endpoints.

### Frontend (Angular)
- **Component-Service Architecture**: Clear separation between UI and logic.
- **Configuration Form**: Allows users to set system parameters.
- **Real-Time Log Display**: Uses WebSockets to fetch backend logs.
- **Start/Stop Simulation Button**: Controls ticketing simulation execution.
- **Automatic Ticket Count Updates**: Fetches ticket availability dynamically.

---

## **Getting Started**

### Prerequisites
- Java 17+
- Node.js and npm
- Angular CLI
- Maven

### Backend Setup
1. Clone the repository.
   ```bash
   git clone https://github.com/NithikaNB/TixSimulate.git
   cd backend
   ```
2. Build and run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
3. Access the backend at `http://localhost:8080`.

### Frontend Setup
1. Navigate to the frontend directory:
   ```bash
   cd frontend/ticketingsystemapp
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
- **`ConfigurationControllerImpl`**: Handles configuration-related API calls.
- **`LogServiceImpl`**: Sends backend logs to WebSocket clients.
- **`WebSocketAppender`**: Captures logs and streams them via WebSocket.
- **`CustomerTask` & `VendorTask`**: Runnable tasks for ticket purchasing.
- **`TicketPoolControllerImpl`**: Manages ticket pool creation and updates.

### **Frontend**
- **Configuration Component**: A form for submitting system settings.
- **Log Display Component**: Displays real-time logs from the backend.
- **Services**: Handle API communication and WebSocket subscriptions.
- **Start/Stop Button**: Controls the simulation task execution.

---

## **Endpoints**

### Backend APIs
| Endpoint                            | Method | Description                                  |
|-------------------------------------|--------|----------------------------------------------|
| `/api/configuration/create-config`  | POST   | Creates a new system configuration.         |
| `/api/configuration/run-task`       | POST   | Starts or stops the simulation.             |
| `/api/customers`                    | POST   | Creates a new customer.                     |
| `/api/vendors`                      | POST   | Creates a new vendor.                       |
| `/api/ticket-pools/create`          | POST   | Creates a ticket pool.                      |
| `/api/ticket-pools/{id}/add-ticket` | POST   | Adds tickets to a specific pool.            |
| `/api/ticket-pools/{id}/remove-ticket` | POST   | Removes tickets from a pool.              |
| `/api/ticket-pools/availabletickets/{name}` | GET | Retrieves available ticket count.        |
| `/logs/websocket`                    | WS     | WebSocket endpoint for real-time logs.      |

### Frontend Routes
| Route          | Description                           |
|---------------|---------------------------------------|
| `/configuration` | Configuration form for system settings. |
| `/logs`         | Displays real-time backend logs.     |

---

## **License**
This project is licensed under the MIT License. See the `LICENSE` file for details.

---

## **Contributors**
- [Nithika Bandara](https://github.com/NithikaNB)

---
