package com.team_project.shop.Service.IFS;

import com.team_project.shop.domain.Publisher.Publisher;
import com.team_project.shop.domain.user.Users;

public interface LoginService_IFS {

    public void login(Users user);
    public void logout();

    public void publisherLogin(Publisher publisher);
    public void publisherLogout();

}
