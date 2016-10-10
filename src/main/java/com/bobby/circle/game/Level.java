package com.bobby.circle.game;

import com.bobby.circle.SuperCircle;
import lombok.Getter;

/**
 * Created by jacob on 9/10/2016.
 */
@Getter
public class Level {
    private SuperCircle game;
    public float currSpeed;
    private float increaseRate;
    private int background;
    private float radiusDec;
    public Level(int background, float baseSpeed, float increaseRate, float radiusDec) {
        game = SuperCircle.getInstance();
        this.background = background;
        this.currSpeed = baseSpeed;
        this.increaseRate = increaseRate;
        this.radiusDec = radiusDec;
        game.getPlayer().setRotateAmt(currSpeed/25f);
    }
}