import DB.DataBase;
import Telegram.Bot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        final Logger LOGGER = Logger.getLogger(Main.class.getName());
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            Bot bot = new Bot();
            telegramBotsApi.registerBot(bot);
            LOGGER.info("Start bot " + bot.getBotUsername());
        } catch (TelegramApiException e) {
            LOGGER.severe(e.toString());
        }
        DataBase dataBase = new DataBase();
        dataBase.connect();
    }
}