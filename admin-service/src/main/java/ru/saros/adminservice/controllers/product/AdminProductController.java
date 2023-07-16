package ru.saros.adminservice.controllers.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.saros.adminservice.models.ProductDto;
import ru.saros.adminservice.producer.RabbitMQProducerService;

@RestController
@RequiredArgsConstructor
public class AdminProductController {

    private final RabbitMQProducerService producerService;

    @PostMapping("/upload")
    public void uploadProduct(@RequestBody ProductDto productDto) throws JsonProcessingException {
        producerService.uploadProduct(productDto);
    }
}
