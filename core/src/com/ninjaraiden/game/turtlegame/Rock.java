package com.ninjaraiden.game.turtlegame;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.ninjaraiden.game.framework.BaseActor;

public class Rock extends BaseActor {

    public Rock(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("assets/rock.png");
        setBoundaryPolygon(8);
    }

}
