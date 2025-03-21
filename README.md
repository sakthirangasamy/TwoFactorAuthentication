User Management System with OTP Verification
This project is a User Management System that allows users to register, verify their identity using OTP, and store their details in a database. The system sends an OTP to the user's mobile number and a registration confirmation email to their email address. It also captures the user's location and sends it via SMS.
________________________________________
Key Features
1.	User Registration:
o	Users can register by providing their name, email, phone number, and location.
o	OTP is sent to the user's mobile number for verification.
o	Registration confirmation email is sent to the user's email address.
2.	OTP Verification:
o	Users must enter the OTP received on their mobile number to complete the registration process.
3.	Location Capture:
o	The system captures the user's location (latitude and longitude) during registration.
o	The location is sent via SMS to a predefined number.
4.	User Details Storage:
o	All user details (name, email, phone number, location) are stored in a MySQL database.
5.	Different User Details:
o	Only the location is sent via SMS for different users.
________________________________________
Technologies Used
•	Frontend: HTML, CSS, JavaScript
•	Backend: Spring Boot 4.0.1
•	Templating Engine: Thymeleaf
•	Database: MySQL
•	APIs: SMS Gateway (e.g., Twilio), Email Service (e.g., JavaMail)
________________________________________
Setup Instructions
Prerequisites
•	Java 17 or higher
•	MySQL Server
•	Maven
•	Twilio API credentials (for SMS)
•	Email service credentials (for sending emails)


Steps
1.	Clone the Repository:
bash
Copy
git clone https://github.com/your-username/user-management-system.git
2.	Configure the Database:
o	Create a MySQL database named user_management.
o	Update the application.properties file with your database credentials:
properties
Copy
spring.datasource.url=jdbc:mysql://localhost:3306/user_management
spring.datasource.username=your-username
spring.datasource.password=your-password
3.	Configure SMS and Email Services:
o	Update the application.properties file with your Twilio and email service credentials:
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
4.	Run the Application:
bash
Copy
mvn spring-boot:run
5.	Access the Application:
o	Open your browser and go to http://localhost:8080.
________________________________________
Screenshots
 
User Registration Page
 
OTP Verification Page
________________________________________
API Endpoints
•	POST /register: Register a new user.
•	POST /verify-otp: Verify the OTP.
•	GET /users: Retrieve all registered users.
________________________________________
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
________________________________________
How It Works
1.	User Registration:
o	The user fills out the registration form (name, email, phone number, location).
o	The system sends an OTP to the user's mobile number and a confirmation email to their email address.
2.	OTP Verification:
o	The user enters the OTP received on their mobile number.
o	If the OTP is valid, the user is registered, and their details are stored in the database.
3.	Location Capture:
o	The user's location (latitude and longitude) is captured using JavaScript's Geolocation API.
o	The location is sent via SMS to a predefined number.
________________________________________
Contributing
Contributions are welcome! If you'd like to contribute to this project, please follow these steps:
1.	Fork the repository.
2.	Create a new branch for your feature or bugfix.
3.	Commit your changes.
4.	Submit a pull request.

