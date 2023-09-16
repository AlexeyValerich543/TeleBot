package ru.alex543.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;
    private UpdateController updateController;
    public TelegramBot(UpdateController updateController){
        this.updateController=updateController;
    }
    public void init(){
        updateController.registerBot(this);
    }
    private static final Logger log = Logger.getLogger(TelegramBot.class);


    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
updateController.processUpdate(update);
    }

    public void sendAnswerMessage(SendMessage message){
        if (message!= null){
            try{
               execute(message);
            } catch (TelegramApiException e) {
                log.error(e);
            }
        }
    }

}