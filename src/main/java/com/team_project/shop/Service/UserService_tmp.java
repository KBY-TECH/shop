package com.team_project.shop.Service;

import com.team_project.shop.domain.user.Users;
import com.team_project.shop.domain.user.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService_tmp {
    private final UsersRepository usersRepository;

    public Users findById(Long id) {
        return usersRepository.findById(id).get();
    }
}
