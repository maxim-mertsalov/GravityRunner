package me.xmertsalov.exeptions;

import java.awt.*;

public class LobbyException extends RuntimeException {
    public LobbyException(String message) {
        super(message);
    }

    public void draw(Graphics g, int x, int y, int fontSize) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, fontSize));
        g.drawString(getMessage(), x, y);
    }
}
