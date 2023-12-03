package univ.lab.ninjagame1.controller.processor;

import univ.lab.ninjagame1.client.Communicator;
import univ.lab.ninjagame1.client.MovementParams;
import univ.lab.ninjagame1.event.Event;
import univ.lab.ninjagame1.event.EventType;
import univ.lab.ninjagame1.event.FlingEvent;
import univ.lab.ninjagame1.filtered.Vector3;
import univ.lab.ninjagame1.movement.MovementManager;

public class FlingEventHandler extends AbstractEventHandler<FlingEvent> {
    private final Communicator communicator;
    private final MovementManager movementManager;

    public FlingEventHandler(Communicator communicator, MovementManager movementManager) {
        this.communicator = communicator;
        this.movementManager = movementManager;
    }

    @Override
    protected void handleEvent(FlingEvent event) {
        int speed = calculateSpeed(event.getVelocityX(), event.getVelocityY());
        Vector3 orientation = movementManager.getCurrentOrientation();
        MovementParams movementParams = new MovementParams(speed, orientation);
        communicator.send(movementParams);
    }

    private int calculateSpeed(double xv, double yv) {
        return (int) Math.sqrt(xv * xv + yv * yv);
    }

    @Override
    protected FlingEvent cast(Event event) {
        return (FlingEvent) event;
    }

    @Override
    public boolean canHandle(Event event) {
        return event.getType()== EventType.FLING;
    }
}
