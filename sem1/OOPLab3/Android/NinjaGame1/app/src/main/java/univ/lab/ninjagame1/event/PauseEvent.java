package univ.lab.ninjagame1.event;

public class PauseEvent implements Event{
    @Override
    public EventType getType() {
        return EventType.PAUSE_GAME;
    }
}
