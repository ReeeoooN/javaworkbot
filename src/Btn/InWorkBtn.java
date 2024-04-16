package Btn;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
//Метод создающий кнопки, которые используются для навигации во время сделки

public class InWorkBtn extends BtnBody{
    public InWorkBtn () {
        super();
        InlineKeyboardButton oneChat = new InlineKeyboardButton("1 чат");
        oneChat.setCallbackData("1chat");
        InlineKeyboardButton twoChat = new InlineKeyboardButton("2 чата");
        twoChat.setCallbackData("2chats");
        InlineKeyboardButton threeChat = new InlineKeyboardButton("3 чата");
        threeChat.setCallbackData("3chats");
        InlineKeyboardButton endWork = new InlineKeyboardButton("Завершить сделку");
        endWork.setCallbackData("endwork");
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(oneChat); firstRow.add(twoChat); firstRow.add(threeChat);
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(endWork);
        inlineKeyboardMarkup.add(firstRow); inlineKeyboardMarkup.add(secondRow);
    }
}
