package com.brunodias.dsin_cabeleleila_server.useCases.authentications.registerUser;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestRegisterUser;
import com.brunodias.dsin_cabeleleila_server.entities.Role;
import com.brunodias.dsin_cabeleleila_server.entities.User;
import com.brunodias.dsin_cabeleleila_server.exceptions.UserFoundException;
import com.brunodias.dsin_cabeleleila_server.mappers.EntityToDtoMapper;
import com.brunodias.dsin_cabeleleila_server.repositories.RoleRepository;
import com.brunodias.dsin_cabeleleila_server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase implements IRegisterUserUseCase {
        private final UserRepository _userRepository;
        private final PasswordEncoder passwordEncoder;
        private final RoleRepository _roleRepository;

        @Transactional
        @Override
        public BaseResponseDTO execute(RequestRegisterUser request) {

                this._userRepository.findByEmail(request.email()).ifPresent((user) -> {
                        throw new UserFoundException();
                });

                Role userRole = _roleRepository.findByName("ROLE_USER")
                        .orElseGet(() -> _roleRepository.save(Role.builder().name("ROLE_USER").build()));

                var user = User.builder()
                        .name(request.name())
                        .email(request.email())
                        .password(passwordEncoder.encode(request.password()))
                        .phoneNumber(request.phoneNumber())
                        .roles(new HashSet<>())
                        .build();

                user.getRoles().add(userRole);

                var savedUser = _userRepository.save(user);

                var userDto = EntityToDtoMapper.mapUserToDtoBasic(savedUser);

                return BaseResponseDTO.builder()
                        .status(201)
                        .message("Usuário adicionado com sucesso")
                        .data(userDto)
                        .build();
        }
}
