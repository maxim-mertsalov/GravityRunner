package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
import me.xmertsalov.components.PhisicsControler;
import me.xmertsalov.entities.Player;
import me.xmertsalov.gameWorld.LevelsManager;
import me.xmertsalov.scenes.IScene;
import me.xmertsalov.scenes.Scene;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PlayingScene extends Scene implements IScene {

    // Game objects
    private Player player;
    private LevelsManager levelsManager;
    private PhisicsControler phisicsControler;


    public PlayingScene(Game game) {
        super(game);

        start();
    }

    private void start() {
        player = new Player( Game.TILES_SIZE * 12, Game.TILES_SIZE * 5, KeyEvent.VK_SPACE);

        levelsManager = new LevelsManager(this.getGame());
        phisicsControler = new PhisicsControler(this.getGame());
    }

    @Override
    public void update() {
        player.update();
        levelsManager.update();
        phisicsControler.update();
    }

    @Override
    public void draw(Graphics g) {
        player.render(g);
        levelsManager.render(g);
        phisicsControler.render(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == this.getPlayer().getChangeGravityKey()){
            this.getPlayer().changeGravity();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public LevelsManager getLevelsManager() {
        return levelsManager;
    }
}
