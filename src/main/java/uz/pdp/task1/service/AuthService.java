package uz.pdp.task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> userList=new ArrayList<>(
                Arrays.asList(
                        new User("pdp", passwordEncoder.encode("pdpUz"), new ArrayList<>()),
                        new User("oop", passwordEncoder.encode("oopUz"), new ArrayList<>()),
                        new User("jwt", passwordEncoder.encode("jwtUz"), new ArrayList<>())
                )
        );
        for (User user : userList) {
            if (username.equals(user.getUsername())){
                return user;
            }
        }
        throw new UsernameNotFoundException("User not found");
    }
}
