package com.brunodias.dsin_cabeleleila_server.useCases.authentications.loginUser;

import com.brunodias.dsin_cabeleleila_server.configurations.security.users.ApplicationUserDetaillsImpl;
import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestLoginUser;
import com.brunodias.dsin_cabeleleila_server.repositories.UserRepository;
import com.brunodias.dsin_cabeleleila_server.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LoginUserUseCase implements ILoginUserUseCase{
    private final UserRepository _userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService _tokenService;
    private final AuthenticationManager _authenticationManager;
    @Override
    public BaseResponseDTO execute(RequestLoginUser request) {
        Authentication authentication = _authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.email(),
                        request.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = _tokenService.generateJwtTokenForUser(authentication);
        ApplicationUserDetaillsImpl userDetails = (ApplicationUserDetaillsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();

        return BaseResponseDTO.builder()
                .status(200)
                .message("Usuario logado com sucesso !")
                .jwt(jwt)
                .build();
    }
}
