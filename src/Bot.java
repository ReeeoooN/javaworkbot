import Btn.InWorkBtn;
import Btn.WorkStartBtn;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Bot extends TelegramLongPollingBot {
    private final String botToken = new ApiKey().getKey();
    private final String botName = "WorkStat";
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().equals("/start")) {
            long chatId = update.getMessage().getChatId();
            System.out.println("Message from " + chatId + " text: " + update.getMessage().getText());
            sendMess(chatId, "Привет, работяга, го на сделочку?", new WorkStartBtn().getInlineBtn());
        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            deleteMess(chatId, update.getCallbackQuery().getMessage().getMessageId());
            if (data.equals("startwork")) {
                sendMess(chatId, "Чатов в работе: ", new InWorkBtn().getInlineBtn());
            }
            if (data.equals("endwork")) {
                sendMess(chatId, "Молодец, отработал, заработано ", new WorkStartBtn().getInlineBtn());
            }
            if (data.equals("1chat") || data.equals("2chats") || data.equals("3chats")) {
                sendMess(chatId, "Чатов в работе: ", new InWorkBtn().getInlineBtn());
            }
            if (data.equals("stats")) {
                sendMess(chatId, "Статистика", new WorkStartBtn().getInlineBtn());
            }

        }
    }
    public void  sendMess (long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void sendMess (long chatId, String text, InlineKeyboardMarkup markup) {
        System.out.println("Send mess to " + chatId + " text: " + text);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setReplyMarkup(markup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void deleteMess (long chatId, int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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
