package univ.lab.ninjagame1.client;

import android.util.Log;

import java.util.function.Consumer;

import univ.lab.ninjagame1.dto.DesktopDTO;

public class MockCommunicator implements Communicator {
    @Override
    public void start() {
        Log.d("CMN", "Started!");
    }

    @Override
    public void send(MovementParams movementParams) {
        Log.d("CMN", "Sent!");
    }

    @Override
    public void stopConnection() {
        Log.d("CMN", "Stop!");
    }

    @Override
    public void onReceive(Consumer<DesktopDTO> o) {
        Log.d("CMN", "Received!");
    }
}
