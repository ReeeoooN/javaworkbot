package Telegram;

import Btn.InWorkBtn;
import Btn.WorkStartBtn;
import DB.DataBase;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Logger;

public class Bot extends TelegramLongPollingBot {
    private static final Logger LOGGER = Logger.getLogger(Bot.class.getName());
    private final String botToken = new ApiKey().getKey();
    private final String botName = "WorkStat";
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().equals("/start")) {
            long chatId = update.getMessage().getChatId();
            LOGGER.info("Message (ID" + update.getMessage().getMessageId() + ") from @" + update.getMessage().getFrom().getUserName() + " (" + chatId + ")" + " text: " + update.getMessage().getText());
            sendMess(chatId, "Привет, работяга, го на сделочку?", new WorkStartBtn().getInlineBtn());
        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            LOGGER.info("Callback (ID" + update.getCallbackQuery().getMessage().getMessageId() + ")  from @" + update.getCallbackQuery().getFrom().getUserName() + " (" + chatId + ")" + " data: " + data);
            deleteMess(chatId, update.getCallbackQuery().getMessage().getMessageId());
            DataBase dataBase = new DataBase();
            if (data.equals("startwork")) {
                if (dataBase.newWork(chatId)) {
                    sendMess(chatId, "Обработано чатов: 0 (0 руб.)", new InWorkBtn().getInlineBtn());
                } else {
                    sendMess(chatId, "Произошла ошибка, попробуй позже");
                }
            }
            if (data.equals("endwork")) {
                sendMess(chatId, "Молодец, отработал, заработано ", new WorkStartBtn().getInlineBtn());
            }
            if (data.equals("1chat") || data.equals("2chats") || data.equals("3chats")) {

                sendMess(chatId, dataBase.addChat(Character.getNumericValue(data.toCharArray()[0])), new InWorkBtn().getInlineBtn());
            }
            if (data.equals("stats")) {
                sendMess(chatId, dataBase.getStat(chatId), new WorkStartBtn().getInlineBtn());
            }

        }
    }
    public void  sendMess (long chatId, String text) {
        LOGGER.info("Send mess to " + chatId);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            LOGGER.severe(e.toString());
        }
    }
    public void sendMess (long chatId, String text, InlineKeyboardMarkup markup) {
        LOGGER.info("Send mess to " + chatId + " with keyboard");
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setReplyMarkup(markup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            LOGGER.severe(e.toString());
        }
    }
    public void deleteMess (long chatId, int messageId) {
        LOGGER.info("Delete message " + messageId + " from " + chatId);
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            LOGGER.severe(e.toString());
        }
    }
    @Override
    public String getBotUsername() {
        return this.botName;
    }
    public String getBotToken() {
        return this.botToken;
    }


}
