package edu.co.icesi.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/secure")
public class sampleController {

    @Get("/test")
    public String test(){
        return "se logro!";
    }

}
