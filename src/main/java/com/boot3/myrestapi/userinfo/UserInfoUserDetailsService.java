package com.boot3.myrestapi.userinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> optionalUserInfo = userInfoRepository.findByEmail(username);
        //optionalUserInfo.map(userInfo -> new UserInfoUserDetails(userInfo))
        return optionalUserInfo.map(UserInfoUserDetails::new) //Optional<UserInfoUserDetails>
                .orElseThrow(() -> new UsernameNotFoundException(username + " User Not Found"));
    }
}
