User Management System with OTP Verification
This project is a User Management System that allows users to register, verify their identity using OTP, and store their details in a database. The system sends an OTP to the user's mobile number and a registration confirmation email to their email address. It also captures the user's location and sends it via SMS.

Features
User Registration:

Users can register by providing their name, email, phone number, and location.

OTP is sent to the user's mobile number for verification.

Registration confirmation email is sent to the user's email address.

OTP Verification:

Users must enter the OTP received on their mobile number to complete the registration process.

Location Capture:

The system captures the user's location (latitude and longitude) during registration.

The location is sent via SMS to a predefined number.

User Details Storage:

All user details (name, email, phone number, location) are stored in a MySQL database.

Different User Details:

Only the location is sent via SMS for different users.

Technologies Used
Frontend: HTML, CSS, JavaScript

Backend: Spring Boot 4.0.1

Templating Engine: Thymeleaf

Database: MySQL

APIs: SMS Gateway (e.g., Twilio), Email Service (e.g., JavaMail)

Setup Instructions
Prerequisites
Java 17 or higher

MySQL Server

Maven

Twilio API credentials (for SMS)

Email service credentials (for sending emails)

Steps
Clone the Repository:

bash
Copy
git clone https://github.com/your-username/user-management-system.git
Configure the Database:

Create a MySQL database named user_management.

Update the application.properties file with your database credentials:

properties
Copy
spring.datasource.url=jdbc:mysql://localhost:3306/user_management
spring.datasource.username=your-username
spring.datasource.password=your-password
Configure SMS and Email Services:

Update the application.properties file with your Twilio and email service credentials:

properties
Copy
twilio.account.sid=your-twilio-sid
twilio.auth.token=your-twilio-token
twilio.phone.number=your-twilio-phone-number

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-email-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
Run the Application:

bash
Copy
mvn spring-boot:run
Access the Application:

Open your browser and go to http://localhost:8080.

Screenshots
Registration Page
User Registration Page

OTP Verification
OTP Verification Page

API Endpoints
POST /register: Register a new user.

POST /verify-otp: Verify the OTP.

GET /users: Retrieve all registered users.

Code Structure
Copy
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           ├── controller/
│   │           │   └── UserController.java
│   │           ├── model/
│   │           │   └── User.java
│   │           ├── repository/
│   │           │   └── UserRepository.java
│   │           ├── service/
│   │           │   └── UserService.java
│   │           └── UserManagementApplication.java
│   ├── resources/
│   │   ├── static/
│   │   │   ├── css/
│   │   │   │   └── styles.css
│   │   │   └── js/
│   │   │       └── script.js
│   │   ├── templates/
│   │   │   ├── register.html
│   │   │   └── verify-otp.html
│   │   └── application.properties
│   └── webapp/
└── test/
How It Works
User Registration:

The user fills out the registration form (name, email, phone number, location).

The system sends an OTP to the user's mobile number and a confirmation email to their email address.

OTP Verification:

The user enters the OTP received on their mobile number.

If the OTP is valid, the user is registered, and their details are stored in the database.

Location Capture:

The user's location (latitude and longitude) is captured using JavaScript's Geolocation API.

The location is sent via SMS to a predefined number.

Contributing
Contributions are welcome! If you'd like to contribute to this project, please follow these steps:

Fork the repository.

Create a new branch for your feature or bugfix.

Commit your changes.

Submit a pull request.

License
This project is licensed under the MIT License. See the LICENSE file for details.

Contact
If you have any questions or suggestions, feel free to reach out:

Name: Your Name

Email: your.email@example.com

GitHub: your-username

