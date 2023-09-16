package ru.alex543.controller;


import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.alex543.service.UpdateProducer;
import ru.alex543.utils.MessageUtils;

import static ru.alex543.model.RabbitQueue.*;

@Component
@Log4j
public class UpdateController {
    private TelegramBot telegramBot;
    private final MessageUtils messageUtils;
    private final UpdateProducer updateProducer;

    public UpdateController(MessageUtils messageUtils, UpdateProducer updateProducer) {
        this.messageUtils = messageUtils;
        this.updateProducer = updateProducer;
    }

    public void registerBot(TelegramBot telegramBot){
        this.telegramBot=telegramBot;
    }

    public void processUpdate(Update update){
        if (update == null) {
            log.error("Received update is null");
            return;
        }
        if (update != null) {
            distributeMessage(update);

        }else {
            log.error("Received unsupported Message" + update);
        }

    }

    private void distributeMessage(Update update) {
        var message = update.getMessage();
        if (message.getText() != null) {
            processTextMessage(update);
        }else if (message.getDocument()!= null){
            processDocMessage(update);
        }else if (message.getPhoto()!= null){
            processPhotoMessage(update);
        }else {
            setUnsupportedMessageTypeView(update);
        }
    }

    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,"Неподдерживаемые типы сообщения");
        setView(sendMessage);
    }
    private void setFileIsReceivView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,"Файл получен! идет обработка...");
        setView(sendMessage);
    }
    private void setView(SendMessage sendMessage) {
     telegramBot.sendAnswerMessage(sendMessage);
    }

    private void processPhotoMessage(Update update) {
        updateProducer.produce(PHOTO_MESSAGE_UPDATE, update);
        setFileIsReceivView(update);
    }



    private void processDocMessage(Update update) {
        updateProducer.produce(DOC_MESSAGE_UPDATE, update);
    }

    private void processTextMessage(Update update) {
        updateProducer.produce(TEXT_MESSAGE_UPDATE, update);
    }
}
