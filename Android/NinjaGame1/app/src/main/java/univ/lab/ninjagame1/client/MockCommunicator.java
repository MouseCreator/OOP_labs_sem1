package univ.lab.ninjagame1.client;

import android.util.Log;

import java.util.List;
import java.util.function.Consumer;

import univ.lab.ninjagame1.dto.DesktopDTO;
import univ.lab.ninjagame1.dto.MobileDTO;
import univ.lab.ninjagame1.filtered.Vector3;

public class MockCommunicator implements Communicator {
    @Override
    public void start() {
        Log.d("CMN", "Started!");
    }

    public void send(MobileDTO mobileDTO) {
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
