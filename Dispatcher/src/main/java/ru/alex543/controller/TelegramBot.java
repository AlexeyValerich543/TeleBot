package ru.alex543.controller;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Component
public class TelegramBot extends TelegramLongPollingBot {


    private static final long RECONNECT_PAUSE = 1000;

    @Override
    public String getBotUsername() {
        return "Alex543Bot";
    }

    @Override
    public String getBotToken() {
        return "6360698148:AAGCq7w4VXV9TZdHZUSBm7oIz9jzEhbEtBc";
    }

    @Override
    public void onUpdateReceived(Update update) {
//        System.out.println("update");
        var originalMessage = update.getMessage();
        System.out.println(originalMessage.getText());

    }

//    public void botConnect() {
//        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
//        try {
//            telegramBotsApi.registerBot(this);
////            log.info("TelegramAPI started. Look for messages");
//        } catch (TelegramApiException e) {
////            log.error("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
//            try {
//                Thread.sleep(RECONNECT_PAUSE);
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//                return;
//            }
//            botConnect();
//        }
//    }
}