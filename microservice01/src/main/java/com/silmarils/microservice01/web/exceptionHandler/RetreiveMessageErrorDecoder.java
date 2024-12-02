package com.silmarils.microservice01.web.exceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silmarils.microservice01.exceptions.BadRequestException;
import com.silmarils.microservice01.exceptions.EntityNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class RetreiveMessageErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        ErrorMessage message = null;
        log.error("method key: " +methodKey);

        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, ErrorMessage.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }


        switch (response.status()) {
            case 400:
                return new BadRequestException(message.getMessage());
            case 404:
                return new EntityNotFoundException(message.getMessage() != null ? message.getMessage() : "Not found");
            default:
                return errorDecoder.decode(methodKey, response);
        }

    }

}
