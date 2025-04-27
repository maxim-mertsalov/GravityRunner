package me.xmertsalov.ui.buttons;

import me.xmertsalov.Game;

/**
 * The {@code ButtonFactory} class provides a blueprint for creating button instances.
 * It uses the Factory Method design pattern to allow subclasses to define the specific
 * type of button to create.
 */
public abstract class ButtonFactory {

    /**
     * Creates a button instance with the specified parameters.
     *
     * @param x       the x-coordinate of the button's position.
     * @param y       the y-coordinate of the button's position.
     * @param width   the width of the button.
     * @param height  the height of the button.
     * @param variant an integer representing the variant of the button.
     * @param game    the {@link Game} instance associated with the button.
     * @return an {@link IButton} instance representing the created button.
     */
    public IButton createButton(int x, int y, int width, int height, int variant, Game game) {
        return createIButton(x, y, width, height, variant, game);
    }

    /**
     * Abstract method to be implemented by subclasses to create a specific type of button.
     *
     * @param x       the x-coordinate of the button's position.
     * @param y       the y-coordinate of the button's position.
     * @param width   the width of the button.
     * @param height  the height of the button.
     * @param variant an integer representing the variant of the button.
     * @param game    the {@link Game} instance associated with the button.
     * @return an {@link IButton} instance representing the created button.
     */
    protected abstract IButton createIButton(int x, int y, int width, int height, int variant, Game game);
}
