package com.team_project.shop.web;

import com.team_project.shop.Service.IFS.UsersService_IFS;
import com.team_project.shop.config.auth.LoginUser;
import com.team_project.shop.config.auth.dto.SessionUser;
import com.team_project.shop.domain.user.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/user_do")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UsersService_IFS usersService;

    @GetMapping("/{id}")
    public String userInfo(@PathVariable Long id, @LoginUser SessionUser user, Model model) {

        if (user.getId().equals(id)) {
            model.addAttribute("userInfo", user);
            Users byId = usersService.findById(id);
            if(byId.getPassword()!=null)
            {
                model.addAttribute("PossibleUpdatePassword",1);
            }
            return "/user/mypage";
        }
       return "redirect:/";
    }
}
