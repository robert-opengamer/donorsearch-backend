package ru.donorsearch.backend.bot.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
public class BotConfig {

    @Value("${spring.data.bot.name}")
    String botName;

    @Value("${spring.data.bot.key}")
    String token;

}