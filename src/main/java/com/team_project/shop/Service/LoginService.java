package com.team_project.shop.Service;

import com.team_project.shop.Service.IFS.LoginService_IFS;
import com.team_project.shop.config.auth.dto.SessionPublisher;
import com.team_project.shop.config.auth.dto.SessionUser;
import com.team_project.shop.domain.Publisher.Publisher;
import com.team_project.shop.domain.user.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class LoginService implements LoginService_IFS {

    private final HttpSession httpSession;

    @Override
    public void login(Users user) {
        httpSession.setAttribute("user", new SessionUser(user));
    }

    @Override
    public void logout() {
        httpSession.removeAttribute("user");
    }

    @Override
    public void publisherLogin(Publisher publisher) {
        httpSession.setAttribute("publisher",new SessionPublisher(publisher));
    }

    @Override
    public void publisherLogout() {
        httpSession.removeAttribute("publisher");
    }


}
