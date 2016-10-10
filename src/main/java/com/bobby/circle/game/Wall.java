package com.bobby.circle.game;

import com.bobby.circle.SuperCircle;
import com.bobby.circle.utils.Utils;
import lombok.Getter;
import processing.core.PConstants;

@Getter
public class Wall {
    public static final float SECTOR_SIZE = PConstants.TWO_PI/5;
    private static SuperCircle game;
    private int sectorCount;
    private float radius = SuperCircle.MAX_RADIUS;
    private float rotation = 0;
    private float rotateAmount;
    public Wall(int sectorCount) {
        game = SuperCircle.getInstance();
        this.sectorCount = sectorCount;
        rotateAmount = PConstants.TWO_PI/169 * ((game.random(1) > 0.5)?-1:1);
    }

    public void draw() {
        game.pushMatrix();
        radius -= game.getCurrentLevel().getRadiusDec();
        rotation = Utils.bounds(rotation + rotateAmount*game.getCurrentLevel().getCurrSpeed());
        game.noFill();
        game.stroke(Utils.getInverseColor(game.getCurrentLevel().getBackground()));
        game.strokeWeight(10);
        game.ellipseMode(PConstants.CENTER);
        game.ellipse(0, 0, radius, radius);
        game.rotate(rotation);
        game.stroke(game.getCurrentLevel().getBackground());
        game.arc(0, 0, radius, radius, 0, SECTOR_SIZE*sectorCount);
        game.popMatrix();
    }

    boolean checkCollisions(float angle) {
        //Flip the size to get the size of the gap
        double test = Utils.bounds(PConstants.TWO_PI-(SECTOR_SIZE*sectorCount));
        //find the center of the gap
        test = rotation-test/2;
        return Math.abs(test-angle)<(SECTOR_SIZE*sectorCount/2);
    }

}
