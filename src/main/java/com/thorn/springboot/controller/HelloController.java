package com.thorn.springboot.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;

@Controller
@MapperScan("com.thorn.springboot.dao")
public class HelloController {


    /*@GetMapping("/USER/{jsp}")
    public String user(@PathVariable(value = "jsp") String jsp) {
        return "/USER/" + jsp;
    }*/

    /*@GetMapping("/OA/{jsp}")
    public String sub(@PathVariable(value = "jsp") String jsp) {
        return "/OA/" + jsp;
    }


    @GetMapping("/BBS/{jsp}")
    public String BBS(@PathVariable(value = "jsp") String jsp) {
        return "/BBS/" + jsp;
    }*/
}
