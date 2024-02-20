package univ.lab.ninjagame1.event;

public class FlingEvent implements Event{
    @Override
    public EventType getType() {
        return EventType.FLING;
    }

    public FlingEvent(double velocityX, double velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    private final double velocityX;
    private final double velocityY;

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

}
