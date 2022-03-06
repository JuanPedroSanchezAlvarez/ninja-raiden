package com.ninjaraiden.game.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class BaseScreen implements Screen {

    protected Stage mainStage;
    protected Stage uiStage;
    public BaseScreen()
    {
        mainStage = new Stage();
        uiStage = new Stage();
        initialize();
    }
    public abstract void initialize();
    public abstract void update(float dt);
    public void render(float dt)
    {
        uiStage.act(dt);
        mainStage.act(dt);
        update(dt);
        Gdx.gl.glClearColor(0,0,0,1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainStage.draw();
        uiStage.draw();
    }
    // methods required by Screen interface
    public void resize(int width, int height) { }
    public void pause() { }
    public void resume() { }
    public void dispose() { }
    public void show() { }
    public void hide() { }
}
