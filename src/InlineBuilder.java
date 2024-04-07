import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineBuilder {
    private ArrayList<InlineKeyboardButton> btnList = new ArrayList<>();
    public InlineBuilder (String [] texts, String [] callbacks) {
        this.btnList = buttonArrayBuilder(texts, callbacks);
    }
    public ArrayList<InlineKeyboardButton> getBtnList() {
        return btnList;
    }
    private ArrayList<InlineKeyboardButton> buttonArrayBuilder (String [] texts, String [] callbacks) {
        ArrayList<InlineKeyboardButton> btnArray = new ArrayList<>();
        for (int i = 0; i<texts.length; i++) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(texts[i]);
            inlineKeyboardButton.setCallbackData(callbacks[i]);
            btnArray.add(inlineKeyboardButton);
        }
        return btnArray;
    }
    public InlineKeyboardMarkup createInlineKeyboard (ArrayList<InlineKeyboardButton> btn, int size) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (int i = 0; i<btn.size(); ) {
            List<InlineKeyboardButton> keyRow = new ArrayList<>();
            for (int j = 0; j<size; j++, i++) {
                if (i>= btn.size()) {break;}
                keyRow.add(btn.get(i));
            }
            keyboard.add(keyRow);
        }
        return new InlineKeyboardMarkup(keyboard);
    }

}
