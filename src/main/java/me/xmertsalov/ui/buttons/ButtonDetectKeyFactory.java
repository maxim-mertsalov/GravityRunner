package me.xmertsalov.ui.buttons;

public class ButtonDetectKeyFactory extends ButtonFactory {
    @Override
    protected IButton createIButton(int x, int y, int width, int height, int variant) {
        ButtonDetectedKey button =  new ButtonDetectedKey(x, y, width, height, variant);
        button.loadImage();
        return button;
    }
}
