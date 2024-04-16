package Btn;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
/*Базовый класс родитель для всех Inline кнопок бота, в котором реализован метод возврата кнопок, который используется классами наследниками*/
public class BtnBody {
    protected List<List<InlineKeyboardButton>> inlineKeyboardMarkup;

    public BtnBody () {;
        inlineKeyboardMarkup = new ArrayList<>();
    }
    public InlineKeyboardMarkup getInlineBtn() {
        return  new InlineKeyboardMarkup(inlineKeyboardMarkup);
    }
}
