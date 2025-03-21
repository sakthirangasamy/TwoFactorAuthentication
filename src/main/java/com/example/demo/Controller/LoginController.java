package com.example.demo.Controller;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.User;
import com.example.demo.Service.LoginService;
import com.example.demo.Service.TwilioService;
import com.example.demo.Service.UserService;
import com.example.demo.Service.EmailService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TwilioService twilioService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    // OTP Storage (for simplicity, storing in session. In production, use database or cache)
    private String generatedOtp;

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Render login.html
    }

    @PostMapping("/login")
    public String login(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam(required = false) Double latitude,
        @RequestParam(required = false) Double longitude,
        Model model,
        HttpSession session,
        HttpServletRequest request
    ) {
        // Fetch user by username
        Optional<User> user = loginService.findByUsername(username);
        System.out.println("User: " + user);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            // User is authenticated, set session attribute
            session.setAttribute("username", user.get().getUsername());
            System.out.println("User Logged In: " + user.get().getUsername());

            // Prepare SMS message
            String smsMessage = "Name: " + user.get().getUsername() +
                                "\nEmail: " + user.get().getEmail() +
                                "\nContact: " + user.get().getContact();

            // Prepare email message
            String emailMessage = "Login Details for " + user.get().getUsername() + ":\n" +
                                  "Email: " + user.get().getEmail() +
                                  "\nContact: " + user.get().getContact();

         // Add location details if available
            if (latitude != null && longitude != null) {
                // Generate Google Maps URL for the login location
                String googleMapsUrl = "https://www.google.com/maps?q=" + latitude + "," + longitude;
                smsMessage += "\nLatitude: " + latitude + "\nLongitude: " + longitude + "\nGoogle Maps URL: " + googleMapsUrl;
                emailMessage += "\nLatitude: " + latitude + "\nLongitude: " + longitude + "\nGoogle Maps URL: " + googleMapsUrl;

                // Compare login location with registration location
                Double registrationLatitude = user.get().getLatitude();
                Double registrationLongitude = user.get().getLongitude();

                if (registrationLatitude != null && registrationLongitude != null) {
                    boolean isSameLocation = isSameLocation(latitude, longitude, registrationLatitude, registrationLongitude);
                    System.out.println("Same Location: " + isSameLocation);

                    if (isSameLocation) {
                        // Generate and send OTP if on the same location
                        String otp = generateOtp(); // Call the generateOtp() method
                        smsMessage += "\nOTP: " + otp;
                        emailMessage += "\nOTP: " + otp;
                        session.setAttribute("generatedOtp", otp); // Store OTP in session
                        System.out.println("OTP generated: " + otp);
                    } else {
                        // If location is different, send the login location URL and a warning message
                        smsMessage += "\nWarning: The login location does not match the registered location.";
                        emailMessage += "\nWarning: The login location does not match the registered location.";

                        // Generate Google Maps URL for the registered location
                        String registeredLocationUrl = "https://www.google.com/maps?q=" + registrationLatitude + "," + registrationLongitude;
                        smsMessage += "\nRegistered Location URL: " + registeredLocationUrl;
                        emailMessage += "\nRegistered Location URL: " + registeredLocationUrl;
                    }
                }
            } else {
                smsMessage += "\nLocation: Unable to retrieve location.";
                emailMessage += "\nLocation: Unable to retrieve location.";
            }

            // Send SMS to the user's phone number
            String toPhoneNumber = user.get().getContact();
//            String toPhoneNumber = "+91" + toPhoneNumber1; // Use the contact number from the user entity
            if (toPhoneNumber != null && !toPhoneNumber.isEmpty()) {
                try {
                    twilioService.sendSms(toPhoneNumber, smsMessage);
                    System.out.println("SMS sent to: " + toPhoneNumber);
                } catch (Exception e) {
                    System.err.println("Failed to send SMS: " + e.getMessage());
                    e.printStackTrace();
                    model.addAttribute("loginError", true);
                    model.addAttribute("errorMessage", "Failed to send SMS. Please try again later.");
                    return "login";
                }
            } else {
                System.err.println("User phone number is missing or invalid.");
                model.addAttribute("loginError", true);
                model.addAttribute("errorMessage", "User phone number is missing or invalid.");
                return "login";
            }

            // Send email to the user
            String toEmail = user.get().getEmail();
            if (toEmail != null && !toEmail.isEmpty()) {
                try {
                    emailService.sendEmail(toEmail, "Login Attempt Details", emailMessage);
                    System.out.println("Email sent to: " + toEmail);
                } catch (Exception e) {
                    System.err.println("Failed to send email: " + e.getMessage());
                    e.printStackTrace();
                    model.addAttribute("loginError", true);
                    model.addAttribute("errorMessage", "Failed to send email. Please try again later.");
                    return "login";
                }
            } else {
                System.err.println("User email is missing or invalid.");
                model.addAttribute("loginError", true);
                model.addAttribute("errorMessage", "User email is missing or invalid.");
                return "login";
            }

            model.addAttribute("loginSuccess", true); // Add success flag for SweetAlert
            model.addAttribute("successMessage", "Login Successful! Welcome, " + user.get().getUsername()); // Success message
            return "userhome"; // Redirect to user home page
        }

        model.addAttribute("loginError", true); // Add error flag for SweetAlert
        model.addAttribute("errorMessage", "Invalid credentials"); // Error message
        return "login"; // Return to login page on failure
    }

    // Method to generate a 6-digit OTP
    private String generateOtp() {
        int otp = (int) (Math.random() * 900000) + 100000; // Generates a number between 100000 and 999999
        return String.valueOf(otp);
    }

    // Method to check if the login location matches the registered location
    private boolean isSameLocation(Double currentLatitude, Double currentLongitude, Double registrationLatitude, Double registrationLongitude) {
        // For simplicity, you can just compare the latitudes and longitudes directly
        return currentLatitude.equals(registrationLatitude) && currentLongitude.equals(registrationLongitude);
        // If needed, implement more sophisticated location comparison (e.g., Haversine formula for distance)
    }

    @GetMapping("/userhome")
    public String userHome(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        System.out.println("User Home: " + username);
        if (username != null) {
            model.addAttribute("username", username);
            return "userhome"; // Render userhome.html
        }
        return "redirect:/login"; // Redirect to login if session is not valid
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate the session
        return "redirect:/"; // Redirect to login page
    }

    @GetMapping("/myprofile")
    public String myProfile(HttpSession session, Model model) {
        // Retrieve the username from the session
        String username = (String) session.getAttribute("username");

        if (username == null) {
            // If the user is not logged in, redirect to the login page
            return "redirect:/login";
        }

        // Fetch the user details from the database
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            // Add user details to the model
            model.addAttribute("user", user.get());
            return "myprofile"; // Render myprofile.html
        } else {
            // If the user is not found, redirect to the login page
            return "redirect:/login";
        }
    }
    // Show the OTP verification page
  @GetMapping("/updateProfile")
  public String showUpdateProfilePage() {
      return "otpVerification";  // A page where the user enters OTP
  }

  @PostMapping("/verifyOtp")
  public String verifyOtpForProfileUpdate(@RequestParam String otp, HttpSession session, Model model) {
      String generatedOtp = (String) session.getAttribute("generatedOtp"); // Retrieve OTP from session

      if (otp.equals(generatedOtp)) {
          // OTP is correct, allow the user to update their profile
          session.setAttribute("otpVerified", true); // Mark OTP as verified
          session.removeAttribute("generatedOtp"); // Clear the OTP from session

          String username = (String) session.getAttribute("username");

          // Fetch user and add to the model
          Optional<User> user = userService.findByUsername(username);
          if (user.isPresent()) {
              model.addAttribute("user", user.get());
              model.addAttribute("successMessage", "OTP verified successfully!"); // Set success message
              return "updateProfileForm"; // Render the form to update the profile
          }

          // If the user is not found, set an error message
          model.addAttribute("error", "User not found");
          return "otpVerification"; // Return to OTP input page
      } else {
          // OTP is incorrect, set an error message
          model.addAttribute("error", "Invalid OTP. Please try again.");
          return "otpVerification"; // Return to OTP input page
      }
  }

    // Handle profile update submission
    @PostMapping("/submitProfileUpdate")
    public String submitProfileUpdate(
        @RequestParam String username,
        @RequestParam String email,
        @RequestParam String contact,
        HttpSession session,
        Model model
    ) {
        // Check if OTP has been verified
        Boolean otpVerified = (Boolean) session.getAttribute("otpVerified");
        if (otpVerified == null || !otpVerified) {
            model.addAttribute("error", "OTP verification required to update profile.");
            return "redirect:/myprofile"; // Redirect to profile page
        }

        String loggedInUsername = (String) session.getAttribute("username");
        Optional<User> user = userService.findByUsername(loggedInUsername);

        if (user.isPresent()) {
            User updatedUser = user.get();
            updatedUser.setUsername(username);
            updatedUser.setEmail(email);
            updatedUser.setContact(contact);

            // Save the updated user details
            userService.save(updatedUser);

            // Clear the OTP verification flag
            session.removeAttribute("otpVerified");

            // Add success message to the model
            model.addAttribute("successMessage", "Profile updated successfully!");
            return "userhome"; // Redirect to the user home page or show success message
        }

        model.addAttribute("error", "User not found");
        return "redirect:/login";
    }
}