package me.xmertsalov.ui.buttons;

public class SmallButtonFactory extends ButtonFactory {
    @Override
    protected IButton createIButton(int x, int y, int width, int height, int variant) {
        SmallButton button = new SmallButton(x, y, width, height, variant);
        button.loadImage();
        return button;
    }
}
