package me.xmertsalov.ui.buttons;

import me.xmertsalov.Game;

public abstract class ButtonFactory {
    public IButton createButton(int x, int y, int width, int height, int variant, Game game) {

        return createIButton(x, y, width, height, variant, game);
    }

    protected abstract IButton createIButton(int x, int y, int width, int height, int variant, Game game);
}
