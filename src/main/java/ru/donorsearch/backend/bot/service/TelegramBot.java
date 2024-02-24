package ru.donorsearch.backend.bot.service;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import ru.donorsearch.backend.bot.config.BotConfig;
import ru.donorsearch.backend.entity.DonationPlan;
import ru.donorsearch.backend.entity.User;
import ru.donorsearch.backend.repository.DonationPlanRepo;
import ru.donorsearch.backend.repository.UserRepo;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final Map<String, String> bloodTypes = Map.of(
            "blood", "цельной крови",
            "plasma", "плазмы",
            "platelets","тромбоцитов",
            "erythrocytes", "эритроцитов",
            "leukocytes", "лейкоцитов"
    );

    private final UserRepo userRepo;

    private final DonationPlanRepo donationPlanRepo;

    final BotConfig config;


    public TelegramBot(UserRepo userRepo, DonationPlanRepo donationPlanRepo, BotConfig config) {
        this.userRepo = userRepo;
        this.donationPlanRepo = donationPlanRepo;
        this.config = config;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChatId();
        Message message = update.getMessage();
        if (message.hasText()) {
            if (message.getText().equals("/notify")) {
                sendNotification();
                return;
            }
            sendMessage(chatId, message.getText());
        }
    }

    public void sendMessage(Long chatId, String msg) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(msg);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.getLocalizedMessage();
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void sendNotification() {
        LocalDate currentDate = LocalDate.now();

        String nextThreeDays = currentDate.plusDays(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String nextOneDay = currentDate.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<DonationPlan> donationPlansThreeDays = donationPlanRepo.findDonationPlansByPlanDate(nextThreeDays);
        List<DonationPlan> donationPlansOneDays = donationPlanRepo.findDonationPlansByPlanDate(nextOneDay);


        donationPlansThreeDays.forEach(donationPlan -> {

            User user = donationPlan.getUser();
            String workTime = donationPlan.getWorkTime() == null || Objects.equals(donationPlan.getWorkTime(), "") ? "" : "Адрес: " + donationPlan.getAddress() + '\n' + '\n';
            String event = bloodTypes.get(donationPlan.getBloodClass()) == null  ? "Приближающееся событие: " + "донорство" + '\n' + '\n' : "Приближающееся событие: " + "сдача" + " " + bloodTypes.get(donationPlan.getBloodClass()) + '\n' + '\n';
            String address = donationPlan.getAddress() == null  || Objects.equals(donationPlan.getAddress(), "") ? "" : "Адрес: " + donationPlan.getAddress() + '\n' + '\n';
            String phones = donationPlan.getWorkPhones() == null  || Objects.equals(donationPlan.getWorkPhones(), "") ? "" : "Контакты: " + donationPlan.getWorkPhones();
            String title = donationPlan.getTitle() == null  || Objects.equals(donationPlan.getTitle(), "") ? "" : "Центр крови: " + donationPlan.getTitle() + '\n' + '\n';

            String text =
                    event
                            + "Дата: " + donationPlan.getPlanDate() + '\n' + '\n'
                            + title
                            + address
                            + workTime
                            + phones
                    ;

            sendMessage(user.getChatId(), text);

        });

        donationPlansOneDays.forEach(donationPlan -> {

            User user = donationPlan.getUser();

            String workTime = donationPlan.getWorkTime() == null || Objects.equals(donationPlan.getWorkTime(), "") ? "" : "Расписание: " + donationPlan.getWorkTime() + '\n' + '\n';
            String event = bloodTypes.get(donationPlan.getBloodClass()) == null  ? "Приближающееся событие: " + "донорство" + '\n' + '\n' : "Приближающееся событие: " + "сдача" + " " + bloodTypes.get(donationPlan.getBloodClass()) + '\n' + '\n';
            String address = donationPlan.getAddress() == null  || Objects.equals(donationPlan.getAddress(), "") ? "" : "Адрес: " + donationPlan.getAddress() + '\n' + '\n';
            String phones = donationPlan.getWorkPhones() == null  || Objects.equals(donationPlan.getWorkPhones(), "") ? "" : "Контакты: " + donationPlan.getWorkPhones();
            String title = donationPlan.getTitle() == null  || Objects.equals(donationPlan.getTitle(), "") ? "" : "Центр крови: " + donationPlan.getTitle() + '\n' + '\n';

            String text =
                            event
                            + "Дата: " + donationPlan.getPlanDate() + '\n' + '\n'
                            + title
                            + address
                            + workTime
                            + phones
                    ;

            sendMessage(user.getChatId(), text);

        });
    }
}
