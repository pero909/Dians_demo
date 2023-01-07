package com.example.dians_demo.model;

import com.example.dians_demo.model.Enum.LocationEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Location {
    public Location(Double longitude, Double latitude,
                    String name,LocationEnum locationType,
                    Double rating) {
        Longitude = longitude;
        Latitude = latitude;
        this.name = name;
        this.locationType = locationType;
        this.rating = rating;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double Longitude;
    private Double Latitude;
    private String name;

    private LocationEnum locationType;
    private Double rating;



    public LocationEnum getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationEnum locationType) {
        this.locationType = locationType;
    }



    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Location() {

    }


    public Long getId() {
        return id;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
