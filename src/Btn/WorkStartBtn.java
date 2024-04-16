package Btn;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
//Метод который конструирует кнопки необходимые для навигации по боту вне сделки
public class WorkStartBtn extends BtnBody{

    public WorkStartBtn () {
        super();
        InlineKeyboardButton start = new InlineKeyboardButton("Начать сделку");
        start.setCallbackData("startwork");
        InlineKeyboardButton stat = new InlineKeyboardButton("Статистика");
        stat.setCallbackData("stats");
        List<InlineKeyboardButton> listBtn = new ArrayList<>();
        listBtn.add(start);
        listBtn.add(stat);
        inlineKeyboardMarkup.add(listBtn);
    }
}
