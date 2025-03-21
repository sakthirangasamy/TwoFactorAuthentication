package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.Location;
import com.example.demo.Entity.User;
import com.example.demo.Service.LocationService;
import com.example.demo.Service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    /**
     * Displays the registration form.
     *
     * @param model The model to pass data to the view.
     * @return The registration page.
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Fetch all locations from the database
        List<Location> locations = locationService.findAllLocations();

        // Add the locations list to the model
        model.addAttribute("locations", locations);

        // Add an empty User object to the model for the form
        model.addAttribute("user", new User());

        return "register"; // Return the registration page
    }

    /**
     * Fetches the coordinates for the selected location.
     *
     * @param locationName The name of the selected location.
     * @param model        The model to pass data to the view.
     * @return The registration page with updated coordinates.
     */
    @PostMapping("/fetchCoordinates")
    public String fetchCoordinates(
            @RequestParam("location") String locationName,
            Model model) {

        // Fetch the selected location's coordinates
        Location location = locationService.findByName(locationName);
        if (location != null) {
            model.addAttribute("latitude", location.getLatitude());
            model.addAttribute("longitude", location.getLongitude());
            model.addAttribute("location", location.getName()); // Add location name to the model
        } else {
            model.addAttribute("error", "Location not found!");
        }

        // Add the locations list back to the model for the form
        List<Location> locations = locationService.findAllLocations();
        model.addAttribute("locations", locations);

        // Add an empty User object to the model for the form
        model.addAttribute("user", new User());

        return "register"; // Return to the registration page
    }

    /**
     * Handles user registration.
     *
     * @param user         The user object containing registration details.
     * @param locationName The name of the selected location.
     * @param latitude     The latitude of the selected location.
     * @param longitude    The longitude of the selected location.
     * @param model        The model to pass data to the view.
     * @return The registration page with success or error messages.
     */
    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("user") User user,
            @RequestParam("location") String locationName,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            Model model) {

        // Set the location name in the User object
        user.setLocation(locationName);

        // Set the latitude and longitude in the User object
        user.setLatitude(latitude);
        user.setLongitude(longitude);

        // Check if the username already exists
        if (userService.usernameExists(user.getUsername())) {
            model.addAttribute("usernameExists", true); // Add error message for existing username
            model.addAttribute("locations", locationService.findAllLocations()); // Add locations list back to the model
            model.addAttribute("user", user); // Add the user object back to the model
            return "register"; // Return to registration page with error message
        }

        // If the username doesn't exist, save the user
        userService.registerUser(user);
        model.addAttribute("registrationSuccess", true); // Add success message

        // Add the locations list back to the model for the form
        model.addAttribute("locations", locationService.findAllLocations());

        return "register"; // Return to the registration page
    }
}