package com.vgreview.user;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;
    AuthorityRepository authorityRepository;

    UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository){
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void setUser(String username, String password, String email){
        User user = new User();
        user.setUsername(username);
        password = password.replaceAll("slash", "/");
        user.setPassword(password);
        user.setEmail(email);
        Optional<Authority> authority = authorityRepository.findAuthorityById(2); //user
        user.setCreationDate(LocalDate.now());
        userRepository.save(user);
        authority.get().getUsers().add(user);
        authorityRepository.save(authority.get());
    }
}
