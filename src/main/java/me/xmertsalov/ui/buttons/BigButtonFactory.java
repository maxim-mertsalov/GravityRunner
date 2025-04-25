package me.xmertsalov.ui.buttons;

public class BigButtonFactory extends ButtonFactory {
    @Override
    protected IButton createIButton(int x, int y, int width, int height, int variant) {
        BigButton button =  new BigButton(x, y, width, height, variant);
        button.loadImage();
        return button;
    }
}
