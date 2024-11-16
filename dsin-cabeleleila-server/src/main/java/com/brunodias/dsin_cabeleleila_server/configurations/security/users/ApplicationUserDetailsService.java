package com.brunodias.dsin_cabeleleila_server.configurations.security.users;

import com.brunodias.dsin_cabeleleila_server.entities.User;
import com.brunodias.dsin_cabeleleila_server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {
    private final UserRepository _userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = _userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario nao foi encontrado !"));

        return ApplicationUserDetaillsImpl.buildUserDetails(user);
    }
}
