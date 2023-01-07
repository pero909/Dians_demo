package com.example.dians_demo.service;

import com.example.dians_demo.model.Enum.LocationEnum;
import com.example.dians_demo.model.Location;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface LocationService {
    void save(Location location);
    Optional<Location> findByName(String name);
    List<Location> findAll();
    Optional<Location> findById(Long id);
    Optional<Location> findByType(LocationEnum locationType);
    List<Location> searchKeyword(String keyword);
}
