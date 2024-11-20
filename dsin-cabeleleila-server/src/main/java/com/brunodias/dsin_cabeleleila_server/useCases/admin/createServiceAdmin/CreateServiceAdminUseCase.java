package com.brunodias.dsin_cabeleleila_server.useCases.admin.createServiceAdmin;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestCreateServiceAdmin;
import com.brunodias.dsin_cabeleleila_server.entities.Service;
import com.brunodias.dsin_cabeleleila_server.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class CreateServiceAdminUseCase implements ICreateServiceAdminUseCase {

    private final ServiceRepository serviceRepository;

    @Override
    @Transactional
    public BaseResponseDTO execute(RequestCreateServiceAdmin request) {
        // Validação do nome
        if (request.getName() == null || request.getName().isBlank()) {
            return BaseResponseDTO.builder()
                    .status(400)
                    .message("O nome do serviço é obrigatório.")
                    .build();
        }

        if (serviceRepository.existsByName(request.getName())) {
            return BaseResponseDTO.builder()
                    .status(409)
                    .message("Já existe um serviço com o nome informado.")
                    .build();
        }

        if (request.getPrice() <= 0) {
            return BaseResponseDTO.builder()
                    .status(400)
                    .message("O preço deve ser maior que zero.")
                    .build();
        }

        // Criar o novo serviço
        Service newService = Service.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

        serviceRepository.save(newService);

        return BaseResponseDTO.builder()
                .status(201)
                .message("Serviço criado com sucesso.")
                .data(newService)
                .build();
    }
}

