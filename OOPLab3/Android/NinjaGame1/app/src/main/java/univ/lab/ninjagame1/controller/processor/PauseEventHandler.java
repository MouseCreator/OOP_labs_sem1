package univ.lab.ninjagame1.controller.processor;

import univ.lab.ninjagame1.client.mode.ModeManager;
import univ.lab.ninjagame1.event.Event;
import univ.lab.ninjagame1.event.EventType;
import univ.lab.ninjagame1.event.PauseEvent;

public class PauseEventHandler extends AbstractEventHandler<PauseEvent> {
    public PauseEventHandler(ModeManager modeManager) {
        this.modeManager = modeManager;
    }

    private final ModeManager modeManager;
    @Override
    protected void handleEvent(PauseEvent event) {
        modeManager.onPauseEvent();
    }

    @Override
    protected PauseEvent cast(Event event) {
        return (PauseEvent) event;
    }

    @Override
    public boolean canHandle(Event event) {
        return event.getType()== EventType.PAUSE_GAME;
    }
}
