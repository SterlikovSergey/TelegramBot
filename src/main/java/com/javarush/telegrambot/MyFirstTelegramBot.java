package com.javarush.telegrambot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Map;

public class MyFirstTelegramBot extends MultiSessionTelegramBot {
    public static final String NAME = "JavaRushMyExampleBot";
    public static final String TOKEN = "7036408066:AAFPHhM7inxZBd4ZJ7hix48ucca82VElpAA";

    public MyFirstTelegramBot() {
        super(NAME, TOKEN);
    }

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new MyFirstTelegramBot());
    }

    @Override
    public void onUpdateEventReceived(Update updateEvent) {
        if (updateEvent.hasMessage() && updateEvent.getMessage().hasText()) {
            String message = updateEvent.getMessage().getText();
            if ("/start".equals(message)) {
                User user = updateEvent.getMessage().getFrom();
                sendTextMessageAsync("Привет, " + user.getFirstName() + "!" );
                sendImageMessageAsync("/Users/sergeysterlikov/Developer/IdeaProjects/TelegramBot/src/main/resources/images/step_1_pic.jpg");
                sendTextMessageAsync(TelegramBotContent.STEP_1_TEXT, Map.of("Взлом холодильника", "fridge_hack"));
            } else if ("/glory".equals(message)) {
                int userGlory = getUserGlory();
                sendTextMessageAsync("У вас " + userGlory + " очков славы.");
            } else {
                sendTextMessageAsync("Лучше попрубуй /start !!!");
            }

        } else if (updateEvent.hasCallbackQuery()) {
            String buttonKey = updateEvent.getCallbackQuery().getData();
            int userGlory = getUserGlory();
            switch (buttonKey) {
                case "fridge_hack":
                    addUserGlory(20);
                    sendImageMessageAsync("/Users/sergeysterlikov/Developer/IdeaProjects/TelegramBot/src/main/resources/images/step_2_pic.jpg");
                    sendTextMessageAsync(TelegramBotContent.STEP_2_TEXT, Map.of(
                            "Взять сосиску!", "sausage",
                            "Взять рыбку!", "fish",
                            "Сбросить банку з огурцами!", "jar_of_pickles"
                    ));
                    break;
                case "sausage":
                case "fish":
                case "jar_of_pickles":
                    if (userGlory >= 20) {
                        addUserGlory(30);
                        sendImageMessageAsync("/Users/sergeysterlikov/Developer/IdeaProjects/TelegramBot/src/main/resources/images/step_3_pic.jpg");
                        sendTextMessageAsync(TelegramBotContent.STEP_3_TEXT, Map.of(
                                "Взлом робота пылесоса", "vacuum_hack"
                        ));
                    }
                    break;
                case "vacuum_hack":
                    if (userGlory >= 50) {
                        addUserGlory(40);
                        sendImageMessageAsync("/Users/sergeysterlikov/Developer/IdeaProjects/TelegramBot/src/main/resources/images/step_4_pic.jpg");
                        sendTextMessageAsync(TelegramBotContent.STEP_4_TEXT, Map.of(
                                "Отправить робопылесос за едой!", "send_vacuum",
                                "Проехаться на робопылесосе!", "ride_vacuum",
                                "Убегать от робопылесоса!", "escape_vacuum"
                        ));
                    }
                    break;
                case "send_vacuum":
                case "ride_vacuum":
                case "escape_vacuum":
                    if (userGlory >= 90) {
                        addUserGlory(40);
                        sendImageMessageAsync("/Users/sergeysterlikov/Developer/IdeaProjects/TelegramBot/src/main/resources/images/step_5_pic.jpg");
                        sendTextMessageAsync(TelegramBotContent.STEP_5_TEXT, Map.of(
                                "Надеть и включить GoPro!", "wear_gopro"
                        ));
                    }
                    break;
                case "wear_gopro":
                    if (userGlory >= 130) {
                        addUserGlory(40);
                        sendImageMessageAsync("/Users/sergeysterlikov/Developer/IdeaProjects/TelegramBot/src/main/resources/images/step_6_pic.jpg");
                        sendTextMessageAsync(TelegramBotContent.STEP_6_TEXT, Map.of(
                                "Бегать по крышам, снимать на GoPro!", "run_roofs",
                                "С GoPro нападать на других котов из засады!", "attack_cats",
                                "С GoPro нападать на собак из засады!", "attack_dogs"
                        ));
                    }
                    break;
                case "run_roofs":
                case "attack_cats":
                case "attack_dogs":
                    if (userGlory >= 170) {
                        addUserGlory(50);
                        sendImageMessageAsync("/Users/sergeysterlikov/Developer/IdeaProjects/TelegramBot/src/main/resources/images/step_7_pic.jpg");
                        sendTextMessageAsync(TelegramBotContent.STEP_7_TEXT, Map.of(
                                "Взлом пароля", "password_hack"
                        ));
                    }
                    break;
                case "password_hack":
                    if (userGlory >= 210) {
                        addUserGlory(50);
                        sendImageMessageAsync("/Users/sergeysterlikov/Developer/IdeaProjects/TelegramBot/src/main/resources/images/step_8_pic.jpg");
                        sendTextMessageAsync(TelegramBotContent.STEP_8_TEXT, Map.of(
                                "Выйти во двор", "exit_yard"
                        ));
                    }
                    break;
                case "exit_yard":
                    if (userGlory >= 260) {
                        sendImageMessageAsync("/Users/sergeysterlikov/Developer/IdeaProjects/TelegramBot/src/main/resources/images/final_pic.jpg");
                        sendTextMessageAsync(TelegramBotContent.FINAL_TEXT, Map.of(
                                "Результат", "final_step"
                        ));

                    }
                    break;
                case "final_step": {
                    sendTextMessageAsync("У вас " + userGlory + " очков славы.");
                }
                break;
            }
        }
    }
}
