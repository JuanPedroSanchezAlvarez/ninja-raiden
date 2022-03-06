package com.ninjaraiden.game.turtlegame;

import com.ninjaraiden.game.framework.BaseGame;

public class StarfishGame extends BaseGame {
    @Override
    public void create() {
        setActiveScreen(new MenuScreen());
    }
}
