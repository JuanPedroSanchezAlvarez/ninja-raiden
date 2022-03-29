package com.ninjaraiden.game.framework;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SceneSegment {

    private final Actor actor;
    private final Action action;

    public SceneSegment(final Actor a1, final Action a2) {
        actor = a1;
        action = a2;
    }

    public void start() {
        actor.clearActions();
        actor.addAction(action);
    }

    public boolean isFinished() {
        return (actor.getActions().size == 0);
    }

    public void finish() {
        // Simulate 100000 seconds elapsed time to complete in-progress action
        if (actor.hasActions()) {
            actor.getActions().first().act(100000);
        }
        // Remove any remaining actions
        actor.clearActions();
    }

}
