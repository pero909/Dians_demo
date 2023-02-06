package com.example.dians_demo.Bootsrap;

import com.example.dians_demo.model.Location;
import jakarta.annotation.PostConstruct;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Location> locationList;


    @PostConstruct
    void init() throws IOException, ParseException {
        locationList=new ArrayList<>();

    }
}
