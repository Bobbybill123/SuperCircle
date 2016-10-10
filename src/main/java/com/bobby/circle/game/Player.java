package com.bobby.circle.game;

import com.bobby.circle.SuperCircle;
import com.bobby.circle.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import processing.core.PConstants;

@Getter
public class Player {
    private static SuperCircle game;
    @Setter
    private float rotateAmt = PConstants.TWO_PI/87;
    private float x = 0;
    private float y = 0;
    private float currAngle = 0;
    public Player() {
        game = SuperCircle.getInstance();
    }

    public void draw() {
        game.pushMatrix();
        //Due to how the triangle is drawn, we actually need to rotate by 90 degrees less
        game.rotate(currAngle-PConstants.HALF_PI);
        game.translate(0, SuperCircle.MIN_RADIUS);
        game.fill(0);
        game.triangle(x,y,x-10,y-20,x+10,y-20);
        game.popMatrix();
    }

    public void checkCollisions() {
        if(game.getCurrentWalls().isEmpty()) return;
        if(game.getCurrentWalls().get(0).checkCollisions(currAngle)) die();
    }

    public void move(boolean left) {
        currAngle = Utils.bounds(currAngle + (rotateAmt * (left?1:-1)));
    }

    private void die() {
        game.setCurrentMenu(game.getDeathMenu());
        game.setMode(SuperCircle.Mode.MENU);
        game.getCurrentWalls().clear();
    }
}
