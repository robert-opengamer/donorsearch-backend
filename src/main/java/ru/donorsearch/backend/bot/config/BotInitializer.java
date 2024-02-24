package ru.donorsearch.backend.bot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.donorsearch.backend.bot.service.TelegramBot;

@Component
public class BotInitializer {

//    private final TelegramBot bot;
//
//    @Autowired
//    public BotInitializer(TelegramBot bot) {
//        this.bot = bot;
//    }
//
//    @EventListener({ContextRefreshedEvent.class})
//    public void init() throws TelegramApiException {
//        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//        try {
//            telegramBotsApi.registerBot(bot);
//        } catch (TelegramApiException e) {
//            System.out.println(e.getMessage());
//        }
//    }
}
