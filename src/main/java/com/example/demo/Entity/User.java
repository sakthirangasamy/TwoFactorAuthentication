package com.example.demo.Entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(name = "password", nullable = false)  // Store plain password
    private String password;

    @Column(name = "password_argon2", nullable = false)
    private String passwordArgon2;  // Store Argon2 hashed password

    @Column(name = "password_scrypt", nullable = false)
    private String passwordScrypt;  // Store scrypt hashed password

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Double latitude;  // Latitude for user's location

    @Column(nullable = false)
    private Double longitude;  // Longitude for user's location

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordArgon2() {
        return passwordArgon2;
    }

    public void setPasswordArgon2(String passwordArgon2) {
        this.passwordArgon2 = passwordArgon2;
    }

    public String getPasswordScrypt() {
        return passwordScrypt;
    }

    public void setPasswordScrypt(String passwordScrypt) {
        this.passwordScrypt = passwordScrypt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}