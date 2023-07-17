package ru.saros.productservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.saros.productservice.models.ProductDto;

@Component
@EnableRabbit
@RequiredArgsConstructor
public class RabbitMQConsumer {

    private final ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = {"uploadProduct"})
    public void processMyQueue(String message) throws JsonProcessingException {
        System.out.printf("Processed from uploadQueue: %s", mapper.readValue(message, ProductDto.class).toString());
    }
}
