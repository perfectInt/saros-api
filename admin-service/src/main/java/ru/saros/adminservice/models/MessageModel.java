package ru.saros.adminservice.models;

import lombok.Data;

@Data
public class MessageModel {
    private String message;
    private String routingKey;
}
