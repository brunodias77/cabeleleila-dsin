package com.brunodias.dsin_cabeleleila_server.mappers;

import com.brunodias.dsin_cabeleleila_server.dtos.UserDTO;
import com.brunodias.dsin_cabeleleila_server.entities.User;
import org.springframework.stereotype.Component;

@Component
public class EntityToDtoMapper {

    public static UserDTO mapUserToDtoBasic(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setName(user.getName());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        return userDto;

    }
}
