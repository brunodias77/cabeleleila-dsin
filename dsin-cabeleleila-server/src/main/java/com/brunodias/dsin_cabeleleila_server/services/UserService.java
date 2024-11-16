package com.brunodias.dsin_cabeleleila_server.services;



import com.brunodias.dsin_cabeleleila_server.entities.User;
import com.brunodias.dsin_cabeleleila_server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository _userRepository;

    public User getLoginUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return _userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario nao foi encontrado !"));
    }
}