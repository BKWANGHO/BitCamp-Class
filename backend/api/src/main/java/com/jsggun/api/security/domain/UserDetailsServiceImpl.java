package com.jsggun.api.security.domain;

import com.jsggun.api.user.model.UserModel;
import com.jsggun.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<UserModel> user = Optional.ofNullable(repository.findByUsername(username))
                .orElseThrow(()->new UsernameNotFoundException(username+"에 해당하는 객체가 존재하지 않습니다. "));

        return UserDetailsImpl.build(user.get());
    }
}
