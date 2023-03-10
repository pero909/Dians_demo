package com.example.dians_demo.Controllers;

import com.example.dians_demo.model.Enum.LocationEnum;
import com.example.dians_demo.model.Location;
import com.example.dians_demo.service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping({"/"})
public class LocationController {

    private final LocationService locationService;


    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping({"/Map",""})
    public String loadMapPage(Model model) throws IOException, ParseException {
        List<Location> locations= this.locationService.findAll();
        model.addAttribute("hotelList",locations);
//4,7+13.449+
        return "proba";
    }

    @GetMapping("/Map/{id}")
    public String showLocationOnMap(Model model, @PathVariable Long id) throws IOException, ParseException {
        List<Location> locations= this.locationService.findAll();
        Location location=this.locationService.findById(id).orElse(null);
        model.addAttribute("latitude",location.getLatitude());
        model.addAttribute("longitude",location.getLongitude());
        model.addAttribute("locationName",location.getName());
        model.addAttribute("rating",location.getRating());
        model.addAttribute("hotelList",locations);

        return "proba";
    }

    @PostMapping
    public String getMapPage(@RequestParam(name = "hotel",required = false) Long id, Model model){
        List<Location> locations= this.locationService.findAll();
        model.addAttribute("hotelList",locations);
        Location location=this.locationService.findById(id).orElse(null);
        assert location != null;
        model.addAttribute("latitude",location.getLatitude());
        model.addAttribute("longitude",location.getLongitude());
        model.addAttribute("locationName",location.getName());
        model.addAttribute("rating",location.getRating());

        return "proba";
    }
    @PostMapping("/search")
    public String getMapPageFilter(@RequestParam String keyword, Model model){
      List<Location> locations = new ArrayList<>();

      if(keyword.equals("")){
          locations=this.locationService.findAll();
      }else locations=this.locationService.searchKeyword(keyword);
      model.addAttribute("hotelList",locations);
        return "proba";
    }


    @GetMapping("/table")
    public String allLocationsPage(Model model,HttpServletRequest request){
        model.addAttribute("locations",this.locationService.findAll());
        return "locationTable";
    }
    @GetMapping("/search")
    public String returnSearch(Model model,@RequestParam String keyword){
         List<Location> locations=this.locationService.searchKeyword(keyword);
         model.addAttribute("locations",locations);
         return "locationTable";
    }

    @GetMapping("/populate")
    public String populate() throws IOException, ParseException {
        JSONParser parser= new JSONParser();
        FileReader json_file = new FileReader("D:\\DIANS\\Dians_demo\\src\\main\\resources\\templates\\unfiltered(Google Places)\\restaurantsUnfiltered.json");
        Object location_obj=parser.parse(json_file);
        JSONObject location_JSON=(JSONObject) location_obj;
        JSONArray location_Array = (JSONArray) location_JSON.get("results");

        for(int i=0;i<location_Array.size();i++){

            JSONObject location= (JSONObject) location_Array.get(i);
            JSONObject geometry=(JSONObject) location.get("geometry");
            JSONObject geometry_values=(JSONObject) geometry.get("location") ;
            Double lat=(Double)geometry_values.get("lat");
            Double longitude=(Double) geometry_values.get("lng");

            String name=location.get("name").toString();
            Double rating;
            if(location.get("rating") instanceof Long) {
                Long num = (Long) location.get("rating");
                rating= num.doubleValue();
            }else rating=(Double) location.get("rating");


            LocationEnum locationEnum=LocationEnum.RESTAURANT;
            Location location_to_Save=new Location(longitude,lat,name,locationEnum,rating);
            this.locationService.save(location_to_Save);
            System.out.println(rating);
        }
        return "proba";
    }
}
