package ru.saros.adminservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.saros.adminservice.models.MessageModel;
import ru.saros.adminservice.models.ProductDto;

@Service
@RequiredArgsConstructor
public class RabbitMQProducerService {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper mapper;

    public void uploadProduct(ProductDto productDto) throws JsonProcessingException {
        MessageModel messageModel = new MessageModel();
        messageModel.setMessage(mapper.writeValueAsString(productDto));
        messageModel.setRoutingKey("testRoutingKey");
        rabbitTemplate.convertAndSend("uploadProductExchange", messageModel.getRoutingKey(), messageModel.getMessage());
    }
}
