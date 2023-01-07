package com.example.dians_demo.service.impl;

import com.example.dians_demo.model.Enum.LocationEnum;
import com.example.dians_demo.model.Location;
import com.example.dians_demo.repository.jpa.LocationRepository;
import com.example.dians_demo.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public void save(Location location) {

        this.locationRepository.save(location);
    }

    @Override
    public Optional<Location> findByName(String name) {
        return this.locationRepository.findByName(name);
    }

    @Override
    public List<Location> findAll() {
        return this.locationRepository.findAll();
    }

    @Override
    public Optional<Location> findById(Long id) {
        return this.locationRepository.findById(id);
    }

    @Override
    public Optional<Location> findByType(LocationEnum locationType) {
        return this.locationRepository.findByLocationType(locationType);
    }

    @Override
    public List<Location> searchKeyword(String keyword) {
        return this.locationRepository.findByNameIgnoreCaseContaining(keyword);
    }

}
