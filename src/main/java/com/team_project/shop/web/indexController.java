package com.team_project.shop.web;

import com.team_project.shop.config.auth.LoginUser;
import com.team_project.shop.config.auth.dto.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
