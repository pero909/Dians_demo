package com.example.dians_demo.Controllers;

import com.example.dians_demo.model.Enum.LocationEnum;
import com.example.dians_demo.model.Location;
import com.example.dians_demo.model.User;
import com.example.dians_demo.service.AuthenticationService;
import com.example.dians_demo.service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import org.h2.engine.Mode;
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
@RequestMapping({"/Navster","/"})
public class LocationController {

    private final LocationService locationService;
    private final AuthenticationService authenticationService;

    public LocationController(LocationService locationService, AuthenticationService authenticationService) {
        this.locationService = locationService;
        this.authenticationService = authenticationService;
    }
    @GetMapping()
    public String loginPage(Model model,HttpServletRequest request
            ,@RequestParam(required = false) String error_login){
        model.addAttribute("hasError_login",true);
        model.addAttribute("error_login",error_login);
        return "login";
    }

    @GetMapping("/Map")
    public String loadMapPage(Model model) throws IOException, ParseException {
        List<Location> locations= this.locationService.findAll();
        model.addAttribute("hotelList",locations);

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
    @PostMapping({"/login","/"})
    public String login(Model model, HttpServletRequest request){
        String username = request.getParameter("username");
        String password= request.getParameter("password");
        try {
            User user = this.authenticationService
                    .findByUsernameAndPassword(username, password).orElse(null);
            request.getSession().setAttribute("user",user);

            return "redirect:/Navster/Map";
        }catch (Exception e){
            return "redirect:/Navster/login?error_login=" + e.getMessage();

        }




    }
    @GetMapping("/signUp")
    public String signUpError(Model model,@RequestParam(required = false)String error,
                              HttpServletRequest request){
        model.addAttribute("hasError",true);
        model.addAttribute("error",error);

        return "login";

    }
    @PostMapping("/signUp")
    public String signup(Model model,HttpServletRequest request
            ,@RequestParam(required = false) String error){
        String username = request.getParameter("username_signUp");
        String password = request.getParameter("password_signUp");
        String repeatPassword=  request.getParameter("repeatPassword");
        try{
            this.authenticationService.signUp(username,password,repeatPassword);
            return "login";
        }catch (Exception e){
              return "redirect:/Navster/signUp?error=" + e.getMessage();
        }

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
    @GetMapping("/logout")
    public String logOut(Model model,HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/Navster";
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
