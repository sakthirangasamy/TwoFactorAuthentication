package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Location;
import com.example.demo.Repository.LocationRepository;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

  
    public Location findByName(String name) {
        return locationRepository.findByName(name);
    }
    public List<Location> findAllLocations() {
        return locationRepository.findAll(); // Fetch all locations from the repository
    }
}