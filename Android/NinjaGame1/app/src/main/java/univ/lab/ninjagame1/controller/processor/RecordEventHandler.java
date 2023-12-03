package univ.lab.ninjagame1.controller.processor;

import univ.lab.ninjagame1.client.mode.ModeManager;
import univ.lab.ninjagame1.event.Event;
import univ.lab.ninjagame1.event.EventType;
import univ.lab.ninjagame1.event.RecordingEvent;

public class RecordEventHandler extends AbstractEventHandler<RecordingEvent> {

    private final ModeManager modeManager;

    public RecordEventHandler(ModeManager modeManager) {
        this.modeManager = modeManager;
    }

    @Override
    protected void handleEvent(RecordingEvent event) {
        modeManager.onRecordEvent();
    }

    @Override
    protected RecordingEvent cast(Event event) {
        return (RecordingEvent) event;
    }

    @Override
    public boolean canHandle(Event event) {
        return event.getType()== EventType.RECORDING;
    }
}
