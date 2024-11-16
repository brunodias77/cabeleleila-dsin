package com.brunodias.dsin_cabeleleila_server.useCases.users.getUserByToken;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.mappers.EntityToDtoMapper;
import com.brunodias.dsin_cabeleleila_server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserByTokenUseCase implements IGetUserByTokenUseCase{
    private final UserRepository _userRepository;
    @Override
    public BaseResponseDTO execute(UUID request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        var user = _userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario nao foi encontrado !"));
        var userDTO = EntityToDtoMapper.mapUserToDtoBasic(user);
        return BaseResponseDTO.builder()
                .status(200)
                .message("Usuario encontrado com sucesso !").data(userDTO).build();
    }
}
