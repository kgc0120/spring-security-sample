package com.sample.security.form;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * @author bumblebee
 */
@Controller
public class SampleController {


    @GetMapping("/")
    public String index(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("message","Hello Security");
        }else {
            model.addAttribute("message", "Hello" + principal.getName());
        }

        return "index";
    }

    @GetMapping("/info")
    public String info(Model model, Principal principal) {
        model.addAttribute("message", "Info, ");
        return "info";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("message", "dashboard, "+ principal.getName());
        return "dashboard";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("message", "Hello admin, "+ principal.getName());
        return "admin";
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        model.addAttribute("message", "Hello user, "+ principal.getName());
        return "user";
    }

}
