package univ.lab.ninjagame1.controller;

import android.content.Context;
import android.hardware.SensorManager;

import univ.lab.ninjagame1.client.Communicator;
import univ.lab.ninjagame1.client.SocketCommunicator;
import univ.lab.ninjagame1.client.mode.ModeManager;
import univ.lab.ninjagame1.controller.processor.EventProcessor;
import univ.lab.ninjagame1.event.Event;
import univ.lab.ninjagame1.movement.MovementManager;
import univ.lab.ninjagame1.movement.MovementManagerImpl;

public class GameController {

    private Communicator communicator;

    private ModeManager modeManager;
    private MovementManager movementManager;
    private InputListener inputListener;
    private Context context;
    private UIManager uiManager;
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
        communicator = new SocketCommunicator();
    }

    public InputListener getInputListener() {
        return inputListener;
    }

    public void run() {
        movementManager.begin();
        communicator.start();
    }

    public void onPause() {
        movementManager.stop();
    }

    public void onResume() {
        movementManager.begin();
    }

    private static class InputProcessor implements Runnable {
        private InputListener inputListener;
        private EventProcessor eventProcessor;
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                Event event = inputListener.get();
                eventProcessor.process(event);
            }
        }
    }
}
