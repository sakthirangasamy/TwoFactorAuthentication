package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByName(String name);
}