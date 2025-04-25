package me.xmertsalov.ui.buttons;

public class ArrowsButtonFactory extends ButtonFactory {
    @Override
    protected IButton createIButton(int x, int y, int width, int height, int variant) {
        ArrowsButton button = new ArrowsButton(x, y, width, height, variant);
        button.loadImage();
        return button;
    }
}
