package com.team_project.shop.web;

import com.team_project.shop.config.auth.LoginUser;
import com.team_project.shop.config.auth.dto.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class indexController {

    @GetMapping("")
    public String index(Model model, @LoginUser SessionUser user)
    {
        if(user!=null)
        {
            model.addAttribute("loginUser",user.getName());
        }
        return "index";
    }

    @GetMapping("/signUpForm")
    public String signUp()
    {
        return "user/signUpForm";
    }


    @GetMapping("/loginForm")
    public String loginForm()
    {
        return "user/loginForm";
    }

}
