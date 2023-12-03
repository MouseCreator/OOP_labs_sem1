package univ.lab.ninjagame1.controller;

import android.content.Context;
import android.hardware.SensorManager;

import univ.lab.ninjagame1.client.AdvancedCommunicator;
import univ.lab.ninjagame1.client.mode.ModeManager;
import univ.lab.ninjagame1.controller.processor.EventProcessor;
import univ.lab.ninjagame1.controller.processor.EventProcessorImpl;
import univ.lab.ninjagame1.event.Event;
import univ.lab.ninjagame1.movement.MovementManager;
import univ.lab.ninjagame1.movement.MovementManagerImpl;

public class GameController {

    private AdvancedCommunicator advancedCommunicator;
    private MovementManager movementManager;
    private InputListener inputListener;
    private Context context;
    private UIManager uiManager;
    private Thread inputThread;
    private GameController() {

    }
    public static GameController create(Context context, UIManager uiManager) {
        GameController gameController = new GameController();
        gameController.context = context;
        gameController.uiManager = uiManager;
        gameController.initialize();
        return gameController;
    }

    private void initialize() {
        SensorManager sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        movementManager = new MovementManagerImpl(sensorManager);
        inputListener = new InputListenerImpl();
        advancedCommunicator = new AdvancedCommunicator();
        ModeManager modeManager = new ModeManager(movementManager, uiManager);
        modeManager.bind(advancedCommunicator);
        EventProcessor eventProcessor = new EventProcessorImpl(modeManager, movementManager);
        inputThread = new Thread(new InputProcessor(inputListener, eventProcessor));
    }

    public InputListener getInputListener() {
        return inputListener;
    }

    public void run() {
        movementManager.begin();
        advancedCommunicator.startConnection();
        inputThread.start();
    }

    public void terminate() {
        advancedCommunicator.stopConnection();
        movementManager.stop();
        inputThread.interrupt();
    }

    public void onPause() {
        movementManager.stop();
    }

    public void onResume() {
        movementManager.begin();
    }

    private static class InputProcessor implements Runnable {
        private final InputListener inputListener;
        private final EventProcessor eventProcessor;
        public InputProcessor(InputListener inputListener, EventProcessor eventProcessor) {
            this.inputListener = inputListener;
            this.eventProcessor = eventProcessor;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                Event event = inputListener.get();
                eventProcessor.process(event);
            }
        }
    }
}
