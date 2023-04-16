package edu.co.icesi.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@Controller("/secure")
@Secured(SecurityRule.IS_ANONYMOUS)
public class sampleController {

    @Get("/test")
    public String test(){
        return "se logro!";
    }

}
