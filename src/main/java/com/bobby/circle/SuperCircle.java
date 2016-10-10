package com.bobby.circle;

import com.bobby.circle.game.Level;
import com.bobby.circle.game.Player;
import com.bobby.circle.game.Wall;
import com.bobby.circle.menu.Button;
import com.bobby.circle.menu.Menu;
import com.bobby.circle.utils.Utils;
import com.sanjay900.ProcessingRunner;
import lombok.Getter;
import lombok.Setter;
import processing.core.PApplet;
import processing.core.PVector;
import processing.opengl.PGraphicsOpenGL;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class SuperCircle extends PApplet {
    public static void main(String[] args) {
        ProcessingRunner.run(new SuperCircle());
    }

    @Getter
    private static SuperCircle instance;
    public static final int MIN_RADIUS = 50;
    public static float MAX_RADIUS;
    private static final int WIN_TIME = 10;
    public static PVector mid;
    @Setter
    private Mode mode = Mode.MENU;
    private Player player;
    private Menu pauseMenu;
    private Menu mainMenu;
    private Menu deathMenu;
    private Menu winMenu;
    @Setter
    private Menu currentMenu;
    private long startTime;
    private float time;
    private List<Wall> currentWalls = new ArrayList<Wall>();
    private List<Level> levels = new ArrayList<Level>();
    private Level currentLevel;
    private int levelIndex = 0;

    public void settings() {
        size(800,600,P2D);
        fullScreen();
    }

    public void setup() {
        ((PGraphicsOpenGL)g).textureSampling(3);
        instance = this;
        MAX_RADIUS = max(width, height);
        mid = new PVector(width/2, height/2);
        player = new Player();
        levels.add(currentLevel = new Level(Utils.generateRandomColor(Color.WHITE).getRGB(), 2, 5/360, MAX_RADIUS/100));
        levels.add(new Level(Utils.generateRandomColor(Color.WHITE).getRGB(), 3, 10/360, MAX_RADIUS/50));
        pauseMenu = new Menu("Paused", "Continue", "Exit");
        mainMenu = new Menu("Super Circle", "Start", "Exit");
        deathMenu = new Menu("You Died!", "Retry", "Main Menu");
        winMenu = new Menu("You Win!", "Play Again", "Menu");
        Runnable play = new Runnable() {
            @Override
            public void run() {
                clear();
                mode = Mode.PLAYING;
                addWall();
                startTime = System.currentTimeMillis();
            }
        };
        Runnable main = new Runnable() {
            @Override
            public void run() {
                clear();
                currentMenu = mainMenu;
            }
        };
        mainMenu.getButtons()[0].setAction(play);
        mainMenu.getButtons()[1].setAction(new Runnable() {
            @Override
            public void run() {
                exit();
            }
        });
        pauseMenu.getButtons()[0].setAction(new Runnable() {
            @Override
            public void run() {
                clear();
                mode = Mode.PLAYING;
            }
        });
        pauseMenu.getButtons()[1].setAction(main);
        deathMenu.getButtons()[0].setAction(play);
        deathMenu.getButtons()[1].setAction(main);
        winMenu.getButtons()[0].setAction(play);
        winMenu.getButtons()[1].setAction(main);
        currentMenu = mainMenu;
    }

    public void draw() {
        strokeWeight(5);
        if(mode == Mode.MENU) {
            background(Color.PINK.getRGB());
            currentMenu.draw(mouseX, mouseY);
        } else if (mode == Mode.PLAYING) {
            background(currentLevel.getBackground());
            if(keyPressed) {
                if(keyCode == LEFT || key == 'a') player.move(true);
                else if (keyCode == RIGHT || key == 'd') player.move(false);
            } else if(mousePressed) {
                if(mouseButton == LEFT) player.move(true);
                else if (mouseButton == RIGHT) player.move(false);
            }
            time = (System.currentTimeMillis() - startTime)/1000f;
            if(time >= WIN_TIME) {
                if((levels.size() == levels.indexOf(currentLevel) + 1)) {
                    currentLevel = levels.get(0);
                    mode = Mode.MENU;
                    currentMenu = winMenu;
                    currentWalls.clear();
                    return;
                }
                changeLevel();
                return;
            }
            text(time, width/2, textAscent());
            currentLevel.currSpeed += currentLevel.getIncreaseRate();
            translate(mid.x, mid.y);
            if(!currentWalls.isEmpty() && currentWalls.get(currentWalls.size() - 1).getRadius() <= MAX_RADIUS - MAX_RADIUS/2)
                currentWalls.add(new Wall(((int) random(1, 5))));
            fill(Utils.getInverseColor(currentLevel.getBackground()));
            //drawing the centre circle
            ellipse(0, 0, (MIN_RADIUS - 20)*2, (MIN_RADIUS - 20)*2);
            player.draw();
            for (int i = 0; i < currentWalls.size(); i++) {
                Wall currentWall = currentWalls.get(i);
                if(currentWall.getRadius() <= MIN_RADIUS + currentLevel.getRadiusDec()*3 && currentWall.getRadius() >= MIN_RADIUS)
                    player.checkCollisions();
                if((currentWall.getRadius() - currentLevel.getRadiusDec()) <= MIN_RADIUS) {
                    currentWalls.remove(currentWall);
                    continue;
                }
                currentWall.draw();
            }
        }

    }

    public void keyPressed() {
        if(key == ESC) {
            key = 0;
            if(mode == Mode.MENU) {
                if(currentMenu == pauseMenu) currentMenu = mainMenu;
                else if (currentMenu == mainMenu) exit();
            }
            else if(mode == Mode.PLAYING) {
                mode = Mode.MENU;
                currentMenu = pauseMenu;
            }
        }
    }

    public void mousePressed() {
        if(mode == Mode.MENU && mouseButton == LEFT) {
            for (Button button : currentMenu.getButtons()) {
                button.checkMouse(mouseX, mouseY);
            }
        }
    }

    public void changeLevel() {
        if(levelIndex + 1 == levels.size()) currentLevel = levels.get(++levelIndex);
        clear();
    }

    private void addWall() {
        currentWalls.add(new Wall(((int) random(2, 5))));
    }

    public enum Mode {
        PLAYING,MENU
    }
}