package me.xmertsalov.ui.buttons;

public class SliderButtonFactory extends ButtonFactory {
    @Override
    protected IButton createIButton(int x, int y, int width, int height, int variant) {
        SliderButton button =  new SliderButton(x, y, width, height, variant);
        button.loadImage();
        return button;
    }
}
