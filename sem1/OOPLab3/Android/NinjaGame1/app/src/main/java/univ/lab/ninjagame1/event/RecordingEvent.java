package univ.lab.ninjagame1.event;

public class RecordingEvent implements Event{
    @Override
    public EventType getType() {
        return EventType.RECORDING;
    }
}
