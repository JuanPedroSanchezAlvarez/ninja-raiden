package com.ninjaraiden.game.framework;

import com.badlogic.gdx.scenes.scene2d.Actor;
import java.util.ArrayList;

public class Scene extends Actor {

    private final ArrayList<SceneSegment> segmentList;
    private int index;

    public Scene() {
        super();
        segmentList = new ArrayList<>();
        index = -1;
    }

    public void addSegment(final SceneSegment segment) {
        segmentList.add(segment);
    }

    public void clearSegments() {
        segmentList.clear();
    }

    public void start() {
        index = 0;
        segmentList.get(index).start();
    }

    @Override
    public void act(final float delta) {
        if (isSegmentFinished() && !isLastSegment()) {
            loadNextSegment();
        }
    }

    public boolean isSegmentFinished() {
        return segmentList.get(index).isFinished();
    }

    public boolean isLastSegment() {
        return (index >= segmentList.size() - 1);
    }

    public void loadNextSegment() {
        if (isLastSegment()) {
            return;
        }
        segmentList.get(index).finish();
        index++;
        segmentList.get(index).start();
    }

    public boolean isSceneFinished() {
        return (isLastSegment() && isSegmentFinished());
    }

}
