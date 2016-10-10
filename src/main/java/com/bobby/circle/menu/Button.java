package com.bobby.circle.menu;

import com.bobby.circle.SuperCircle;
import lombok.Getter;
import processing.core.PConstants;
import processing.core.PFont;

@Getter
public class Button {
    private SuperCircle game;
    private String text;
    private Runnable action;
    private float x;
    private float y;
    private float width;
    private float height;
    private PFont font;

    Button(String text, float x, float y, float width, float height) {
        game = SuperCircle.getInstance();
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        font = game.createFont("",height*0.5f);
    }

    void draw(float grey) {
        game.rectMode(PConstants.CENTER);
        game.fill(grey);
        game.rect(x, y, width, height, 10);
        game.noFill();
        game.stroke(255);
        game.rect(x, y, width, height, 10);
        game.fill(255);
        game.textAlign(PConstants.CENTER, PConstants.CENTER);
        game.textFont(font);
        game.text(text, x, y);
    }

    boolean contains(float x, float y) {
        return x > this.x - width/2 && this.x + width/2 > x && y > this.y-  height/2 && this.y + height/2 > y;
    }

    public void checkMouse(float x, float y) {
        if(contains(x,y)) action.run();
    }

    public void setAction(Runnable action) {
        this.action = action;
    }
}
