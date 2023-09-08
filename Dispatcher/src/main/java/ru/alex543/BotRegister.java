package ru.alex543;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.alex543.controller.TelegramBot;


@Component
public class BotRegister {

        final
        TelegramBot telegramBot;

        public BotRegister(TelegramBot telegramBot) {
            this.telegramBot = telegramBot;
        }

        @PostConstruct
        public void init() throws TelegramApiException {

            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBot);

        }

}