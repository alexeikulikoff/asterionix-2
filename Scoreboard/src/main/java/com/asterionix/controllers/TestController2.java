package com.asterionix.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TestController2 {
	
	 @RequestMapping("/knock")
	 public String home() {
		 System.out.println("test2 knock");
	        return "knock";
	 }
}
