package me.xmertsalov.ui.buttons;

import me.xmertsalov.Game;

public class ButtonDetectKeyFactory extends ButtonFactory {
    @Override
    protected IButton createIButton(int x, int y, int width, int height, int variant, Game game) {
        ButtonDetectedKey button =  new ButtonDetectedKey(x, y, width, height, variant, game);
        button.loadImage();
        return button;
    }
}
