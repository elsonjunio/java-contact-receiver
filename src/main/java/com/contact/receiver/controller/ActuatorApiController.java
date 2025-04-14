package com.contact.receiver.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class ActuatorApiController {
    
    @GetMapping("live")    
    public String live(){
        return "success";
    }

}
