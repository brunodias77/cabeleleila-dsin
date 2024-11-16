package com.brunodias.dsin_cabeleleila_server.useCases.services.getAllServices;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllServicesUseCase implements IGetAllServicesUseCase{
    private final ServiceRepository _serviceRepository;
    private static final String SUCCESS_MESSAGE = "A busca pelos serviços foi realizada com sucesso!";

    @Override
    public BaseResponseDTO execute() {
        try {
            var services = _serviceRepository.findAll();
            return BaseResponseDTO.builder()
                    .status(HttpStatus.OK.value())
                    .message(SUCCESS_MESSAGE)
                    .data(services)
                    .build();
        } catch (Exception e) {
            return BaseResponseDTO.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Erro ao buscar serviços: " + e.getMessage())
                    .build();
        }
    }
}
