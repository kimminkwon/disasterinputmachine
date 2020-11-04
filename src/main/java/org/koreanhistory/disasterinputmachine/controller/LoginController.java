package org.koreanhistory.disasterinputmachine.controller;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log
public class LoginController {
    @GetMapping("/")
    public String index() {
        return "redirect:/maintenance/list";
    }
    @GetMapping("/login")
    public void login() {
        log.info("IN LOGIN CONTROLLER: calling login()...");
    }
    @GetMapping("/accessDenied")
    public void accessDenied() {
        log.info("IN LOGIN CONTROLLER: calling accessDenied()...");
    }
    @GetMapping("/logout")
    public void logout() {
        log.info("IN LOGIN CONTROLLER: calling logout()...");
    }
}
