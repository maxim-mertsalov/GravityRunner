package me.xmertsalov.ui.buttons;

public abstract class ButtonFactory {
    public IButton createButton(int x, int y, int width, int height, int variant) {
        IButton button = createIButton(x, y, width, height, variant);

        return button;
    }

    protected abstract IButton createIButton(int x, int y, int width, int height, int variant);
}
