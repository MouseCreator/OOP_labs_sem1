package univ.lab.ninjagame1.controller.processor;

import java.util.ArrayList;
import java.util.List;

import univ.lab.ninjagame1.client.mode.ModeManager;
import univ.lab.ninjagame1.event.Event;
import univ.lab.ninjagame1.movement.MovementManager;

public class EventProcessorImpl implements EventProcessor {
    private final List<EventHandler> handlerList;
    public EventProcessorImpl(ModeManager modeManager, MovementManager movementManager) {
        handlerList = new ArrayList<>();
        handlerList.add(new FlingEventHandler(modeManager, movementManager));
        handlerList.add(new PauseEventHandler(modeManager));
        handlerList.add(new RecordEventHandler(modeManager));
    }
    @Override
    public void process(Event event) {
        for (EventHandler handler : handlerList) {
            handler.handle(event);
        }
    }
}
