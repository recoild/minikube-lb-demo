package com.fisa.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class HelloWorldController {
    @GetMapping("/hello")
    public String helloWorld() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        return "Server IP Address: " + inetAddress.getHostAddress();
    }
}
