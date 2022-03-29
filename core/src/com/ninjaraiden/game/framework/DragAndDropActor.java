package com.ninjaraiden.game.framework;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class DragAndDropActor extends BaseActor {

    private final DragAndDropActor self;
    private DropTargetActor dropTarget;
    private float grabOffsetX, grabOffsetY, startPositionX, startPositionY;
    private boolean draggable;

    public DragAndDropActor(final float x, final float y, final Stage s) {
        super(x, y, s);
        self = this;
        draggable = true;
        addListener(new InputListener() {

            @Override
            public boolean touchDown(final InputEvent event, final float offsetX, final float offsetY, final int pointer, final int button) {
                if (!self.isDraggable()) {
                    return false;
                }
                self.grabOffsetX = offsetX;
                self.grabOffsetY = offsetY;
                self.toFront();
                self.startPositionX = self.getX();
                self.startPositionY = self.getY();
                self.addAction(Actions.scaleTo(1.1f, 1.1f, 0.25f));
                self.onDragStart();
                return true;
            }

            @Override
            public void touchDragged(final InputEvent event, final float offsetX, final float offsetY, final int pointer) {
                self.moveBy(offsetX - self.grabOffsetX, offsetY - self.grabOffsetY);
            }

            @Override
            public void touchUp(final InputEvent event, final float offsetX, final float offsetY, final int pointer, final int button) {
                self.setDropTarget(null);
                // Keep track of distance to closest object
                float currentDistance, closestDistance = Float.MAX_VALUE;
                for (BaseActor actor : BaseActor.getList(self.getStage(), "DropTargetActor")) {
                    DropTargetActor target = (DropTargetActor)actor;
                    if (target.isTargetable() && self.overlaps(target)) {
                        currentDistance = Vector2.dst(self.getX(), self.getY(), target.getX(), target.getY());
                        // Check if this target is even closer
                        if (currentDistance < closestDistance) {
                            self.setDropTarget(target);
                            closestDistance = currentDistance;
                        }
                    }
                }
                self.addAction(Actions.scaleTo(1.00f, 1.00f, 0.25f));
                self.onDrop();
            }

        });
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }

    public boolean hasDropTarget() {
        return (dropTarget != null);
    }

    public void setDropTarget(final DropTargetActor dt) {
        dropTarget = dt;
    }

    public DropTargetActor getDropTarget() {
        return dropTarget;
    }

    public void setDraggable(final boolean d) {
        draggable = d;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void moveToActor(final BaseActor other) {
        float x = other.getX() + (other.getWidth() - this.getWidth()) / 2;
        float y = other.getY() + (other.getHeight() - this.getHeight()) / 2;
        addAction(Actions.moveTo(x, y, 0.50f, Interpolation.pow3));
    }

    public void moveToStart() {
        addAction(Actions.moveTo(startPositionX, startPositionY, 0.50f, Interpolation.pow3));
    }

    public void onDragStart() {}
    public void onDrop() {}

}
