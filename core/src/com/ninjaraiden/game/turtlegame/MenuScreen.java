package com.ninjaraiden.game.turtlegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.ninjaraiden.game.framework.BaseActor;
import com.ninjaraiden.game.framework.BaseScreen;

public class MenuScreen extends BaseScreen {

    @Override
    public void initialize() {
        BaseActor ocean = new BaseActor(0,0, mainStage);
        ocean.loadTexture( "assets/water.jpg" );
        ocean.setSize(800,600);
        BaseActor title = new BaseActor(0,0, mainStage);
        title.loadTexture( "assets/starfish-collector.png" );
        title.centerAtPosition(400,300);
        title.moveBy(0,100);
        BaseActor start = new BaseActor(0,0, mainStage);
        start.loadTexture( "assets/message-start.png" );
        start.centerAtPosition(400,300);
        start.moveBy(0,-100);
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            StarfishGame.setActiveScreen( new LevelScreen() );
    }

}
