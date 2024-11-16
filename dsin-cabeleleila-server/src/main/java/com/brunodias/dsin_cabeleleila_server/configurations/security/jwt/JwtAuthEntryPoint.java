package com.brunodias.dsin_cabeleleila_server.configurations.security.jwt;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Define o tipo de conteúdo da resposta como JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Define o status HTTP como 401 (Não Autorizado)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Cria um mapa para armazenar os detalhes da resposta
        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED); // Código de status HTTP
        body.put("error", "Unauthorized"); // Mensagem de erro genérica
        body.put("message", authException.getMessage()); // Mensagem de exceção específica
        body.put("path", request.getServletPath()); // Caminho da requisição

        // Converte o mapa para JSON e escreve no corpo da resposta
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);

        // Loga a mensagem de erro para ajudar na depuração
        logger.error("Unauthorized access: {}", authException.getMessage());
    }
}
