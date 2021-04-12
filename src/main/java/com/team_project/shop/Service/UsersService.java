package com.team_project.shop.Service;

import com.team_project.shop.Service.IFS.LoginService_IFS;
import com.team_project.shop.Service.IFS.UsersService_IFS;
import com.team_project.shop.domain.user.Users;
import com.team_project.shop.domain.user.UsersRepository;
import com.team_project.shop.network.request.UserRequestDto;
import com.team_project.shop.network.request.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.team_project.shop.network.response.HttpStatusResponseEntity.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class UsersService implements UsersService_IFS  {
    private final UsersRepository usersRepository;
    private final LoginService_IFS loginService_ifs;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> singUp(UserRequestDto requestDto) {

        if (!isDuplicateEmail(requestDto.getEmail())) {
            return RESPONSE_CONFLICT;
        }
        requestDto.encrypt(passwordEncoder);
        usersRepository.save(requestDto.toEntity());
        return RESPONSE_OK;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isDuplicateEmail(String email) {
        Optional<Users> user = usersRepository.findByEmail(email);
        return user.isEmpty();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isValidMember(UserRequestDto requestDto, PasswordEncoder passwordEncoder) {

        Optional<Users> users = usersRepository.findByEmail(requestDto.getEmail());
        if (users.isPresent()) {
            boolean flag = passwordEncoder.matches(requestDto.getPassword(), users.get().getPassword());
            log.debug(" isvalid {}",flag);
            if (flag)
                return true;
        }
        return false;
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updatePassword(UserUpdateDto updateDto) {
        UserRequestDto requestDto = UserRequestDto.of(updateDto.getEmail(), "", updateDto.getOrgPassword());
        if (isValidMember(requestDto, passwordEncoder)) {
            Users users = usersRepository.findByEmail(requestDto.getEmail()).get();
            Users updatedUser = users.updatePassword(passwordEncoder.encode(updateDto.getUpdatingPassword()));
            log.info("IS_EQUAL : {}", passwordEncoder.matches(updateDto.getUpdatingPassword(), updatedUser.getPassword()));
            return RESPONSE_OK;
        }
        return RESPONSE_UNAUTHORIZED;
    }

    /*@Override
    public ResponseEntity<HttpStatus> login(UserRequestDto user) {
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@ {}",isValidMember(user,passwordEncoder));
        if (isValidMember(user, passwordEncoder)) {
            Users users = usersRepository.findByEmail(user.getEmail()).get();
            if (passwordEncoder.matches(user.getPassword(), users.getPassword())) {
                loginService_ifs.login(users);
                return RESPONSE_OK;
            }
        }
        return RESPONSE_UNAUTHORIZED;
    }*/

    @Override
    public ResponseEntity<HttpStatus> logout() {
        loginService_ifs.logout();
        return RESPONSE_OK;
    }


    @Transactional(readOnly = true)
    public Users findById(Long id) {
        return usersRepository.findById(id).get();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("username = " + email);
        Optional<Users> user=usersRepository.findByEmail(email);
        if(user.isPresent())
        {
            loginService_ifs.login(user.get());
            log.info(user.get().getPassword());
            return user.get();
        }
        log.info(" this "+email+" not signUp email ,check you Email!");
        throw new UsernameNotFoundException(" this "+email+" not signUp email ,check you Email!");
    }
}
