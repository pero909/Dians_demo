package com.example.dians_demo.Bootsrap;

import com.example.dians_demo.model.Location;
import com.example.dians_demo.model.User;
import jakarta.annotation.PostConstruct;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Location> locationList;
    public static List<User> users;

    @PostConstruct
    void init() throws IOException, ParseException {
        locationList=new ArrayList<>();
        users=new ArrayList<>();
        users.add(new User("",""));
    }
}
