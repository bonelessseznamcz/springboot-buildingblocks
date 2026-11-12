package com.flem.resthello.hello;

import com.flem.resthello.model.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorldController {

    //@RequestMapping(method= RequestMethod.GET, path="/hello")
    @GetMapping("/hello1")
    public String hello() {
        return "Hello cunt!";
    }

    @GetMapping("/user-details")
    public UserDetails getUser() {
        return new UserDetails("Lolo", "Koko", "Oklahoma");
    }
}
