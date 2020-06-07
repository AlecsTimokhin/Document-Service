package com.myservice.mainpack.service;

import com.myservice.mainpack.model.User;
import com.myservice.mainpack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService implements UserDetailsService {

    @Value("${USER_NOT_FOUND}")
    private String USER_NOT_FOUND;

    private UserRepository userRepository;
    public UserRepository getUserRepository() { return userRepository; }
    @Autowired
    public void setUserRepository(UserRepository userRepository) { this.userRepository = userRepository; }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user;

        try {
            user = userRepository.findByUsername(username);

            if( user == null ){
                throw new UsernameNotFoundException(USER_NOT_FOUND);
            }

            return user;
        }
        catch(Exception ex){
            return null;
        }


    }

}
